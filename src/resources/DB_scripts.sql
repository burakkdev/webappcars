DROP DATABASE IF EXISTS cars;

CREATE DATABASE cars;

Use cars;

create table User (
UserID INT NOT NULL AUTO_INCREMENT,
FirstName VARCHAR(50),
LastName VARCHAR(50),
Email VARCHAR(50),
CompanyName VARCHAR(50),
Address1 VARCHAR(50),
Address2 VARCHAR(50),
City VARCHAR(50),
State VARCHAR(50),
Zip VARCHAR(50),
Country VARCHAR(50),
CreditCardType VARCHAR(50),
CreditCardNumber VARCHAR(50),
CreditCardExpirationDate VARCHAR(50),

PRIMARY KEY (UserID)
);

CREATE TABLE Invoice (
InvoiceID INT NOT NULL AUTO_INCREMENT,
UserID INT NOT NULL,
InvoiceDate DATETIME NOT NULL DEFAULT '0000-00-00 00:00:00',
TotalAmount FLOAT NOT NULL DEFAULT '0',
IsProcessed enum('y','n') DEFAULT NULL,

PRIMARY KEY (InvoiceID),
FOREIGN KEY (UserID) REFERENCES User (UserID)
);

CREATE TABLE LineItem (
LineItemID INT NOT NULL AUTO_INCREMENT,
InvoiceID INT NOT NULL DEFAULT '0',
ProductID INT NOT NULL DEFAULT '0',
Quantity INT NOT NULL DEFAULT '0',

PRIMARY KEY (LineItemID),
FOREIGN KEY (InvoiceID) REFERENCES Invoice (InvoiceID)
);

CREATE TABLE Product(
ProductID INT NOT NULL AUTO_INCREMENT,
ProductCode VARCHAR(10) NOT NULL DEFAULT '',
ProductDescription VARCHAR(100) NOT NULL DEFAULT '',
ProductPrice DECIMAL(7,2) NOT NULL DEFAULT '0.00',

PRIMARY KEY (ProductID)
);


INSERT INTO Product values
('1','me357','MERCEDES - Automatic Transmission Solenoid REP320703', '14.94'),
('2','bu358','BUGATTI - Led Lights Bar Mounting Bracket for SUV Boat', '12.44'),
('3','ch359','CHEVROLET - Trico 25-200 Force Beam Wiper Blade 20', '12.42'),
('4','mu360','MUSTANG - DieHard 26 Beam Wiper Blade', '17.84');


CREATE TABLE Download (
DownloadID INT NOT NULL AUTO_INCREMENT,
UserID INT NOT NULL,
DownloadDate DATETIME NOT NULL,
ProductCode VARCHAR(10) NOT NULL,

PRIMARY KEY (DownloadID),
FOREIGN KEY (UserID) REFERENCES User (UserID)
);
**********************************************SECOND DATABASE SCHEMA****************************
DROP DATABASE IF EXISTS author;

CREATE DATABASE author;

Use author;

CREATE TABLE UserPAss (
Username  varchar(15) NOT NULL PRIMARY KEY,
Password varchar(15) not null
);

insert into userpass values
('burak','burakkor'),
('pelin','pelinkor'),
('juanito','juanjuan');


CREATE TABLE userrole (
UserName  varchar(15) NOT NULL,
Rolename varchar(15) not null,

PRIMARY KEY (Username,Rolename)
);

insert into userrole values 
('burak','service'),
('pelin','programmer'),
('juanito','programmer');












