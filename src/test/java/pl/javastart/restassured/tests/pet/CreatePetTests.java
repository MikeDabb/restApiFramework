package pl.javastart.restassured.tests.pet;

import org.apache.http.HttpStatus;
import org.assertj.core.api.Assertions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import pl.javastart.restassured.main.pojo.response.ApiResponse;
import pl.javastart.restassured.main.pojo.pet.Pet;
import pl.javastart.restassured.main.request.configuration.RequestConfigurationBuilder;
import pl.javastart.restassured.main.test.data.pet.PetTestDataGenerator;
import pl.javastart.restassured.tests.testbases.SuiteTestBase;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

public class CreatePetTests extends SuiteTestBase {

    private Pet actualPet;

    @Test
    public void givenPetWhenPostPetThenPetIsCreatedTest() {

        Pet pet = new PetTestDataGenerator().generatePet();

        actualPet = given().spec(RequestConfigurationBuilder.getDefaultRequestSpecification()).body(pet)
                .when().post("pet")
                .then().statusCode(HttpStatus.SC_OK).extract().as(Pet.class);

        Assertions.assertThat(actualPet)
                .describedAs("Send Pet was different than received by API")
                .usingRecursiveComparison()
                .isEqualTo(pet);
    }

    @AfterMethod
    public void cleanUpAfterTest(){
        ApiResponse apiResponse = given().spec(RequestConfigurationBuilder.getDefaultRequestSpecification())
                .when().delete("pet/{petId}", actualPet.getId())
                .then().statusCode(HttpStatus.SC_OK).extract().as(ApiResponse.class);

        ApiResponse expectedApiResponse = new ApiResponse();
        expectedApiResponse.setCode(200);
        expectedApiResponse.setType("unknown");
        expectedApiResponse.setMessage(actualPet.getId().toString());

        Assertions.assertThat(apiResponse)
                .describedAs("API Response from system was not as expected")
                .usingRecursiveComparison()
                .isEqualTo(expectedApiResponse);
    }

}