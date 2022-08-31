package org.sample;

import java.io.*;
import java.util.*;

public class Coursework {

    public List<String[]> fileData;

    // Please do not hardcode the filename into the program! It's ok to change the program to prompt the user
    // for the filename, but when I download assignments from LC the filenames get changed so hardcoding can cause problems.
    public Coursework(String filename) {
        String COMMA_DELIMITER = ",";
        
        List<String[]> records = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            // Skip first line
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] values = line.split(COMMA_DELIMITER);
                // change how dates are formatted to make sorting easier
                String[] newDate = values[4].split("\\/");
                values[4] = newDate[2]+"-"+newDate[1]+"-"+newDate[0];
                records.add(values);
            }
        } catch (Exception e){
            System.out.println("File could not be found");
        }

        this.fileData = records;

        // Sort the file data by IDs
        Collections.sort(fileData, new Comparator<String[]>() {
            @Override
            public int compare(String[] o1, String[] o2) {
                return Integer.valueOf(o1[0]).compareTo(Integer.valueOf(o2[0]));
            }
        });
    }

    //A main method that will take command line arguments to call the given methods.
    //args [0] = data file filename
    //args [1] = customer ID
    //args [2] = city c -- as a String e.g. "Aylesbury"
    //args [3] = n for the customers of the most recent n orders
    public static void main(String[] args) {
        // add validation of args -- please do not change the orderings of these
        Coursework cw = new Coursework(args[0]); // add arguments if needed
        try {
            System.out.println(cw.getCustomer(Integer.parseInt(args[1])));
        } catch (Exception e){
            System.out.println("Something went wrong trying to find that user.");
        }
        System.out.println();
        try{
            System.out.println(cw.getCountOfCustomersWhoLiveIn(args[2]));
        }catch (Exception e){
            System.out.println("Something went wrong with that location.");
        }
        System.out.println();
        try {
            String[] customers = cw.getCustomersForMostRecentOrdersSlow(Integer.parseInt(args[3]));
            for (int i = 0; i < Integer.parseInt(args[3]); i++) {
                System.out.println(customers[i] + ",");
            }
        } catch (Exception e){
            System.out.println("Something went wrong with that number of most recent orders.");
        }
        System.out.println();
    }

//    Your application will provide a method to return details of a customer chosen via the ID
    public String getCustomer(int id){
        String customer = fileData.get(id-1)[0] +" "+fileData.get(id-1)[1] +" "+fileData.get(id-1)[2] +" "+fileData.get(id-1)[3] +" "+fileData.get(id-1)[4];
        return customer;
    }

    //  SLOW VERSION OF METHOD
    public String getCustomerSlow(int id){
            for (String[] record : fileData) {
                if(record[0].equals(Integer.toString(id))){
                    return record[0]+" "+record[1]+" "+record[2]+" "+record[3]+" "+record[4];
                }
            }
        return "User could not be found";
    }

    //  Your application will provide a method to return the number of customers that live in a specific city c
    public int getCountOfCustomersWhoLiveIn(String c){
        int count = 0;
        for (String[] record:fileData) {
            if(record[3].equals(c)){
                count++;
            }
        }
        return count;
    }

    //  SLOW VERSION OF METHOD
    public int getCountOfCustomersWhoLiveInSlow(String c){
        List<List<String>> customersInArea = new ArrayList<>();
        for(int i = 0; i<fileData.size();i++){
            List<String> currentLine = Arrays.asList(fileData.get(i));
            if(Objects.equals(currentLine.get(3), c)){
                customersInArea.add(currentLine);
            };
        }
        return customersInArea.size();
    }

    //  Your application will provide a method to return the n customers that most recently made an order
    public String[] getCustomersForMostRecentOrders(int n){

        List<String[]> fileDataCopy = fileData;


        String[] recentOrders = new String[n];


        for(int i = 0;i<n;i++) {

            String[] currentBiggest = {"0","0","0","0","0"};
            for (String[] data : fileDataCopy) {
                if (data[4].compareTo(currentBiggest[4]) > 0) {
                    currentBiggest = data;

                } else {
                    continue;
                }

            }
            for(int f=0; f<fileDataCopy.size();f++){
                if(fileDataCopy.get(f)[0].equals(currentBiggest[0])){
                    fileDataCopy.remove(f);
                }
            }
            recentOrders[i] = currentBiggest[0]+" "+currentBiggest[1]+" "+currentBiggest[2]+" "+currentBiggest[3]+" "+currentBiggest[4];
            for(int j=0;j<currentBiggest.length;j++){
                currentBiggest[j]="0";
            }
        }

        return recentOrders;
    }

    // SLOW VERSION OF METHOD
    public String[] getCustomersForMostRecentOrdersSlow(int n){
        String[] customers = new String[n];

        Collections.sort(fileData, new Comparator<String[]>() {
            @Override
            public int compare(String[] o1, String[] o2) {
                return o1[4].compareTo(o2[4]);
            }
        });

        for(int i = 0; i<n; i++){
            int fileSize = fileData.size()-i-1;

            customers[i] = fileData.get(fileSize)[0]+" "+fileData.get(fileSize)[1]+" "+fileData.get(fileSize)[2]+" "+fileData.get(fileSize)[3]+" "+fileData.get(fileSize)[4];
        }

        return customers;
    }


}
