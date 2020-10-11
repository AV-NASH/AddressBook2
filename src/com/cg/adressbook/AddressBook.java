package com.cg.adressbook;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeMap;

public class AddressBook {
    private TreeMap<String, ArrayList<PersonDetails>> adrbook= new TreeMap<String,ArrayList<PersonDetails>>();
   private Scanner scanner =new Scanner(System.in);

    public void addressBookManager() {
        int choice=0;
        do{
            System.out.println("Please enter your action\n"+"1.Add a addressbook\n"+"2.Access a addressbook\n"+"3. Exit");
            choice=scanner.nextInt();
            scanner.nextLine();
            switch (choice){
                case 1:{
                       System.out.println("Please enter address book name");
                       String bookname=scanner.nextLine();
                       adrbook.put(bookname,new ArrayList<PersonDetails>());
                       System.out.println("created successfully");
                       break;
                }
                case 2:{
                    if(adrbook.isEmpty()) System.out.println("no address book found, please creae one please");
                    else{

                        System.out.println("Enter the address book name");
                        String bookname=scanner.nextLine();
                       if( adrbook.containsKey(bookname))
                       PersonDetailsManager( adrbook.get(bookname));
                        else System.out.println("please enter a valid book name");
                    }
                    break;
                }
                default:System.out.println("thank you for using the application");

            }

        }while(!(choice==3));
    }




    public ArrayList<PersonDetails> setDetails(ArrayList<PersonDetails> newDetails) {

        System.out.println("Enter your first name");
      String  first_name= scanner.nextLine();
        System.out.println("Enter your last name");
        String last_name= scanner.nextLine();
        System.out.println("Enter your address/block number/street name ");
        String address= scanner.nextLine();
        System.out.println("Enter your city");
        String city= scanner.nextLine();
        System.out.println("Enter your state");
        String state= scanner.nextLine();
        System.out.println("Enter your zip/pincode");
        Long zip= scanner.nextLong();
        scanner.nextLine();
        System.out.println("Enter your phone number");
        String phone_number= scanner.nextLine();
        System.out.println("Enter your email_id");
        String email_id= scanner.nextLine();

        PersonDetails p=new PersonDetails(first_name, last_name, address, city, state, zip, phone_number,email_id);

        newDetails.add(p);
        System.out.println("Person details added successfully");
        return newDetails;

    }


    public ArrayList<PersonDetails> editDetails(ArrayList<PersonDetails> editDetails,String name) {

        Scanner sc=new Scanner(System.in);
        String fname;
        int i=0;
        for(PersonDetails per:editDetails) {

            fname=per.getFirst_name();
            if(name.equals(fname)) break;
            i++;
        }

        if((i)==editDetails.size()) {
            System.out.println("No record found with such name");

        }

        else {

            System.out.println("Edit the first name");
            String first_name=sc.nextLine();
            System.out.println("Edit the last name");
            String last_name=sc.nextLine();
            System.out.println("Edit the address/block number/street name ");
            String address=sc.nextLine();
            System.out.println("Edit the city");
            String city=sc.nextLine();
            System.out.println("Edit the state");
            String state=sc.nextLine();
            System.out.println("Edit the zip/pincode");
            Long zip=sc.nextLong();
            sc.nextLine();
            System.out.println("Edit the phone number");
            String phone_number=sc.nextLine();
            System.out.println("Edit the email_id");
            String email_id=sc.nextLine();

            PersonDetails newPerson=new PersonDetails(first_name, last_name, address, city, state, zip, phone_number, email_id);
            editDetails.remove(i);
            editDetails.add(i, newPerson);

            System.out.println("Person details edited successfully");


        }
        return editDetails;

    }


    public ArrayList<PersonDetails> removeRecord(ArrayList<PersonDetails> removeDetails,String name) {

        Scanner sc=new Scanner(System.in);
        String fname;
        int i=0;
        for(PersonDetails per:removeDetails) {

            fname=per.getFirst_name();
            if(name.equals(fname)) {break;}
            i++;
        }

        if((i)==removeDetails.size()) {
            System.out.println("No record found with such name");

        }

        else {


            removeDetails.remove(i);


            System.out.println("Person details removed successfully");

        }
        return removeDetails;
    }

    public  void PersonDetailsManager(ArrayList<PersonDetails> personDetails) {

        int choice;
        String name,adrbookname;

            do {
                System.out.println("1.Add a entry to addressbook");
                System.out.println("2.Edit a entry of addressbook");
                System.out.println("3. Delete a entry of addressbook");
                System.out.println("4. Exit the address book");
                System.out.println("Enter your choice");
                choice=scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1: {
                        personDetails=setDetails(personDetails);
                        break;
                    }

                    case 2: {
                        if(personDetails.isEmpty())System.out.println("Addressbook is emppty,please add details");
                        else{
                        System.out.println("Enter first name of person whose record is to be edited");
                        name=scanner.nextLine();
                        personDetails=editDetails(personDetails,name);}
                        break;
                    }

                    case 3: {
                        if(personDetails.isEmpty())System.out.println("Addressbook is emppty,please add details");
                        else{
                        System.out.println("Enter first name of person whose record is to be remove");
                        name=scanner.nextLine();
                        personDetails=removeRecord(personDetails,name);}
                        break;
                    }

                }

            }while(choice!=4);


    }
    public static void main(String[] args) {

        // TODO Auto-generated method stub
        System.out.println("Welcome to the addressbook\n");
        AddressBook addressBook=new AddressBook();
        addressBook.addressBookManager();

    }
}
