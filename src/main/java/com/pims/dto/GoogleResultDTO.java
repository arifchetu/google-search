/* 
 * ===========================================================================
 * File Name GoogleResultDTO.java
 * 
 * Created on Mar 29, 2017
 *
 * This code contains copyright information which is the proprietary property
 * of ArtigemRS-FI. No part of this code may be reproduced, stored or transmitted
 * in any form without the prior written permission of ArtigemRS-FI.
 *
 * Copyright (C) ArtigemRS-FI. 2017
 * All rights reserved.
 *
 * Modification history:
 * $Log: GoogleResultDTO.java,v $
 * ===========================================================================
 */
package com.pims.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;

/**
 * This class is used to hold the data for the google results
 * 
 * @author gauravk - Chetu
 * @version 1.0 - Mar 29, 2017
 */
public class GoogleResultDTO implements Serializable {

	/** long Short Description */
	private static final long serialVersionUID = 615773001799455274L;
	private String description;
	private char[] image;
	private String itemURL;
	private String linkDescription;
	private BigDecimal price;
	private String itemPrice;
	private String itemImage;
	private String base64ImageUrl;

	/**
	 * @return the itemImage
	 */
	public String getItemImage() {
		return itemImage;
	}

	/**
	 * @param itemImage the itemImage to set
	 */
	public void setItemImage(String itemImage) {
		this.itemImage = itemImage;
	}

	/**
	 * @return the itemPrice
	 */
	public String getItemPrice() {
		return itemPrice;
	}

	/**
	 * @param itemPrice the itemPrice to set
	 */
	public void setItemPrice(String itemPrice) {
		this.itemPrice = itemPrice;
	}

	/**
	 * @return description of String Type
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @return image of char[] Type
	 */
	public char[] getImage() {
		return image;
	}

	/**
	 * @return itemURL of String Type
	 */
	public String getItemURL() {
		return itemURL;
	}

	/**
	 * @return linkDescription of String Type
	 */
	public String getLinkDescription() {
		return linkDescription;
	}

	/**
	 * @return price of BigDecimal Type
	 */
	public BigDecimal getPrice() {
		return price;
	}

	/**
	 * @param String
	 *            type set into description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @param char[]
	 *            type set into image
	 */
	public void setImage(char[] image) {
		this.image = image;
	}

	/**
	 * @param String
	 *            type set into itemURL
	 */
	public void setItemURL(String itemURL) {
		this.itemURL = itemURL;
	}

	/**
	 * @param String
	 *            type set into linkDescription
	 */
	public void setLinkDescription(String linkDescription) {
		this.linkDescription = linkDescription;
	}

	/**
	 * @param BigDecimal
	 *            type set into price
	 */
	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	/**
	 * @return the base64ImageUrl
	 */
	public String getBase64ImageUrl() {
		return base64ImageUrl;
	}

	/**
	 * @param base64ImageUrl the base64ImageUrl to set
	 */
	public void setBase64ImageUrl(String base64ImageUrl) {
		this.base64ImageUrl = base64ImageUrl;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "GoogleResultDTO [description=" + description + ", image=" + Arrays.toString(image) + ", itemURL="
				+ itemURL + ", linkDescription=" + linkDescription + ", price=" + price + "]";
	}

}
