package pl.javastart.restassured.main.rop;

import io.restassured.response.Response;
import org.assertj.core.api.Assertions;

import java.lang.reflect.Type;

// parameter E - will define Endpoint class, for example: CreatePetEndpoint
// parameter M - will define POJO class for the response
public abstract class BaseEndpoint<E, M> {

    //field to store response
    protected Response response;

    //method used to return type of the POJO model
    protected abstract Type getModelType();

    // method used to send request, returns E parameter - endpoint class
    public abstract E sendRequest();

    // method used to return response success code
    protected abstract int getSuccessStatusCode();

    // method returns response as a model, equal to typing response.as(Pet.class)
    public M getResponseModel() {
        return response.as(getModelType());
    }

    // method for verification of the status code, return E parameter - endpoint class
    public E assertStatusCode(int statusCode)
    {
        Assertions.assertThat(response.getStatusCode()).as("Status code").isEqualTo(statusCode);
        return (E)this;
    }

}
