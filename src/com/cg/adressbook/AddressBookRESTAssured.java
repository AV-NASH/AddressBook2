package com.cg.adressbook;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class AddressBookRESTAssured<E> {
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
    public void updateDataInMemory(String name, String field, E value) {
        personDetailsArrayList.stream().filter(p->p.getFirst_name().equals(name)).forEach(p->p.updateWithField(field,value));

    }

    public PersonDetails getContactData(String name) {
        PersonDetails reqPersonDetails=null;
      for(PersonDetails personDetails:personDetailsArrayList){
         if(personDetails.getFirst_name().equals(name)) reqPersonDetails=personDetails;
      }
      return reqPersonDetails;
    }

    public ArrayList<PersonDetails> getPersonDetailsArrayList() {
        return personDetailsArrayList;
    }

    public int getContactID(String name) {
      int id=0;
        for(PersonDetails personDetails:personDetailsArrayList){
            id++;
            if(personDetails.getFirst_name().equals(name)) break;
        }
        return id;
    }
}
