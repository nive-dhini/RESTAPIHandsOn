package pk_spreeCommerce;

import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class POST_CreateAToken_and_Address {
	String outh_token = null;

	@BeforeTest
	public void oAuth_Token() {

		RestAssured.baseURI = "https://demo.spreecommerce.org";
		RequestSpecification request = RestAssured.given();

		JSONObject requestParams = new JSONObject();
		requestParams.put("grant_type", "password");
		requestParams.put("username", "nivedhini@spree.com");
		requestParams.put("password", "spree123");
		// Add a header stating the Request body is a JSON
		request.header("Content-Type", "application/json");
		request.body(requestParams.toJSONString());
		Response response = request.post("/spree_oauth/token");
		response.prettyPrint();
		int statusCode = response.getStatusCode();
		Assert.assertEquals(statusCode, 200);

		JsonPath jsonPathEvaluator = response.getBody().jsonPath();
		outh_token = jsonPathEvaluator.get("access_token").toString();
		System.out.println("oAuth Token is =>  " + outh_token);
	}

	@Test
	public void CreateAddress() throws IOException, ParseException {

		JSONParser jsonparser = new JSONParser();
		FileReader reader = new FileReader(".\\TestData\\CreateAnAddress.json");
		Object obj = jsonparser.parse(reader);
		JSONObject prodjsonobj = (JSONObject) obj;

		Response response = RestAssured.given().auth().oauth2(outh_token).contentType(ContentType.JSON)
				.body(prodjsonobj).post("https://demo.spreecommerce.org/api/v2/storefront/account/addresses").then()
				.extract().response();
		response.getBody().prettyPrint();

		String responseBody = response.getBody().asString();
		System.out.println("Response Body is =>  " + responseBody);
		int statusCode = response.getStatusCode();
		System.out.println("Status code is =>  " + statusCode);
		Assert.assertEquals(200, statusCode);

		JsonPath jsonPathEvaluator = response.getBody().jsonPath();
		String fname = jsonPathEvaluator.get("data.attributes.firstname").toString();
		System.out.println("First Name is =>  " + fname);
		Assert.assertEquals(fname, "Nivedhini");
		String Lname = jsonPathEvaluator.get("data.attributes.lastname").toString();
		Assert.assertEquals(Lname, "R");
	}

}
