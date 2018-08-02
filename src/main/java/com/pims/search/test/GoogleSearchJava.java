package com.pims.search.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.commons.codec.binary.Base64;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.pims.dto.GoogleResultDTO;

public class GoogleSearchJava {
	static int resultSize;

	public static final String GOOGLE_SEARCH_URL = "https://www.google.com/search";

	public static void main(String[] args) throws IOException {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Please enter the search term.");
		String searchTerm = scanner.nextLine();
		System.out.println("Please enter the number of results. Example: 5 10 20");
		int num = scanner.nextInt();
		scanner.close();
		String info[] = { BaseJSoupConstant.CSS_QUERY_FOR_URL.getValue(), BaseJSoupConstant.CSS_QUERY_IMAGE.getValue(),
				BaseJSoupConstant.CSS_PRICE_QUERY.getValue() };
		String searchURL = GOOGLE_SEARCH_URL + "?q=" + searchTerm + "&num=" + num;
		List<GoogleResultDTO> searchResultUrl = null;
		List<GoogleResultDTO> finalsearchResult = new ArrayList<GoogleResultDTO>();
		List<GoogleResultDTO> searchResultImage = null;
		List<GoogleResultDTO> searchResultQuery = null;
		for (String action : info) {
			if (action.equals(BaseJSoupConstant.CSS_QUERY_FOR_URL.getValue())) {
				searchResultUrl = jsoupConvertor(action, searchURL);
			} else if (action.equals(BaseJSoupConstant.CSS_QUERY_IMAGE.getValue())) {
				searchResultImage = jsoupConvertor(action, searchURL);
			} else if (action.equals(BaseJSoupConstant.CSS_PRICE_QUERY.getValue())) {
				searchResultQuery = jsoupConvertor(action, searchURL);
			}
		}
		for (int i = 0; i < num; i++) {
			GoogleResultDTO googleResultDTO = new GoogleResultDTO();
			if (checkNull(searchResultUrl, i)) {
				googleResultDTO.setDescription(searchResultUrl.get(i).getLinkDescription());
				googleResultDTO.setItemURL(searchResultUrl.get(i).getItemURL());
			} else {
				googleResultDTO.setDescription("N/A");
			}

			if (checkNull(searchResultQuery, i)) {
				googleResultDTO.setItemPrice(searchResultQuery.get(i).getItemPrice());
			} else {
				googleResultDTO.setItemPrice("N/A");
			}

			if (checkNull(searchResultImage, i)) {
				googleResultDTO.setItemImage(searchResultImage.get(i).getItemImage());
			} else {
				googleResultDTO.setItemImage("N/A");
			}

			finalsearchResult.add(googleResultDTO);
		}

		for (GoogleResultDTO googleResultDTO : finalsearchResult) {
			System.out.println("Description--> " + googleResultDTO.getDescription());
			System.out.println("Item Url--> " + googleResultDTO.getItemURL());

			System.out.println("Image URL-->" + googleResultDTO.getItemImage());
			String base64Converted = convertIntoBase64(googleResultDTO.getItemImage());
			System.out.println("base64 converted image--> " + base64Converted);
			System.out.println("Price--> " + googleResultDTO.getItemPrice());
		}
	}

	public static List<GoogleResultDTO> jsoupConvertor(String cssQuery, String searchURL) throws IOException {

		List<GoogleResultDTO> searchResult = new ArrayList<GoogleResultDTO>();
		Document doc = Jsoup.connect(searchURL).userAgent("Mozilla/5.0").get();
		System.out.println("Doc: " + doc);
		Elements results = doc.select(cssQuery);
		System.out.println("Result: " + results);

		if (cssQuery.equals(BaseJSoupConstant.CSS_QUERY_FOR_URL.getValue())) {

			for (Element result : results) {
				GoogleResultDTO googleResultDTO = new GoogleResultDTO();
				String linkHref = result.attr("href");
				String linkText = result.text();

				googleResultDTO.setLinkDescription(linkText);
				googleResultDTO.setItemURL(linkHref.substring(6, linkHref.indexOf("&")));
				searchResult.add(googleResultDTO);
			}
		} else if (cssQuery.equals(BaseJSoupConstant.CSS_QUERY_IMAGE.getValue())) {

			Elements media = doc.select(BaseJSoupConstant.CSS_QUERY_IMAGE.getValue());
			for (Element src : media) {
				GoogleResultDTO googleResultDTO = new GoogleResultDTO();
				print(" * %s: <%s>", src.tagName(), src.attr("abs:src"));
				String img = print(" * %s: <%s>", src.tagName(), src.attr("abs:src"));

				googleResultDTO.setItemImage(img.substring(9, img.length() - 1));
				searchResult.add(googleResultDTO);
			}
		} else if (cssQuery.equals(BaseJSoupConstant.CSS_PRICE_QUERY.getValue())) {
			Elements priceList = doc.select(BaseJSoupConstant.CSS_PRICE_QUERY.getValue()).after("$");
			System.out.println("PriceList--> " + priceList);
			for (Element price : priceList) {
				GoogleResultDTO googleResultDTO = new GoogleResultDTO();
				String prices = price.text();
				if (prices.startsWith("₹") || prices.startsWith("$")) {
					googleResultDTO.setItemPrice(prices);
					searchResult.add(googleResultDTO);
				}
			}
		}

		return searchResult;
	}

	private static boolean checkNull(List<GoogleResultDTO> list, int i) {
		boolean flag = true;
		if (i > list.size() - 1) {
			flag = false;
		}
		return flag;

	}

	enum BaseJSoupConstant {
		CSS_QUERY_FOR_URL("h3.r > a"), CSS_QUERY_IMAGE("[src]"), CSS_PRICE_QUERY("[span]");

		private String value;

		BaseJSoupConstant(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

	}

	public static String convertIntoBase64(String url) {
		byte[] encoded = Base64.encodeBase64(url.getBytes());
		return new String(encoded);

	}

	/**
	 * These are the temporary methods.
	 * 
	 * @param msg
	 * @param args
	 */
	private static String print(String msg, Object... args) {
		return String.format(msg, args);
	}
}
