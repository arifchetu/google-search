/* 
 * ===========================================================================
 * File Name ResultDTO.java
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
 * $Log: ResultDTO.java,v $
 * ===========================================================================
 */
package com.pims.dto;

import java.io.Serializable;
import java.util.List;

/**
 * This class is used to hold the data for the replacement options from various
 * stores
 * 
 * @author gauravk - Chetu
 * @version 1.0 - Mar 29, 2017
 */
public class ResultDTO implements Serializable {

	private static final long serialVersionUID = 1003277189504241006L;
	private List<GoogleResultDTO> googleResults;

	/**
	 * @return the googleResults
	 */
	public List<GoogleResultDTO> getGoogleResults() {
		return googleResults;
	}

	/**
	 * @param googleResults
	 *            the googleResults to set
	 */
	public void setGoogleResults(List<GoogleResultDTO> googleResults) {
		this.googleResults = googleResults;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ResultDTO [googleResults=" + googleResults + "]";
	}
	
}
