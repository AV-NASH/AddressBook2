package com.cg.adressbook;

import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class AddressBookCSVOperation {

    Path path = Paths.get("C:\\Users\\Avinash\\IdeaProjects\\AddressBookProblem\\src\\com\\cg\\adressbook\\CSVFile.csv");
    public void userInterface(TreeMap<String, ArrayList<PersonDetails>> adrbook) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        Scanner scanner=new Scanner(System.in);
        System.out.println("Please choose your action"+"1. add addressbook to json file"+"2. read json file");
        int choice=scanner.nextInt();
        scanner.nextLine();
        switch (choice){
            case 1:{System.out.println("Enter the addressbook name you want to add to file");
                String bookname=scanner.nextLine();
                if(adrbook.containsKey(bookname))
                    writeAddressBookToCSV(adrbook.get(bookname));
                else System.out.println("Addressbook does not exist");
                break;}
            case 2:{ readAddressBookFromCSV();
                break;
            }
        }
    }

    public void writeAddressBookToCSV(ArrayList<PersonDetails> personDetailsArrayList) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {

            Writer writer= Files.newBufferedWriter(path);

        StatefulBeanToCsv<PersonDetails> personDetailsStatefulBeanToCsv=new StatefulBeanToCsvBuilder(writer)
                .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER).build();
        personDetailsStatefulBeanToCsv.write(personDetailsArrayList);
        writer.close();
    }
    public void readAddressBookFromCSV() throws IOException {
        try(
        Reader reader=Files.newBufferedReader(path);)
        {

        CsvToBean<AddressBookCSVUser> csvToBean=new CsvToBeanBuilder(reader).withType(AddressBookCSVUser.class)
                .withIgnoreLeadingWhiteSpace(true).build();


        Iterator<AddressBookCSVUser> addressBookCSVUserIterator=csvToBean.iterator();

        while (addressBookCSVUserIterator.hasNext()){
            System.out.println("hi bitch");
            AddressBookCSVUser addressBookCSVUser=addressBookCSVUserIterator.next();
            System.out.println(addressBookCSVUser.toString());
        }

        }
    }
}
