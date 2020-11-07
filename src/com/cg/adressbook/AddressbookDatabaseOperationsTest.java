package com.cg.adressbook;

import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class AddressbookDatabaseOperationsTest {
    @Test
    public void givenDataIDBWhenRetrievedShouldMatchExactCount(){
       AddressbookDatabaseOperations addressbookDatabaseOperations=new AddressbookDatabaseOperations();
       addressbookDatabaseOperations.retrieveDataFromDatabase();
        ArrayList<PersonDetails> personDetailsArrayList=addressbookDatabaseOperations.getPersonDetailsArrayList();
        Assert.assertEquals(2,personDetailsArrayList.size());

    }

    @Test
    public void givenDataUpdateInMemoryAndSyncItWithDataBase() {
        AddressbookDatabaseOperations addressbookDatabaseOperations=new AddressbookDatabaseOperations();
        addressbookDatabaseOperations.updateContactInfo("Bill","phone","9988776659");
        boolean check=addressbookDatabaseOperations.checkDataSyncWithDataBase("Bill");
        Assert.assertTrue(check);

    }

    @Test
    public void givenDateRangeRetrieveDataShouldMatchCount() {
        AddressbookDatabaseOperations addressbookDatabaseOperations=new AddressbookDatabaseOperations();
        ArrayList<PersonDetails> personDetailsArrayList=addressbookDatabaseOperations.retriveFromDataBaseOnDate(LocalDate.of(2020,01,01),LocalDate.of(2020,01,30));
        Assert.assertEquals(1,personDetailsArrayList.size());
    }

    @Test
    public void givenCityOrStateRetrievedDataShouldMatchCount() {
        AddressbookDatabaseOperations addressbookDatabaseOperations=new AddressbookDatabaseOperations();
        ArrayList<PersonDetails> personDetailsArrayList=addressbookDatabaseOperations.retrieveFromDatabaseOnCityState("city","vegas");
        Assert.assertEquals(1,personDetailsArrayList.size());
    }

    @Test
    public void givenDataAddToDataBase() {
        AddressbookDatabaseOperations addressbookDatabaseOperations=new AddressbookDatabaseOperations();
        addressbookDatabaseOperations.addAddressbookToDataBase("Thomas","Scott","Chi-Town Street","Virginia","Torronto",123211,"7878565634","thsc22@fr.com");
        boolean check=addressbookDatabaseOperations.checkAddedDataInDataBase("Thomas");
        Assert.assertTrue(check);

    }

    @Test
    public void givenMultipleContactsAddToDatabase() {
        AddressbookDatabaseOperations addressbookDatabaseOperations=new AddressbookDatabaseOperations();
        long zip=234466;
        ArrayList<PersonDetails> personDetailsArrayList=new ArrayList<>();
        personDetailsArrayList.add(new PersonDetails("Thomas","Scott","Chi-Town Street","Virginia","Torronto", 123453L,"7878565634","thsc22@fr.com"));
        personDetailsArrayList.add(new PersonDetails("Phife","Kurt","Baltimore Street","Atlanta","George", 566799L,"7878565677","fdgs22@fr.com"));
        personDetailsArrayList.add(new PersonDetails("Jonathan","Clark","West Coast Lane","Paris","Italy", 878565L,"3478565634","yuj2@.com"));
        addressbookDatabaseOperations.addMultipleEntriesToDB(personDetailsArrayList);
         personDetailsArrayList=addressbookDatabaseOperations.getPersonDetailsArrayList();
        Assert.assertEquals(2,personDetailsArrayList.size());




    }
}