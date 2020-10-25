package com.cg.adressbook;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeMap;

public class AddressBookJsonOperation {
    Scanner scanner=new Scanner(System.in);
    Path path= Paths.get("C:\\Users\\Avinash\\IdeaProjects\\AddressBookProblem\\src\\com\\cg\\adressbook\\JSONFile.json");
    public void userInterface(TreeMap<String, ArrayList<PersonDetails>> adrbook) throws IOException {
        System.out.println("Please choose your action"+"1. add addressbook to json file"+"2. read json file");
        int choice=scanner.nextInt();
        scanner.nextLine();
        switch (choice){
            case 1:{System.out.println("Enter the addressbook name you want to add to file");
                String bookname=scanner.nextLine();
                if(adrbook.containsKey(bookname))
                    addAddressBookTOJson(adrbook.get(bookname));
                else System.out.println("Addressbook does not exist");
                break;}
            case 2:{ readAddressBookJsonFile();
                break;
            }
        }
    }

    public void addAddressBookTOJson(ArrayList<PersonDetails> personDetailsArrayList) throws IOException {
        try(BufferedWriter bufferedWriter= Files.newBufferedWriter(path)){
            Gson gson=new Gson();
            gson.toJson(personDetailsArrayList,bufferedWriter);
        }
    }

    public void readAddressBookJsonFile() throws IOException {
        try(BufferedReader bufferedReader= Files.newBufferedReader(path)){
            Gson gson=new Gson();

          ArrayList<PersonDetails> personDetails= gson.fromJson(bufferedReader,new TypeToken<ArrayList<PersonDetails>>(){}.getType());
          personDetails.forEach(p-> System.out.println(p.toString()));
        }
    }
}
