package com.cg.adressbook;

import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
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

    private Response addPersonToJsonServer(PersonDetails personDetails) {
        String jsonFile=new Gson().toJson(personDetails);
        RequestSpecification requestSpecification=RestAssured.given();
        requestSpecification.header("Content-Type","application/json");
        requestSpecification.body(jsonFile);
        return requestSpecification.post("/addressbook");
    }
    @Test
    public void givenMultipleContactsWhenAddedShouldMatchCount(){
        PersonDetails[] personDetails=getPersonDetails();
       AddressBookRESTAssured addressBookRESTAssured=new AddressBookRESTAssured(Arrays.asList(personDetails));
        long count= addressBookRESTAssured.countEntries();
        Assert.assertEquals(1,count);

    }

    @Test
    public void givenListOfContactWhenAddedMatch201StatusCodeAndCount() {
       PersonDetails[] personDetailsarray=getPersonDetails();
        AddressBookRESTAssured addressBookRESTAssured=new AddressBookRESTAssured((Arrays.asList(personDetailsarray)));
       PersonDetails[] newEmployeePayroll={new PersonDetails("Frank","Ocean","Baltimore Street","City of Baltimore","Barcelona", 121256L,"9977553311","froc1@sd.com"),
               new PersonDetails("Sally","Muse","tokyo Street","Tokyo City","Tokyo", 127856L,"9567662311","samu5@sd.com")
        };

       for(PersonDetails personDetails:newEmployeePayroll){
           Response response=addPersonToJsonServer(personDetails);
           int status=response.getStatusCode();
           Assert.assertEquals(201,status);
           personDetails=new Gson().fromJson(response.asString(),PersonDetails.class);
           System.out.println(personDetails.toString());
           addressBookRESTAssured.addPersonToList(personDetails);
       }

        long check= addressBookRESTAssured.countEntries();
        Assert.assertEquals(3,check);

    }

    @Test
    public void givenToBeUpdatedContact_WhenUpdated_ShouldMatch200Response() {
        PersonDetails[] personDetailsArray=getPersonDetails();
        AddressBookRESTAssured addressBookRESTAssured=new AddressBookRESTAssured((Arrays.asList(personDetailsArray)));
       addressBookRESTAssured.updateDataInMemory("Sally","city","Rome");
        PersonDetails  personDetails=addressBookRESTAssured.getContactData("Sally");
        int id=addressBookRESTAssured.getContactID("Sally");

        String jsonFile=new Gson().toJson(personDetails);
        RequestSpecification requestSpecification=RestAssured.given();
        requestSpecification.header("Content-Type","application/json");
        requestSpecification.body(jsonFile);
        Response response= requestSpecification.put("/addressbook/"+id);
        int statuscode=response.getStatusCode();
        Assert.assertEquals(200,statuscode);
    }

}

