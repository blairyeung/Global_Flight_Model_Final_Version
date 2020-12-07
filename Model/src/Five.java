import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Five {
    public static void main(String[] args) throws FileNotFoundException {
        File output2 = new File("E:/Matrix/FurthurStra.csv");
        Scanner myScan = new Scanner(output2);
        File output3 = new File("E:/Matrix/Max.csv");
        PrintWriter printer = new PrintWriter(output3);
        while (myScan.hasNext()) {
            ArrayList<String> A = new ArrayList<>();
            String code = null;
            String[] groups = {"0 to 15","15 to 25","25 to 45","45 to 65","65+"};
            double[][] frequencies = new double[5][5];
            for (int i = 0; i < 80; i++) {
                A.add(myScan.nextLine());
            }
            for (int i = 0; i < A.size(); i++) {
                String line = A.get(i);
                String Code = line.substring(0,line.indexOf(","));
                code = Code;
                line = line.substring(line.indexOf(",")+1);
                String contactband = line.substring(0,line.indexOf(","));
                line = line.substring(line.indexOf(",")+1);
                String contacteeband = line.substring(0,line.indexOf(","));
                line = line.substring(line.indexOf(",")+1);
                line = line.substring(line.indexOf(",")+1);
                String Frequency = line;
                double F = Double.parseDouble(Frequency);
                int Place = i%5;
                int Column = i/5;
                if(Column<3){
                    Column = 0;
                }
                else if(Column<5){
                    Column = 1;
                }
                else if(Column<9){
                    Column = 2;
                }
                else if(Column<13){
                    Column = 3;
                }
                else {
                    Column = 4;
                }
                frequencies[Column][Place] +=F;
            }
            for (int i = 0; i < 5; i++) {
                for (int i1 = 0; i1 < 5; i1++) {
                    printer.println(code +","+groups[i]+"," +groups[i1]+","+frequencies[i][i1]);
                }
            }
        }
        printer.close();
    }
}
