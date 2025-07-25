package SpecBuilder;

import POJO_GoogleMaps.AddPlace;
import POJO_GoogleMaps.Location;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class Req_Res_SpecBuilder {

    public static void main(String[] args) {

        AddPlace ap = new AddPlace();
        ap.setAccuracy(50);
        ap.setAddress("29, side layout, cohen 09");
        ap.setLanguage("French-IN");
        ap.setName("Frontline house");
        ap.setPhone_number("(+91) 983 893 3937");
        ap.setWebsite("http://google.com");

        List<String> ls = new ArrayList<>() ;
        ls.add("shoe park");
        ls.add("shop");
        ap.setTypes(ls);

        Location loc = new Location();
        loc.setLat(-38.383494);
        loc.setLng(33.427362);
        ap.setLocation(loc);

        RestAssured.baseURI = "https://rahulshettyacademy.com";

        String res = given().log().all().queryParam("key", "qaclick123").body(ap)
                .when().post("/maps/api/place/add/json")
                .then().log().all().assertThat().statusCode(200).extract().response().asString();

        System.out.println(res);


        // Scenario : Use Spec Builder class
        RequestSpecification req = new RequestSpecBuilder()
                .setBaseUri("https://rahulshettyacademy.com").addQueryParam("key", "qaclick123")
                .setContentType(ContentType.JSON)
                .build();
        ResponseSpecification resp = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType(ContentType.JSON)
                .build();

        RequestSpecification respo = given().spec(req).body(ap);

        Response response = respo.when().post("/maps/api/place/add/json")
                .then().spec(resp).extract().response();

        String outputRes = response.asString();

        System.out.println("Spec Builder output is: "+outputRes);
    }
}
//Un:Postman
//email:postman@gmail.com
//pwd:Hello123@