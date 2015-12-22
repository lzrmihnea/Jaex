package jaex.service.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.MessageFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import jaex.dto.JaexProduct;
import jaex.dto.JaexPurchase;
import jaex.dto.JaexUser;
import jaex.service.HttpAccess;

public class HttpAccessImpl implements HttpAccess {

	private static final String METHOD_GET = "GET";

	private static final String XTEAM_URL = "http://74.50.59.155:6000";
	private static final String API_URL_USERS = "/api/users";
	private static final String API_URL_PURCHASE_BY_USER_WITH_LIMIT = "/api/purchases/by_user/{0}?limit={1}";
	private static final String API_URL_PURCHASE_BY_PRODUCT = "/api/purchases/by_product/{0}";
	private static final String API_URL_PRODUCT = "/api/products/{0}";

	@Override
	public List<JaexUser> getUserList() throws JSONException, Exception {
		List<JaexUser> userList = new ArrayList<>();
		JSONArray jsonArray = this.getJSONArrayFromUrl(API_URL_USERS, JaexUser.FIELD_USERS);

		JaexUser jaexUser;
		for (int i = 0; i < jsonArray.length(); i++) {
			jaexUser = getUserFromJSON(jsonArray.getJSONObject(i));
			userList.add(jaexUser);
			print(jaexUser);
		}
		return userList;
	}

	private static JaexUser getUserFromJSON(JSONObject jsonObject) throws JSONException {
		String username = jsonObject.getString(JaexUser.FIELD_USERNAME);
		String email = jsonObject.getString(JaexUser.FIELD_EMAIL);
		return new JaexUser(username, email);
	}

	@Override
	public List<JaexPurchase> getPurchasesForUser(JaexUser user, int limit) throws Exception {
		List<JaexPurchase> purchasesOfUser = new ArrayList<JaexPurchase>();

		// 5 recent purchases of the user
		JSONArray jsonArray = getJSONArrayFromUrl(API_URL_PURCHASE_BY_USER_WITH_LIMIT, JaexPurchase.FIELD_PURCHASES,
				user.getUsername(), String.valueOf(limit));
//		print(jsonArray);

		JaexPurchase purchaseOfUser;
		for (int i = 0; i < jsonArray.length(); i++) {
			purchaseOfUser = getPurchaseFromJSONObject(jsonArray.getJSONObject(i), user);
			purchasesOfUser.add(purchaseOfUser);
			
			print("   Purchase of user "+user.getUsername()+":");
			print("    " + purchaseOfUser);

			// Purchases of product
			// FIXME
			List<JaexPurchase> purchasesOfProduct = getPurchasesOfProduct(
					String.valueOf(purchaseOfUser.getProductId()));
			print("      Other purchases of the same product");
			for (JaexPurchase jaexPurchase : purchasesOfProduct) {
				print("       " + jaexPurchase);
				JaexProduct productPurchased = getProductInfo(String.valueOf(jaexPurchase.getProductId()));
				print("       Product info: " + productPurchased);
			}
			print(" ");
//			JaexProduct productBoughtByUser = getProductInfo(purchaseOfUser.getProductId());

//			print(purchaseOfUser);
		}

		return purchasesOfUser;
	}

	public JaexProduct getProductInfo(String productId) throws Exception {
		JaexProduct jaexProduct = new JaexProduct(Integer.valueOf(productId));

		String url = MessageFormat.format(XTEAM_URL + API_URL_PRODUCT, productId);
		final JSONObject jsonObject = getJSONObjFromUrl(url).getJSONObject(JaexProduct.FIELD_PRODUCT);
		jaexProduct = getProductFromJSONObject(jsonObject);

		return jaexProduct;
	}

	private JaexProduct getProductFromJSONObject(JSONObject jsonObject) throws JSONException {
		int id = getIntFromJSON(jsonObject, JaexProduct.FIELD_ID);
		String face = jsonObject.getString(JaexProduct.FIELD_FACE);
		long price = getLongFromJSON(jsonObject, JaexProduct.FIELD_PRICE);
		int size = getIntFromJSON(jsonObject, JaexProduct.FIELD_SIZE);
		return new JaexProduct(id, face, price, size);
	}

	@Override
	public List<JaexPurchase> getPurchasesOfProduct(String productId) throws Exception {
		List<JaexPurchase> listOfPurchases = new ArrayList<>();

//		String url = MessageFormat.format(XTEAM_URL + API_URL_PRODUCT, );
//		final JSONObject jsonObject = getJSONObjFromUrl(url);
		JSONArray jsonArray = getJSONArrayFromUrl(API_URL_PURCHASE_BY_PRODUCT, JaexPurchase.FIELD_PURCHASES, productId);

		JaexPurchase purchase;
		for (int i = 0; i < jsonArray.length(); i++) {
			purchase = getPurchaseFromJSONObject(jsonArray.getJSONObject(i), null);
			listOfPurchases.add(purchase);
			print("     " + purchase);
		}

		return listOfPurchases;
	}

	private JaexPurchase getPurchaseFromJSONObject(JSONObject jsonObject, JaexUser user)
			throws JSONException, ParseException {
		int id = getIntFromJSON(jsonObject, JaexPurchase.FIELD_ID);
		Date dateOfPurchase = getDateFromJSON(jsonObject, JaexPurchase.FIELD_DATE);
		int productId = getIntFromJSON(jsonObject, JaexPurchase.FIELD_PRODUCT_ID);
		String username = jsonObject.getString(JaexUser.FIELD_USERNAME);
		JaexPurchase jaexPurchase = new JaexPurchase(id, productId, dateOfPurchase);
		JaexUser addedUser = (user != null ? user : new JaexUser(username, null));
		jaexPurchase.setUser(addedUser);
		return jaexPurchase;
	}

	private Date getDateFromJSON(JSONObject jsonObject, final String fieldDate) throws JSONException {
		final String dateAsString = jsonObject.getString(fieldDate);
		final DateTimeFormatter dateTimeFormatter = ISODateTimeFormat.dateTime();
		Date date = dateTimeFormatter.parseDateTime(dateAsString).toDate();
		return date;
	}

	private Integer getIntFromJSON(JSONObject jsonObject, final String fieldId) throws JSONException {
		return Integer.valueOf(jsonObject.getString(fieldId));
	}
	
	private Long getLongFromJSON(JSONObject jsonObject, final String fieldId) throws JSONException {
		return Long.valueOf(jsonObject.getString(fieldId));
	}

	private JSONArray getJSONArrayFromUrl(String apiUrl, String jsonFieldname, Object... urlParams) throws Exception {
		String url = MessageFormat.format(XTEAM_URL + apiUrl, urlParams);
		final JSONObject jsonObject = getJSONObjFromUrl(url);
		return jsonObject.getJSONArray(jsonFieldname);
	}

	private JSONObject getJSONObjFromUrl(String url) throws JSONException, Exception {
		final JSONObject jsonObject = new JSONObject(getFromUrl(url));
		return jsonObject;
	}

	// HTTP GET request
	private String getFromUrl(String urlToAccess) throws Exception {

		HttpURLConnection con = (HttpURLConnection) new URL(urlToAccess).openConnection();
		con.setRequestMethod(METHOD_GET);
		// print("Response Code : " + con.getResponseCode());

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		return response.toString();

	}

	// TODO remove this
	private static void print(Object textToPrint) {
		System.out.println(textToPrint.toString());
	}

}
