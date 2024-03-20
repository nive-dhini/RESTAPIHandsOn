package pk_spreeCommerce;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import jdk.internal.org.jline.utils.Log;
import junit.framework.Assert;

public class GET_GetDefaultCountry {
  @Test
  public void getDefaultCountry() {
	  // Rest Assured will get you to this base URL
	  RestAssured.baseURI = "https://demo.spreecommerce.org";
	  
	  // Assigning base URI to httpRequest object
	  RequestSpecification httpRequest = RestAssured.given();
	  Response response = httpRequest.request(Method.GET,"/api/v2/storefront/countries/default");
	  // print to see the body of the response
	  String res = response.prettyPrint();
	  System.out.println("Response Body is: "+res);
	  
	  String responseBody = response.getBody().asString();
	  System.out.println("Response Body is: "+responseBody);
	  
	  int statusCode = response.getStatusCode();
	  System.out.println("statusCode is: "+ statusCode);
	  
	  Assert.assertEquals(200, statusCode);
	  Assert.assertEquals(responseBody.contains("UNITED STATES"), true);
	  
	  
	  
  }
}
