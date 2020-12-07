    import java.io.File;
    import java.io.FileNotFoundException;
    import java.io.PrintWriter;
    import java.util.ArrayList;
    import java.util.Scanner;

    public class IO extends Flight{
        public static ArrayList<String> Countryname = new ArrayList<>();
        public static ArrayList<String> Countrycode = new ArrayList<>();
        public static ArrayList<Double> BasicReproduction = new ArrayList<>();
        public static ArrayList<double[]> AgeS = new ArrayList<>();
        public static ArrayList<String> Event = new ArrayList<>();
        public static ArrayList<double[][]> Matrices = new ArrayList<>();
        public static ArrayList<double[]> Biases = new ArrayList<>();
        public static ArrayList<Integer> Countrypopulation = new ArrayList<>();

        public IO(){
            File file = new File(Parameters.ReadPath+"FlightCity/WPP2019_TotalPopulationBySex.csv");
            File Codefile = new File(Parameters.ReadPath+"FlightCity/CCode.csv");
            File EVENT = new File(Parameters.ReadPath+"FlightCity/Countrycode.csv");
            File fileR0 = new File(Parameters.ReadPath+"FlightCity/R File.csv");
            Scanner scan = null;

            try {
                scan = new Scanner(Codefile);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            while (scan.hasNextLine()) {
                String buffer = scan.nextLine();
                int comma = buffer.indexOf(",");
                String sub1 = buffer.substring(comma+1);
                String sub2 = buffer.substring(0,comma);
                //System.out.println(sub1);
                String c = sub1.substring(0,sub1.indexOf(","));
                sub1 = sub1.substring(sub1.indexOf(",")+1);
                String r = sub1.substring(0,sub1.indexOf(","));
                sub1 = sub1.substring(sub1.indexOf(",")+1);
                double[] ages = new double[16];
                for (int i = 0; i < 16; i++) {
                    String b;
                    //System.out.println(i);
                    if(i==16){
                        b = sub1;
                    }
                    else {
                        b = sub1.substring(0,sub1.indexOf(","));
                    }
                    //System.out.println(b);
                    sub1 = sub1.substring(sub1.indexOf(",")+1);
                    double d = Double.parseDouble(b);
                    ages[i] = d;
                }
                Countryname.add(sub2);
                Countrycode.add(c);
                BasicReproduction.add(Double.parseDouble(r));
                AgeS.add(ages);
            }
            for (int i = 0; i < Countrycode.size(); i++) {
                //System.out.print(Countryname.get(i) + "  Code: "+ Countrycode.get(i) + "  R0：" + BasicReproduction.get(i) +"  Age Stratification: ");
                for (int i1 = 0; i1 < 16; i1++) {
                    //System.out.print(AgeS.get(i)[i1]+",");
                }
                //System.out.println();
            }
            //System.out.println(Countryname.size() + " Countries imported");
            //System.out.println(Flight.CountyName.size() + " Counties imported");
            File[] SocialMix = new File[Countrycode.size()];
            Scanner[] SocialScans = new Scanner[SocialMix.length];
            for (int i = 0; i < SocialMix.length; i++) {
                String Suf = " SocialMixing.csv";
                String name = Parameters.ReadPath+"Social Mixing 16/"+Countrycode.get(i)+Suf;
                SocialMix[i] = new File(name);
                try {
                    SocialScans[i] = new Scanner(SocialMix[i]);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }

            for (int i = 0; i < SocialMix.length; i++) {
                double[][] Matrix = new double[16][16];
                //System.out.println(Countrycode.get(i));
                String Empty = SocialScans[i].nextLine();
                for (int i1 = 0; i1 < 16; i1++) {
                    String line = SocialScans[i].nextLine();
                    line = line.substring(line.indexOf(",")+1);
                    String end = null;
                    for (int i2 = 0; i2 < 15; i2++) {
                        String d = line.substring(0,line.indexOf(","));
                        double D = Double.parseDouble(d);
                        if(i2>10){D+=0.3;}
                        line = line.substring(line.indexOf(",")+1);
                        end = line;
                        Matrix[i2][i1] = D;
                    }
                    if(end.contains(",")){
                        end = end.substring(0,end.indexOf(","));
                    }
                    double e = Double.parseDouble(end);
                    Matrix[i1][15] = e;
                }
                Matrices.add(Matrix);
                double[] bias = new double[16];
                for (int i1 = 0; i1 < 16; i1++) {
                    for (int i2 = 0; i2 < 16; i2++) {
                        bias[i1] += Matrix[i1][i2];
                    }
                }
                double s = 0;
                for (int i1 = 0; i1 < 16; i1++) {
                    s += bias[i1];
                    //System.out.print(bias[i1]+",");
                }
                for (int i1 = 0; i1 < 16; i1++) {
                    bias[i1] = 10 * bias[i1]/s;
                    //System.out.print(bias[i1]+",");
                }
                Biases.add(bias);
                //System.out.println();
            }
        }
        public static void main(String[] args) {
            IO io = new IO();
        }
    }
