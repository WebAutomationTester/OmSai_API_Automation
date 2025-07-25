package EcomApp;

import POJO_EcomApp.*;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class EcommerceTest {

    public static void main(String[] args) {

        // Spec Builder
        RequestSpecification req = new RequestSpecBuilder()
                .setBaseUri("https://rahulshettyacademy.com/api/ecom")
                .setContentType(ContentType.JSON).build();

        // Serialization
        LoginEcomApp_Request LoginEA = new LoginEcomApp_Request();
        LoginEA.setUserEmail("omsai@gmail.com");
        LoginEA.setUserPassword("Omsai@1958");

        // Actual Logic for Login to Application
        RequestSpecification reqLogin = given().log().all().spec(req).body(LoginEA);

        LoginEcomApp_Response response = reqLogin.when().post("/auth/login")
                .then().log().all().assertThat().statusCode(200).extract().response().as(LoginEcomApp_Response.class);

        // Deserialization
        String token = response.getToken();
        String Id = response.getUserId();
        String msg = response.getMessage();

        // Print output
        System.out.println("Token: "+token+" UserID: "+Id+" Message: "+msg);

        // Spec Builder Add product
        RequestSpecification AddProdReq = new RequestSpecBuilder()
                .setBaseUri("https://rahulshettyacademy.com/api/ecom")
                .build();

        // Logic to add the product
       RequestSpecification AddProductReq = given().log().all().spec(AddProdReq).header("Authorization", token)
                .param("productName", "Raymonds")
                .param("productAddedBy", Id )
                .param("productCategory", "Modern fashion" )
                .param("productSubCategory", "Punjabi")
                .param("productPrice", "999")
                .param("productDescription", "Raymonds Original Punjabi dress")
                .param("productFor", "women")
                .multiPart("productImage", new File("E:/Selenium/API Test New/Postman/Documents/kurti.jpg"));

        AddProduct_Response AddProductResponse = AddProductReq.when().post("/product/add-product")
               .then().log().all().extract().response().as(AddProduct_Response.class);

        // Deserialization for AddProduct
        String ProductID = AddProductResponse.getProductId();
        String ProductMessage = AddProductResponse.getMessage();

        System.out.println("Product Id is: "+ProductID);
        System.out.println("Product Message is: "+ProductMessage);

        // Serialization for Create Order
        OrdersProductDetails_Request order = new OrdersProductDetails_Request();
        order.setCountry("India");
        order.setProductOrderedId(ProductID);

        List<OrdersProductDetails_Request> orderList = new ArrayList<>();
        orderList.add(order);

        OrderProduct_Request OrderPro = new OrderProduct_Request();
        OrderPro.setOrders(orderList);

        // Create Order
        RequestSpecification productOrderReq = given().log().all().spec(req).header("Authorization", token).body(OrderPro);

       String responseProductOrder = productOrderReq.when().post("/order/create-order")
                .then().log().all().extract().response().asString();

       System.out.println("Order is: "+responseProductOrder);

       // Delete Product & Bypass SSL certification
        String DeleteResponse = given().relaxedHTTPSValidation().log().all().spec(req).header("Authorization", token )
                .pathParam("productId", ProductID)
                .when().delete("/product/delete-product/{productId}")
                .then().log().all().extract().response().asString();

        System.out.println("Deleted product: "+DeleteResponse);
    }
}
