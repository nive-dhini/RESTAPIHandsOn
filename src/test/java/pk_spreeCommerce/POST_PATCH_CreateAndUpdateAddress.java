package pk_spreeCommerce;

import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class POST_PATCH_CreateAndUpdateAddress {

	String ID = null;

	@Test(priority = 1)
	public void createAnAddress() throws IOException, ParseException {

		Response response = RestAssured.given().auth()
				.oauth2(UtilityClass.oAuth_Token())
				.contentType(ContentType.JSON)
				.body(UtilityClass.readFile("CreateAnAddress.json"))
				.post("https://demo.spreecommerce.org/api/v2/storefront/account/addresses")
				.then().extract().response();
		response.getBody().prettyPrint();

		int statusCode = response.getStatusCode();
		System.out.println("Status code is =>  " + statusCode);
		Assert.assertEquals(200, statusCode);

		Map<String, String> id_create = response.jsonPath().getJsonObject("data");
		ID = id_create.get("id");
	}

	@Test(priority = 2)
	public void Update_Address() throws IOException, ParseException {

		Response response = RestAssured.given().auth()
				.oauth2(UtilityClass.oAuth_Token())
				.contentType(ContentType.JSON)
				.body(UtilityClass.readFile("UpdateAnAddress.json"))
				.patch("https://demo.spreecommerce.org/api/v2/storefront/account/addresses/" + ID)
				.then().extract().response();
		response.getBody().prettyPrint();

		int statusCode = response.getStatusCode();
		System.out.println("Status code is =>  " + statusCode);
		Assert.assertEquals(200, statusCode);

		Map<String, String> default_billing_address = response.jsonPath().getJsonObject("data.attributes");
		String firstName = default_billing_address.get("firstname");
		Assert.assertEquals(firstName, "Nivedhini");

		String lastName = default_billing_address.get("lastname");
		Assert.assertEquals(lastName, "Ramesh");

		String addressOne = default_billing_address.get("address1");
		Assert.assertEquals(addressOne, "Jayanagar, Bangalore");

		String addressTwo = default_billing_address.get("address2");
		Assert.assertEquals(addressTwo, "4th T Block 19th Cross");
	}
}
