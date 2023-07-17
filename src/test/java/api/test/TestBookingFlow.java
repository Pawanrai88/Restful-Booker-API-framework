package api.test;


import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import api.payloads.BookingPayload;
import api.payloads.TokenPayload;
import api.utility.GetBookingData;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import api.endpoints.BookingEndpoints;
import api.payloads.BookingDates;
import net.datafaker.Faker;

public class TestBookingFlow {
	BookingPayload b;
	GetBookingData getAPIData=new GetBookingData();
	JsonPath js;
	String token_id;
	String booking_id;
	 public Logger logger;
	
	@BeforeClass
	public void setup() {
		PropertyConfigurator.configure("C:\\Users\\12363\\eclipse-workspace\\HEROKUAPP\\src\\test\\resources\\Config\\log4j.properties");
		logger=LogManager.getLogger(this.getClass());
		b =getAPIData.getData();
		logger.info("******Request for Token*******");
		Response response=BookingEndpoints.createToken(getAPIData.getTokenData());
		String result=response.then().log().all().extract().response().asString();
		js=new JsonPath(result);
		token_id=js.get("token");
		Assert.assertNotNull(token_id);
		Assert.assertEquals(response.getStatusCode(),200);
		logger.info("******Token request successfully completed*******");
	}
	
	@Test(priority=1)
	public void test_CreateBooking() {
		logger.info("******Create new Booking*******");
		Response response = BookingEndpoints.createBooking(getAPIData.getData());
		String result=response.then().log().all().extract().response().asString();
		js=new JsonPath(result);
		booking_id=js.getString("bookingid");
		Assert.assertNotNull(booking_id);
		Assert.assertEquals(response.getStatusCode(),200);
		logger.info("******Booking created*******");
	}
	
	@Test(priority=2,dependsOnMethods="test_CreateBooking")
	public void test_GetBooking() {
		logger.info("******Retrieve the details of Booking*******");
		Response response = BookingEndpoints.getBooking(booking_id);
		String result=response.then().log().all().extract().response().asString();
		Assert.assertEquals(response.getStatusCode(),200);
		js=new JsonPath(result);
		Assert.assertEquals(js.getString("firstname"),b.getFirstname());
		Assert.assertEquals(js.getString("lastname"),b.getLastname());
		logger.info("******Details are retrieved*******");
	}
	//the implementation of this API is not stable
	@Test(priority=3,dependsOnMethods="test_CreateBooking")
	public void test_PartialUpdateBooking() {
		logger.info("******Update Fname and Lname of Booking*******");
		Response response = BookingEndpoints.partialUpdateBooking(getAPIData.getPartialUpdateData(),booking_id,token_id);
		String result=response.then().log().all().extract().response().asString();
		Assert.assertEquals(response.getStatusCode(),200);
		logger.info("******Update request completed*******");
	}
	
	@Test(priority=4,dependsOnMethods="test_CreateBooking")
	public void test_DeleteBooking() {
		logger.info("******Delete the Booking*******");
		Response response = BookingEndpoints.DeleteBooking(booking_id,token_id );
		String result=response.then().log().all().extract().response().asString();
		Assert.assertEquals(response.getStatusCode(),201);
		logger.info("******Booking deleted*******");
	}

}
