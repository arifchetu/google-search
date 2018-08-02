package com.pims.search;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.SynchronousQueue;
import java.util.Set;

import org.apache.log4j.Logger;

import com.pims.dto.GoogleResultDTO;
import com.qprofit.http.scrape.httpscrapelib.ParseHtml;
import com.qprofit.http.scrape.httpscrapelib.ParseHtmlException;

public class GoogleSearch {
	private static final Logger LOGGER = Logger.getLogger(GoogleSearch.class);

	public static List<GoogleResultDTO> scarpeSearch(String str) {
		LOGGER.info("Inside second search...");
		String itemLink = null;
		String price = null;
		String title = null;

		try {
			ParseHtml p4 = null;
			String imageBody = null;
			String imageKey = null;

			final String GOOGLE_SEARCH_URL = "https://www.google.com/search";

			// String urlStr = GOOGLE_SEARCH_URL + "?q="+str;
			// String urlStr =GOOGLE_SEARCH_URL + "?q="+URLEncoder.encode(str,
			// "UTF-8");
			/// String urlStr =
			// "https://www.google.com/search?output=search&tbm=shop&q=" +
			// URLEncoder.encode(str, "UTF-8");
			// String urlStr =
			// "https://www.google.com/search?ei=1XA7W-3SLtb1rQGEg6Eg&q="+
			// URLEncoder.encode(str, "UTF-8");
			// LOGGER.info("urlStr...1" + urlStr);

			final List<GoogleResultDTO> googleResults = new ArrayList<GoogleResultDTO>();
			int debug = 0;
			// final HttpScraper scrape = new HttpScraper();
			// String resStr = scrape.getSource(urlStr);
			// LOGGER.info("resStr...2" + resStr);

			final File file = new File("D:\\shop.html");// temporary code for
			// testing
			final String resStr = readingFileContent(file);// temporary code
			// for testing
			LOGGER.info("searchOutput...2" + resStr);
			final ParseHtml p1 = new ParseHtml(resStr);
			final ParseHtml i1 = new ParseHtml(resStr);
			LOGGER.info("ParseHtml ============>>>>i1");

			if (debug != 0) {
				final PrintWriter writer = new PrintWriter("D:\\ser1.html", "UTF-8");
				writer.print(resStr);
				writer.close();
			}
			LOGGER.info("GOOGLE PAGE START :   " + resStr);
			LOGGER.info("GOOGLE PAGE END");
			final Map<String, String> imgMap = new HashMap<String, String>();
			while (true) {
				LOGGER.info("Inside while loop...3");
				String imgscript = "";
				try {
					imgscript = i1.getTextBetween("function(){var _image_src=",
							"_setImagesSrc(_image_ids,_image_src);");
					LOGGER.info("imgscript...4" + imgscript);
				} catch (ParseHtmlException ex) {
					LOGGER.error("ParseHtmlException..", ex);

					break;
				}
				final ParseHtml pi1 = new ParseHtml(imgscript);
				LOGGER.info("pi1.5..." + pi1);
				final String img = pi1.getTextBetween("'", "';var");
				LOGGER.info("img...6" + img);
				//final String imgId = pi1.getTextBetween("_image_ids=[", "];");
				final String imgId = pi1.getTextBetween("_image_ids=['", "'];");
				LOGGER.info("imgId..7..." + imgId);
				for (String key : imgId.split(",")) {
					LOGGER.info("Inside for loop...8");
					key = key.replace("'", "");
					imgMap.put(key, img);
				}
			}
			while (true) {
				LOGGER.info("Inside while loop...9");
				String itemBody = "";
				try {
					itemBody = p1.getTextBetween("<!--m-->", "<!--n-->");
					LOGGER.info("itemBody...10" + itemBody);
				} catch (ParseHtmlException ex) {
					// break;
					LOGGER.info("exception in itemBody");
					break;
				}
				try {

					final File file1 = new File("D:\\shop.html");// temporary
																	// code for
					// testing
					itemBody = readingFileContent(file1);// temporary code
					LOGGER.info("item body by read file.." + itemBody);
					final ParseHtml p2 = new ParseHtml(itemBody);
					LOGGER.info("item body after read file.." + p2);
					imageBody = p2.getTextBetween("<img src", ">");
					if (imageBody == null) {
						imageBody = p2.getTextBetween("<img ", "</g-img>");
					}
					LOGGER.info("imageBody...11" + imageBody);
				} catch (Exception e) {
					LOGGER.info("exception in imageBody");
				}

				try {
					//final ParseHtml p3 = new ParseHtml(imageBody);
					final ParseHtml p3 = new ParseHtml(itemBody);
				
					//LOGGER.info("p3####################### " +p3);
					imageKey = p3.getTextBetween("id=\"", "\"");
					System.out.println(imageKey);
					//imageKey = p3.getTextBetween("=\"", "\"");
					LOGGER.info("imageKey...12" + imageKey);
				} catch (Exception e) {
					LOGGER.info("exception in imageKey");
				}
				
			
				final String imagesrc = imgMap.get(imageKey);
				LOGGER.info("imagesrc.....13" + imagesrc);
				if (debug != 0) {
					LOGGER.error("match key=%s value=%s\n " + imageKey + imagesrc);
				}
				try {
					p4 = new ParseHtml(itemBody);
					p4.movePostionPast("<a class");
					p4.movePostionPast("<a class");
					title = p4.getTextBetween("spop.c\">", "</a>").replace("<em>", "").replace("</em>", "");
					LOGGER.info("title........14");
				} catch (Exception e) {
					LOGGER.info("exception in title");
				}

				try {
					final ParseHtml priceBody = new ParseHtml(p4.getTextBetween("price", "</span>"));
					LOGGER.info("priceBody.....15" +priceBody);
					price = priceBody.getTextBetween("$", "</b>").replace(",", "");
					LOGGER.info("price......16");
				} catch (Exception e) {
					LOGGER.info("exception in price");
				}

				try {
					final ParseHtml itemUrlParse = new ParseHtml(itemBody);
					LOGGER.info("itemUrlParse......17");
					itemLink = itemUrlParse.getTextBetween("href=\"", "jsaction=\"");
					LOGGER.info("itemLink........18");
				} catch (Exception e) {
					LOGGER.info("exception in itemLink");
				}

				final GoogleResultDTO googleItem = new GoogleResultDTO();
				try {
					if (imagesrc != null) {
						googleItem.setImage(imagesrc.toCharArray());
					}
					if (itemLink != null) {
						LOGGER.info("itemLink.....19");
						googleItem.setItemURL(new StringBuilder("https://www.google.com").append(itemLink).toString()
								.replace("\t", ""));
					}
					if (title != null) {
						LOGGER.info("title......20");
						googleItem.setLinkDescription(title.replace("\t", ""));
						googleItem.setDescription(title.replace("\t", ""));
					}
					googleItem.setPrice(new BigDecimal(price));
					googleResults.add(googleItem);
				} catch (Exception e) {
					LOGGER.info("exception in googleItem");
				}

			}
			System.out.println(googleResults.get(0).getDescription());
			return googleResults;
		} catch (Exception ex) {
			LOGGER.error("Error in parsing the google result...", ex);
		}
		return null;
	}

	/**
	 * This is a temporary method for reading a google response from a text
	 * file.
	 * 
	 * @author gauravk
	 * @date Jan 17, 2017
	 * @return <code>String</code> response string.
	 * @param file
	 *            text file to read for google response.
	 */
	static String readingFileContent(final File file) {
		StringBuilder responseAsString = new StringBuilder();
		try (BufferedReader br = new BufferedReader(new FileReader(file));) {
			String sCurrentLine;
			while ((sCurrentLine = br.readLine()) != null) {
				responseAsString.append(sCurrentLine);
			}
		} catch (IOException e) {
			LOGGER.error("Error in reading file...", e);
		}
		// System.out.println(responseAsString.toString());
		return responseAsString.toString();
	}

}
