package jaex.main;

import java.util.List;

import org.json.JSONArray;

import jaex.dto.JaexPurchaseWithDetails;
import jaex.dto.JaexUser;
import jaex.service.JSONTransformService;
import jaex.service.JaexService;
import jaex.service.impl.JSONTransformServiceImpl;
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
		JSONTransformService jsonTransformer = new JSONTransformServiceImpl();
		
		List<JaexUser> userList = http.getUserList();
		JaexUser user = userList.get(0);
		List<JaexPurchaseWithDetails> recentPurchasesForUser = http.getRecentPurchasesForUser(user);
		
		print(recentPurchasesForUser);
		JSONArray returnedValues = jsonTransformer.getRecentPurchasesJSON(recentPurchasesForUser);
		print(returnedValues);
		
	}
	
		private static void print(Object textToPrint) {
			System.out.println(textToPrint.toString());
		}

}
