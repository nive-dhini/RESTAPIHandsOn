package pk_spreeCommerce;

import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.Assert;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class UtilityClass {

	public static String oAuth_Token() {

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
		String outh_token = jsonPathEvaluator.get("access_token").toString();
		System.out.println("oAuth Token is =>  " + outh_token);

		return outh_token;
	}

	public static JSONObject readFile(String fileName) throws IOException, ParseException {
		// Create json object of JSONParser class to parse the JSON data
		JSONParser jsonparser = new JSONParser();
		// Create object for FileReader class, which help to load and read JSON file
		// ******* provide the path of JSON file *******

		FileReader reader = new FileReader(".\\TestData\\" + fileName);
		// Returning/assigning to Java Object
		Object obj = jsonparser.parse(reader);
		// Convert Java Object to JSON Object, JSONObject is typecast here
		JSONObject prodjsonobj = (JSONObject) obj;
		return prodjsonobj;

	}

}
