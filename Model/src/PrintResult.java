import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class PrintResult{
    static PrintWriter PRINT = null;
    static PrintWriter PRINT2 = null;
    static PrintWriter PRINT3 = null;
    static PrintWriter PRINT4 = null;
    static PrintWriter COUNTRYPRINTER = null;
    public PrintResult(){
    }
    public void PRINTOUT(){
        String toPrint[] = {"Infected","Exposed","Active Cases","Resolved","Death","Immuned","Mobility Index","FlightCancelled"};
        String firstline = "";
        String firstline2 = "";
        String firstline3 = "";
        String firstline4 = "";
        System.out.print("Day,");
        File file = new File(Parameters.PathDefault+"Output/Infected Trail "+ Main.ini +" "+Main.Filecode+".csv");
        File filed = new File(Parameters.PathDefault+"Output/Death Trail "+ Main.ini +" "+Main.Filecode+".csv");
        File fileIm = new File(Parameters.PathDefault+"Output/Mobility Index Trail "+ Main.ini +" "+Main.Filecode+".csv");
        File fileEp = new File(Parameters.PathDefault+"Output/FlightsCancelled Trail "+ Main.ini +" "+Main.Filecode+".csv");
        //File P = new File(Parameters.PathDefault+"Patient Track/Patient Report "+ Main.ini +" "+Main.Filecode+".csv");
        try {
            PRINT = new PrintWriter(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            PRINT2 = new PrintWriter(filed);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            PRINT3 = new PrintWriter(fileIm);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            PRINT4 = new PrintWriter(fileEp);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        for (int i1 = 0; i1 < Main.NAMING.length; i1++) {
            for (int i2 = 0; i2 < Main.NAMING[i1].length; i2++) {
                if(Globe.NAMING[i1][i2]!=null){
                    firstline += Main.NAMING[i1][i2] + " " + toPrint[0]+",";
                    firstline2 += Main.NAMING[i1][i2] + " " + toPrint[4]+",";
                    firstline3 += Main.NAMING[i1][i2] + " " + toPrint[6]+",";
                    firstline4 += Main.NAMING[i1][i2] + " " + toPrint[7]+",";
                    Globe.counties[i1][i2].Printer.close();
                }
            }
        }
        //System.out.println(firstline);
        PRINT.println(firstline);
        PRINT2.println(firstline2);
        PRINT3.println(firstline3);
        PRINT4.println(firstline4);
        System.out.println(firstline);
        String line = null;
        String line2 = null;
        String line3 = null;
        String line4 = null;
        for (int i = 0; i < Main.ObservationRange; i++) {
            String buffer;
            String buffer2;
            String buffer3;
            String buffer4;
            line ="";
            line2 ="";
            line3 ="";
            line4 ="";
            for (int i1 = 0; i1 < Main.NAMING.length; i1++) {
                for (int i2 = 0; i2 < Main.NAMING[i1].length; i2++) {
                    if(Globe.NAMING[i1][i2]!=null){
                        buffer = Double.toString(Globe.counties[i1][i2].getPrintPack()[i][1]);
                        buffer2 = Double.toString(Globe.counties[i1][i2].getPrintPack()[i][6]);
                        buffer3 = Double.toString(Globe.counties[i1][i2].getPrintPack()[i][12]);
                        buffer4 = Double.toString(Globe.counties[i1][i2].getPrintPack()[i][11]);
                        //System.out.println(buffer +"BUFFER");
                        if(buffer!=null){
                            line += buffer+",";
                            line2 += buffer2+",";
                            line3 += buffer3+",";
                            line4 += buffer4+",";
                        }
                        else {
                            System.out.println("EMPTY");
                        }
                    }
                }
            }
            //System.out.println(line);
            PRINT.println(line);
            PRINT2.println(line2);
            PRINT3.println(line3);
            PRINT4.println(line4);
        }
        PRINT.close();
        PRINT2.close();
        PRINT3.close();
        PRINT4.close();
        /*int COUNTRY;
        for (int i = 0; i < Main.ObservationRange; i++) {
            String buffer;
            line ="";

            for (int i1 = 0; i1 < Main.NAMING.length; i1++) {
                ArrayList<String> AFFECTEDCOUNTRY = new ArrayList();
                ArrayList<Double> index = new ArrayList();
                for (int i2 = 0; i2 < Main.NAMING[i1].length; i2++) {
                    if(Globe.NAMING[i1][i2]!=null){
                        if(!AFFECTEDCOUNTRY.contains(Globe.NAMING[i1][i2].substring(0,2))){
                            AFFECTEDCOUNTRY.add(Globe.NAMING[i1][i2].substring(0,2));
                            index.add(0.0);
                        }
                        else {
                            index.set(AFFECTEDCOUNTRY.indexOf(Globe.NAMING[i1][i2].substring(0,2)),index.get(AFFECTEDCOUNTRY.indexOf(Globe.NAMING[i1][i2].substring(0,2)))+Globe.counties[i1][i2].getPrintPack()[i][2]);
                        }
                        buffer = Double.toString(Globe.counties[i1][i2].getPrintPack()[i][2]);
                        //System.out.println(buffer +"BUFFER");
                        if(buffer!=null){
                            line += buffer+",";
                        }
                    }
                }
            }
            //System.out.println(line);
            COUNTRYPRINTER.println(line);
        }

        COUNTRYPRINTER.close();*/
    }
}
