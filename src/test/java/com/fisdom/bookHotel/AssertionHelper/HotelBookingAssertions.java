package com.fisdom.bookHotel.AssertionHelper;
import com.fisdom.bookHotel.pojo.BookingDetailsbyID;
import com.fisdom.bookHotel.pojo.BookingDetails;
import org.testng.asserts.SoftAssert;

public class HotelBookingAssertions {

        public static void bookingIDAssertion(SoftAssert softAssert, Integer id, BookingDetailsbyID bookingDetailsbyID){
                softAssert.assertEquals(bookingDetailsbyID.getBookingid(),id,"Booking id is not as expected");
        }
        public void bookingRespAssertion(SoftAssert softAssert, BookingDetails bookingDetails, String firstName, String lastName, Integer totalprice, Boolean depositpaid, String checkin, String checkout, String additionalneeds){
                softAssert.assertEquals(bookingDetails.getFirstname(),firstName,"Firstname is not as expectes");
                softAssert.assertEquals(bookingDetails.getLastname(),lastName,"LastName is not as expectes");
                softAssert.assertEquals(bookingDetails.getAdditionalneeds(),additionalneeds,"AdditionalNeeds is not as expectes");
                softAssert.assertEquals(bookingDetails.getDepositpaid(),depositpaid,"DepositPaid time is not as expectes");
                softAssert.assertEquals(bookingDetails.getTotalprice(),totalprice,"TotalPrice is not as expectes");
                softAssert.assertEquals(bookingDetails.getBookingdates().getCheckin(),checkin,"CheckIn time is not as expectes");
                softAssert.assertEquals(bookingDetails.getBookingdates().getCheckout(),checkout,"CheckOUT time is not as expectes");
        }
}
