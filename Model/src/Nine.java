import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Nine {
    public static void main(String[] args) throws FileNotFoundException {
        File output2 = new File("E:/Matrix/Contacts_2020.csv");
        Scanner myScan = new Scanner(output2);
        File output3 = new File("E:/Matrix/Max9.csv");
        PrintWriter printer = new PrintWriter(output3);
        while (myScan.hasNext()) {
            ArrayList<String> A = new ArrayList<>();
            String code = null;
            String[] groups = {"0 to 4","5 to 9","10 to 14","15 to 19","20 to 24","25 to 29","30 to 34","35 to 39","40 to 44","45 to 49","50 to 54","55 to 59","60 to 64","65 to 69","70 to 74","75+"};
            double[][] frequencies = new double[groups.length][groups.length];
            for (int i = 0; i < 256; i++) {
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
                int Place = i%groups.length;
                int Column = i/groups.length;
                /*witch (Column){

                }
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
                }*/
                frequencies[Column][Place] = F;
            }
            for (int i = 0; i < groups.length; i++) {
                for (int i1 = 0; i1 < groups.length; i1++) {
                    printer.println(code +","+groups[i]+"," +groups[i1]+","+frequencies[i][i1]);
                }
            }
        }
        printer.close();
    }
}
