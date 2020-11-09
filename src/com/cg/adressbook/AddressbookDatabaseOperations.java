package com.cg.adressbook;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class AddressbookDatabaseOperations<E> {
    static Statement statement;
    static PreparedStatement preparedStatement;
    ArrayList<PersonDetails> personDetailsArrayList=new ArrayList<PersonDetails>();

    public void userInterface(TreeMap<String, ArrayList<PersonDetails>> adrbook) {

    }

    private void viewDataFromDatabase() {
        try {
            retrieveDataFromDatabase();
        } catch (ErrorInSqlOperation errorInSqlOperation) {
            errorInSqlOperation.getMessage();
        }
        personDetailsArrayList.forEach(personDetails -> personDetails.toString());
    }

     void retrieveDataFromDatabase() throws ErrorInSqlOperation {
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
        throw new ErrorInSqlOperation("Unable to access data...");
        }

    }
    

    public void updateContactInfo(String name, String field, E value) {
        updateDataInMemory(name,field,value);
        try {
            updateDataInDataBase(name,field,value);
        } catch (ErrorInSqlOperation errorInSqlOperation) {
            errorInSqlOperation.getMessage();
        }

    }

    private void updateDataInMemory(String name, String field, E value) {
        try {
            retrieveDataFromDatabase();
        } catch (ErrorInSqlOperation errorInSqlOperation) {
            errorInSqlOperation.getMessage();
        }
        personDetailsArrayList.stream().filter(p->p.getFirst_name().equals(name)).forEach(p->p.updateWithField(field,value));
        
    }

    private int updateDataInDataBase(String name,String field, E value) throws ErrorInSqlOperation {
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
            throw new ErrorInSqlOperation("Unable to access data...");
        }
        return  result;

    }


    public ArrayList<PersonDetails> getPersonDetailsArrayList() {
        return personDetailsArrayList;
    }

    public boolean checkDataSyncWithDataBase(String name) throws ErrorInSqlOperation {
        ArrayList<PersonDetails> personDetailsupdatedlist=new ArrayList<>(  personDetailsArrayList.stream().filter(p->p.getFirst_name().equals(name)).collect(Collectors.toList()));
        return personDetailsupdatedlist.toString().equals(checkParticularRecordinDB(name).toString());
    }

    private ArrayList<PersonDetails> checkParticularRecordinDB(String name) throws ErrorInSqlOperation {
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
            throw new ErrorInSqlOperation("Unable to access data...");
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
            try {
                throw new ErrorInSqlOperation("Unable to connect to database...");
            } catch (ErrorInSqlOperation errorInSqlOperation) {
                errorInSqlOperation.getMessage();
            }
        }
        return connection;
    }

    public ArrayList<PersonDetails> retriveFromDataBaseOnDate(LocalDate startDate, LocalDate endDate) throws ErrorInSqlOperation {
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
            throw new ErrorInSqlOperation("Unable to perform required database operation...");
        }
        return arrayList;
    }

    public ArrayList<PersonDetails> retrieveFromDatabaseOnCityState(String field, String value) throws ErrorInSqlOperation {
        ArrayList<PersonDetails> arrayList=new ArrayList<>();
        String query="select contact_details.first_name,contact_details.last_name,contact_details.phone, contact_details.email,address_details.address,address_details.city,address_details.state,address_details.zip" +
                " from address_details inner join contact_details on address_details.id=contact_details.id" +
                " where "+field+" =?;";
        Connection connection=getConnection();
        try {
            preparedStatement=connection.prepareStatement(query);
            preparedStatement.setString(1,value);
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
            throw new ErrorInSqlOperation("Unable to perform required database operation...");
        }
        return arrayList;
    }

    public synchronized void addAddressbookToDataBase(String firstname, String lastname, String address, String city, String state, int zip, String phone, String email) throws ErrorInSqlOperation, ErrorInRollbackException {
        int rowaffected=0;
        Connection connection=getConnection();
        try {
            retrieveDataFromDatabase();
        } catch (ErrorInSqlOperation errorInSqlOperation) {
            errorInSqlOperation.getMessage();
        }
        String query1=String.format("insert into contact_details values (%s,'%s','%s','%s','%s','%s');",
                (personDetailsArrayList.isEmpty())?1:((int)personDetailsArrayList.size()+1),firstname,lastname,phone,email,Date.valueOf(LocalDate.now()));
        try {
            connection.setAutoCommit(false);
            statement= connection.createStatement();
            rowaffected=statement.executeUpdate(query1);
            connection.commit();
        } catch (SQLException throwables) {

            try {
                connection.rollback();
            } catch (SQLException e) {
                throw new ErrorInRollbackException("Unable to rollback data...");
            }
        }

        String query2=String.format("insert into address_details values (%s,'%s','%s','%s',%s);",
                (personDetailsArrayList.isEmpty())?1:((int)personDetailsArrayList.size()+1),address,city,state,zip);
        try {
            connection.setAutoCommit(false);
            statement= connection.createStatement();
            rowaffected=statement.executeUpdate(query2);
            connection.commit();
        } catch (SQLException throwables) {

            try {
                connection.rollback();
            } catch (SQLException e) {
                throw new ErrorInRollbackException("Unable to rollback data...");
            }
        }
        finally {
            try {
                connection.close();
            } catch (SQLException throwables) {
                throw new ErrorInSqlOperation("Unable to close the connection...");
            }
        }

    }

    public boolean checkAddedDataInDataBase(String first_name) {
        try {
            personDetailsArrayList= checkParticularRecordinDB(first_name);
        } catch (ErrorInSqlOperation errorInSqlOperation) {
            errorInSqlOperation.getMessage();
        }
        if(personDetailsArrayList.isEmpty()) return false;
       else return true;
    }

    public void addMultipleEntriesToDB(ArrayList<PersonDetails> personDetailsArrayList) throws ThreadInterruptionException {
        int[] successfulop = new int[1];successfulop[0]=0;
        personDetailsArrayList.stream().forEach(p->{
            Runnable runnable=()->{
                try {
                    addAddressbookToDataBase(p.getFirst_name(),p.getLast_name(),p.getAddress(),p.getCity(),p.getState(),p.getZip().intValue(),p.getPhone_number(),p.getEmail_id());
                } catch (ErrorInSqlOperation errorInSqlOperation) {
                    errorInSqlOperation.getMessage();
                } catch (ErrorInRollbackException e) {
                    e.getMessage();
                }
                successfulop[0]++;
            };
            Thread thread=new Thread(runnable);
            thread.start();
        });
        while(successfulop[0]<personDetailsArrayList.size()) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                throw new ThreadInterruptionException("Process interrupted for unknown reasons...");
            }
        }
    }
}
