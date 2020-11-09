package com.cg.adressbook;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeMap;

public class AddressBookFileHandling {

    public void userInterface(TreeMap<String, ArrayList<PersonDetails>> adrbook) throws IOException {
        Scanner scanner=new Scanner(System.in);
        System.out.println("Please choose your action"+"1. add addressbook to json file"+"2. read json file");
        int choice=scanner.nextInt();
        scanner.nextLine();
        switch (choice){
            case 1:{System.out.println("Enter the addressbook name you want to add to file");
                String bookname=scanner.nextLine();
                if(adrbook.containsKey(bookname))
                    AddressBookWriteToFile(adrbook.get(bookname),bookname);
                else System.out.println("Addressbook does not exist");
                break;}
            case 2:{
                try {
                    AddressBookReadFromFile();
                } catch (FileDoNotExistException e) {
                    e.getMessage();
                } catch (ErrorInFileOperationException e) {
                  e.getMessage();
                }
                break;
            }
        }
    }

    public void AddressBookWriteToFile(ArrayList<PersonDetails> personDetailsArrayList, String bookname) throws IOException {
        if (personDetailsArrayList.isEmpty()) System.out.println("List is empty");
        else {
            FileWriter fout = new FileWriter("C:\\Users\\Avinash\\IdeaProjects\\AddressBookProblem\\src\\com\\cg\\adressbook\\file.txt", true);
            BufferedWriter bout = new BufferedWriter(fout);
            String detailes = "Details of Address book "+bookname+"  \n";
            for (PersonDetails personDetails : personDetailsArrayList) {
                detailes = detailes + personDetails.toString() + "\n";
            }

            bout.write(detailes);
            bout.flush();
            bout.close();
            fout.close();
            System.out.println("success");
        }
    }

    public void AddressBookReadFromFile() throws FileDoNotExistException, ErrorInFileOperationException {
        File file = new File("C:\\Users\\Avinash\\IdeaProjects\\AddressBookProblem\\src\\com\\cg\\adressbook\\file.txt");

        try {
                FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line,read="";
            while((line=bufferedReader.readLine())!=null)
                read=read+line+"\n";
            System.out.println(read);


        } catch (FileNotFoundException e) {
            throw new FileDoNotExistException("File does not exist");
        }

        catch(IOException e){
        throw new ErrorInFileOperationException("There was some error in reading the file");
        }


    }
}
