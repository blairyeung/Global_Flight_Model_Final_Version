    import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class ToCountry {
    public ToCountry(){

    }

    public static void main(String[] args) {
        IO io = new IO();
        for (int i = 0; i < Parameters.Count; i++) {
            System.out.println("File Code: " + (i+1));
            ToCountry T= new ToCountry(i+1);
        }
    }
    public ToCountry(int io) {
        String O = "CN-42";
        String read = Parameters.PathDefault+"Output/Infected Trail " + O + " " + io + ".csv";
        String read2 = Parameters.PathDefault+"Output/Death Trail " + O + " " + io + ".csv";
        String read3 = Parameters.PathDefault+"Output/Importation Trail " + O + " " + io + ".csv";
        String read4 = Parameters.PathDefault+"Output/Exportation Trail " + O + " " + io + ".csv";
        String read5 = Parameters.PathDefault+"Output/Importation OutBound Trail " + O + " " + io + ".csv";
        String read6 = Parameters.PathDefault+"Output/Exportation OutBound Trail " + O + " " + io + ".csv";
        String read7 = Parameters.PathDefault+"Output/FlightsCancelled Trail " + O + " " + io + ".csv";
        String read8 = Parameters.PathDefault+"Output/Mobility Index Trail " + O + " " + io + ".csv";
        String outf = Parameters.PathDefault+"Output/Country Infected Trail "+ O +" "+ io+".csv";
        String outf2 = Parameters.PathDefault+"Output/Country Death Trail "+ O +" "+ io+".csv";
        String outf3 = Parameters.PathDefault+"Output/Country Importation Trail "+ O +" "+ io+".csv";
        String outf4 = Parameters.PathDefault+"Output/Country Exportation Trail "+ O +" "+ io+".csv";
        String outf5 = Parameters.PathDefault+"Output/Country Importation OutBound Trail "+ O +" "+ io+".csv";
        String outf6 = Parameters.PathDefault+"Output/Country Exportation OutBound Trail "+ O +" "+ io+".csv";
        String outf7 = Parameters.PathDefault+"Output/Country FlightsCancelled Trail "+ O +" "+ io+".csv";
        String outf8 = Parameters.PathDefault+"Output/Country Mobility Index Trail "+ O +" "+ io+".csv";
        String firstcase = Parameters.PathDefault+"Output/FIRSTCASE Trail "+ O +" "+ io+".csv";
        ////System.out.println(read);
        File file = new File(read);
        File filedeath = new File(read2);
        File fileImport = new File(read3);
        File fileExport = new File(read4);
        File fileImportOB = new File(read5);
        File fileExportOB = new File(read6);
        File fileFlight = new File(read7);
        File fileMobility = new File(read8);
        File out = new File(outf);
        File outd = new File(outf2);
        File outIm = new File(outf3);
        File outEx = new File(outf4);
        File outImOB = new File(outf5);
        File outExOB = new File(outf6);
        File outFlightC = new File(outf7);
        File outMob = new File(outf8);
        PrintWriter print = null;
        PrintWriter print2 = null;
        PrintWriter print3 = null;
        PrintWriter printIm = null;
        PrintWriter printEx = null;
        PrintWriter printImOB = null;
        PrintWriter printExOB = null;
        PrintWriter printFlight = null;
        PrintWriter printMob = null;

        Scanner scan2 = null;
        Scanner scan3 = null;
        Scanner Importscan = null;
        Scanner ExportScan = null;
        Scanner ImportOBScan = null;
        Scanner ExportOBScan = null;
        Scanner FlightScan = null;
        Scanner MobScan = null;
        try {
            Importscan = new Scanner(fileImport);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            ExportScan = new Scanner(fileExport);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            ImportOBScan = new Scanner(fileImportOB);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            ExportOBScan = new Scanner(fileExportOB);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            FlightScan = new Scanner(fileFlight);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            MobScan = new Scanner(fileMobility);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            print = new PrintWriter(out);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            printIm = new PrintWriter(outIm);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            printEx = new PrintWriter(outEx);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            print3 = new PrintWriter(outd);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            print2 = new PrintWriter(firstcase);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            printExOB = new PrintWriter(outExOB);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            printImOB = new PrintWriter(outImOB);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            printFlight = new PrintWriter(outFlightC);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            printMob = new PrintWriter(outMob);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            scan2 = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            scan3 = new Scanner(filedeath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ArrayList<String> lines = new ArrayList<>();
        ArrayList<String> linesdeath = new ArrayList<>();
        ArrayList<String> linesimport = new ArrayList<>();
        ArrayList<String> linesimportOb = new ArrayList<>();
        ArrayList<String> linesexport = new ArrayList<>();
        ArrayList<String> linesexportOb = new ArrayList<>();
        ArrayList<String> linesFlight = new ArrayList<>();
        ArrayList<String> linesMob = new ArrayList<>();
        int count = 0;
        while (scan2.hasNext()&&count<=270) {
            count++;
            lines.add(scan2.nextLine());
            linesdeath.add(scan3.nextLine());
            linesimport.add(Importscan.nextLine());
            linesimportOb.add(ImportOBScan.nextLine());
            linesexport.add(ExportScan.nextLine());
            linesexportOb.add(ExportOBScan.nextLine());
            linesFlight.add(FlightScan.nextLine());
            linesMob.add(MobScan.nextLine());
        }
        ArrayList<String> Country = new ArrayList<>();
        ArrayList<Integer> index = new ArrayList<>();
        ArrayList<Double> cases = new ArrayList<>();
        ArrayList<Double> deaths = new ArrayList<>();
        ArrayList<Double> Imports = new ArrayList<>();
        ArrayList<Double> ImportsOb = new ArrayList<>();
        ArrayList<Double> Exports = new ArrayList<>();
        ArrayList<Double> ExportsOb = new ArrayList<>();
        ArrayList<Double> FlightCancel = new ArrayList<>();
        ArrayList<Double> Mobilityindex = new ArrayList<>();
        ArrayList<Integer> first = new ArrayList<>();
        ArrayList<Boolean> rec = new ArrayList<>();
        ArrayList<String> LINE = new ArrayList<>();
        ArrayList<String> LINEDeath = new ArrayList<>();
        ArrayList<String> LINEImport = new ArrayList<>();
        ArrayList<String> LINEImportOb= new ArrayList<>();
        ArrayList<String> LINEExport = new ArrayList<>();
        ArrayList<String> LINEExportOb = new ArrayList<>();
        ArrayList<String> LineFlight = new ArrayList<>();
        ArrayList<String> LineMob = new ArrayList<>();
        //System.out.println(lines);
        String county = lines.get(0);
        while (county.length() > 6) {
            String buffer = county.substring(0, county.indexOf(","));
            String buffer2 = buffer.substring(0,2);
            county = county.substring(county.indexOf(",") + 1);
            if (county.length() < 6) {
                break;
            }
            if (Country.contains(buffer2)) {
                index.set(Country.indexOf(buffer2), index.get(Country.indexOf(buffer2)) + 1);
            } else {
                Country.add(buffer2);
                index.add(1);
                cases.add(0.0);
                deaths.add(0.0);
                Imports.add(0.0);
                ImportsOb.add(0.0);
                Exports.add(0.0);
                ExportsOb.add(0.0);
                first.add(150);
                FlightCancel.add(0.0);
                Mobilityindex.add(0.0);
                rec.add(false);
            }
        }
        String firstline = "";
        String nameline = "";
        String nameline2 = "";
        for (int i = 0; i < Country.size(); i++) {
            firstline += Country.get(i) + ",";
            nameline += IO.Countryname.get(IO.Countrycode.indexOf(Country.get(i))) + ",";
            nameline2 += IO.Countrycode.get(IO.Countrycode.indexOf(Country.get(i))) + ",";
        }
        for (int i = 1; i < lines.size(); i++) {
            for (int i1 = 0; i1 < cases.size(); i1++) {
                cases.set(i1, 0.0);
                deaths.set(i1, 0.0);
                Imports.set(i1, 0.0);
                ImportsOb.set(i1, 0.0);
                Exports.set(i1, 0.0);
                ExportsOb.set(i1, 0.0);
                FlightCancel.set(i1, 0.0);
                Mobilityindex.set(i1, 0.0);
            }
            String aline = "";
            String aline2 = "";
            String aline3 = "";
            String aline4 = "";
            String aline5 = "";
            String aline6 = "";
            String aline7 = "";
            String aline8 = "";
            String linebuff;
            String linebuff2;
            String linebuff3;
            String linebuff4;
            String linebuff5;
            String linebuff6;
            String linebuff7;
            String linebuff8;
            String linebuffering;
            String linebuffering2;
            String linebuffering3;
            String linebuffering4;
            String linebuffering5;
            String linebuffering6;
            String linebuffering7;
            String linebuffering8;
            linebuff = lines.get(i);
            linebuff2 = linesdeath.get(i);
            linebuff3 = linesimport.get(i);
            linebuff4 = linesexport.get(i);
            linebuff5 = linesimportOb.get(i);
            linebuff6 = linesexportOb.get(i);
            linebuff7 = linesFlight.get(i);
            linebuff8 = linesMob.get(i);
            for (int i1 = 0; i1 < Country.size(); i1++) {
                for (int integer = 0; integer < index.get(i1); integer++) {
                    linebuffering = linebuff.substring(0, linebuff.indexOf(","));
                    linebuffering2 = linebuff2.substring(0, linebuff2.indexOf(","));
                    linebuffering3 = linebuff3.substring(0, linebuff3.indexOf(","));
                    linebuffering4 = linebuff4.substring(0, linebuff4.indexOf(","));
                    linebuffering5 = linebuff5.substring(0, linebuff5.indexOf(","));
                    linebuffering6 = linebuff6.substring(0, linebuff6.indexOf(","));
                    linebuffering7 = linebuff7.substring(0, linebuff7.indexOf(","));
                    linebuffering8 = linebuff8.substring(0, linebuff8.indexOf(","));
                    linebuff = linebuff.substring(linebuff.indexOf(",") + 1);
                    linebuff2 = linebuff2.substring(linebuff2.indexOf(",") + 1);
                    linebuff3 = linebuff3.substring(linebuff3.indexOf(",") + 1);
                    linebuff4 = linebuff4.substring(linebuff4.indexOf(",") + 1);
                    linebuff5 = linebuff5.substring(linebuff5.indexOf(",") + 1);
                    linebuff6 = linebuff6.substring(linebuff6.indexOf(",") + 1);
                    linebuff7 = linebuff7.substring(linebuff7.indexOf(",") + 1);
                    linebuff8 = linebuff8.substring(linebuff8.indexOf(",") + 1);
                    cases.set(i1, cases.get(i1) + Double.parseDouble(linebuffering));
                    deaths.set(i1, deaths.get(i1) + Double.parseDouble(linebuffering2));
                    Imports.set(i1, Imports.get(i1) + Double.parseDouble(linebuffering3));
                    ImportsOb.set(i1, ImportsOb.get(i1) + Double.parseDouble(linebuffering5));
                    Exports.set(i1, Exports.get(i1) + Double.parseDouble(linebuffering4));
                    ExportsOb.set(i1, ExportsOb.get(i1) + Double.parseDouble(linebuffering6));
                    FlightCancel.set(i1, FlightCancel.get(i1) + Double.parseDouble(linebuffering7));
                    Mobilityindex.set(i1, Mobilityindex.get(i1) + Double.parseDouble(linebuffering8));
                }
                aline += cases.get(i1).toString() + ",";
                aline2 += deaths.get(i1).toString() + ",";
                aline3 += Imports.get(i1).toString() + ",";
                aline4 += Exports.get(i1).toString() + ",";
                aline5 += ImportsOb.get(i1).toString() + ",";
                aline6 += ExportsOb.get(i1).toString() + ",";
                aline7 += FlightCancel.get(i1).toString() + ",";
                aline8 += Mobilityindex.get(i1).toString() + ",";
                if (cases.get(i1) > 0 && rec.get(i1) == false) {
                    first.set(i1, i);
                    rec.set(i1, true);
                }
            }
            LINE.add(aline);
            LINEDeath.add(aline2);
            LINEImport.add(aline3);
            LINEExport.add(aline4);
            LINEImportOb.add(aline5);
            LINEExportOb.add(aline6);
            LineFlight.add(aline7);
            LineMob.add(aline8);
        }
        //System.out.println(nameline);
        print.println(nameline);
        print.println(nameline2);
        print3.println(nameline);
        print3.println(nameline2);
        printIm.println(nameline);
        printIm.println(nameline2);
        printEx.println(nameline);
        printEx.println(nameline2);
        printImOB.println(nameline);
        printImOB.println(nameline2);
        printExOB.println(nameline);
        printExOB.println(nameline2);
        printFlight.println(nameline);
        printFlight.println(nameline2);
        printMob.println(nameline);
        printMob.println(nameline2);
        for (int i = 0; i < LINE.size(); i++) {
            print.println(LINE.get(i));
            print3.println(LINEDeath.get(i));
            printIm.println(LINEImport.get(i));
            printImOB.println(LINEImportOb.get(i));
            printEx.println(LINEExport.get(i));
            printExOB.println(LINEExportOb.get(i));
            printFlight.println(LineFlight.get(i));
            printMob.println(LineMob.get(i));
        }
        print.close();
        print3.close();
        printIm.close();
        printImOB.close();
        printEx.close();
        printExOB.close();
        printFlight.close();
        printMob.close();
        print2.println(firstline);
        for (int i = 0; i < Country.size(); i++) {
            print2.print(first.get(i)+",");
        }
        print2.close();
    }
}