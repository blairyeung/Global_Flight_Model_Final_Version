import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class RelativeFreq {
    private boolean aBoolean;

    public static void main(String[] args) throws Exception{
        File file = new File("E:/Matrix/Max9.csv");
        Scanner scan = new Scanner(file);
        File output = new File("E:/Matrix/Relative9.csv");
        PrintWriter Printer = new PrintWriter(output);
        ArrayList<String> lines = new ArrayList<>();
        while (scan.hasNext()) {
            String line = scan.nextLine();
            lines.add(line);
        }
        ArrayList<String> CODES = new ArrayList<>();
        ArrayList<String> Band1 = new ArrayList<>();
        ArrayList<String> Band2 = new ArrayList<>();
        ArrayList<Double> Frequencies = new ArrayList<>();
        ArrayList<Double> divideby = new ArrayList<>();
        double sum = 0;
        for (int i = 0; i < lines.size(); i++) {
            int ind = i/16;
            String line = lines.get(i);
            String Code = line.substring(0,line.indexOf(","));
            line = line.substring(line.indexOf(",")+1);
            String contactband = line.substring(0,line.indexOf(","));
            line = line.substring(line.indexOf(",")+1);
            String contacteeband = line.substring(0,line.indexOf(","));
            line = line.substring(line.indexOf(",")+1);
            line = line.substring(line.indexOf(",")+1);
            String Frequency = line;
            double F = Double.parseDouble(Frequency);
            CODES.add(Code);
            Band1.add(contactband);
            Band2.add(contacteeband);
            Frequencies.add(F);
            sum += F;
            if(i%16==15){
                divideby.add(sum);
                sum=0;
            }
        }
        for (int i = 0; i < CODES.size(); i++) {
            int ind = i/16;
            Printer.println(CODES.get(i)+","+Band1.get(i)+","+Band2.get(i)+","+(Frequencies.get(i)/divideby.get(ind)));
        }
        Printer.close();
    }
}
