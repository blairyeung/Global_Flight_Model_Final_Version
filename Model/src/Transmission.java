import java.util.ArrayList;
import java.util.Random;

public class Transmission extends County{

    private static String[] G = {"0 to 4","5 to 9","10 to 14","15 to 19","20 to 24","25 to 29","30 to 34","35 to 39","40 to 44","45 to 49","50 to 54","55 to 59","60 to 64","65 to 69","70 to 74","75+"};

    private double isolation = 0;

    private double R0 = 2.68;

    private String name = "CN-42";

    public Data Model(Data data){
        double[] datapack = data.getDataPack();
        double[][] StratifiedPack = data.getStratifiedPack();

        ArrayList<Integer> ArrayPackInt[][] = super.DATA.getIntPack();
        ArrayList<Double> ArrayPackDouble[][] = super.DATA.getDoublePack();

        ArrayList[] PatientStatus = ArrayPackInt[0];
        ArrayList[] DaysAfterOnset =  ArrayPackInt[1];
        ArrayList[] PatientProperty =  ArrayPackInt[2];
        ArrayList[] PatientAge = ArrayPackInt[3];
        ArrayList[] NextGenerationMatrix =  ArrayPackInt[4];
        ArrayList[] Immune = ArrayPackInt[5];
        ArrayList[] SocialActiveness = ArrayPackDouble[0] ;

        R0 = data.getR0();
        name = data.getName();

        int day = (int) datapack[0];
        int population = (int) datapack[1];
        int infected = (int) datapack[2];
        int exposed = (int) datapack[3];
        int activecases = (int) datapack[4];
        int Resolved = (int) datapack[5];
        int death = (int) datapack[6];
        int lockdown = (int) datapack[7];
        int immuned = (int) datapack[8];

        if(population<400000){
            population=400000;
        }

        double[] AgeMatrix = super.DATA.getAgeMatrix();
        double sums = 0;
        for (int i = 0; i < AgeMatrix.length; i++) {
            sums += AgeMatrix[i];
        }
        for (int i = 0; i < AgeMatrix.length; i++) {
            AgeMatrix[i] = 10000.0*AgeMatrix[i]/sums;
        }
        //System.out.println();


        double R,constant = 1;
        Random radchange = new Random();
        int newcase = 0;
        int caninfect =population;
        int contact = 0;
        double immunity;
        if(death>=lockdown&&constant<0.15){
            constant =0.15;
        }
        final double[] PStackOver = StratifiedPack[2];
        for (int i = 0; i < 16; i++) {
            if(infected==1&&PatientStatus[i].size()<1){
                PatientStatus[i].add(0);
                DaysAfterOnset[i].add(0);
                PatientProperty[i].add(-1);
                SocialActiveness[i].add(0.0);
                int a = 6;
                PatientAge[i].add(a);
                NextGenerationMatrix[i].add(0);
                Immune[i].add(0);
            }
        }
        double[] ImmunityMatrix = new double[16];
        for (int i = 0; i < 16; i++) {
            AgeMatrix = new double[16];
            //System.out.print("Age Group:"+i+"Size:"+StratifiedPack[2][i]);
            for (int i1 = 0; i1 < PStackOver[i]; i1++) {
                //System.out.println("Group" + i);
                //System.out.println("SIZE"+StratifiedPack[2][i]);
                //System.out.println(i1);
                //System.out.println(PatientStatus[i].size());
                PatientStatus[i].set(i1,1);
                SocialActiveness[i].set(i1,radchange.nextGaussian());
                DaysAfterOnset[i].set(i1,(int)(DaysAfterOnset[i].get(i1))+1);//days after being infected

                if((int)DaysAfterOnset[i].get(i1)>=5+ (double)SocialActiveness[i].get(i1)&&(int)PatientProperty[i].get(i1)==-1) {
                    exposed++;
                    StratifiedPack[3][i]++;
                    PatientProperty[i].set(i1,0);
                    continue;
                }
                if((int) DaysAfterOnset[i].get(i1)>6 &&(int) PatientProperty[i].get(i1)==0){
                    newcase++;
                    activecases++;
                    StratifiedPack[4][i]++;
                    PatientProperty[i].set(i1, 1);
                    continue;
                }
                if((int) DaysAfterOnset[i].get(i1)>11&&(int) PatientProperty[i].get(i1)==1) {
                    if (radchange.nextInt(1000) < Parameters.CFR[i]) {
                        PatientProperty[i].set(i1,4);
                    }
                    else {
                        PatientProperty[i].set(i1, 3);
                        activecases--;
                        StratifiedPack[4][i]--;
                        exposed--;
                        StratifiedPack[4][i]--;
                        Resolved++;
                        StratifiedPack[5][i]++;
                        continue;
                    }

                }
                if((int) DaysAfterOnset[i].get(i1)>18.8&&((int) PatientProperty[i].get(i1)==4)) {//canada 12
                    PatientProperty[i].set(i1, 2);
                    death++;
                    StratifiedPack[6][i]++;
                    activecases--;
                    StratifiedPack[4][i]--;
                    exposed--;
                    StratifiedPack[3][i]--;
                    continue;
                }
            }
            int contacts = 0;
            ImmunityMatrix = new double[16];
            for (int i1 = 0; i1 < ImmunityMatrix.length; i1++) {
                ImmunityMatrix[i1] = 1.0 - (StratifiedPack[8][i1]/StratifiedPack[1][i1]);
                if(ImmunityMatrix[i1]==0.0||infected<2){
                    ImmunityMatrix[i1] = 1.0;
                }
                //System.out.println("GROUP:"+i+","+ImmunityMatrix[i]);
            }
            for(int i4=((int)StratifiedPack[6][i]+(int)StratifiedPack[5][i]);i4<(int)(StratifiedPack[3][i]+StratifiedPack[6][i]+StratifiedPack[5][i]);i4++){
                contact = (Contact(constant, (double) SocialActiveness[i].get(Math.min(i4,SocialActiveness[i].size()-1)),name,i,AgeMatrix));
                immunity = 1.0- ((double) immuned/(double)population);
                if(radchange.nextInt(100)>isolation*100){
                    for (int i2 = 0; i2 < immunity*(double)contact-1; i2++) {
                        if((100>radchange.nextInt(100)+1)){
                            if(radchange.nextInt(10000)<(int)(10000.0*R0/(94))){
                                contacts++;
                                int a = AgeDistribution.getPatientAge(name,AgeMatrix,ImmunityMatrix);
                                PatientStatus[a].add(0);
                                DaysAfterOnset[a].add(0);
                                PatientProperty[a].add(-1);
                                SocialActiveness[a].add(0.0);
                                PatientAge[a].add(a);
                                Immune[a].add(0);
                                //NextGenerationMatrix[a].add(0);
                                infected++;
                                StratifiedPack[2][a]++;
                                immuned++;
                                StratifiedPack[8][a]++;
                                //NextGenerationMatrix[i].set(Math.min(i4,NextGenerationMatrix[i].size()-1),(int)NextGenerationMatrix[i].get(Math.min(i4,NextGenerationMatrix[i].size()-1))+1);
                            }
                        }
                    }
                }
            }
        }

        System.out.println("County: " + name +"    Day: " + day +"    Population: " + population +"    Infected: " + infected + "    cases: " +activecases +"    Exposed:"+exposed +"    Recovered:" +Resolved +"    Death:" +death +"    R0:" + R0 + "    CFR:" + 120*((double)death/(double)infected) +"%     Constant:"+lockdown);      day++;
        /*for (int i = 0; i < 16; i++) {
            System.out.println(G[i]+":"+(int) (100*StratifiedPack[6][i]/death)+"%,");
        }
        System.out.println();*/
        /*double avg = 0;
        for (int i = 0; i < 16; i++) {
            if(NextGenerationMatrix[i].size()>1000){
                int sum = 0;
                for (int i1 = 0; i1 < 1000; i1++) {
                    sum += (int) NextGenerationMatrix[i].get(i1);
                }
                double d = (double) sum/1000.0;
                avg += d/16;
                System.out.println("Age Group:" + i*5 +"    R0:" + d);
            }
        }
        System.out.println("AVERAGE R0:" + avg);*/

        datapack[0] = day;
        datapack[1] = population;
        datapack[2] = infected;
        datapack[3] = exposed;
        datapack[4] = activecases;
        datapack[5] = Resolved;
        datapack[6] = death;
        datapack[7] = lockdown;
        datapack[8] = immuned;

        ArrayPackInt[0] = PatientStatus;
        ArrayPackInt[1] = DaysAfterOnset;
        ArrayPackInt[2] = PatientProperty;
        ArrayPackInt[3] = PatientAge;
        ArrayPackInt[4] = NextGenerationMatrix;
        ArrayPackInt[5] = Immune;
        ArrayPackDouble[0] = SocialActiveness;

        super.DATA.setDatapack(datapack);
        super.DATA.setArrayPackInt(ArrayPackInt);
        super.DATA.setArrayPackDouble(ArrayPackDouble);
        super.DATA.setAgeMatrix(AgeMatrix);
        return DATA;
    }

