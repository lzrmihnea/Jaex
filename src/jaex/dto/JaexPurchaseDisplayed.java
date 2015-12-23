package jaex.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.Formatter;
import java.util.List;

public class JaexPurchaseDisplayed {
	
	public static final String FIELD_PURCHASES = "purchases";
	public static final String FIELD_ID = "id";
	public static final String FIELD_PRODUCT_ID = "productId";
	public static final String FIELD_USERNAME = "username";
	public static final String FIELD_DATE = "date";
	
	private static final String JAEX_PURCHASE_TOSTRING_TEMPLATE1 = "Class%-25s productId%-10s user%-20s date%-10s";
	private Formatter formatter;
	
	private int id;
	private JaexProduct product;
	private JaexUser user;
	private Date date;
	
	private List<JaexUser> peopleWhoAlsoBoughtThis = new ArrayList<>();
	
	public JaexPurchaseDisplayed() {
		formatter = new Formatter();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public JaexProduct getProduct() {
		return product;
	}

	public void setProduct(JaexProduct product) {
		this.product = product;
	}

	public JaexUser getUser() {
		return user;
	}

	public void setUser(JaexUser user) {
		this.user = user;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return formatter.format(JAEX_PURCHASE_TOSTRING_TEMPLATE1, inBrackets(this.getClass().getName()),
				inBrackets(String.valueOf(product)), inBrackets(user.getUsername()), inBrackets(date.toString())).toString();
	}

	private String inBrackets(final String textToPlaceInBrackets) {
		return "[" + textToPlaceInBrackets + "]";
	}

	public List<JaexUser> getPeopleWhoAlsoBoughtThis() {
		return peopleWhoAlsoBoughtThis;
	}

	public void setPeopleWhoAlsoBoughtThis(List<JaexUser> peopleWhoAlsoBoughtThis) {
		this.peopleWhoAlsoBoughtThis = peopleWhoAlsoBoughtThis;
	}

}
