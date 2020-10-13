package com.cg.adressbook;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PersonDetails {
    private String first_name;
    private String last_name;
    private String address;
    private String city;
    private String state;
    private Long zip;
    private String phone_number;
    private String email_id;


    public PersonDetails(String first_name, String last_name, String address, String city, String state, Long zip,
                         String phone_number, String email_id) {
        super();
        this.first_name = first_name;
        this.last_name = last_name;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.phone_number = phone_number;
        this.email_id=email_id;
    }
    public PersonDetails(String first_name, String last_name) {
        super();
        this.first_name = first_name;
        this.last_name = last_name;
        this.address = null;
        this.city = null;
        this.state = null;
        this.zip = null;
        this.phone_number = null;
        this.email_id=null;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Long getZip() {
        return zip;
    }

    public void setZip(Long zip) {
        this.zip = zip;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getEmail_id() {
        return email_id;
    }

    public void setEmail_id(String email_id) {
        this.email_id = email_id;
    }

    @Override
    public boolean equals(Object obj) {
        boolean result;
        ArrayList<PersonDetails> personDetailsArray=(ArrayList<PersonDetails>)obj;
        if((personDetailsArray.stream().filter(p->(p.getFirst_name().compareTo(this.getFirst_name())==0)&&(p.getLast_name().compareTo(this.getLast_name())==0)).count())==0) return false;
        else return true;

    }

    @Override
    public String toString(){
        return first_name+" "+last_name+" || "+address+" || "+city+" || "+state+" || "+zip+" || "+phone_number+" || "+email_id;
    }

}
