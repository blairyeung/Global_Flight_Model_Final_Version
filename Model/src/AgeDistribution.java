import java.util.Random;

public class AgeDistribution {

    public static int getExportationAge(String name){

        return 6;
    }

    public static int getPatientAge(String name, double[] AgeMatrix, double[] ImmunityMatrix){
        String Country = name.substring(0,2);
        int ind = IO.Countrycode.indexOf(Country);
        double distribut[] = new double[16];
        for (int i = 0; i < 16; i++) {
            double p  = ImmunityMatrix[i];
            distribut[i] = p * AgeMatrix[i];
        }

        double sum = 0;
        for (int i = 0; i < distribut.length; i++) {
            sum += distribut[i];
        }
        for (int i2 = 0; i2 < 16; i2++) {
            if (Double.toString(AgeMatrix[i2])=="NaN") {
                distribut = IO.AgeS.get(ind);
                sum = 10000;
                for (int i = 0; i < distribut.length; i++) {
                    distribut[i] = (int) (distribut[i]*100.0);
                }
                System.out.println(true);
                break;
            }
        }
        if(sum<10000){
            for (int i = 0; i < 16; i++) {
                distribut[i] = 10000.0*distribut[i]/sum;
            }
            sum = 10000;
        }
        Random rad = new Random();
        int age = 0;
        int rado = rad.nextInt((int)sum);
        int residu = 5;
        int Chara = 0;
        int last = 0;
        for (int i = 0; i < distribut.length; i++) {
            if(rado>0.98*sum){
                residu =15;
                break;
            }
            Chara += distribut[i];
            if(last<=rado&&rado<Chara) {
                residu = i;
                break;
            }
            last = Chara;
        }

        return residu;
    }
    public static double getMortalityRate(int age){
        return (0.047*Math.pow(Math.E,0.07*age));
    }
}
