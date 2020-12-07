import java.io.File;
import java.io.FileNotFoundException;
import java.nio.charset.CoderMalfunctionError;
import java.util.ArrayList;
import java.util.Scanner;

public class Flight {

    public static ArrayList<String> CountryName = new ArrayList<>();//Arraylist to store flight code
    public static ArrayList<String> CountyName = new ArrayList<>();//Arraylist to store flight code
    public static ArrayList<String> IATACode = new ArrayList<>();//Arraylist to store flight code

    public static ArrayList<FlightData> Flights[][][][] =null;
    public static ArrayList<String> MenuIndex = new ArrayList<>();
    public static ArrayList<FlightData> Flightlist = new ArrayList<>();
    public static ArrayList<FlightTime>  Date = new ArrayList<>();
    public Flight(){
        File Flightfile = new File(Parameters.ReadPath+"FlightCity/Path4.csv");//PATH2
        File Cityfile = new File(Parameters.ReadPath+"FlightCity/AIR.csv");
        Scanner scan = null;
        try {
            scan = new Scanner(Flightfile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Scanner scan2 = null;
        try {
            scan2 = new Scanner(Cityfile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String A,C,D,T;
        while(scan.hasNext()){
            String scanned = scan.nextLine();
            int comma = scanned.indexOf(",");
            String sub1 = scanned.substring(comma+1);
            String sub2 = scanned.substring(0,comma);
            C = sub2;
            //System.out.println(sub2);
            comma = sub1.indexOf(",");
            sub2 = sub1.substring(0,comma);
            //System.out.println(sub2);
            T = sub2;
            sub1 = sub1.substring(comma+1);
            comma = sub1.indexOf(",");
            sub2 = sub1.substring(0,comma);
            sub1 = sub1.substring(comma+1);
            /*System.out.println(sub2);
            System.out.println(sub1);*/
            D = sub2;
            A = sub1;
            Flightlist.add(new FlightData(C,D,A,T));
            Date.add(new FlightTime(T));
        }

        while(scan2.hasNext()){
            String scanned = scan2.nextLine();
            int comma = scanned.indexOf(",");
            String sub1 = scanned.substring(comma+1);
            String sub2 = scanned.substring(0,comma);
            String sub3 = sub2;
            comma = sub1.indexOf(",");
            sub2 = sub1.substring(0,comma);
            sub1 = sub1.substring(comma+1);
            if(!sub1.isEmpty()){
                CountyName.add((sub2));
                IATACode.add((sub1));
                CountryName.add(sub3);
            }
        }
        int size;
        Flights = new ArrayList[2][12][31][IATACode.size()];
        for (int i1 = 0; i1 < IATACode.size(); i1++) {
            for (int i = 0; i < 7; i++) {
                Flights[0][8][i][i1] = new ArrayList();
            }
        }
        System.out.println(Flightlist.size());
        for (int i = 0; i < Flightlist.size(); i++) {
            if(!MenuIndex.contains(Flightlist.get(i).getDepart())){
                if(IATACode.contains(Flightlist.get(i).getDepart())){
                    MenuIndex.add(Flightlist.get(i).getDepart());
                    Flights[Flightlist.get(i).getYear()-2019][Flightlist.get(i).getMonth()-1][Flightlist.get(i).getDay()-1][MenuIndex.indexOf(Flightlist.get(i).getDepart())] = new ArrayList();
                }
            }
            if(IATACode.contains(Flightlist.get(i).getDepart())) {
                Flights[Flightlist.get(i).getYear()-2019][Flightlist.get(i).getMonth()-1][Flightlist.get(i).getDay()-1][MenuIndex.indexOf(Flightlist.get(i).getDepart())].add(Flightlist.get(i));
            }
        }
        Flightlist = null;
    }

    public static void main(String[] args) {
        Flight f = new Flight();
    }
    public Flight(String Code){
        //TimeDepart = flights.get(1);
    }

}
