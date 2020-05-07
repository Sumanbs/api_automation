package com.fisdom.bookHotel.helper;
import com.fisdom.bookHotel.pojo.BookingDetails;
import com.fisdom.bookHotel.pojo.BookingDetailsbyID;
import com.fisdom.bookHotel.pojo.Bookingdates;
import com.google.gson.Gson;
import io.restassured.config.RestAssuredConfig;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class BookingHotelHelper {

        public BookingDetails assignToPojo(String firstName, String lastName, String additionalNeeds, Boolean depositPaid, int totalPrice, String checkInDate, String checkOutDate) {
                BookingDetails bookingDetails = new BookingDetails();
                bookingDetails.setFirstname(firstName);
                bookingDetails.setLastname(lastName);
                bookingDetails.setAdditionalneeds(additionalNeeds);
                bookingDetails.setDepositpaid(depositPaid);
                if(totalPrice!=-1){
                        bookingDetails.setTotalprice(totalPrice);
                }
                if(checkInDate!=null || checkOutDate!=null){
                        Bookingdates bookingdates = new Bookingdates();
                        bookingdates.setCheckin(checkInDate);
                        bookingdates.setCheckout(checkOutDate);
                        bookingDetails.setBookingdates(bookingdates);
                }
                return bookingDetails;
        }

        public Response restMethod(String methodName, String url, Object body, String username, String password) {
                RequestSpecification requestSpecification = given().config(new RestAssuredConfig());
                requestSpecification.headers("Content-Type", "application/json");
                if(body != null) {
                        Gson gson = new Gson();
                        requestSpecification.body(body);
                        //Need to remove and add logs
                        System.out.println("**************BODY*****************"+gson.toJson(body));
                }
                if(username!=null){
                        requestSpecification.auth().preemptive().basic(username, password);
                }
                Response response = requestSpecification.request(methodName, url);
                //Need to remove and add logs
                System.out.println("*********************Response **************" + response.asString());
                System.out.println(response.getStatusCode());
                return response;
        }

        public BookingDetailsbyID assignResponseToBookingDetailsbyIDPojo(Response response) {
                Gson gson = new Gson();
                String jsonResult = response.getBody().asString();
                BookingDetailsbyID bookingDetailsbyID = gson.fromJson(jsonResult, BookingDetailsbyID.class);
                return bookingDetailsbyID;
        }

        public BookingDetails assignResponseToBookingDetailsPojo(Response response) {
                Gson gson = new Gson();
                String jsonResult = response.getBody().asString();
                BookingDetails bookingDetails = gson.fromJson(jsonResult, BookingDetails.class);
                return bookingDetails;
        }

}
