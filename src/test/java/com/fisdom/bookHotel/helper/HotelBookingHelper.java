package com.fisdom.bookHotel.helper;
import io.restassured.config.RestAssuredConfig;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.Test;

import java.util.Map;

import static io.restassured.RestAssured.basePath;
import static io.restassured.RestAssured.given;

public class HotelBookingHelper {


        public Response restMethod(String methodName,String url, String userName, String password, Object body,Map headers) {
        RequestSpecification requestSpecification = given().config(new RestAssuredConfig());
        if(userName!=null){
                requestSpecification.auth().basic(userName,password);
        }
        requestSpecification.headers(headers);
        if(body!=null){
                requestSpecification.body(body);
        }
        Response response = requestSpecification.request(methodName,url);
        return response;
        }
}
