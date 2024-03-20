package pk_spreeCommerce;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import jdk.internal.org.jline.utils.Log;
import junit.framework.Assert;

public class GET_GetDefaultCountry_JSON {
	@Test
	public void getDefaultCountry() {
		// Rest Assured will get you to this base URL
		RestAssured.baseURI = "https://demo.spreecommerce.org";

		// Assigning base URI to httpRequest object
		RequestSpecification httpRequest = RestAssured.given();
		Response response = httpRequest.request(Method.GET, "/api/v2/storefront/countries/default");
		// print to see the body of the response

		int statusCode = response.getStatusCode();
		Assert.assertEquals(200, statusCode);

		JsonPath js = new JsonPath(response.asString());
		String type_act = js.get("data.type");
		Assert.assertEquals(type_act, "country");

		String iso_act = js.get("data.attributes.iso_name");
		System.out.println(iso_act);
		Assert.assertEquals(iso_act, "INDIA");

		

	}
}
