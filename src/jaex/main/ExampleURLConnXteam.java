package jaex.main;

import java.util.List;

import jaex.dto.JaexPurchase;
import jaex.dto.JaexPurchaseWithDetails;
import jaex.dto.JaexUser;
import jaex.service.JaexService;
import jaex.service.impl.JaexServiceImpl;

public class ExampleURLConnXteam {
	
//	TODO
//	x all functionalities in service
//	x put them together for final functionality
//	- build JSON
//	- create API functionality
//	- chaching

	public static void main(String[] args) throws Exception {

		JaexService http = new JaexServiceImpl();
		List<JaexUser> userList = http.getUserList();
		JaexUser user = userList.get(0);
		List<JaexPurchaseWithDetails> recentPurchasesForUser = http.getRecentPurchasesForUser(user);
		
		print(recentPurchasesForUser);
		
	}
	
		private static void print(Object textToPrint) {
			System.out.println(textToPrint.toString());
		}

}
