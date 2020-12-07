import java.io.*;
import java.util.*;
public class GraphMaker {
    public GraphMaker() throws Exception {
        int ini = 0;
        int end = 50;
        int diff = end - ini;
        int datenum = 3;
        int ObservationRange = 270;
        int Conutries;

        String Input[] = {"Infected", "Exportation", "Importation", "Exportation OutBound", "Importation OutBound", "Death", "FlightsCancelled","Mobility Index","Daily Infected"};
        for (int S = 0; S < Input.length; S++) {
            System.out.println(Input[S]);
            ArrayList<double[][]> DATA = new ArrayList<>();
            File files[] = new File[end - ini];
            Scanner scan[] = new Scanner[end - ini];
            String part1 = Parameters.PathDefault + "Output/Country " + Input[S] + " Trail CN-42 ";

            String part2 = ".csv";
            String output1 = Parameters.PathDefault + "Output/Country " + Input[S] + "/";
            File sumfile = new File("E:/Global Model/Sum/" + "Sums.csv");
            double Sum[] = new double[diff];
            String output2;
            for (int i = ini; i < end; i++) {
                String path = part1 + (i + 1) + part2;
                System.out.println(path);
                System.out.println(path);
                files[i - ini] = new File(path);
                scan[i - ini] = new Scanner(files[i - ini]);
            }
            ArrayList<String> arr = IO.Countryname;
            ArrayList<String> arr2 = IO.Countrycode;
            File outputfiles[] = new File[arr.size()];

            PrintWriter printers[] = new PrintWriter[arr.size()];

            for (int i = 0; i < arr.size(); i++) {
                double[][] buffer = new double[end - ini][ObservationRange];
                DATA.add(buffer);
                output2 = arr.get(i) + "  " + arr2.get(i) + " " + Input[S] + ".csv";
                if (arr2.get(i).equals("CI") || arr2.get(i).equals("BL")) {
                    output2 = arr2.get(i) + ".csv";
                }
                String path = output1 + output2;
                outputfiles[i] = new File(path);
                printers[i] = new PrintWriter(outputfiles[i]);
            }

            for (int i = 0; i < diff; i++) {
                System.out.println(i);
                ArrayList<String> CountryScanBuffer = new ArrayList<>();

                ArrayList<double[]> DATASET = new ArrayList<>();

                String firstline = scan[i].nextLine();
                String nd = scan[i].nextLine();

                while (firstline.length() > 1) {
                    String country = firstline.substring(0, firstline.indexOf(","));
                    firstline = firstline.substring(firstline.indexOf(",") + 1);
                    // System.out.println(country);
                    CountryScanBuffer.add(country);
                    DATASET.add(new double[ObservationRange]);
                }
            /*if(i==27||i==30){
                continue;

            }*/
                if(i==10){
                    continue;
                }
                for (int i1 = 0; i1 < ObservationRange; i1++) {
                    String buffer = scan[i].nextLine();
                    int counter = 0;
                    double sum = 0;
                    while (buffer.length() > 1) {
                        String number = buffer.substring(0, buffer.indexOf(","));
                        buffer = buffer.substring(buffer.indexOf(",") + 1);
                        //System.out.println(CountryScanBuffer.get(counter));
                        //System.out.print(number);
                        double buff[] = DATASET.get(counter);
                        buff[i1] = Double.parseDouble(number);
                        DATASET.set(counter, buff);
                        counter++;
                        if (ObservationRange - 1 == (i1)) {
                            sum +=  Double.parseDouble(number);
                        }
                    }
                    Sum[i] = sum;
                    //System.out.println();
                }

                for (int i1 = 0; i1 < CountryScanBuffer.size(); i1++) {
                    int index = arr.indexOf(CountryScanBuffer.get(i1));
                    double[] buff = DATASET.get(i1);
                    double DATAbuff[][] = DATA.get(index);
                    DATAbuff[i] = buff;
                    DATA.set(index, DATAbuff);
                }
            }
            for (int i = 0; i < arr.size(); i++) {
                //System.out.print(arr.get(i)+",");
                for (int i1 = 0; i1 < diff; i1++) {
                    // System.out.print(arr.get(i) + " Trail " + i1 + ",");
                    printers[i].print(arr.get(i) + " Trail " + i1 + ",");
                    for (int i2 = 0; i2 < ObservationRange; i2++) {
                        //  System.out.print(DATA.get(i)[i1][i2] + ",");
                        printers[i].print(DATA.get(i)[i1][i2] + ",");
                    }
                    ///System.out.println();
                    printers[i].println();
                }
                printers[i].close();
            }
            PrintWriter print = new PrintWriter(sumfile);
            for (int i = 0; i < Sum.length; i++) {
                //  System.out.print("Trail " + i + ",");
                print.print("Trail " + i + ",");
            }
            // System.out.println();
            print.println("Actual" + ",");
            for (int i = 0; i < Sum.length; i++) {
                //   System.out.print(Sum[i] + ",");
                print.print(Sum[i] + ",");
            }
            print.print("305875,");
            print.close();
        }
    }

    public static void main(String[] args) throws Exception {
        IO io = new IO();
        GraphMaker g = new GraphMaker();
    }
}
