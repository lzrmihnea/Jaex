package jaex.service;

import java.util.List;

import org.json.JSONException;

import jaex.dto.JaexPurchase;
import jaex.dto.JaexUser;

public interface HttpAccess {
	
	List<JaexUser> getUserList() throws JSONException, Exception;

	List<JaexPurchase> getPurchasesForUser(JaexUser user, int limit) throws Exception;

	List<JaexPurchase> getPurchasesOfProduct(String product) throws Exception;

}
