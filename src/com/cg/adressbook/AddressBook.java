package com.cg.adressbook;

import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class AddressBook {
    private TreeMap<String, ArrayList<PersonDetails>> adrbook = new TreeMap<String, ArrayList<PersonDetails>>();
    private Scanner scanner = new Scanner(System.in);
    private final int STATE = 2;
    private final int CITY = 1;

    public void addressBookManager() throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        int choice = 0;
        do {
            System.out.println("Please enter your action\n" + "1.Add a addressbook\n" + "2.Access a addressbook\n" +
                    "3. Search for persons in a city/state\n" + "4. Number of persons in city/state\n" +
                    "5. Add Addressbook to file\n"+ "6. View Contents of file\n"+"7. Add addressbook to csv File"+
                    "8. View Contents of csv file"+"9. Exit");
            choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1: {
                    System.out.println("Please enter address book name");
                    String bookname = scanner.nextLine();
                    adrbook.put(bookname, new ArrayList<PersonDetails>());
                    System.out.println("created successfully");
                    break;
                }
                case 2: {
                    if (adrbook.isEmpty()) System.out.println("no address book found, please create one please");
                    else {

                        System.out.println("Enter the address book name");
                        String bookname = scanner.nextLine();
                        if (adrbook.containsKey(bookname))
                            PersonDetailsManager(adrbook.get(bookname));
                        else System.out.println("please enter a valid book name");
                    }
                    break;
                }

                case 3: {
                    checkPersonByStateorCity();
                    break;
                }

                case 4: {
                    countPersonByStateorCity();
                    break;
                }

                case 5: {
                   AddressBookFileHandling addressBookFileHandling=new AddressBookFileHandling();
                    System.out.println("Enter the addressbook name you want to add to file");
                    String bookname=scanner.nextLine();
                    if(adrbook.containsKey(bookname))
                        addressBookFileHandling.AddressBookWriteToFile(adrbook.get(bookname),bookname);
                    else System.out.println("Addressbook does not exist");
                    break;
                }
                case 6:{
                    AddressBookFileHandling addressBookFileHandling=new AddressBookFileHandling();
                    addressBookFileHandling.AddressBookReadFromFile();
                    break;
                }

                case 7: {
                    AddressBookCSVOperation addressBookCSVOperation=new AddressBookCSVOperation();
                    System.out.println("Enter the addressbook name you want to add to file");
                    String bookname=scanner.nextLine();
                    if(adrbook.containsKey(bookname))
                      addressBookCSVOperation.writeAddressBookToCSV(adrbook.get(bookname));
                    else System.out.println("Addressbook does not exist");
                    break;
                }
                case 8:{
                    AddressBookCSVOperation addressBookCSVOperation=new AddressBookCSVOperation();
                    addressBookCSVOperation.readAddressBookFromCSV();
                    break;
                }
                default:
                    System.out.println("thank you for using the application");

            }

        } while (!(choice == 9));
    }


    public ArrayList<PersonDetails> setDetails(ArrayList<PersonDetails> newDetails) {

        boolean check;
        String first_name, last_name;
        do {
            System.out.println("Enter your first name");
            first_name = scanner.nextLine();
            System.out.println("Enter your last name");
            last_name = scanner.nextLine();
            PersonDetails checkduplicatename = new PersonDetails(first_name, last_name);
            if (checkduplicatename.equals(newDetails)) {
                System.out.println("Name exists...please enter non duplicate name");
                check = true;
            } else check = false;
        } while (check);

        System.out.println("Enter your address/block number/street name ");
        String address = scanner.nextLine();
        System.out.println("Enter your city");
        String city = scanner.nextLine();
        System.out.println("Enter your state");
        String state = scanner.nextLine();
        System.out.println("Enter your zip/pincode");
        Long zip = scanner.nextLong();
        scanner.nextLine();
        System.out.println("Enter your phone number");
        String phone_number = scanner.nextLine();
        System.out.println("Enter your email_id");
        String email_id = scanner.nextLine();

        PersonDetails p = new PersonDetails(first_name, last_name, address, city, state, zip, phone_number, email_id);

        newDetails.add(p);
        System.out.println("Person details added successfully");
        return newDetails;

    }


    public ArrayList<PersonDetails> editDetails(ArrayList<PersonDetails> editDetails, String name) {

        Scanner sc = new Scanner(System.in);
        String fname;
        int i = 0;
        for (PersonDetails per : editDetails) {

            fname = per.getFirst_name();
            if (name.equals(fname)) break;
            i++;
        }

        if ((i) == editDetails.size()) {
            System.out.println("No record found with such name");

        } else {

            System.out.println("Edit the first name");
            String first_name = sc.nextLine();
            System.out.println("Edit the last name");
            String last_name = sc.nextLine();
            System.out.println("Edit the address/block number/street name ");
            String address = sc.nextLine();
            System.out.println("Edit the city");
            String city = sc.nextLine();
            System.out.println("Edit the state");
            String state = sc.nextLine();
            System.out.println("Edit the zip/pincode");
            Long zip = sc.nextLong();
            sc.nextLine();
            System.out.println("Edit the phone number");
            String phone_number = sc.nextLine();
            System.out.println("Edit the email_id");
            String email_id = sc.nextLine();

            PersonDetails newPerson = new PersonDetails(first_name, last_name, address, city, state, zip, phone_number, email_id);
            editDetails.remove(i);
            editDetails.add(i, newPerson);

            System.out.println("Person details edited successfully");


        }
        return editDetails;

    }


    public ArrayList<PersonDetails> removeRecord(ArrayList<PersonDetails> removeDetails, String name) {

        Scanner sc = new Scanner(System.in);
        String fname;
        int i = 0;
        for (PersonDetails per : removeDetails) {

            fname = per.getFirst_name();
            if (name.equals(fname)) {
                break;
            }
            i++;
        }

        if ((i) == removeDetails.size()) {
            System.out.println("No record found with such name");

        } else {


            removeDetails.remove(i);


            System.out.println("Person details removed successfully");

        }
        return removeDetails;
    }

    public void viewDetails(ArrayList<PersonDetails> viewPersons) {
        System.out.println("want to view contact details sorted by which f the following filed\n" + "1. Name\n" + "2. City\n" + "3. State\n" + "4. Zip");
        int choice = scanner.nextInt();
        scanner.nextLine();
        switch (choice) {

            case 1: {

                viewPersons.sort(Comparator.comparing(PersonDetails::getFirst_name).thenComparing(PersonDetails::getLast_name));
                System.out.println("Contacts in address book sort by name is shown");
                viewPersons.stream().forEach(viewPerson -> System.out.println(viewPerson.toString()));
                break;
            }
            case 2: {
                Comparator<PersonDetails> comparator = Comparator.comparing(PersonDetails::getCity);
                viewPersons.sort(comparator);
                System.out.println("Contacts in address book sort by city is shown");
                viewPersons.stream().forEach(viewPerson -> System.out.println(viewPerson.toString()));
                break;
            }

            case 3: {
                Comparator<PersonDetails> comparator = Comparator.comparing(PersonDetails::getState);
                viewPersons.sort(comparator);
                System.out.println("Contacts in address book sort by state is shown");
                viewPersons.stream().forEach(viewPerson -> System.out.println(viewPerson.toString()));
                break;
            }
            case 4: {
                Comparator<PersonDetails> comparator = Comparator.comparing(PersonDetails::getZip);
                viewPersons.sort(comparator);
                System.out.println("Contacts in address book sort by state is shown");
                viewPersons.stream().forEach(viewPerson -> System.out.println(viewPerson.toString()));
                break;
            }

        }
    }

    public void PersonDetailsManager(ArrayList<PersonDetails> personDetails) {

        int choice;
        String name, adrbookname;

        do {
            System.out.println("1.Add a entry to addressbook");
            System.out.println("2.Edit a entry of addressbook");
            System.out.println("3. Delete a entry of addressbook");
            System.out.println("4. View a entry of addressbook");
            System.out.println("5. Exit the address book");

            System.out.println("Enter your choice");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1: {
                    personDetails = setDetails(personDetails);
                    break;
                }

                case 2: {
                    if (personDetails.isEmpty()) System.out.println("Addressbook is empty,please add details");
                    else {
                        System.out.println("Enter first name of person whose record is to be edited");
                        name = scanner.nextLine();
                        personDetails = editDetails(personDetails, name);
                    }
                    break;
                }

                case 3: {
                    if (personDetails.isEmpty()) System.out.println("Addressbook is emppty,please add details");
                    else {
                        System.out.println("Enter first name of person whose record is to be remove");
                        name = scanner.nextLine();
                        personDetails = removeRecord(personDetails, name);
                    }
                    break;
                }

                case 4: {
                    if (personDetails.isEmpty()) System.out.println("Addressbook is emppty,please add details");
                    else {
                        viewDetails(personDetails);
                    }
                    break;
                }

            }

        } while (choice != 5);


    }

    public ArrayList<String> checkPersonByStateorCity() {
        System.out.println("Do you want to search names by\n" + "1. City\n" + "2. State");
        int choice = scanner.nextInt();
        scanner.nextLine();
        ArrayList<String> ListofPerson = new ArrayList<>();
        switch (choice) {

            case 1: {
                System.out.println("Enter the city name");
                String cityname = scanner.nextLine();
                if (adrbook.isEmpty())
                    System.out.println("No adress book found please enter one and add contact there");
                else {
                    adrbook.entrySet().stream().forEach(t -> t.getValue().stream().filter(p -> p.getCity().compareTo(cityname) == 0).
                            forEach(p -> ListofPerson.add(p.getFirst_name().concat(" ".concat(p.getLast_name())))));
                    if (ListofPerson.isEmpty()) System.out.println("No record found");
                    else viewPersonByStateorCity(ListofPerson, cityname, CITY);
                }

                break;

            }

            case 2: {
                System.out.println("Enter the state name");
                String statename = scanner.nextLine();
                if (adrbook.isEmpty())
                    System.out.println("No adress book found please enter one and add contact there");
                else {
                    adrbook.entrySet().stream().forEach(t -> t.getValue().stream().filter(p -> p.getState().compareTo(statename) == 0).
                            forEach(p -> ListofPerson.add(p.getFirst_name().concat(" ".concat(p.getLast_name())))));
                    if (ListofPerson.isEmpty()) System.out.println("No record found");
                    else viewPersonByStateorCity(ListofPerson, statename, STATE);
                }
                break;
            }

        }
        System.out.println("List of persons living in your searched area are\n");
        ListofPerson.stream().forEach(l -> System.out.println(l + "\n"));
        return ListofPerson;
    }

    public void viewPersonByStateorCity(ArrayList<String> ListofPersons, String name, int choice) {
        TreeMap<String, ArrayList<String>> CityDictionary = new TreeMap<String, ArrayList<String>>();
        TreeMap<String, ArrayList<String>> StateDictionary = new TreeMap<String, ArrayList<String>>();
        switch (choice) {
            case 1: {
                CityDictionary.put(name, ListofPersons);
                System.out.println("Want to view city dictionary\n" + "yes\n" + "2. no\n");
                String answer = scanner.nextLine();
                if (answer.toLowerCase().compareTo("yes") == 0)
                    CityDictionary.entrySet().stream().forEach(c -> System.out.println(c.getKey() + "---->" + c.getValue()));
                break;
            }

            case 2: {
                StateDictionary.put(name, ListofPersons);
                System.out.println("Want to view state dictionary\n" + "yes\n" + "2. no\n");
                String answer = scanner.nextLine();
                if (answer.toLowerCase().compareTo("yes") == 0)
                    StateDictionary.entrySet().stream().forEach(c -> System.out.println(c.getKey() + "---->" + c.getValue()));
                break;
            }
        }

    }

    public void countPersonByStateorCity() {
        long[] count = {0};
        System.out.println("Do you want to count by\n" + "1. City\n" + "2. State");
        int choice = scanner.nextInt();
        scanner.nextLine();
        switch (choice) {

            case 1: {
                System.out.println("Enter the city name");
                String cityname = scanner.nextLine();
                if (adrbook.isEmpty())
                    System.out.println("No adress book found please enter one and add contact there");
                else {
                    adrbook.entrySet().stream().forEach(t -> {
                        count[0] = count[0] + t.getValue().stream().filter(p -> p.getCity().compareTo(cityname) == 0).count();
                    });
                }
                if (count[0] == 0) System.out.println("No record found");
                else System.out.println("Number of persons in " + cityname + " " + count[0]);
                break;
            }


            case 2: {
                System.out.println("Enter the state name");
                String statename = scanner.nextLine();
                if (adrbook.isEmpty())
                    System.out.println("No adress book found please enter one and add contact there");
                else {
                    adrbook.entrySet().stream().forEach(t -> count[0] = count[0] + t.getValue().stream().filter(p -> p.getCity().compareTo(statename) == 0).count());
                }
                if (count[0] == 0) System.out.println("No record found");
                else System.out.println("Number of persons in " + statename + " " + count[0]);
                break;
            }

        }
    }

    public static void main(String[] args) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {

        // TODO Auto-generated method stub
        System.out.println("Welcome to the addressbook\n");
        AddressBook addressBook = new AddressBook();
        addressBook.addressBookManager();

    }
}
