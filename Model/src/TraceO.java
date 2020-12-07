import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class TraceO {
    public static void main(String[] args) throws Exception{
       // IO io = new IO();
        TraceO t = new TraceO();
    }
    public TraceO(){
        String Input[] = {"Exportation","Importation"};
        int ini = 0;
        int end = Parameters.Count;
        int diff = end - ini;
        int datenum = 3;
        int ObservationRange = 270;

        //Input
        File Scanfiles[] = new File[end - ini];
        //Output
        File ImportationFiles[] = new File[end-ini];
        File ExportationFiles[] = new File[end-ini];

        File OutBoundImportationFiles[] = new File[end-ini];
        File OutBoundExportationFiles[] = new File[end-ini];

        Scanner scan[] = new Scanner[end - ini];

        PrintWriter ImportationPrint[] = new PrintWriter[end-ini];
        PrintWriter ExportationPrint[] = new PrintWriter[end-ini];

        PrintWriter OutBoundImportationPrint[] = new PrintWriter[end-ini];
        PrintWriter OutBoundExportationPrint[] = new PrintWriter[end-ini];

        String part1 = Parameters.PathDefault + "Output/Trace Trail CN-42 ";
        String part2 = ".csv";

        for (int i = ini; i < end; i++) {
            String Path = part1 + (i+1) + part2;
            System.out.println(Path);
            Scanfiles[i] = new File(Path);
            try {
                scan[i] = new Scanner(Scanfiles[i]);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            String ExPath = Parameters.PathDefault + "Output/Exportation Trail CN-42 " + (i+1) + ".csv";
            String OutBoundExPath = Parameters.PathDefault + "Output/Exportation OutBound Trail CN-42 " + (i+1) + ".csv";
            String ImPath = Parameters.PathDefault + "Output/Importation Trail CN-42 " + (i+1) + ".csv";
            String OutBoundImPath = Parameters.PathDefault + "Output/Importation OutBound Trail CN-42 " + (i+1) + ".csv";


            ExportationFiles[i]= new File(ExPath);
            OutBoundExportationFiles[i]= new File(OutBoundExPath);
            ImportationFiles[i] = new File(ImPath);
            OutBoundImportationFiles[i] = new File(OutBoundImPath);

            try {
                ExportationPrint[i] = new PrintWriter(ExportationFiles[i]);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            try {
                OutBoundExportationPrint[i] = new PrintWriter(OutBoundExportationFiles[i]);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            try {
                ImportationPrint[i] = new PrintWriter(ImportationFiles[i]);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            try {
                OutBoundImportationPrint[i] = new PrintWriter(OutBoundImportationFiles[i]);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        for (int i = ini; i < end; i++) {

            File AdaptIm = new File(Parameters.PathDefault + "Output/Infected Trail CN-42 " + (i+1) + ".csv");
            Scanner AdaptScan = null;
            try {
                AdaptScan = new Scanner(AdaptIm);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            String buff = AdaptScan.nextLine();

            String ImFirstLine = buff;
            String ExFirstLine = buff;


            ArrayList<String> Fix = new ArrayList<>();

            while (ImFirstLine.contains(",")) {
                String f = ImFirstLine.substring(0,ImFirstLine.indexOf(" Infected"));
                ImFirstLine = ImFirstLine.substring(ImFirstLine.indexOf(",")+1);
                //System.out.println(f);
                Fix.add(f);
            }

            System.out.println(i+"/"+diff+" Processing");


            int[][] ExportationSet = new int[Fix.size()][ObservationRange];
            int[][] OutBoundExportationSet = new int[Fix.size()][ObservationRange];
            int[][] ImportationSet = new int[Fix.size()][ObservationRange];
            int[][] OutBoundImportationSet = new int[Fix.size()][ObservationRange];

            while (scan[i].hasNext()) {

                String line = scan[i].nextLine();
                String DayStr = line.substring(0,line.indexOf(","));
                line = line.substring(line.indexOf(",")+1);
                String Exportor = line.substring(0,line.indexOf(","));
                line = line.substring(line.indexOf(",")+1);
                String Importor = line.substring(0,line.indexOf(","));
                line = line.substring(line.indexOf(",")+1);

                //System.out.println("Day: " + DayStr +"    Exportor: " + Exportor + "    Importor: " + Importor);

                if(!Fix.contains(Exportor)){
                    continue;
                }

                if(!Fix.contains(Importor)){
                    continue;
                }

                int ExIndex  = Fix.indexOf(Exportor);
                int ImIndex  = Fix.indexOf(Importor);


                if(Integer.parseInt(DayStr)>=ObservationRange){
                    break;
                }
                if(Importor!=Exportor){
                    ImportationSet[ImIndex][Integer.parseInt(DayStr)] += 1;
                    ExportationSet[ExIndex][Integer.parseInt(DayStr)] += 1;
                }
                String ImportorC = Importor.substring(0,2);
                String ExportorC = Exportor.substring(0,2);
                if(!ImportorC.equals(ExportorC)){
                    OutBoundExportationSet[ExIndex][Integer.parseInt(DayStr)] += 1;
                    OutBoundImportationSet[ImIndex][Integer.parseInt(DayStr)] += 1;
                }
            }

            for (int i1 = 0; i1 < Fix.size(); i1++) {
                ImportationPrint[i].print(Fix.get(i1) +" Importation,");
                OutBoundImportationPrint[i].print(Fix.get(i1) +" OutdBound Importation,");
                ExportationPrint[i].print(Fix.get(i1) +" Exportation,");
                OutBoundExportationPrint[i].print(Fix.get(i1) +" OutBound Exportation,");
            }

            ImportationPrint[i].println();
            OutBoundImportationPrint[i].println();
            ExportationPrint[i].println();
            OutBoundExportationPrint[i].println();


            for (int i1 = 0; i1 < ObservationRange; i1++) {
                for (int i2 = 0; i2 < Fix.size(); i2++) {
                    ExportationPrint[i].print(ExportationSet[i2][i1]+",");
                    OutBoundExportationPrint[i].print(OutBoundExportationSet[i2][i1]+",");
                }
                for (int i2 = 0; i2 < Fix.size(); i2++) {
                    ImportationPrint[i].print(ImportationSet[i2][i1]+",");
                    OutBoundImportationPrint[i].print(OutBoundImportationSet[i2][i1]+",");
                }
                ExportationPrint[i].println();
                OutBoundExportationPrint[i].println();
                ImportationPrint[i].println();
                OutBoundImportationPrint[i].println();
            }
            ExportationPrint[i].close();
            OutBoundExportationPrint[i].close();
            ImportationPrint[i].close();
            OutBoundImportationPrint[i].close();
        }

        for (int i = 0; i < Input.length; i++) {

        }
    }
}