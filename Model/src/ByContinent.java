import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class ByContinent {
    public static void main(String[] args) throws Exception{
        String[] continent = {"Asia","Europe","Africa","North America","South America","Oceania"};
        File files[] = new File[6];
        Scanner scans[] = new Scanner[6];
        File output[] = new File[6];
        PrintWriter printers[] = new PrintWriter[6];
        for (int i = 0; i < 6; i++) {
            String path1 = "E:/Global Model/Continent/";
            String path2 = ".csv";
            String path = path1 + continent[i] + path2;
            files[i] = new File(path);
            scans[i] = new Scanner(files[i]);
            String outputpath = path1 + continent[i]+" Import"+ path2;
            output[i] = new File(outputpath);
            printers[i] = new PrintWriter(output[i]);
        }
        File Global = new File("E:/Global Model/Comp/GI.csv");
        Scanner GlobalScan = new Scanner(Global);
        ArrayList<String> Country = new ArrayList<>();
        ArrayList<int[]> Dates = new ArrayList<>();
        String Line;
        for (int i = 0; i < 250; i++) {
            Dates.add(new int[2]);
        }
        ArrayList<String> arr = new ArrayList<>();
        int Countrycounter = 0;
        while (GlobalScan.hasNext()) {
            Line = GlobalScan.nextLine();
            int counter = 0;
            String Countryname = Line.substring(0,Line.indexOf(","));
            Country.add(Countryname);
            Line = Line.substring(Line.indexOf(",")+1);
            arr.add(Line);
            System.out.println(Countryname);
            do{
                String Coun = Line.substring(0,Line.indexOf(","));
                Line = Line.substring(Line.indexOf(",")+1);
                int n;
                if(!Coun.isEmpty()){
                    n = (int)Double.parseDouble(Coun);
                }
                else {
                    n = 0;
                }
                int buffer[] = Dates.get(Countrycounter);
                buffer[counter] = n;
                System.out.print(n+",");
                Dates.set(Countrycounter,buffer);
                counter++;
            }while(Line.contains(","));
            Countrycounter++;
            System.out.println();
        }
        for (int i = 0; i < Dates.size(); i++) {
            //System.out.print(Country.get(i)+",");
            for (int i1 = 0; i1 < Dates.get(i).length; i1++) {
                System.out.print(Dates.get(i)[i1]+",");
            }
            System.out.println();
        }
        File InOne = new File("E:/Global Model/Comp/Big.csv");
        PrintWriter PrintOne = new PrintWriter(InOne);
        for (int i = 0; i < 6; i++) {
            System.out.println(continent[i]);
            ArrayList<String> COUNTRIES = new ArrayList<>();
            ArrayList<Integer> DateNum = new ArrayList<>();
            while (scans[i].hasNext()) {
                String line = scans[i].nextLine();
                String thisCountry = line.substring(0,line.indexOf(","));
                System.out.println(thisCountry);
                int ind = Country.indexOf(thisCountry);
                System.out.println(ind);
                if(ind!=-1){
                    System.out.print(Country.get(ind)+",");
                    printers[i].print(Country.get(ind)+",");
                    PrintOne.print(Country.get(ind)+",");

                    System.out.println(arr.get(ind));
                    printers[i].print(arr.get(ind)+",");
                    PrintOne.print(arr.get(ind)+",");

                    System.out.println();
                    printers[i].println();
                    PrintOne.println();
                }
            }
            printers[i].close();
            PrintOne.println();
        }
        PrintOne.close();
    }
}
