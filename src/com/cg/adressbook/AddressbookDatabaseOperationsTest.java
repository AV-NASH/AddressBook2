package com.cg.adressbook;

import org.junit.Assert;
import org.junit.Test;

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
}