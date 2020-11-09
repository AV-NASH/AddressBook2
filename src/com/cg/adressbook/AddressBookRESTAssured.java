package com.cg.adressbook;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class AddressBookRESTAssured {
    ArrayList<PersonDetails> personDetailsArrayList=new ArrayList<>();

    public AddressBookRESTAssured(List<PersonDetails> personDetailsList) {
        personDetailsArrayList=new ArrayList<>(personDetailsList);
    }

    public void userInterface(TreeMap<String, ArrayList<PersonDetails>> adrbook) {
    }

    public long countEntries() {
        return  personDetailsArrayList.stream().count();
    }

    public void addPersonToList(PersonDetails personDetails) {
        personDetailsArrayList.add(personDetails);
    }
}
