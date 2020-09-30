package pl.javastart.restassured.tests.pet;

import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Step;
import org.assertj.core.api.Assertions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import pl.javastart.restassured.main.pojo.response.ApiResponse;
import pl.javastart.restassured.main.pojo.pet.Pet;
import pl.javastart.restassured.main.rop.CreatePetEndpoint;
import pl.javastart.restassured.main.rop.DeletePetEndpoint;
import pl.javastart.restassured.main.test.data.pet.PetTestDataGenerator;
import pl.javastart.restassured.tests.testbases.SuiteTestBase;


public class CreatePetTests extends SuiteTestBase {

    private Pet actualPet;

    @Step
    @Severity(SeverityLevel.CRITICAL)
    @Test(description = "The goal of this test is to create pet and check if returned Pet object is the same", priority = 1)
    public void givenPetWhenPostPetThenPetIsCreatedTest() {
        Pet pet = new PetTestDataGenerator().generatePet();
        actualPet = new CreatePetEndpoint().setPet(pet).sendRequest().getResponseModel();
        Assertions.assertThat(actualPet)
                .describedAs("Send Pet was different than received by API")
                .usingRecursiveComparison()
                .isEqualTo(pet);
    }

    @Step
    @AfterMethod
    public void cleanUpAfterTest(){
        ApiResponse apiResponse = new DeletePetEndpoint().setPetId(actualPet.getId()).sendRequest().getResponseModel();

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