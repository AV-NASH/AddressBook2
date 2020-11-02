package com.cg.adressbook;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class AddressbookDatabaseOperations<E> {
    Statement statement;
    PreparedStatement preparedStatement;
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
    

    public void updateContactInfo(String name, String field, E value) {
        updateDataInMemory(name,field,value);
        updateDataInDataBase(name,field,value);
      
    }

    private void updateDataInMemory(String name, String field, E value) {
        retrieveDataFromDatabase();
        personDetailsArrayList.stream().filter(p->p.getFirst_name().equals(name)).forEach(p->p.updateWithField(field,value));
        
    }

    private int updateDataInDataBase(String name,String field, E value) {
        int result=0;
       String  query=" update contact_details inner join address_details on contact_details.id=address_details.id " +
               "set "+field+"=? where contact_details.first_name=?;";
        Connection connection=getConnection();
        try {
            preparedStatement=connection.prepareStatement(query);
            if(field.equals("zip")) preparedStatement.setLong(1,(long)value);
            else preparedStatement.setString(1,(String) value);
            preparedStatement.setString(2,name);
            result= preparedStatement.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("cannot update the database");
        }
        return  result;

    }


    public ArrayList<PersonDetails> getPersonDetailsArrayList() {
        return personDetailsArrayList;
    }

    public boolean checkDataSyncWithDataBase(String name) {
        ArrayList<PersonDetails> personDetailsupdatedlist=new ArrayList<>(  personDetailsArrayList.stream().filter(p->p.getFirst_name().equals(name)).collect(Collectors.toList()));
        return personDetailsupdatedlist.toString().equals(checkParticularRecordinDB(name).toString());
    }

    private ArrayList<PersonDetails> checkParticularRecordinDB(String name) {
        ArrayList<PersonDetails> arrayList=new ArrayList<>();
        String query="select contact_details.first_name,contact_details.last_name,contact_details.phone," +
                "contact_details.email,address_details.address,address_details.city,address_details.state,address_details.zip" +
                " from address_details inner join contact_details on address_details.id=contact_details.id where first_name=?;";
        Connection connection=getConnection();
        try {
            preparedStatement=connection.prepareStatement(query);
            preparedStatement.setString(1,name);
            ResultSet resultSet=preparedStatement.executeQuery();
            while(resultSet.next()){

                String firstname=resultSet.getString(1);
                String lastname=resultSet.getString(2);
                String phone=resultSet.getString(3);
                String email=resultSet.getString(4);
                String address=resultSet.getString(5);
                String city=resultSet.getString(6);
                String state=resultSet.getString(7);
                long zip=resultSet.getLong(8);

                arrayList.add( new PersonDetails(firstname,lastname,address,city,state,zip,phone,email));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return arrayList;
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

    public ArrayList<PersonDetails> retriveFromDataBaseOnDate(LocalDate startDate, LocalDate endDate) {
        ArrayList<PersonDetails> arrayList=new ArrayList<>();
        String query="select contact_details.first_name,contact_details.last_name,contact_details.phone, contact_details.email,address_details.address,address_details.city,address_details.state,address_details.zip" +
                " from address_details inner join contact_details on address_details.id=contact_details.id" +
                " where  date_of_registration between cast(? as date) and cast(? as date);";
        Connection connection=getConnection();
        try {
            preparedStatement=connection.prepareStatement(query);
            preparedStatement.setString(1,startDate.toString());
            preparedStatement.setString(2,endDate.toString());
            ResultSet resultSet=preparedStatement.executeQuery();
            while(resultSet.next()){

                String firstname=resultSet.getString(1);
                String lastname=resultSet.getString(2);
                String phone=resultSet.getString(3);
                String email=resultSet.getString(4);
                String address=resultSet.getString(5);
                String city=resultSet.getString(6);
                String state=resultSet.getString(7);
                long zip=resultSet.getLong(8);

                arrayList.add( new PersonDetails(firstname,lastname,address,city,state,zip,phone,email));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return arrayList;
    }
}
