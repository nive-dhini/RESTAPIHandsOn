package pk_spreeCommerce;

import java.util.Map;

import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class create_MultipleAddress_UsingTestData {
	
	@Test(dataProvider = "Addresses", dataProviderClass = spreeCom_testdata.class, priority = 1, enabled= false)
	public void addAddress(String fName, String lName, String address1,
			String city, String zipcode, String phone, String state, String country) {
		JSONObject newAddress = new JSONObject();
		newAddress.put("firstname", fName);
		newAddress.put("lastname", lName);
		newAddress.put("address1", address1);
		newAddress.put("city", city);
		newAddress.put("zipcode", zipcode);
		newAddress.put("phone", phone);
		newAddress.put("state_name", state);
		newAddress.put("country_iso", country);
		JSONObject body = new JSONObject();
		body.put("address", newAddress);
		Response response = RestAssured.given()
				.auth()
				.oauth2(UtilityClass.oAuth_Token())
				.body(body)
				.contentType(ContentType.JSON)
				.post("https://demo.spreecommerce.org/api/v2/storefront/account/addresses")
				.then()
				.extract()
				.response();
		 int statusCode = response.getStatusCode();
		 Assert.assertEquals(200, statusCode);
		 
		 Map<String, String> default_billing_address = response.jsonPath().getJsonObject("data.attributes");
			String firstName = default_billing_address.get("firstname");
			Assert.assertEquals(firstName, fName);
	}
	
	
	
	@Test(dataProvider = "addressWithLabel", dataProviderClass = spreeCom_testdata.class, priority = 1,enabled=true)
	public void addSameAddressLable(String fName, String lName, String address1, String city, String zipcode,
			String phone, String state, String country, String label) {
		JSONObject newAddress = new JSONObject();
		newAddress.put("firstname", fName);
		newAddress.put("lastname", lName);
		newAddress.put("address1", address1);
		newAddress.put("city", city);
		newAddress.put("zipcode", zipcode);
		newAddress.put("phone", phone);
		newAddress.put("state_name", state);
		newAddress.put("country_iso", country);
		newAddress.put("label", label);
		JSONObject body = new JSONObject();
		body.put("address", newAddress);
		Response response = RestAssured.given()
				.auth()
				.oauth2(UtilityClass.oAuth_Token())
				.body(body)
				.contentType(ContentType.JSON)
				.post("https://demo.spreecommerce.org/api/v2/storefront/account/addresses")
				.then()
				.extract()
				.response();
		int statusCode = response.getStatusCode();
		Assert.assertEquals(200, statusCode);
		// check label Work exist
		JsonPath jsonPathEvaluator = response.getBody().jsonPath();
		String actLabel = jsonPathEvaluator.get("data.attributes.label").toString();
		Assert.assertEquals(actLabel, label);

		// Add same address with same label
		response = RestAssured.given().auth().oauth2(UtilityClass.oAuth_Token()).body(body).contentType(ContentType.JSON)
				.post("https://demo.spreecommerce.org/api/v2/storefront/account/addresses").then().extract().response();
		statusCode = response.getStatusCode();
		Assert.assertEquals(422, statusCode);
		jsonPathEvaluator = response.getBody().jsonPath();
		String generalErr = jsonPathEvaluator.get("error").toString();
		System.out.println(":generalErr: "+generalErr);
		Assert.assertEquals(generalErr, "Address name has already been taken");
		String specificErr = jsonPathEvaluator.get("errors.label").toString();
		Assert.assertEquals(specificErr, "[has already been taken]");
	}
	
}
