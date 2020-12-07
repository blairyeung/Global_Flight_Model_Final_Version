import javax.naming.MalformedLinkException;
import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Matrix {
    public static void main(String[] args) throws Exception{
        File file = new File("E:/Matrix/synthetic_contacts_2020.csv");
        Scanner scan = new Scanner(file);
        File output = new File("E:/Matrix/Contacts_2020.csv");
        File output2 = new File("E:/Matrix/FurthurStra.csv");
        PrintWriter Printer = new PrintWriter(output);
        PrintWriter Printer2 = new PrintWriter(output2);
        ArrayList<String> Valid = new ArrayList<>();
        File countryfile = new File("E:/Matrix/Code.csv");
        Scanner countryscan = new Scanner(countryfile);
        ArrayList<String> ThreeAbb = new ArrayList<>();
        ArrayList<String> Name = new ArrayList<>();
        while (countryscan.hasNext()) {
            String line = countryscan.nextLine();
            String three = line.substring(0,3);
            System.out.println(three);
            String N = line.substring(4);
            System.out.println(N);
            ThreeAbb.add(three);
            Name.add(N);
        }
        ArrayList<String> lines = new ArrayList<>();
        while (scan.hasNext()) {
            String line = scan.nextLine();
            String Code = line.substring(0,line.indexOf(","));
            line = line.substring(line.indexOf(",")+1);
            String type = line.substring(0,line.indexOf(","));
            line = line.substring(line.indexOf(",")+1);
            String place = line.substring(0,line.indexOf(","));
            line = line.substring(line.indexOf(",")+1);
            String contactband = line.substring(0,line.indexOf(","));
            line = line.substring(line.indexOf(",")+1);
            String contacteeband = line.substring(0,line.indexOf(","));
            line = line.substring(line.indexOf(",")+1);
            String Frequency = line;
            double F = Double.parseDouble(Frequency);
            /*System.out.println(Code);
            System.out.println(type);
            System.out.println(place);
            System.out.println(contactband);
            System.out.println(contacteeband);
            System.out.println(F);*/
            //System.out.println(place);
            if(type.equals("overall")&&place.equals("all")){
                int ind = ThreeAbb.indexOf(Code);
                System.out.println(Code);
                Printer.println(Name.get(ind)+","+contactband+","+contacteeband+","+F);
            }
        }
        Printer.close();
        Scanner scanner = new Scanner(output);
        while (scanner.hasNext()) {
            String[] groups = {"0 to 15","15 to 25","25 to 45","45 to 65","65+"};
            double[] frequencies = {0,0,0,0,0};
            String group1 = null;
            String code = null;
            for (int i = 0; i < 16; i++) {
                String line = scanner.nextLine();
                String Code = line.substring(0,line.indexOf(","));
                code = Code;
                line = line.substring(line.indexOf(",")+1);
                String contactband = line.substring(0,line.indexOf(","));
                System.out.println(contactband);
                line = line.substring(line.indexOf(",")+1);
                String contacteeband = line.substring(0,line.indexOf(","));
                line = line.substring(line.indexOf(",")+1);
                line = line.substring(line.indexOf(",")+1);
                String Frequency = line;
                double F = Double.parseDouble(Frequency);
                group1 = contacteeband;
                if(i<3){
                    frequencies[0]+=F;
                }
                else if(i<5){
                    frequencies[1]+=F;
                }
                else if(i<9){
                    frequencies[2]+=F;
                }
                else if(i<13){
                    frequencies[3] +=F;
                }
                else {
                    frequencies[4] +=F;
                }
            }
            for (int i1 = 0; i1 < groups.length; i1++) {
                Printer2.println(code +","+group1+","+groups[i1]+","+frequencies[i1]);
            }
        }
        Printer2.close();
    }
}
