import java.util.ArrayList;

public class Data {
    private double datapack[] = new double[12];
    private int FlightCancelled = 0;
    private double StratifiedPack[][] = new double[9][16];
    private ArrayList<Integer> ArrayPackInt[][] = new ArrayList[6][16];
    private ArrayList<Double> ArrayPackDouble[][] = new ArrayList[1][16];
    private ArrayList<Integer> NGMT = new ArrayList<>();
    private double[] AgeMatrix = new double[16];
    private double r0 = 2;
    private String n = "Unknown";
    public Data(){
        datapack[0] = Main.day;//=0
        datapack[1] = 100000;
        datapack[2] = 0;
        datapack[3] = 0;
        datapack[4] = 0;
        FlightCancelled = 0;
        datapack[5] = 0;
        datapack[6] = 0;
        datapack[7] = 0;
        datapack[8] = 0;
        datapack[9] = 0;//Importation
        datapack[10] = 0;//Exportation
        datapack[11] = 0;//Exportation
        AgeMatrix = new double[16];
        StratifiedPack = new double[9][16];
        for (int i = 0; i < 16; i++) {
            StratifiedPack[0][i] = Main.day;
        }
        ArrayList[] PatientStatus = new ArrayList[16];
        ArrayList[] DaysAfterOnset = new ArrayList[16];
        ArrayList[] PatientProperty = new ArrayList[16];
        ArrayList[] SocialActiveness = new ArrayList[16];
        ArrayList[] PatientAge = new ArrayList[16];//To simplify the code, we chose to use CFR to represent the age
        ArrayList[] NextGenerationMatrix = new ArrayList[16];
        ArrayList[] Immune = new ArrayList[16];

        for (int i = 0; i < 16; i++) {
            PatientStatus[i] = new ArrayList<Integer>();
            DaysAfterOnset[i] = new ArrayList<Integer>();
            PatientProperty[i] = new ArrayList<Integer>();
            SocialActiveness[i] = new ArrayList<Double>();
            PatientAge[i] = new ArrayList<Integer>();
            NextGenerationMatrix[i] = new ArrayList<Integer>();
            Immune[i] = new ArrayList<Integer>();
        }

        for (int i = 0; i < 6; i++) {
            ArrayPackInt[i] = new ArrayList[16];
            if(i==3){
                for (int i1 = 0; i1 < 16; i1++) {
                    ArrayList<Integer> Buff = new ArrayList<Integer>();
                    Buff.add(3);
                    ArrayPackInt[i][i1] = Buff;
                }
            }
            else {
                for (int i1 = 0; i1 < 16; i1++) {
                    ArrayPackInt[i][i1] = new ArrayList<Integer>();
                }
            }
        }

        for (int i = 0; i < 1; i++) {
            ArrayPackDouble[i] = new ArrayList[16];
            for (int i1 = 0; i1 < 16; i1++) {
                ArrayPackDouble[i][i1] = new ArrayList<Double>();
            }
        }
    }

    public Data(String name, double data[], ArrayList[][] ArrayPackInt, ArrayList[][] ArrayPackDouble, double R, double AgeMatrix[],double[] AgePopulationStratification, ArrayList<Integer> A, int Cancellation){
        this.NGMT = A;
        int sum = 0;
        FlightCancelled = Cancellation;
        for (int i = 0; i < 16; i++) {
            sum+= StratifiedPack[2][i];
        }
        if(data[2]==1&sum==0){
            StratifiedPack[2][6] = 1;
        }
        datapack = data;
        double s = 0;
        for (int i = 0; i < AgePopulationStratification.length; i++) {
            s+=AgePopulationStratification[i];
        }
        for (int i1 = 0; i1 < AgePopulationStratification.length; i1++) {
            double buff = AgePopulationStratification[i1] * datapack[1]/s;
            StratifiedPack[1][i1] = buff;
        }
        this.ArrayPackInt = ArrayPackInt;
        this.ArrayPackDouble = ArrayPackDouble;
        this.r0 = R;
        this.n = name;
        this.AgeMatrix = AgeMatrix;
    }

    public void setDatapack(double data[]){ datapack = data; }
    public void setStratifiedPack(double stratifiedPack[][]){this.StratifiedPack = stratifiedPack;}
    public void setArrayPackInt(ArrayList[][] ArrayPackInt){
        this.ArrayPackInt = ArrayPackInt;
    }
    public void setArrayPackDouble(ArrayList[][] ArrayPackDouble){
        this.ArrayPackDouble = ArrayPackDouble;
    }
    public void setAgeMatrix(double[] Matrix){this.AgeMatrix = Matrix;}
    public void setNGMT(ArrayList<Integer> A){NGMT = A;}
    public void setFlightCancelled(int Cancel){FlightCancelled = Cancel;}
    public double[] getDataPack(){
        return datapack;
    }
    public ArrayList[][] getIntPack(){
        return ArrayPackInt;
    }
    public ArrayList[][] getDoublePack(){ return ArrayPackDouble; }
    public double getR0(){ return r0;}
    public String getName() {return n;}
    public ArrayList<Integer> getNGMT(){return NGMT;}
    public double[][] getStratifiedPack() {return StratifiedPack;};
    public double[] getAgeMatrix(){return AgeMatrix;}
    public int getFlightCancelled(){return FlightCancelled;}
}
