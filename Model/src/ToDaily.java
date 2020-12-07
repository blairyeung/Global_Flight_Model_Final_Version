import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class ToDaily {
    static int w = Parameters.Count;
    static int ObservationRange = 270;
    public static void main(String[] args) throws Exception{
        String Input[] = {"Infected"};
        File[] files = new File[w];
        File[] Outfiles = new File[w];
        Scanner[] scans = new Scanner[w];
        PrintWriter[] Printers = new PrintWriter[w];
        for (int i = 0; i < Input.length; i++) {
            System.out.println(Input[i]);
            String part1 = Parameters.PathDefault + "Output/Country " + Input[i] + " Trail CN-42 ";
            String part2 = ".csv";
            String output1 = Parameters.PathDefault + "Output/Country Daily " + Input[i] +" Trail CN-42 ";
            for (int i1 = 0; i1 < w; i1++) {
                //System.out.println(i1);
                String InputPath = part1 + (i1+1) +part2;
                String OutputPath = output1 + (i1+1) + part2;
                files[i1] = new File(InputPath);
                Outfiles[i1] = new File(OutputPath);
                scans[i1] = new Scanner(files[i1]);
                Printers[i1] = new PrintWriter(Outfiles[i1]);
                System.out.println(InputPath);
            }
            for (int i1 = 0; i1 < w; i1++) {
                System.out.println(i1);
                String firstline = scans[i1].nextLine();
                String firstline3 = scans[i1].nextLine();
                String firstline2 = firstline;
                ArrayList<String> Lines = new ArrayList<>();
                while (scans[i1].hasNext()) {
                    String line = scans[i1].nextLine();
                    Lines.add(line);
                }
                int Counter = 0;
                while (firstline.contains(",")) {
                    Counter++;
                    firstline = firstline.substring(firstline.indexOf(",")+1);
                }
                double DATASET[][] = new double[Counter][270];
                double DiffDATASET[][] = new double[Counter][270];
                for (int i2 = 0; i2 < Lines.size(); i2++) {
                    String buffer = Lines.get(i2);
                    int Counter2 = 0;
                    while (buffer.length() > 1) {
                        String number = buffer.substring(0, buffer.indexOf(","));
                        //System.out.println(number);
                        buffer = buffer.substring(buffer.indexOf(",") + 1);
                        double d  = Double.parseDouble(number);
                        DATASET[Counter2][i2] = d;
                        if(i2>0){
                            DiffDATASET[Counter2][i2-1] =  DATASET[Counter2][i2] -  DATASET[Counter2][i2-1];
                        }
                        Counter2++;
                    }
                }
                Printers[i1].println(firstline2);
                Printers[i1].println(firstline3);
                System.out.println(firstline2);
                System.out.println(firstline3);
                for (int i2 = 0; i2 < 2; i2++) {
                    for (int i3 = 0; i3 < Counter; i3++) {
                        Printers[i1].print(0+",");
                    }
                    Printers[i1].println();
                }
                for (int i2 = 0; i2 < ObservationRange-2; i2++) {
                    for (int i3 = 0; i3 < Counter; i3++) {
                      //  System.out.print(DiffDATASET[i3][i2]+",");
                        Printers[i1].print(DiffDATASET[i3][i2]+",");
                    }
                   // System.out.println();
                    Printers[i1].println();
                }
                Printers[i1].close();
            }
        }
    }
    public ToDaily(){
        String Input[] = {"Infected"};
        File[] files = new File[w];
        File[] Outfiles = new File[w];
        Scanner[] scans = new Scanner[w];
        PrintWriter[] Printers = new PrintWriter[w];
        for (int i = 0; i < Input.length; i++) {
            System.out.println(Input[i]);
            String part1 = Parameters.PathDefault + "Output/Country " + Input[i] + " Trail CN-42 ";
            String part2 = ".csv";
            String output1 = Parameters.PathDefault + "Output/Country Daily " + Input[i] +" Trail CN-42 ";
            for (int i1 = 0; i1 < w; i1++) {
                //System.out.println(i1);
                String InputPath = part1 + (i1+1) +part2;
                String OutputPath = output1 + (i1+1) + part2;
                files[i1] = new File(InputPath);
                Outfiles[i1] = new File(OutputPath);
                try {
                    scans[i1] = new Scanner(files[i1]);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                try {
                    Printers[i1] = new PrintWriter(Outfiles[i1]);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                System.out.println(InputPath);
            }
            for (int i1 = 0; i1 < w; i1++) {
                System.out.println(i1);
                String firstline = scans[i1].nextLine();
                String firstline3 = scans[i1].nextLine();
                String firstline2 = firstline;
                ArrayList<String> Lines = new ArrayList<>();
                while (scans[i1].hasNext()) {
                    String line = scans[i1].nextLine();
                    Lines.add(line);
                }
                int Counter = 0;
                while (firstline.contains(",")) {
                    Counter++;
                    firstline = firstline.substring(firstline.indexOf(",")+1);
                }
                double DATASET[][] = new double[Counter][270];
                double DiffDATASET[][] = new double[Counter][270];
                for (int i2 = 0; i2 < Lines.size(); i2++) {
                    String buffer = Lines.get(i2);
                    int Counter2 = 0;
                    while (buffer.length() > 1) {
                        String number = buffer.substring(0, buffer.indexOf(","));
                        //System.out.println(number);
                        buffer = buffer.substring(buffer.indexOf(",") + 1);
                        double d  = Double.parseDouble(number);
                        DATASET[Counter2][i2] = d;
                        if(i2>0){
                            DiffDATASET[Counter2][i2-1] =  DATASET[Counter2][i2] -  DATASET[Counter2][i2-1];
                        }
                        Counter2++;
                    }
                }
                Printers[i1].println(firstline2);
                Printers[i1].println(firstline3);
                System.out.println(firstline2);
                System.out.println(firstline3);
                for (int i2 = 0; i2 < 2; i2++) {
                    for (int i3 = 0; i3 < Counter; i3++) {
                        Printers[i1].print(0+",");
                    }
                    Printers[i1].println();
                }
                for (int i2 = 0; i2 < ObservationRange-2; i2++) {
                    for (int i3 = 0; i3 < Counter; i3++) {
                        //  System.out.print(DiffDATASET[i3][i2]+",");
                        Printers[i1].print(DiffDATASET[i3][i2]+",");
                    }
                    // System.out.println();
                    Printers[i1].println();
                }
                Printers[i1].close();
            }
        }
    }
}
