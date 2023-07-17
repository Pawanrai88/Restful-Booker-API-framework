package api.test;

import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;

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
	
	@BeforeClass
	public void setup() {
		b =getAPIData.getData();
		Response response=BookingEndpoints.createToken(getAPIData.getTokenData());
		String result=response.then().log().all().extract().response().asString();
		js=new JsonPath(result);
		token_id=js.get("token");
		Assert.assertNotNull(token_id);
		Assert.assertEquals(response.getStatusCode(),200);
		
	}
	
	@Test(priority=1)
	public void test_CreateBooking() {
		Response response = BookingEndpoints.createBooking(getAPIData.getData());
		String result=response.then().log().all().extract().response().asString();
		js=new JsonPath(result);
		booking_id=js.getString("bookingid");
		Assert.assertNotNull(booking_id);
		Assert.assertEquals(response.getStatusCode(),200);
		
	}
	
	@Test(priority=2,dependsOnMethods="test_CreateBooking")
	public void test_GetBooking() {
		Response response = BookingEndpoints.getBooking(booking_id);
		String result=response.then().log().all().extract().response().asString();
		Assert.assertEquals(response.getStatusCode(),200);
		js=new JsonPath(result);
		Assert.assertEquals(js.getString("firstname"),b.getFirstname());
		Assert.assertEquals(js.getString("lastname"),b.getLastname());
		
	}
	//the implementation of this API is not stable
	@Test(priority=3,dependsOnMethods="test_CreateBooking")
	public void test_PartialUpdateBooking() {
		Response response = BookingEndpoints.partialUpdateBooking(getAPIData.getPartialUpdateData(),booking_id,token_id);
		String result=response.then().log().all().extract().response().asString();
		Assert.assertEquals(response.getStatusCode(),200);
		
	}
	
	@Test(priority=4,dependsOnMethods="test_CreateBooking")
	public void test_DeleteBooking() {
		Response response = BookingEndpoints.DeleteBooking(booking_id,token_id );
		String result=response.then().log().all().extract().response().asString();
		Assert.assertEquals(response.getStatusCode(),201);
		
	}

}
