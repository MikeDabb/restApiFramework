package pl.javastart.restassured.tests.pet;

import com.github.javafaker.Faker;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Step;
import jdk.jfr.Description;
import org.apache.http.HttpStatus;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pl.javastart.restassured.main.rop.DeletePetEndpoint;
import pl.javastart.restassured.tests.testbases.SuiteTestBase;

public class DeletePetTests extends SuiteTestBase {

    private int nonExistingPetId;

    @Step
    @BeforeMethod
    public void beforeTest(){
        nonExistingPetId = new Faker().number().numberBetween(1000, 10000);
        new DeletePetEndpoint().setPetId(nonExistingPetId).sendRequest();
    }


    @Step
    @Severity(SeverityLevel.CRITICAL)
    @Test(description = "The goal of this test is to create pet and check if returned Pet object is the same", priority = 1)
    public void givenNonExistingPetWhenDeletingPetThenPetNotFoundTest() {
        new DeletePetEndpoint().setPetId(nonExistingPetId).sendRequest().assertStatusCode(HttpStatus.SC_NOT_FOUND);
    }

}