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

public class POST_DELETE_CreateAndDeleteAddress {

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

	@Test(dependsOnMethods = {"createAnAddress"})
	public void Update_Address() throws IOException, ParseException {

		Response response = RestAssured.given().auth()
				.oauth2(UtilityClass.oAuth_Token())
				.contentType(ContentType.JSON)
				.delete("https://demo.spreecommerce.org/api/v2/storefront/account/addresses/" + ID);

		int statusCode = response.getStatusCode();
		System.out.println("Status code is =>  " + statusCode);
		Assert.assertEquals(301, statusCode);

	}
}
