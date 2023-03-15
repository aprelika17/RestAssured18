import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.*;

public class ReqresInTests {

    @Test
    @DisplayName("Get user by Id, verify email and support.text")
    void checkKeysHasExactValue() {
        given()
                .log().uri()
                .when()
                .get("https://reqres.in/api/users/2")
                .then()
                .log().status()
                .log().body()
                .body("support.text", is ("To keep ReqRes free, contributions " +
                        "towards server costs are appreciated!"))
                .body("data.email", is ("janet.weaver@reqres.in"))
                .statusCode(200);
    }

    @Test
    @DisplayName("Create user and verify name and job")
    void createUser(){
        String data = "{ \"name\": \"morpheus\", \"job\": \"leader\"}";

        given()
                .log().uri()
                .contentType(JSON)
                .body(data)
                .when()
                .post("https://reqres.in/api/users")
                .then()
                .log().status()
                .log().body()
                .body("name", is ("morpheus"))
                .body("job", is ("leader"))
                .statusCode(201);
    }
    @Test
    @DisplayName("Update user and verify name and job")
    void updateUser() {

        String data = "{ \"name\": \"morpheus\", \"job\": \"resident\"}";

        given()
                .log().uri()
                .contentType(JSON)
                .body(data)
                .when()
                .put("https://reqres.in/api/users/2")
                .then()
                .log().status()
                .log().body()
                .body("name", is ("morpheus"))
                .body("job", is ("resident"))
                .statusCode(200);
    }

    @Test
    @DisplayName("Get users array and verify it's size")
    void checkArraySizeGetUserPage() {
        given()
                .log().uri()
                .when()
                .get("https://reqres.in/api/users?page=2")
                .then()
                .log().status()
                .log().body()
                .body("data.size()", is (6))
                .statusCode(200);
    }



    @Test
    @DisplayName("Get unknown array and verify year items")
    void checkArrayKeyHasItems() {
        given()
                .log().uri()
                .when()
                .get("https://reqres.in/api/unknown")
                .then()
                .log().status()
                .log().body()
                .body("data.year", hasItems(2000,2001,2002,2003,2004,2005))
                .statusCode(200);
    }
}