    public Transmission() {

    }

    public Transmission(Data DATA) {
        double datapack[] = new double[9];
        ArrayList[] PatientStatus = new ArrayList[16];
        ArrayList[] DaysAfterOnset = new ArrayList[16];
        ArrayList[] PatientProperty = new ArrayList[16];
        ArrayList[] SocialActiveness = new ArrayList[16];
        ArrayList[] PatientAge = new ArrayList[16];//To simplify the code, we chose to use CFR to represent the age
        ArrayList[] NextGenerationMatrix = new ArrayList[16];
        ArrayList[] Immune = new ArrayList[16];
        for (int i = 0; i < 10; i++) {
            PatientStatus[i] = new ArrayList<Integer>();
            DaysAfterOnset[i] = new ArrayList<Integer>();
            PatientProperty[i] = new ArrayList<Integer>();
            SocialActiveness[i] = new ArrayList<Double>();
            PatientAge[i] = new ArrayList<Integer>();
            NextGenerationMatrix[i] = new ArrayList<Integer>();
            Immune[i] = new ArrayList<Integer>();
        }

        Day days[] = new Day[100];
        days[Main.day] = new Day(1,10000,1,0,0,0,0,0,0);
        datapack[0] = days[Main.day].getDay();
        datapack[1] = days[Main.day].getPopulation();
        datapack[2] = days[Main.day].getInfected();
        datapack[3] = days[Main.day].getExposed();
        datapack[4] = days[Main.day].getActivecases();
        datapack[5] = days[Main.day].getResolved();
        datapack[6] = days[Main.day].getDeath();
        datapack[7] = days[Main.day].getLockdown();
        datapack[8] = days[Main.day].getImmuned();
        DATA = Model(DATA);
        days[Main.day+1] = new Day((int)datapack[0],(int)datapack[1],(int)datapack[2],(int)datapack[3],(int)datapack[4],(int)datapack[5],(int)datapack[6],datapack[7],(int)datapack[8]);
        return;
    }

