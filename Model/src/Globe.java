import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

public class Globe extends Main{
    File file = new File(Parameters.PathDefault+"OVERALL.csv");
    static PrintWriter print = null;
    public boolean run[][] = null;
    public int Exported[][] = null;
    public int FlightCanceled[][][];
    public int Imported[][] = null;
    public double Incidence[][] = null;
    public double CountryIncidence[] = null;
    public ArrayList<String[]> Trace = new ArrayList<>();
    public static Random rad = new Random();
    public static ArrayList<String[]> overall = new ArrayList<>();
    public static ArrayList<String> ind = new ArrayList<String>();
    File outputFile = null;
    PrintWriter TracePrint = null;
    public void GarbageC(){
        for (int i1 = 0; i1 < Main.NAMING.length; i1++) {
            for (int i2 = 0; i2 < Main.NAMING[i1].length; i2++) {
                if(run[i1][i2]==true){
                    //Globe.counties[i1][i2] = new County();
                    Globe.counties[i1][i2].Restore();
                    countiesname.clear();
                    run[i1][i2] = false;
                }
            }
        }
    }
    public Globe(){
        countiesname.add(ini);
        System.out.println(ini);
        counties[IO.Countrycode.indexOf(ini.substring(0,2))][CountyinCountry[IO.Countrycode.indexOf(ini.substring(0,2))].indexOf(ini)] = new County(ini,Main.ObservationRange,1,Main.PopCountyinCountry[IO.Countrycode.indexOf(ini.substring(0,2))].get(CountyinCountry[IO.Countrycode.indexOf(ini.substring(0,2))].indexOf(ini)));
        NAMING[IO.Countrycode.indexOf(ini.substring(0,2))][CountyinCountry[IO.Countrycode.indexOf(ini.substring(0,2))].indexOf(ini)] = ini;
        run = new boolean[IO.Countrycode.size()][];
        Exported = new int[IO.Countrycode.size()][];
        Imported = new int[IO.Countrycode.size()][];
        Incidence = new double[IO.Countrycode.size()][];
        for (int i1 = 0; i1 < IO.Countrycode.size(); i1++) {
            run[i1] = new boolean[counties[i1].length];
            Exported[i1] = new int[counties[i1].length];
            Imported[i1] = new int[counties[i1].length];
            Incidence[i1] = new double[counties[i1].length];
            for (int i2 = 0; i2 < counties[i1].length; i2++) {
                run[i1][i2] = false;
                Exported[i1][i2] = 0;
                Imported[i1][i2] = 0;
                Incidence[i1][i2] = 0;
            }
        }
        for (int i = 0; i < Main.ObservationRange; i++) {
            ArrayList<String> Exportnewcases = new ArrayList<>();
            for (int i1 = 0; i1 < IO.Countrycode.size(); i1++) {
                for (int i2 = 0; i2 < counties[i1].length; i2++) {
                    run[i1][i2] = false;
                    Exported[i1][i2] = 0;
                    Imported[i1][i2] = 0;
                    Incidence[i1][i2] = 0;
                }
            }
            for (int i1 = 0; i1 < IO.Countrycode.size(); i1++) {
                if(IO.Countrycode.get(i1).equals("SC")){
                    continue;
                }
                for (int i2 = 0; i2 < counties[i1].length; i2++) {
                    if(counties[i1][i2].getInfected()!=0){
                        run[i1][i2] = true;
                        //break;
                    }
                }
            }
            for (int i1 = 0; i1 < IO.Countrycode.size(); i1++) {
                for (int i2 = 0; i2 < counties[i1].length; i2++) {
                    if(run[i1][i2]){
                        counties[i1][i2].Resume(i, Exported[i1][i2],Imported[i1][i2]);
                        int FlightCount = FlightCheck(i,i1,i2,CountyinCountry[i1].get(i2));
                        double Proportion = 1 * (double) FlightCount*300/ (double) counties[i1][i2].getPopulation();//ADJUST
                        double P = 1;
                        int SmallEP = (int)((double)FlightCount*(P));
                        double inc = 0;
                        if(i>150) {
                            inc = (double) counties[i1][i2].getExposed() / (double) counties[i1][i2].getPopulation();
                            System.out.println("Incidence: " + inc + "    Flight Count: " + FlightCount);
                            if(true) {//Expected Exp = 2400
                                System.out.println("Red Level Lockdown Enforced");
                                P = 0.01;
                                SmallEP = (int)((double)FlightCount*P);
                                counties[i1][i2].DATA.setFlightCancelled(FlightCount-SmallEP);
                            }

                            /**
                             * For scenario i5
                             */

                            /**if(inc * 100.0 > 1) {//Expected Exp = 2400
                                System.out.println("Grey Level Lockdown Enforced");
                                P = 0.0001;
                                SmallEP = (int)((double)FlightCount*P);
                                counties[i1][i2].DATA.setFlightCancelled(FlightCount-SmallEP);
                            }
                            else if(inc * 100.0 > 0.5) {//Expected Exp = 240
                                System.out.println("Red Level Lockdown Enforced");
                                P = 0.005;
                                SmallEP = (int)((double)FlightCount*P);
                                counties[i1][i2].DATA.setFlightCancelled(FlightCount-SmallEP);
                            }
                            else if(inc * 100.0 > 0.1){//Expected Exp = 100
                                System.out.println("Orange Level Lockdown Enforced");
                                P = 0.05;
                                SmallEP = (int)((double)FlightCount*P);
                                counties[i1][i2].DATA.setFlightCancelled(FlightCount-SmallEP);
                            }
                            else if(inc * 100.0 > 0.05) {//Expected Exp = 10
                                System.out.println("Yellow Level Lockdown Enforced");
                                P = 0.2;
                                SmallEP = (int)((double)FlightCount*P);
                                counties[i1][i2].DATA.setFlightCancelled(FlightCount-SmallEP);
                            }*/

                            /**
                             * For scenario m5
                             */


                            /*if (FlightCount * inc * 2> 100) {//Expected Exp = 24000
                                System.out.println("Grey Level Lockdown Enforced");
                                P = 0.0001;
                                SmallEP = (int) ((double) FlightCount * P);
                                counties[i1][i2].DATA.setFlightCancelled(FlightCount - SmallEP);
                            } else if (FlightCount * inc * 2> 10) {//Expected Exp = 2400
                                System.out.println("Red Level Lockdown Enforced");
                                P = 0.005;
                                SmallEP = (int) ((double) FlightCount * P);
                                counties[i1][i2].DATA.setFlightCancelled(FlightCount - SmallEP);
                            } else if (FlightCount * inc * 2> 1) {//Expected Exp = 1000
                                System.out.println("Orange Level Lockdown Enforced");
                                P = 0.05;
                                SmallEP = (int) ((double) FlightCount * P);
                                counties[i1][i2].DATA.setFlightCancelled(FlightCount - SmallEP);
                            } else if (FlightCount * inc * 2 > 0.1) {//Expected Exp = 100
                                System.out.println("Yellow Level Lockdown Enforced");
                                P = 0.2;
                                SmallEP = (int) ((double) FlightCount * P);
                                counties[i1][i2].DATA.setFlightCancelled(FlightCount - SmallEP);
                            }*/
                        }
                        Proportion  *= P;
                        double exp = (counties[i1][i2].getExposed()*0.8)*Proportion;
                        System.out.println("EXPORTATION: "+exp);
                        if(SmallEP>0){
                            Exportnewcases = RandomAssignArrival(i,i1,i2,CountyinCountry[i1].get(i2),exp);
                            System.out.println("Exportation: " +Exportnewcases.size());
                            double[] bufferpack = counties[i1][i2].DATA.getDataPack();
                            bufferpack[10] += Exportnewcases.size();
                            bufferpack[11] += ((double) SmallEP)*inc;
                            counties[i1][i2].DATA.setDatapack(bufferpack);
                            Modification(i1,i2,Exportnewcases,CountyinCountry[i1].get(i2),i);
                        }
                    }
                }
            }
        }
        PrintResult print = new PrintResult();
        print.PRINTOUT();
        String path = Parameters.PathDefault+"Output/Trace Trail "+ Main.ini +" "+Main.Filecode+".csv";
        outputFile = new File(path);
        try {
            TracePrint = new PrintWriter(outputFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < Trace.size(); i++) {
            for (int i1 = 0; i1 < 3; i1++) {
                TracePrint.print(Trace.get(i)[i1]+",");
            }
            TracePrint.println();
        }
        TracePrint.close();
    }

    public void Modification(int i1,int i2,ArrayList<String> Export,String name,int d){
        Exported[i1][i2] = Export.size();
        for (int i = 0; i < Export.size(); i++) {//Remove the patients from that city
            int country;
            int county;
            if(IO.IATACode.indexOf(Export.get(i))==-1){
                continue;
            }
            String buffer = IO.CountyName.get(IO.IATACode.indexOf(Export.get(i)));
            country = IO.Countrycode.indexOf(buffer.substring(0,2));
            county = CountyinCountry[IO.Countrycode.indexOf(buffer.substring(0,2))].indexOf(buffer);
            if(country==-1||county==-1){
                continue;
            }
            if(country==i1&&county==i2){
                continue;
            }
            String s[] = new String[3];
            s[0] = Integer.toString(d);
            s[1] = name;
            s[2] = buffer;
            Trace.add(s);
            if(counties[country][county].getInfected()==0){
                counties[country][county] = new County(buffer,i,1,Main.PopCountyinCountry[IO.Countrycode.indexOf(buffer.substring(0,2))].get(CountyinCountry[IO.Countrycode.indexOf(buffer.substring(0,2))].indexOf(buffer)));
                NAMING[country][county] = buffer;
            }
            else{
                double[] Modify = counties[country][county].DATA.getDataPack();
                Modify[9] += 1;
                if(Modify[2]>10){
                    Modify[2] += 1;
                    Modify[3] += 1;
                    Modify[4] += 1;
                    Modify[8] += 1;
                }
                Imported[country][county] +=1;
                counties[country][county].DATA.setDatapack(Modify);
            }
        }
    }
    public int FlightCheck(int i,int i1,int i2, String Name){
        int flightcount = 0;
        for (int i3 = 0; i3 < AirportinCountyandCountry[IO.Countrycode.indexOf(Name.substring(0, 2))][CountyinCountry[IO.Countrycode.indexOf(Name.substring(0, 2))].indexOf(Name)].size(); i3++) {
            //System.out.println(Flight.MenuIndex.indexOf(AirportinCountyandCountry[IO.Countrycode.indexOf(ini.substring(0, 2))][CountyinCountry[IO.Countrycode.indexOf(ini.substring(0, 2))].indexOf(ini)].get(i3)));
            if((Flight.MenuIndex.indexOf(AirportinCountyandCountry[IO.Countrycode.indexOf(Name.substring(0, 2))][CountyinCountry[IO.Countrycode.indexOf(Name.substring(0, 2))].indexOf(Name)].get(i3)))!=-1){
                for (int i4 = 0; i4 < Flight.Flights[0][8][i%7][Flight.MenuIndex.indexOf(AirportinCountyandCountry[IO.Countrycode.indexOf(Name.substring(0, 2))][CountyinCountry[IO.Countrycode.indexOf(Name.substring(0, 2))].indexOf(Name)].get(i3))].size(); i4++) {
                    flightcount++;
                }
            }
        }
        return flightcount;
    }

    public int DestinationFlightCheck(int i,int i1,int i2, String Name){
        int flightcount = 0;
        for (int i3 = 0; i3 < AirportinCountyandCountry[IO.Countrycode.indexOf(Name.substring(0, 2))][CountyinCountry[IO.Countrycode.indexOf(Name.substring(0, 2))].indexOf(Name)].size(); i3++) {
            //System.out.println(Flight.MenuIndex.indexOf(AirportinCountyandCountry[IO.Countrycode.indexOf(ini.substring(0, 2))][CountyinCountry[IO.Countrycode.indexOf(ini.substring(0, 2))].indexOf(ini)].get(i3)));
            if((Flight.MenuIndex.indexOf(AirportinCountyandCountry[IO.Countrycode.indexOf(Name.substring(0, 2))][CountyinCountry[IO.Countrycode.indexOf(Name.substring(0, 2))].indexOf(Name)].get(i3)))!=-1){
                for (int i4 = 0; i4 < Flight.Flights[0][8][i%7][Flight.MenuIndex.indexOf(AirportinCountyandCountry[IO.Countrycode.indexOf(Name.substring(0, 2))][CountyinCountry[IO.Countrycode.indexOf(Name.substring(0, 2))].indexOf(Name)].get(i3))].size(); i4++) {
                    flightcount++;
                }
            }
        }
        return flightcount;
    }

    public ArrayList RandomAssignArrival(int i, int i1, int i2, String Name, double OnBoard) {
        //OnBoard = 0;
        ArrayList<String> Places = new ArrayList<>();
        ArrayList<Integer> CODE = new ArrayList<>();
        ArrayList<Integer> DEPAR = new ArrayList<>();
        int Counter = 0;
        Random rad = new Random();
        ArrayList<String> choices = AirportinCountyandCountry[IO.Countrycode.indexOf(Name.substring(0,2))][CountyinCountry[IO.Countrycode.indexOf(Name.substring(0,2))].indexOf(Name)];
        for (int i3 = 0; i3 < choices.size(); i3++) {
            if(!Flight.MenuIndex.contains(choices.get(i3))){
                choices.remove(i3);
            }
        }
        for (int i3 = 0; i3 < (int) OnBoard*(1+0.1*rad.nextGaussian()); i3++) {
            DEPAR.add(rad.nextInt(choices.size()));
        }
        if(DEPAR.size()==0){
            return new ArrayList();
        }
        int date = Main.day;
        int week = i%7;
        for (int i3 = 0; i3 < DEPAR.size(); i3++) {
            if (Flight.MenuIndex.indexOf(choices.get(DEPAR.get(i3)))!=-1) {
                int Limit = Flight.Flights[0][8][week][Flight.MenuIndex.indexOf(choices.get(DEPAR.get(i3)))].size();
                if(Limit!=0){
                    CODE.add(rad.nextInt(Limit));
                }
                if(Flight.MenuIndex.indexOf(choices.get(DEPAR.get(i3)))>=Flight.Flights[0][8][week].length){
                    for (int i4 = 0; i4 < Flight.Flights[0][8][week].length; i4++) {
                        for (int i5 = 0; i5 < Flight.Flights[0][8][week][i4].size(); i5++) {

                        }
                    }
                    //System.out.println(Name);
                }
                if(i3<CODE.size()) {
                    int ind = CODE.get(i3);
                    Places.add(Flight.Flights[0][8][week][Flight.MenuIndex.indexOf(choices.get(DEPAR.get(i3)))].get(ind).getArrive());
                }
            }
        }
        return Places;
    }
}