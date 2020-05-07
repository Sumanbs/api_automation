package com.fisdom.bookHotel.pojo;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BookingDetailsbyID {

        @SerializedName("bookingid")
        @Expose
        private Integer bookingid;
        @SerializedName("booking")
        @Expose
        private BookingDetails booking;

        public Integer getBookingid() {
                return bookingid;
        }

        public void setBookingid(Integer bookingid) {
                this.bookingid = bookingid;
        }

        public BookingDetails getBooking() {
                return booking;
        }

        public void setBooking(BookingDetails booking) {
                this.booking = booking;
        }

}
