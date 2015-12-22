package jaex.main;

import java.util.List;

import jaex.dto.JaexPurchase;
import jaex.dto.JaexUser;
import jaex.service.HttpAccess;
import jaex.service.impl.HttpAccessImpl;

public class ExampleURLConnXteam {

	public static void main(String[] args) throws Exception {

		HttpAccess http = new HttpAccessImpl();
		List<JaexUser> userList = http.getUserList();
		
		final int limitOfPurchases = 5;
		for (JaexUser user : userList) {
			print(" 5 recent purchases from "+user.getUsername());
			List<JaexPurchase> purchaseList = http.getPurchasesForUser(user, limitOfPurchases);
			print(" ");
		}
		
	}
	
	// TODO remove this
		private static void print(Object textToPrint) {
			System.out.println(textToPrint.toString());
		}

}
