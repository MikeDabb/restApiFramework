package pl.javastart.restassured.tests.user;

import org.testng.annotations.Test;
import pl.javastart.restassured.main.pojo.response.ApiResponse;
import pl.javastart.restassured.main.pojo.user.User;
import pl.javastart.restassured.main.test.data.UserTestDataGenerator;
import pl.javastart.restassured.tests.testbases.SuiteTestBase;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

public class CreateUserTests extends SuiteTestBase {

    @Test
    public void givenUserWhenPostUserThenUserIsCreatedTest() {

        UserTestDataGenerator userTestDataGenerator = new UserTestDataGenerator();
        User user = userTestDataGenerator.generateUser();

        ApiResponse apiResponse = given().contentType("application/json")
                .body(user)
                .when().post("user")
                .then().extract().as(ApiResponse.class);

        assertEquals(apiResponse.getCode(),  Integer.valueOf(200), "Code");
        assertEquals(apiResponse.getType(), "unknown", "Type");
        assertEquals(apiResponse.getMessage(), "445", "Message");
    }
}
