package pk_spreeCommerce;

import java.util.Map;
import java.util.Random;

import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class create_MultipleAddress_withUnique_Label {
	
	@Test(dataProvider = "Addresses_Unique_Label", dataProviderClass = spreeCom_testdata.class, priority = 1)
	public void addAddress(String fName, String lName, String address1,
			String city, String zipcode, String phone, String state, String country,String label) {
		
		Random rand = new Random();
		
		int rnumber = rand.nextInt(1000);
		String ULabel = label+rnumber;
		
		JSONObject newAddress = new JSONObject();
		newAddress.put("firstname", fName);
		newAddress.put("lastname", lName);
		newAddress.put("address1", address1);
		newAddress.put("city", city);
		newAddress.put("zipcode", zipcode);
		newAddress.put("phone", phone);
		newAddress.put("state_name", state);
		newAddress.put("country_iso", country);
		newAddress.put("label", ULabel);
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
			
			String label_Act = default_billing_address.get("label");
			Assert.assertEquals(label_Act, ULabel);
	}
	
}
