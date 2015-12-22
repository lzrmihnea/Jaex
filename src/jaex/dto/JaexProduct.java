package jaex.dto;

import java.util.Formatter;

public class JaexProduct {

	public static final String FIELD_USERNAME = "productId";

	private static final String JAEX_PRODUCT_TOSTRING_TEMPLATE = "Class%-25s productId%-10s";
	private Formatter formatter;

	private int productId;

	public JaexProduct(int productId) {
		super();
		this.productId = productId;
		this.formatter = new Formatter();
	}

	@Override
	public String toString() {
		return formatter.format(JAEX_PRODUCT_TOSTRING_TEMPLATE, inBrackets(this.getClass().getName()),
				inBrackets(String.valueOf(productId))).toString();
	}

	private String inBrackets(final String textToPlaceInBrackets) {
		return "[" + textToPlaceInBrackets + "]";
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

}
