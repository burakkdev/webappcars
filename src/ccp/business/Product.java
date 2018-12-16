package ccp.business;

import java.io.Serializable;
import java.text.NumberFormat;

public class Product implements Serializable {
	private Long productId;
	private String code;
	private String description;
	private double price;
	
	public Product() {
	}

	public Long getId() {
		return productId;
	}

	public void setId(Long productId) {
		this.productId = productId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
	
	public String getCarBrand() {
		String carBrand = description.substring(0, description.indexOf(" - "));
		return carBrand;
	}
	public String getCarName() {
		String carName = description.substring(description.indexOf(" - ") + 3);
		return carName;
	}
	
	public String getPriceCurrencyFormat() {
		NumberFormat currency = NumberFormat.getCurrencyInstance();
		return currency.format(price);
	}
	
	public String getImageURL() {
		String imageURL = "/CcParts/images/" + code + "_cover.jpg";
		return imageURL;
	}
	
	public String getProductType() {
		return "Car Parts";
	}
}
