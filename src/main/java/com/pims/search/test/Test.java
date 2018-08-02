package com.pims.search.test;

import java.util.List;

import org.apache.log4j.Logger;

import com.pims.dto.GoogleResultDTO;
import com.pims.search.GoogleSearch;

public class Test {
	private static final Logger LOGGER = Logger.getLogger(Test.class);

	public static void main(String[] args) {
		LOGGER.info("Inside tmain method..");
		final List<GoogleResultDTO> googleResults = GoogleSearch.scarpeSearch("iphone");
		System.out.println(googleResults);
		LOGGER.info("googleResults....." + googleResults);
		if (googleResults != null && !googleResults.isEmpty()) {
			for (int i = 0; i <= googleResults.size(); i++) {
				System.out.println(googleResults.get(0).getItemURL());
				System.out.println(googleResults.get(0).getDescription());
				System.out.println(googleResults.get(0).getImage());
			}
		}
	}
}
