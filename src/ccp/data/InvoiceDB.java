package ccp.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ccp.business.Invoice;
import ccp.business.LineItem;
import ccp.business.User;

public class InvoiceDB {

    public static void insert(Invoice invoice) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        //This method adds a record to the Invoices table.
        //To insert the exact invoice date, the SQL NOW() function is used.
        String query = "INSERT INTO Invoice (UserID, InvoiceDate, TotalAmount, IsProcessed) "
                + "VALUES (?, NOW(), ?, 'n')";
        try {
            ps = connection.prepareStatement(query);
            ps.setLong(1, invoice.getUser().getUserId());
            ps.setDouble(2, invoice.getInvoiceTotal());
            ps.executeUpdate();

            //Get the InvoiceID from the last INSERT statement.
            String identityQuery = "SELECT @@IDENTITY AS IDENTITY";
            Statement identityStatement = connection.createStatement();
            ResultSet identityResultSet = identityStatement.executeQuery(identityQuery);
            identityResultSet.next();
            long invoiceID = identityResultSet.getLong("IDENTITY");
            identityResultSet.close();
            identityStatement.close();

            //Write line items to the LineItem table.
            List<LineItem> lineItems = invoice.getLineItems();
            for (LineItem item : lineItems) {
                LineItemDB.insert(invoiceID, item);
            }
        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }

    // This method sets the Invoice.IsProcessed column to 'y'
    public static void update(Invoice invoice) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "UPDATE Invoice SET "
                + "IsProcessed = 'y' "
                + "WHERE InvoiceID = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setLong(1, invoice.getInvoiceNumber());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }
    
    public static ArrayList<Invoice> selectUnprocessedInvoices() {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

       
        String query = "SELECT * "
                + "FROM User "
                + "INNER JOIN Invoice "
                + "ON User.UserID = Invoice.UserID "
                + "WHERE Invoice.IsProcessed = 'n' "
                + "ORDER BY InvoiceDate";
        try {
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            ArrayList<Invoice> unprocessedInvoices = new ArrayList<>();
            while (rs.next()) {
                User user = UserDB.selectUser(rs.getString("Email"));

                long invoiceID = rs.getLong("Invoice.InvoiceID");
                List<LineItem> lineItems = LineItemDB.selectLineItems(invoiceID);

                Invoice invoice = new Invoice();
                invoice.setUser(user);
                invoice.setInvoiceDate(rs.getDate("InvoiceDate"));
                invoice.setInvoiceNumber(invoiceID);
                invoice.setLineItems(lineItems);

                unprocessedInvoices.add(invoice);
            }
            return unprocessedInvoices;
        } catch (SQLException e) {
            System.err.println(e);
            return null;
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }
}