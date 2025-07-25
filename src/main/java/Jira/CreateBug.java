package Jira;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import java.io.File;

import static io.restassured.RestAssured.*;

public class CreateBug {

    public static void main(String[] args) {
        RestAssured.baseURI = "https://omsai.atlassian.net";

        // Scenario 1: Create a jira issue
        String response = given().log().all().header("Content-Type","application/json").header("Authorization", "Basic bXVrc3BnMjNAZ21haWwuY29tOkFUQVRUM3hGZkdGMEVCT0tIalo3eHJJcmZkdVlCbnpWcUduTXNVWEJpeXVsbmJMV2x3TzcxY2dnT092YkZ1LWFMM1VFTHh5TGlkM2ptQnZyT2dlZW05RnZDX2V0VGV0b3pEbFpQdHVfOXRBNkRfTTU2UFhGaXNBcm9yazlJNXd3RlpCcmlBOC1jQktpRHlFbzNuZ3dYMFlNNFFXOHhIRks1OXJVY1Y0X1VKcHUtYnF4RVJJci1LYz0yMTdFOTI2Rg==")
                .body("{\n" +
                        "    \"fields\": {\n" +
                        "       \"project\":\n" +
                        "       {\n" +
                        "          \"key\": \"SCRUM\"\n" +
                        "       },\n" +
                        "       \"summary\": \"Check boxes are not working\",\n" +
                        "       \"issuetype\": {\n" +
                        "          \"name\": \"Bug\"\n" +
                        "       }\n" +
                        "   }\n" +
                        "}")
                .when().post("/rest/api/3/issue")
                .then().log().all().assertThat().statusCode(201).extract().response().asString();

        JsonPath js = PayloadFiles.ReUsableMethods.convertToJson(response);
        String BugId = js.getString("id");
        System.out.println("The Jira ID is: "+BugId);

        // Scenario 2: Add attachment to issue
        given().header("Authorization", "Basic bXVrc3BnMjNAZ21haWwuY29tOkFUQVRUM3hGZkdGMEVCT0tIalo3eHJJcmZkdVlCbnpWcUduTXNVWEJpeXVsbmJMV2x3TzcxY2dnT092YkZ1LWFMM1VFTHh5TGlkM2ptQnZyT2dlZW05RnZDX2V0VGV0b3pEbFpQdHVfOXRBNkRfTTU2UFhGaXNBcm9yazlJNXd3RlpCcmlBOC1jQktpRHlFbzNuZ3dYMFlNNFFXOHhIRks1OXJVY1Y0X1VKcHUtYnF4RVJJci1LYz0yMTdFOTI2Rg==")
                .header("X-Atlassian-Token","no-check").pathParam("key",BugId)
                .multiPart("file", new File("E:/Selenium/API Test New/Postman/Documents/71aKhAqIXFL.jpg")).log().all()
                .when().post("/rest/api/3/issue/{key}/attachments")
                .then().assertThat().statusCode(200);

    }
}
