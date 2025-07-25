package OAuth;

import POJO.Api;
import POJO.WebAutomation;
import POJO.GetCourse;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.*;

public class GrantTypeOAuth_And_Deserialization {

    public static void main(String[] args) {

        String[] actual = {"Selenium Webdriver Java", "Cyqwpress", "Protractor"};

        String response = given().log().all()
                .formParam("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
                .formParam("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
                .formParam("grant_type", "client_credentials")
                .formParam("scope","trust")
                .when().post("https://rahulshettyacademy.com/oauthapi/oauth2/resourceOwner/token")
                .then().log().all().assertThat().statusCode(200).extract().response().asString();

        JsonPath js = PayloadFiles.ReUsableMethods.convertToJson(response);
        String TokenID = js.getString("access_token");


        GetCourse gc = given().log().all().queryParam("access_token", TokenID )
                .when().get("https://rahulshettyacademy.com/oauthapi/getCourseDetails")
                .as(GetCourse.class);

        String linkedIn = gc.getLinkedIn();
        String instructorName = gc.getInstructor();
        System.out.println("LinkedIn url is: "+linkedIn);
        System.out.println("Instructor name is: "+instructorName);

        List<Api> courseAPI = gc.getCourses().getApi();

        // Scenario 1: 1st way - Print the price for CourseTitle is "SoupUI Webservices testing"
        for(Api courseName : courseAPI) {
            String courseTitle = courseName.getCourseTitle();
            System.out.println("API's course name is: "+courseTitle);
            if(courseTitle.equals("SoapUI Webservices testing")){
                String coursePrice = courseName.getPrice();
                System.out.println("1st way SoapUI Webservices testing course Price is: "+coursePrice);
            }
        }

        // Scenario 1: 2nd way - Print the price for CourseTitle is "SoupUI Webservices testing"
        for(int i = 0 ; i < courseAPI.size() ; i++) {
           if(courseAPI.get(i).getCourseTitle().equals("SoapUI Webservices testing")) {
               System.out.println("2nd way SoapUI Webservices testing course Price is: "+courseAPI.get(i).getPrice());
           }
       }

        // Scenario 1: 3rd way - Print the price for CourseTitle is "SoupUI Webservices testing"
        String price = gc.getCourses().getApi().get(1).getPrice();
        System.out.println("3rd way SoapUI Webservices testing course Price is: "+price);


        // Scenario 2: 1st way - Print the all course name for WebAutomation
        List<WebAutomation> web = gc.getCourses().getWebAutomation();
        for(WebAutomation w : web) {
            System.out.println("WebAutomation Course is: "+w.getCourseTitle());
        }

        // Scenario 2: 2nd way - Print the all course name for WebAutomation
        ArrayList<String> Expected = new ArrayList<>();
        List<WebAutomation> webCourse = gc.getCourses().getWebAutomation();
        for(int i = 0 ; i < webCourse.size() ; i++) {
            Expected.add(webCourse.get(i).getCourseTitle());
        }
        List<String> actualArray = Arrays.asList(actual);
        Assert.assertTrue(Expected.equals(actualArray));


    }
}


