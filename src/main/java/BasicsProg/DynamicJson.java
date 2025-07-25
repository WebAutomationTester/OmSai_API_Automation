package BasicsProg;

import PayloadFiles.ReUsableMethods;
import PayloadFiles.payload;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;

import io.restassured.path.json.JsonPath;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DynamicJson {

    public DynamicJson() throws IOException {
    }

    @Test(dataProvider = "BookData")
    public void addBook(String aisle, String isbn) throws IOException {
        RestAssured.baseURI = "http://216.10.245.166";

        // Scenario 1 : Dynamically JSON payload with external data inputs --> Passing args to payload file like body(payload.AddBook("omsai", "1958"))
        String res = given().header("Content-Type", "application/json").body(payload.AddBook(aisle, isbn))
                .when().post("Library/Addbook.php")
                .then().log().all().assertThat().statusCode(200).extract().response().asString();

        JsonPath js = ReUsableMethods.convertToJson(res);
        String msgValidate = js.getString("Msg");
        System.out.println("The success message is: " + msgValidate);

        String bookID = js.getString("ID");
        System.out.println("The New book ID is: " + bookID);


        // Scenario 3 : How to send JSON files (Payload) directly into post method of rest assured
        // Convert JSON data into the String - Content of file can convert into Bytes -> Convert Byte data in String
        String res1 = given().header("Content-Type", "application/json").body(new String(Files.readAllBytes(Paths.get("E:\\Selenium\\API Test New\\Postman\\Sai_API_Automation\\OmSai_API\\src\\main\\java\\PayloadFiles\\StaticJsonData.json"))))
                .when().post("Library/Addbook.php")
                .then().log().all().assertThat().statusCode(200).extract().response().asString();

        JsonPath js1 = ReUsableMethods.convertToJson(res1);
        String msgValidate1 = js1.getString("Msg");
        System.out.println("The success message of static JSON is: " + msgValidate1);

    }

    // Scenario 2 : Parameterize the API tests with multiple data sets
    @DataProvider(name = "BookData")
    public Object[][] getData() {
        return new Object[][]{{"om1", "6457"}, {"ram1", "3290"}, {"Sai1", "2308"}};
    }


}

