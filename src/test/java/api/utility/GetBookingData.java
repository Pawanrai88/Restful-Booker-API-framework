package api.utility;

import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;

import api.payloads.BookingDates;
import api.payloads.BookingPayload;
import api.payloads.TokenPayload;
import net.datafaker.Faker;

public class GetBookingData {
	
	Faker fake=new Faker();
	SimpleDateFormat formatter = new SimpleDateFormat ("YYYY-MM-dd");
	BookingPayload py=new BookingPayload();
	BookingDates bookingDate=new BookingDates();
	TokenPayload tkPayload=new TokenPayload();
	
	public BookingPayload getData() {
		py.setFirstname(fake.name().firstName());
		py.setLastname(fake.name().lastName());
		py.setTotalprice(fake.number().numberBetween(1000,2500));
		py.setDepositpaid(fake.equals(true));
		bookingDate.setCheckin(formatter.format(fake.date().future(20,TimeUnit.DAYS)));
		bookingDate.setCheckout(formatter.format(fake.date().future(10,TimeUnit.DAYS)));
		py.setBookingdates(bookingDate);
		py.setAdditionalneeds("Lunch");
		return py;
		
	}
	
	public BookingPayload getPartialUpdateData() {
		py.setFirstname(fake.name().firstName());
		py.setLastname(fake.name().lastName());
		return py;
		
	}
	
	public TokenPayload getTokenData() {
		tkPayload.setUsername("admin");
		tkPayload.setPassword("password123");
		return tkPayload;
		
	}

}
