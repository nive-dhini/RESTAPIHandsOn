package pk_spreeCommerce;

import org.json.simple.JSONObject;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import junit.framework.Assert;

public class POST_CreateAToken {
	@Test
	public void createAToken() {
		
//		oAuth Token is =>  lNvH12-xqKnuK_DUNSBcr13oegNI2JDfs6OdZ0LCyhc

		RestAssured.baseURI = "https://demo.spreecommerce.org";
		RequestSpecification httpRequest = RestAssured.given();

		JSONObject requestParams = new JSONObject();
		requestParams.put("grant_type", "password");
		requestParams.put("username", "nivedhini@spree.com");
		requestParams.put("password", "spree123");

		// Add a header stating the Request body is a JSON
		httpRequest.header("Content-Type", "application/json");
		httpRequest.body(requestParams.toJSONString());
		
		Response response = httpRequest.request(Method.POST, "/spree_oauth/token");
		response.prettyPrint();
		int statusCode = response.getStatusCode();
		Assert.assertEquals(statusCode, 200);

		JsonPath jsonPathEvaluator = response.getBody().jsonPath();
		String outh_token = jsonPathEvaluator.get("access_token").toString();
		System.out.println("oAuth Token is =>  " + outh_token);

		// VErify that Token Type is Bearer or not
//		String ActtokenType = jsonPathEvaluator.get("token_type").toString();
//		String ExpTokenType = "Bearer";
//		Assert.assertEquals(ExpTokenType, ActtokenType);

	}
}
