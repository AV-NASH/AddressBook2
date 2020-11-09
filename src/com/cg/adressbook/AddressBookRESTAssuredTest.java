package com.cg.adressbook;

import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class AddressBookRESTAssuredTest {
    @Before
    public void setUp() throws Exception {
        RestAssured.baseURI="http://localhost";
        RestAssured.port=3000;
    }
    private PersonDetails[] getPersonDetails() {
        Response response=RestAssured.get("/addressbook");
        System.out.println(response.asString());
        PersonDetails[]  personDetails=new Gson().fromJson(response.asString(),PersonDetails[].class);

        return personDetails;
    }
    @Test
    public void givenMultipleContactsWhenAddedShouldMatchCount(){
        PersonDetails[] personDetails=getPersonDetails();
       AddressBookRESTAssured addressBookRESTAssured=new AddressBookRESTAssured(Arrays.asList(personDetails));
        long count= addressBookRESTAssured.countEntries();
        Assert.assertEquals(1,count);

    }

}

