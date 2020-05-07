package com.fisdom.bookHotel.Tests;
import com.fisdom.bookHotel.AssertionHelper.HotelBookingAssertions;
import com.fisdom.bookHotel.helper.BookingHotelHelper;
import com.fisdom.bookHotel.pojo.BookingDetails;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class BookHotelTest {

        public static int id = 0;
        public static BookingHotelHelper bookingHotelHelper = new BookingHotelHelper();
        public static BookingDetails staticbookingDetailsResp = null;
        public static HotelBookingAssertions hotelBookingAssertions = new HotelBookingAssertions();

        public static void make_a_POST_REQUEST() {
                BookingDetails bookingDetails = bookingHotelHelper.assignToPojo(Constants.firstName, Constants.lastName, Constants.additionalneeds, Constants.depositpaid, Constants.totalprice, Constants.checkin, Constants.checkout);
                Response response = bookingHotelHelper.restMethod("POST", Constants.APIURL, bookingDetails, null, null);
                staticbookingDetailsResp = bookingHotelHelper.assignResponseToBookingDetailsbyIDPojo(response).getBooking();
                id = bookingHotelHelper.assignResponseToBookingDetailsbyIDPojo(response).getBookingid();
        }

        @Test
        @Parameters({"method", "firstName", "lastName", "additionalNeeds", "depositPaid", "totalPrice", "checkInDate", "checkOutDate", "statusCode", "userName", "password"})
        public void bookHotel(@Optional("POST") String method, @Optional() String firstName, @Optional() String lastName, @Optional() String additionalNeeds, @Optional() Boolean depositPaid, @Optional("-1") int totalPrice, @Optional() String checkInDate, @Optional() String checkOutDate, int statusCode, @Optional() String userName, @Optional() String password) {
                BookingDetails bookingDetailsResp;
                SoftAssert softAssert = new SoftAssert();
                BookingDetails bookingDetails = null;
                Response response = null;
                bookingDetails = bookingHotelHelper.assignToPojo(firstName, lastName, additionalNeeds, depositPaid, totalPrice, checkInDate, checkOutDate);
                if(method.equalsIgnoreCase(Constants.POST)) {
                        response = bookingHotelHelper.restMethod(method, Constants.APIURL, bookingDetails, userName, password);
                } else {
                        make_a_POST_REQUEST();//Before put or patch request we have to create the resources in backend.That part is done here.
                        response = bookingHotelHelper.restMethod(method, Constants.APIURL + "/" + id, bookingDetails, userName, password);
                }
                if(response.getStatusCode() != statusCode) {
                        /**
                         * Hard assertion. If status code is not 200, no  need to check further.
                         */
                        Assert.fail("Status Code is not equal expecting " + statusCode + " but found " + response.getStatusCode());
                } else {
                        if(method.equalsIgnoreCase("POST")) {
                                id = bookingHotelHelper.assignResponseToBookingDetailsbyIDPojo(response).getBookingid();
                                bookingDetailsResp = bookingHotelHelper.assignResponseToBookingDetailsbyIDPojo(response).getBooking();
                        } else {
                                bookingDetailsResp = bookingHotelHelper.assignResponseToBookingDetailsPojo(response);
                        }
                        if(method.equalsIgnoreCase("PATCH")) {
                                firstName = firstName != null ? firstName : staticbookingDetailsResp.getFirstname();
                                lastName = lastName != null ? lastName : staticbookingDetailsResp.getLastname();
                                totalPrice = totalPrice != -1 ? totalPrice : staticbookingDetailsResp.getTotalprice();
                                depositPaid = depositPaid != null ? depositPaid : staticbookingDetailsResp.getDepositpaid();
                                checkInDate = checkInDate != null ? checkInDate : staticbookingDetailsResp.getBookingdates().getCheckin();
                                checkOutDate = checkOutDate != null ? checkOutDate : staticbookingDetailsResp.getBookingdates().getCheckout();
                                additionalNeeds = additionalNeeds != null ? additionalNeeds : staticbookingDetailsResp.getAdditionalneeds();
                        }
                        /**
                         * Post/PUT/PATCH method Response Assertion
                         */
                        hotelBookingAssertions.bookingRespAssertion(softAssert, bookingDetailsResp, firstName, lastName, totalPrice, depositPaid, checkInDate, checkOutDate, additionalNeeds);
                        /**
                         * Get the booking Id from the  Post response and Hit get api.
                         * Assertion for the get API is done here .
                         * Ex - https://restful-booker.herokuapp.com/booking/1
                         */
                        String getAPI = Constants.APIURL + "/" + id;
                        response = bookingHotelHelper.restMethod(Constants.GET, getAPI, null, userName, password);
                        if(response.getStatusCode() != statusCode) {
                                Assert.fail("Status Code is not equal expecting " + statusCode + " but found " + response.getStatusCode());
                        } else {
                                bookingDetailsResp = bookingHotelHelper.assignResponseToBookingDetailsPojo(response);
                                hotelBookingAssertions.bookingRespAssertion(softAssert, bookingDetailsResp, firstName, lastName, totalPrice, depositPaid, checkInDate, checkOutDate, additionalNeeds);
                        }
                }
                softAssert.assertAll();
        }

}
