package pk_spreeCommerce;

import java.io.IOException;
import java.util.Map;

import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class POST_PATCH_DELETE_CreateAndUpdateDeleteAddress2 {
	String ID;

	@Test(priority = 1)
	public void Create_Address() throws IOException, ParseException {

		Response response = RestAssured.given()
				.auth()
				.oauth2(UtilityClass.oAuth_Token())
				.contentType(ContentType.JSON)
				.body(UtilityClass.readFile("CreateAnAddress.json"))
				.post("https://demo.spreecommerce.org/api/v2/storefront/account/addresses")
				.then()
				.extract()
				.response();
		response.getBody().prettyPrint();

		int statusCode = response.getStatusCode();
		System.out.println("Status code is =>  " + statusCode);
		Assert.assertEquals(200, statusCode);

		Map<String, String> id_create = response.jsonPath().getJsonObject("data");
		ID = id_create.get("id");

	}

	@Test(priority = 2)
	public void Update_Address() throws IOException, ParseException {

		Response response = RestAssured.given()
				.auth()
				.oauth2(UtilityClass.oAuth_Token())
				.contentType(ContentType.JSON)
				.body(UtilityClass.readFile("UpdateAnAddress.json"))
				.patch("https://demo.spreecommerce.org/api/v2/storefront/account/addresses/" + ID)
				.then()
				.extract()
				.response();
		response.getBody().prettyPrint();

		int statusCode = response.getStatusCode();
		System.out.println("Status code is =>  " + statusCode);
		Assert.assertEquals(200, statusCode);

		Map<String, String> default_billing_address = response.jsonPath().getJsonObject("data.attributes");
		String firstName = default_billing_address.get("firstname");
		Assert.assertEquals(firstName, "Nivedhini");

		String lastName = default_billing_address.get("lastname");
		Assert.assertEquals(lastName, "R");

		String addressOne = default_billing_address.get("address1");
		Assert.assertEquals(addressOne, "Jayanagar, Bangalore");

		String addressTwo = default_billing_address.get("address2");
		Assert.assertEquals(addressTwo, "4th T Block 19th Cross");
	}
	
	@Test(priority = 3)
	public void deleteAddress_UI() throws InterruptedException {
		// Launch the Browser
		//WebDriverManager.chromedriver().setup();
		ChromeDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		// Enter the URL
		driver.get("https://demo.spreecommerce.org/login");
		//Click on Icon
		/*
		 * driver.findElement(By.xpath("//button[@id='account-button']//*[name()='svg']"
		 * )).click();
		 * 
		 * //click on login driver.findElement(By.xpath("//a[.='LOGIN']")).click();
		 */
		Thread.sleep(3000);
		driver.findElement(By.xpath("//input[@id='spree_user_email']")).sendKeys("nivedhini@spree.com");
		Thread.sleep(3000);
		driver.findElement(By.xpath("//input[@id='spree_user_password']")).sendKeys("spree123");
		driver.findElement(By.xpath("//input[@name='commit']")).click();
		Thread.sleep(3000);
				
		//Delete User from Search List

	    //driver.findElementByXPath("//a[text()='"+ExpUserName+"']/parent::td/preceding-sibling::td/input").click();
	    driver.findElement(By.xpath("//span[contains(text(),'Nivedhini')]/../../following-sibling::div[1]//a[@aria-label='Remove Address']")).click();
	    Thread.sleep(2000);
	    driver.findElement(By.xpath("//a[@id='delete-address-popup-confirm']")).click();
		//driver.findElement(By.xpath("//a[@data-hook='remove_address']")).click();
		driver.findElement(By.xpath("//span[normalize-space()='Address has been successfully removed.']")).isDisplayed();
	
		System.out.println("Test Success");
		driver.quit();
}
	
	
	
	
}
