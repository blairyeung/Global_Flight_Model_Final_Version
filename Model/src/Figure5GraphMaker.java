import java.io.*;
import java.util.*;
public class Figure5GraphMaker {
    public static void main(String[] args) throws Exception{
        String MaxMeanMin[] = {"Max","Median","Min"};
        String GCodes[] = {"FlightsCancelled","Exportation"};
        String Scenarios[] = new String[10];
        File InputFiles[][][] = new File[Scenarios.length][GCodes.length][GCodes.length];
        double MaxSum[][] = new double[Scenarios.length][GCodes.length];
        double MeanSum[][] = new double[Scenarios.length][GCodes.length];
        double MinSum[][] = new double[Scenarios.length][GCodes.length];
        double Sums[][][] = new double[Scenarios.length][GCodes.length][MaxMeanMin.length];
        File OutputFile = new File("E:/Global Model/By Category/Figure.csv");
        for (int i = 0; i < 5; i++) {
            String scenarioI = "i" + (i+1) +"/";
            String scenarioM = "m" + (i+1) +"/";
            Scenarios[i] = scenarioI;
            Scenarios[5+i] = scenarioM;
        }
        String PrePath = "E:/Global Model/By Category/";
        String SubPath = "Output/";
        for (int i = 0; i < Scenarios.length; i++) {
            String SurPath;
            String MiddlePath;
            String LastPath;
            String PostPath;
            String Path;
            SurPath = PrePath + Scenarios[i] + SubPath;
            if(Scenarios[i].equals("i2/")||Scenarios[i].equals("m1/")){
                continue;
            }
            for (int i1 = 0; i1 < GCodes.length; i1++) {
                MiddlePath = "Country " + GCodes[i1] + "/";
                for (int i2 = 0; i2 < MaxMeanMin.length; i2++) {
                    LastPath = "Global " + MaxMeanMin[i2] + " " + GCodes[i1]  + ".csv";
                    Path = SurPath + MiddlePath + LastPath;
                    System.out.println(Path);
                    File Input = new File(Path);
                    Scanner Scan = new Scanner(Input);
                    String LastLine = null;
                    while (Scan.hasNext()) {
                        LastLine = Scan.nextLine();
                    }
                    LastLine = LastLine.substring(LastLine.indexOf(",")+1);
                    double sum = 0;
                    while (LastLine.contains(",")) {
                        double d;
                        String Dou = LastLine.substring(0,LastLine.indexOf(","));
                        LastLine = LastLine.substring(LastLine.indexOf(",")+1);
                        d = Double.parseDouble(Dou);
                        sum += d;
                    }
                    //System.out.println(sum);
                    Sums[i][i1][i2] = sum;
                }
            }
        }
        PrintWriter Printer = new PrintWriter(OutputFile);
        String firstline = "Scenario,";
        for (int i1 = 0; i1 < GCodes.length; i1++) {
            for (int i = 0; i < MaxMeanMin.length; i++) {
                String buffer = GCodes[i1] + " " + MaxMeanMin[i];
                System.out.println(buffer);
                firstline += (buffer + ",");
            }
        }
        System.out.println(firstline);
        Printer.println(firstline);
        for (int i = 0; i < Scenarios.length; i++) {
            System.out.print(Scenarios[i].substring(0,2)+",");
            Printer.print(  Scenarios[i].substring(0,2)+",");
            for (int i1 = 0; i1 < GCodes.length; i1++) {
                for (int i2 = 0; i2 < MaxMeanMin.length; i2++) {
                    System.out.print(Sums[i][i1][i2]+",");
                    Printer.print(Sums[i][i1][i2]+",");
                }
            }
            System.out.println();
            Printer.println();
        }
        Printer.close();
    }
}
