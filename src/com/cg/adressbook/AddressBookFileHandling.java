package com.cg.adressbook;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class AddressBookFileHandling {


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

    public void AddressBookReadFromFile() {
        File file = new File("C:\\Users\\Avinash\\IdeaProjects\\AddressBookProblem\\src\\com\\cg\\adressbook\\file.txt");

        try {
                FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line,read="";
            while((line=bufferedReader.readLine())!=null)
                read=read+line+"\n";
            System.out.println(read);


        } catch (FileNotFoundException e) {
            System.out.println("File does not exist, please create one by adding data to file");
        }

        catch(IOException e){

        }


    }
}
