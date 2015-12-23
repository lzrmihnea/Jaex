package jaex.dto;

import java.text.MessageFormat;

public class JaexUser {

	public static final String FIELD_USERS = "users";
	public static final String FIELD_USER = "user";
	public static final String FIELD_USERNAME = "username";
	public static final String FIELD_EMAIL = "email";
	
	private static final String JAEX_USER_TOSTRING_TEMPLATE = "Class{0} username{1} email{2}";
//	private static final String JAEX_USER_TOSTRING_TEMPLATE1 = "Class%-20s username%-25s email%-20s";

	private String username;
	private String email;
	
	public JaexUser() {
	}

	public JaexUser(String username, String email) {
		this.username = username;
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return MessageFormat.format(JAEX_USER_TOSTRING_TEMPLATE, inBrackets(this.getClass().getName()),
				inBrackets(username), inBrackets(email)).toString();
	}

	private String inBrackets(final String textToPlaceInBrackets) {
		return "[" + textToPlaceInBrackets + "]";
	}

}