    public Data Model(Data data, Event localevent, int Exported, int Imported){
        double[] datapack = data.getDataPack();
        double[][] StratifiedPack = data.getStratifiedPack();

        ArrayList<Integer> ArrayPackInt[][] = super.DATA.getIntPack();
        ArrayList<Double> ArrayPackDouble[][] = super.DATA.getDoublePack();

        ArrayList[] PatientStatus = ArrayPackInt[0];
        ArrayList[] DaysAfterOnset =  ArrayPackInt[1];
        ArrayList[] PatientProperty =  ArrayPackInt[2];
        ArrayList[] PatientAge = ArrayPackInt[3];
        ArrayList[] NextGenerationMatrix =  ArrayPackInt[4];
        ArrayList[] Immune = ArrayPackInt[5];
        ArrayList[] SocialActiveness = ArrayPackDouble[0] ;

        R0 =data.getR0();
        name = data.getName();

        int day = (int) datapack[0];
        int population = (int) datapack[1];
        int infected = (int) datapack[2];
        int exposed = (int) datapack[3];
        int activecases = (int) datapack[4];
        int Resolved = (int) datapack[5];
        int death = (int) datapack[6];
        int lockdown = (int) datapack[7];
        int immuned = (int) datapack[8];

        if(population<400000){
            population=400000;
        }

        double[] AgeMatrix = super.DATA.getAgeMatrix();
        double sums = 0;
        for (int i = 0; i < AgeMatrix.length; i++) {
            sums += AgeMatrix[i];
        }
        for (int i = 0; i < AgeMatrix.length; i++) {
            AgeMatrix[i] = 10000.0*AgeMatrix[i]/sums;
        }
        System.out.println();

        double constant = 1;
        Random radchange = new Random();
        int newcase = 0;
        int caninfect =population;
        int contact = 0;
        double immunity;
        caninfect  = (int) (population - immuned);
        if(death>=localevent.getDeath()){
            lockdown++;
        }
        constant = 1.0- (double)lockdown*(1.0/(double)localevent.getSpeed());
        if(death>=localevent.getDeath()&&constant<0.15){
            constant =0.15;
        }
        final double[] PStackOver = StratifiedPack[2];

        for (int i = 0; i < 16; i++) {
            if(infected==1&&PatientStatus[i].size()<1){
                PatientStatus[i].add(0);
                DaysAfterOnset[i].add(0);
                PatientProperty[i].add(-1);
                SocialActiveness[i].add(0.0);
                NextGenerationMatrix[i].add(0);
                int a = 6;
                PatientAge[i].add(a);
                Immune[i].add(0);
            }
        }

        infected -=Exported;
        activecases -=Exported;
        exposed -=Exported;
        immuned -=Exported;

        for (int i = 0; i < Exported; i++) {
            int a = 6;
            PatientStatus[a].remove(PatientStatus[a].size()-1);
            DaysAfterOnset[a].remove(DaysAfterOnset[a].size()-1);
            PatientProperty[a].remove(PatientProperty[a].size()-1);
            SocialActiveness[a].remove(SocialActiveness[a].size()-1);
            NextGenerationMatrix[a].remove(NextGenerationMatrix[a].size()-1);
            PatientAge[a].remove(PatientAge[a].size()-1);
            Immune[a].remove(Immune[a].size()-1);
        }

        for (int i = 0; i < Imported; i++) {
            int a = 6;
            PatientStatus[a].add(0);
            DaysAfterOnset[a].add(0);
            PatientProperty[a].add(-1);
            SocialActiveness[a].add(0.0);
            PatientAge[a].add(a);
            Immune[a].add(0);
            infected++;
            StratifiedPack[2][a]++;
            immuned++;
            StratifiedPack[8][a]++;
        }

        double[] ImmunityMatrix = new double[16];
        for (int i = 0; i < 16; i++) {
            AgeMatrix = new double[16];
            //System.out.println("Age Group"+i+"   Size:"+StratifiedPack[2][i]);
            for (int i1 = 0; i1 < PStackOver[i]; i1++) {
                PatientStatus[i].set(i1, 1);
                SocialActiveness[i].set(i1, radchange.nextGaussian());
                DaysAfterOnset[i].set(i1, (int) (DaysAfterOnset[i].get(i1)) + 1);//days after being infected
                if ((int) DaysAfterOnset[i].get(i1) >= 5 + (double) SocialActiveness[i].get(i1) && (int) PatientProperty[i].get(i1) == -1) {
                    exposed++;
                    StratifiedPack[3][i]++;
                    PatientProperty[i].set(i1, 0);
                    continue;
                }
                if ((int) DaysAfterOnset[i].get(i1) > 6 && (int) PatientProperty[i].get(i1) == 0) {
                    newcase++;
                    activecases++;
                    StratifiedPack[4][i]++;
                    PatientProperty[i].set(i1, 1);
                    continue;
                }
                if ((int) DaysAfterOnset[i].get(i1) > 11 && (int) PatientProperty[i].get(i1) == 1) {
                    if (radchange.nextInt(1000) < Parameters.CFR[i]) {
                        PatientProperty[i].set(i1, 4);
                    } else {
                        PatientProperty[i].set(i1, 3);
                        activecases--;
                        StratifiedPack[4][i]--;
                        exposed--;
                        StratifiedPack[3][i]--;
                        Resolved++;
                        StratifiedPack[5][i]++;
                        continue;
                    }

                }
                if ((int) DaysAfterOnset[i].get(i1) > 18.8 && ((int) PatientProperty[i].get(i1) == 4)) {//canada 12
                    PatientProperty[i].set(i1, 2);
                    death++;
                    StratifiedPack[6][i]++;
                    activecases--;
                    StratifiedPack[4][i]--;
                    exposed--;
                    StratifiedPack[3][i]--;
                    continue;
                }
            }
            ImmunityMatrix = new double[16];
            for (int i1 = 0; i1 < ImmunityMatrix.length; i1++) {
                ImmunityMatrix[i1] = 1.0 - (StratifiedPack[8][i1]/StratifiedPack[1][i1]);
                if(ImmunityMatrix[i1]==0.0||infected<100){
                    ImmunityMatrix[i1] = 1.0;
                }
                //System.out.println("GROUP:"+i+","+ImmunityMatrix[i]);
            }
           for(int i4=((int)StratifiedPack[6][i]+(int)StratifiedPack[5][i]);i4<(int)(StratifiedPack[3][i]+StratifiedPack[6][i]+StratifiedPack[5][i]);i4++){
                contact = (Contact(constant, (double) SocialActiveness[i].get(Math.min(i4,SocialActiveness[i].size()-1)),name,i,AgeMatrix));
                immunity = 1.0- ((double) immuned/(double)population);
                if(radchange.nextInt(100)>isolation*100){
                    for (int i2 = 0; i2 < immunity*(double)contact-1; i2++) {
                        if((100>radchange.nextInt(100)+1)){
                            if(radchange.nextInt(10000)<(int)(10000.0*R0/(94))){
                                int a = AgeDistribution.getPatientAge(name,AgeMatrix,ImmunityMatrix);
                                if(a==-1){
                                    a = 6;
                                }
                                PatientStatus[a].add(0);
                                DaysAfterOnset[a].add(0);
                                PatientProperty[a].add(-1);
                                SocialActiveness[a].add(0.0);
                                PatientAge[a].add(a);
                                Immune[a].add(0);
                                //NextGenerationMatrix[a].add(0);
                                infected++;
                                StratifiedPack[2][a]++;
                                immuned++;
                                StratifiedPack[8][a]++;
                                //NextGenerationMatrix[i].set(Math.min(i4,NextGenerationMatrix[i].size()-1),(int)NextGenerationMatrix[i].get(Math.min(i4,NextGenerationMatrix[i].size()-1))+1);
                            }
                        }
                    }
                }
            }

        }
        /*for (int i = 0; i < 16; i++) {
            System.out.print("G"+i+","+ImmunityMatrix[i]);
            System.out.print("Pop:"+StratifiedPack[1][i]);
            System.out.println("Imm:"+StratifiedPack[8][i]);
        }*/
        System.out.println("County: " + name +"    Day: " + day +"    Population: " + population +"    Infected: " + infected + "    cases: " +activecases +"    Exposed:"+exposed +"    Recovered:" +Resolved +"    Death:" +death +"    R0:" + R0 + "    CFR:" + 120*((double)death/(double)infected) +"%     Constant:"+lockdown);
        /*for (int i = 0; i < 16; i++) {
            System.out.println(G[i]+":"+(int) (100*StratifiedPack[6][i]/death)+"%,");
        }*/
        /*double avg = 0;
        for (int i = 0; i < 16; i++) {
            if(NextGenerationMatrix[i].size()>1000){
                int sum = 0;
                for (int i1 = 0; i1 < 1000; i1++) {
                    sum += (int) NextGenerationMatrix[i].get(i1);
                }
                double d = (double) sum/1000.0;
                avg += d/16;
                System.out.println("Age Group:" + i*5 +"    R0:" + d);
            }
        }
        System.out.println("AVERAGE R0:" + avg);*/
        
        day++;
        datapack[0] = day;
        datapack[1] = population;
        datapack[2] = infected;
        datapack[3] = exposed;
        datapack[4] = activecases;
        datapack[5] = Resolved;
        datapack[6] = death;
        datapack[7] = lockdown;
        datapack[8] = immuned;

        ArrayPackInt[0] = PatientStatus;
        ArrayPackInt[1] = DaysAfterOnset;
        ArrayPackInt[2] = PatientProperty;
        ArrayPackInt[3] = PatientAge;
        ArrayPackInt[4] = NextGenerationMatrix;
        ArrayPackInt[5] = Immune;
        ArrayPackDouble[0] = SocialActiveness;

        super.DATA.setDatapack(datapack);
        super.DATA.setArrayPackInt(ArrayPackInt);
        super.DATA.setArrayPackDouble(ArrayPackDouble);
        super.DATA.setAgeMatrix(AgeMatrix);
        return DATA;
    }

    public static int Contact(double lockdown,double random, String name, int Age, double[] AgeMatrix) {
        double[][] Matrix = IO.Matrices.get(IO.Countrycode.indexOf(name.substring(0,2)));
        double[] bias = IO.Biases.get(IO.Countrycode.indexOf(name.substring(0,2)));
        Random rad = new Random();
        double poss;
        do {
            poss = rad.nextGaussian();
        }while(poss<-4.0);
        int contact = (int) (lockdown * ((4+random+0.2*poss)*6*bias[Age]));
        for (int i = 0; i < AgeMatrix.length; i++) {
            double s = Matrix[i][Age];
            AgeMatrix[i] = s * (1+0.1*random);
        }
        return contact;
    }

}
