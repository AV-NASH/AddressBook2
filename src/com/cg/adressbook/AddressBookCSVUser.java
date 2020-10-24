package com.cg.adressbook;

import com.opencsv.bean.CsvBindAndSplitByName;
import com.opencsv.bean.CsvBindByName;

public class AddressBookCSVUser {
    @CsvBindByName(column = "FIRST_NAME")
    private String first_name;
    @CsvBindByName(column = "LAST_NAME")
    private String last_name;
    @CsvBindByName(column = "ADDRESS")
    private String address;
    @CsvBindByName(column = "CITY")
    private String city;
    @CsvBindByName(column = "STATE")
    private String state;
    @CsvBindByName(column = "ZIP")
    private Long zip;
    @CsvBindByName(column = "PHONE_NUMBER")
    private String phone_number;
    @CsvBindByName(column = "EMAIL_ID")
    private String email_id;

    @Override
    public String toString() {
        return "first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", zip=" + zip +
                ", phone_number='" + phone_number + '\'' +
                ", email_id='" + email_id + '\'';
    }
}
