import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class IntegrationMed {
    private static int End = 40;
    public static void main(String[] args){
        IntegrationMed i = new IntegrationMed();
    }
    public IntegrationMed(){
        File intputfile[] = new File[250];
        Scanner scans[] = new Scanner[250];
        IO io = new IO();
        String PPath = Parameters.PathDefault;
        String SPath = ".csv";
        for (int i = 0; i < intputfile.length; i++) {
            String path = PPath + IO.Countrycode.get(i)+SPath;
            intputfile[i] = new File(path);
            try {
                scans[i] = new Scanner(intputfile[i]);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        File output = new File(PPath +"Median"+SPath);
        PrintWriter printer = null;
        try {
            printer = new PrintWriter(output);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        File datefile = new File(Parameters.PathDefault+"Date.csv");
        Scanner datescan = null;
        try {
            datescan = new Scanner(datefile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ArrayList<String> dates = new ArrayList<>();
        while (datescan.hasNext()) {
            dates.add(datescan.nextLine());
        }
        for (int i = 0; i < Main.ObservationRange+1; i++) {
            printer.print(dates.get(i)+",");
        }
        printer.println();
        for (int i = 0; i < 250; i++) {
            ArrayList<String> Lines = new ArrayList<>();
            double[] MedianArr = new double[Main.ObservationRange];
            double[][] Mat = new double[End][Main.ObservationRange];
            String line = "";
            for (int i1 = 0; i1 < End; i1++) {
                line = scans[i].nextLine();
                line = line.substring(line.indexOf(",")+1);
                for (int i2 = 0; i2 < Main.ObservationRange; i2++) {
                    String buffer = line.substring(0,line.indexOf(","));
                    line = line.substring(line.indexOf(",")+1);
                    double d = Double.parseDouble(buffer);
                    Mat[i1][i2] = d;
                }
            }

            for (int i1 = 0; i1 < Main.ObservationRange; i1++) {
                int[] sort = new int[End];
                for (int i2 = 0; i2 < End; i2++) {
                    sort[i2] = (int) Mat[i2][i1];
                }
                int M = Median.median(sort);
                System.out.println(M);
                MedianArr[i1] = M;
            }
            System.out.print(IO.Countryname.get(i)+",");
            printer.print(IO.Countryname.get(i)+",");
            for (int i1 = 0; i1 < MedianArr.length; i1++) {
                System.out.print(MedianArr[i1]+",");
                printer.print(MedianArr[i1]+",");
            }
            System.out.println();
            printer.println();
        }
        printer.close();
    }
}
