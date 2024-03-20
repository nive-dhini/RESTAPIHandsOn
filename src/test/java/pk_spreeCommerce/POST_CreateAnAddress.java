package pk_spreeCommerce;

import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class POST_CreateAnAddress {
	@Test
	public void createAdd() throws IOException, ParseException {

		Response response = RestAssured.given()
				.auth()
				.oauth2(UtilityClass.oAuth_Token())     // calling the class
				.contentType(ContentType.JSON)
				.body(UtilityClass.readFile("CreateAnAddress.json"))	// calling the class
				.post("https://demo.spreecommerce.org/api/v2/storefront/account/addresses").then().extract().response();
		response.getBody().prettyPrint();

		int statusCode = response.getStatusCode();
		System.out.println("Status code is =>  " + statusCode);
		Assert.assertEquals(200, statusCode);

		JsonPath jsonPathEvaluator = response.getBody().jsonPath();
		String fname = jsonPathEvaluator.get("data.attributes.firstname").toString();
		System.out.println("First Name is =>  " + fname);
		Assert.assertEquals(fname, "Nivedhini");
		// VErify that Token Type is Bearer or not
		String Lname = jsonPathEvaluator.get("data.attributes.lastname").toString();
		// String ExpTokenType = "Bearer";
		Assert.assertEquals(Lname, "R");

	}
}
