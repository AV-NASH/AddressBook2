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
        try {
            addressbookDatabaseOperations.retrieveDataFromDatabase();
        } catch (ErrorInSqlOperation errorInSqlOperation) {
            errorInSqlOperation.getMessage();
        }
        ArrayList<PersonDetails> personDetailsArrayList=addressbookDatabaseOperations.getPersonDetailsArrayList();
        Assert.assertEquals(2,personDetailsArrayList.size());

    }

    @Test
    public void givenDataUpdateInMemoryAndSyncItWithDataBase() {
        AddressbookDatabaseOperations addressbookDatabaseOperations=new AddressbookDatabaseOperations();
        addressbookDatabaseOperations.updateContactInfo("Bill","phone","9988776659");
        boolean check= false;
        try {
            check = addressbookDatabaseOperations.checkDataSyncWithDataBase("Bill");
        } catch (ErrorInSqlOperation errorInSqlOperation) {
            errorInSqlOperation.getMessage();
        }
        Assert.assertTrue(check);

    }

    @Test
    public void givenDateRangeRetrieveDataShouldMatchCount() {
        AddressbookDatabaseOperations addressbookDatabaseOperations=new AddressbookDatabaseOperations();
        ArrayList<PersonDetails> personDetailsArrayList= null;
        try {
            personDetailsArrayList = addressbookDatabaseOperations.retriveFromDataBaseOnDate(LocalDate.of(2020,01,01),LocalDate.of(2020,01,30));
        } catch (ErrorInSqlOperation errorInSqlOperation) {
            errorInSqlOperation.getMessage();
        }
        Assert.assertEquals(1,personDetailsArrayList.size());
    }

    @Test
    public void givenCityOrStateRetrievedDataShouldMatchCount() {
        AddressbookDatabaseOperations addressbookDatabaseOperations=new AddressbookDatabaseOperations();
        ArrayList<PersonDetails> personDetailsArrayList= null;
        try {
            personDetailsArrayList = addressbookDatabaseOperations.retrieveFromDatabaseOnCityState("city","vegas");
        } catch (ErrorInSqlOperation errorInSqlOperation) {
            errorInSqlOperation.getMessage();
        }
        Assert.assertEquals(1,personDetailsArrayList.size());
    }

    @Test
    public void givenDataAddToDataBase() {
        AddressbookDatabaseOperations addressbookDatabaseOperations=new AddressbookDatabaseOperations();
        try {
            addressbookDatabaseOperations.addAddressbookToDataBase("Thomas","Scott","Chi-Town Street","Virginia","Torronto",123211,"7878565634","thsc22@fr.com");
        } catch (ErrorInSqlOperation errorInSqlOperation) {
            errorInSqlOperation.printStackTrace();
        } catch (ErrorInRollbackException e) {
            e.getMessage();
        }
        boolean check=addressbookDatabaseOperations.checkAddedDataInDataBase("Thomas");
        Assert.assertTrue(check);

    }

    @Test
    public void givenMultipleContactsAddToDatabase() {
        AddressbookDatabaseOperations addressbookDatabaseOperations=new AddressbookDatabaseOperations();

        ArrayList<PersonDetails> personDetailsArrayList=new ArrayList<>();
        personDetailsArrayList.add(new PersonDetails("Thomas","Scott","Chi-Town Street","Virginia","Torronto", 123453L,"7878565634","thsc22@fr.com"));
        personDetailsArrayList.add(new PersonDetails("Phife","Kurt","Baltimore Street","Atlanta","George", 566799L,"7878565677","fdgs22@fr.com"));
        personDetailsArrayList.add(new PersonDetails("Jonathan","Clark","West Coast Lane","Paris","Italy", 878565L,"3478565634","yuj2@.com"));
        try {
            addressbookDatabaseOperations.addMultipleEntriesToDB(personDetailsArrayList);
        } catch (ThreadInterruptionException e) {
            e.getMessage();
        }
        try {
            addressbookDatabaseOperations.retrieveDataFromDatabase();
        } catch (ErrorInSqlOperation errorInSqlOperation) {
            errorInSqlOperation.getMessage();
        }
        personDetailsArrayList=addressbookDatabaseOperations.getPersonDetailsArrayList();
        Assert.assertEquals(2,personDetailsArrayList.size());




    }
}