package com.cg.adressbook;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeMap;

public class AddressbookDatabaseOperations {
    Statement statement;
    ArrayList<PersonDetails> personDetailsArrayList=new ArrayList<PersonDetails>();
    public void userInterface(TreeMap<String, ArrayList<PersonDetails>> adrbook) {
        Scanner scanner=new Scanner(System.in);
        System.out.println("enter your action\n"+"1. retrieve data\n"+"2. exit");
        int choice =scanner.nextInt();
        switch (choice){
            case 1:{
                viewDataFromDatabase();
            }
        }


    }

    private void viewDataFromDatabase() {
        retrieveDataFromDatabase();
        personDetailsArrayList.forEach(personDetails -> personDetails.toString());
    }

     void retrieveDataFromDatabase() {
        String query="select contact_details.first_name,contact_details.last_name,contact_details.phone," +
                "contact_details.email,address_details.address,address_details.city,address_details.state,address_details.zip" +
                " from address_details inner join contact_details on address_details.id=contact_details.id;";

        Connection connection=getConnection();
        try {
            statement=connection.createStatement();
            ResultSet resultSet=statement.executeQuery(query);

            while(resultSet.next()){

                String firstname=resultSet.getString(1);
                String lastname=resultSet.getString(2);
                String phone=resultSet.getString(3);
                String email=resultSet.getString(4);
                String address=resultSet.getString(5);
                String city=resultSet.getString(6);
                String state=resultSet.getString(7);
                long zip=resultSet.getLong(8);

                personDetailsArrayList.add( new PersonDetails(firstname,lastname,address,city,state,zip,phone,email));
            }
        } catch (Exception throwables) {
         //   System.out.println("cannot retrieve data from database");
            throwables.printStackTrace();
        }

    }



    public  Connection getConnection() {
        String jdbcURL="jdbc:mysql://localhost:3306/addressbook?useSSL=false";
        String userName="root";
        String password="Nuzumaki1@";
        Connection connection = null;
        try{
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("cannot find classpath");
        }
        try {
            connection= DriverManager.getConnection(jdbcURL,userName,password);
        } catch (SQLException e) {
            System.out.println("connection failed");
        }
        return connection;
    }

    public ArrayList<PersonDetails> getPersonDetailsArrayList() {
        return personDetailsArrayList;
    }
}
