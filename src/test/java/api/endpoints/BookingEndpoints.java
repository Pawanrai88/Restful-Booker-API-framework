package api.endpoints;

import static io.restassured.RestAssured.given;

import api.payloads.BookingPayload;
import api.payloads.TokenPayload;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class BookingEndpoints {
	
	public static Response createToken(TokenPayload payload) {
		Response response=given()
		.contentType(ContentType.JSON)
		.body(payload)
		.when()
		.post(Routes.auth_Url);
	   return response;
		
	}
	
	public static Response createBooking(BookingPayload payload) {
		Response response=given()
		.contentType(ContentType.JSON)
		.accept("application/json")
		.body(payload)
		.when()
		.post(Routes.createBooking_Url);
	   return response;
		
	}
	
	public static Response getBooking(String bookingId) {
		Response response=given()
	    .pathParam("id", bookingId)
		
		.when()
		.get(Routes.getBooking_Url);
		
	   return response;
	}
	
	public static Response partialUpdateBooking(BookingPayload payload,String bookingId,String tokenId ) {
		Response response=given()
		 .body(payload)
	    .header("Cookie","token="+ tokenId)
	    .header("Authorization","Basic="+ "YWRtaW46cGFzc3dvcmQxMjM=")
	    .pathParam("id", tokenId)
		.when()
		.patch(Routes.partialUpdateBooking_Url);
		
	   return response;
	}
	
	public static Response DeleteBooking(String bookingId, String tokenId) {
		Response response=given()
	    .pathParam("id", bookingId)
	    .header("Cookie","token="+ tokenId)
	    .header("Authorization","Basic="+ "YWRtaW46cGFzc3dvcmQxMjM=")
		.when()
		.delete(Routes.DeleteBooking_Url);
		
	   return response;
	}

}
