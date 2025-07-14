import PayloadFiles.ReUsableMethods;
import PayloadFiles.payload;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class Basics {
    public static void main(String[] args) {
        // Given: All input details
        // When: Submit the API
        // Then: Validate the response

        // Scenario 1: Validate if ADD place API is working as expected --> Read the payload from external file, extract data, assertions

        RestAssured.baseURI = "https://rahulshettyacademy.com/";
        String response = given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
                .body(payload.inputFiles())
                .when().post("maps/api/place/add/json")
                .then().log().all().assertThat().statusCode(200).body("scope", equalTo("APP"))
                .header("Server", "Apache/2.4.52 (Ubuntu)").extract().response().asString();

        // Extract the attributes values from response using JsonPath
        JsonPath js = new JsonPath(response);
        String placeID = js.getString("place_id");
        System.out.println("The place id is: "+placeID);

        // Scenario 2: Use place id and update the address
        String NewAddress = "Bhaisa Road Bhokar, Nanded Maharastra";
        given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
                .body("{\n" +
                         "\"place_id\":\""+placeID+"\",\n" +
                        "\"address\":\""+NewAddress+"\",\n" +
                        "\"key\":\"qaclick123\"\n" +
                        "}\n")
                .when().put("maps/api/place/update/json")
                .then().log().all().assertThat().statusCode(200).body("msg", equalTo("Address successfully updated"));

        // Scenario 3: Get the data and validate whether address is updated or not

        String addressResponse = given().log().all().queryParam("key","qaclick123").queryParam("place_id", placeID)
                .when().get("maps/api/place/get/json")
                .then().log().all().assertThat().statusCode(200).extract().response().asString();

        JsonPath jsAddress = ReUsableMethods.convertToJson(addressResponse);
        String AddressUpdated = jsAddress.getString("address");

        System.out.println("The updated address is: "+AddressUpdated);

        Assert.assertEquals(AddressUpdated, NewAddress);

    }
}
