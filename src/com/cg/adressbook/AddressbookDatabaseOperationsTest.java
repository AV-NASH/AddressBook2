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
}