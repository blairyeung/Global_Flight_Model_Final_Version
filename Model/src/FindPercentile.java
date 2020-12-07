import java.io.File;

public class FindPercentile {
    //This class is intended to divide the files by risk (5th percentile: high risk 25th percentile: moderate risk else: low risk)
    public static void main(String[] args) {
        String PrePath = Parameters.PathDefault + "Trails/Adaptive mig/Country Importation OutBound/";
        String SurPath = Parameters.PathDefault + "Trails/Adaptive mig/Country Importation OutBound/";
        String Path = PrePath + SurPath;
        File MedianIn = new File(Path);
    }
}
