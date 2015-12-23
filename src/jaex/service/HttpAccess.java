package jaex.service;

import java.util.List;

import org.json.JSONException;

import jaex.dto.JaexPurchase;
import jaex.dto.JaexPurchaseDisplayed;
import jaex.dto.JaexUser;

public interface HttpAccess {
	
	List<JaexUser> getUserList() throws JSONException, Exception;

	List<JaexPurchaseDisplayed> getRecentPurchasesForUser(JaexUser user) throws Exception;

}
