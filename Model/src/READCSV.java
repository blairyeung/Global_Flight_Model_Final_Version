import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class READCSV {
    static int w = 50;
    static int empty = 5;
    public static void main(String[] args) throws Exception{
        IO io = new IO();
        int observation = 270;
        //int observation = 236;
        //int datenum = 4;
        double[] avg = new double[observation];
        double[] Max = new double[observation];
        double[] Min = new double[observation];
        double[][] GlobalAvg = new double[250][observation];
        double[][] GlobalHigh = new double[250][observation];
        double[][] GlobalLow = new double[250][observation];
        double[] corr = new double[observation];
        ArrayList<String> countrycode = IO.Countrycode;
        ArrayList<String> country = IO.Countryname;
        ArrayList<Double> PearsonProduct = new ArrayList<>();
        ArrayList<Integer> Dev = new ArrayList<>();
        ArrayList<Double> PercentageDev = new ArrayList<>();
        File files[] = new File[country.size()];
        File outpfiles[] = new File[country.size()];
        Scanner countryscan[] = new Scanner[country.size()];
        PrintWriter printers[] = new PrintWriter[country.size()];
        //String GlobalDeathPath = "E:/Global Model/gl.csv";
        File datefile = new File("E:/Date.csv");
        Scanner datescan = new Scanner(datefile);
        ArrayList<String> dates = new ArrayList<>();
        while (datescan.hasNext()) {
            dates.add(datescan.nextLine());
        }
        String firstline = "Date,";
        String firstline2 = "Date,";
        for (int i = 0; i < IO.Countryname.size(); i++) {
            firstline += (IO.Countryname.get(i)+" Median,"+IO.Countryname.get(i)+" Max," + IO.Countryname.get(i)+" Min,");
            firstline2 += (IO.Countryname.get(i)+",");
        }
        //String Input[] = {"Daily Infected"};
        String Input[] = {"Infected", "Exportation", "Importation", "Exportation OutBound", "Importation OutBound", "Death", "FlightsCancelled","Mobility Index","Daily Infected"};
        for (int S = 0; S < Input.length; S++) {
            int counter = 0;
            for (int i = 0; i < countrycode.size(); i++) {
                String path1 = Parameters.PathDefault+"Output/Country " + Input[S] +"/";
                String path2 = Input[S] +".csv";
                String code = IO.Countryname.get(i) + "  " + countrycode.get(i) +" ";
                String path =path1 + code + path2;
                if(countrycode.get(i).equals("BL")||countrycode.get(i).equals("CI")){
                    path = path1 + countrycode.get(i) + ".csv";
                }
                String outpath;
                outpath =path1 + code + "TR" + path2;
                if(countrycode.get(i).equals("BL")||countrycode.get(i).equals("CI")){
                    outpath = path1 + countrycode.get(i) + " TR.csv";
                }
                files[i] = new File(path);
                outpfiles[i] = new File(outpath);
                countryscan[i] = new Scanner(files[i]);
                printers[i] = new PrintWriter(outpfiles[i]);
            }
            String GlobalPath = Parameters.PathDefault+"Output/Country " + Input[S] +"/Global 95%CI " + Input[S] + ".csv";
            String Global = Parameters.PathDefault+"Output/Country " + Input[S] +"/Global Median " + Input[S] + ".csv";
            String GlobalMax = Parameters.PathDefault+"Output/Country " + Input[S] +"/Global Max " + Input[S] + ".csv";
            String GlobalMin = Parameters.PathDefault+"Output/Country " + Input[S] +"/Global Min " + Input[S] + ".csv";
            File GlobalMedianFile = new File(GlobalPath);
            File GlobalFile = new File(Global);
            File MaxFile = new File(GlobalMax);
            File MinFile = new File(GlobalMin);
            PrintWriter MedianPrinter = new PrintWriter(GlobalMedianFile);
            PrintWriter MeanPrinter = new PrintWriter(GlobalFile);
            PrintWriter MaxPrinter = new PrintWriter(MaxFile);
            PrintWriter MinPrinter = new PrintWriter(MinFile);
            for (int i = 0; i < countrycode.size(); i++) {
                ArrayList<String> line = new ArrayList<>();
                while (countryscan[i].hasNext()) {
                    line.add(countryscan[i].nextLine());
                }
                ArrayList<String[]> Trails = new ArrayList<>();
                for (int i1 = 0; i1 < w; i1++) {
                    Trails.add(new String[observation]);
                }
                String[] names = new String[w];
                for (int i1 = 0; i1 < line.size(); i1++) {
                    String buffering = line.get(i1);
                    String name = buffering.substring(0,buffering.indexOf(","));
                    buffering.substring(buffering.indexOf(",")+1);
                    names[i1] = name;
                    for (int i2 = 0; i2 < observation; i2++) {
                        String number = buffering.substring(0,buffering.indexOf(","));
                        String[] buff = Trails.get(i1);
                        buff[i2] = number;
                        buffering  = buffering.substring(buffering.indexOf(",")+1);
                        Trails.set(i1,buff);
                    }
                }
                String naming = names[0].substring(0,names[0].indexOf("Trail")-1);
                //System.out.println(naming);
                for (int i1 = 0; i1 < observation; i1++) {
                    double Mean[] = new double[w];
                    double lineaverage = 0;
                    if(i1==0){
                        printers[i].print("Date");
                    }
                    else {
                        printers[i].print(dates.get(i1)+"");
                    }
                    for (int i2 = 0; i2 < w; i2++) {
                        //System.out.print(Trails.get(i2)[i1]);
                        if(i2!=0){
                            printers[i].print(Trails.get(i2)[i1]);
                        }
                        if(i1!=0){
                            lineaverage += Double.parseDouble(Trails.get(i2)[i1])/(double)(w);
                            Mean[i2] = Double.parseDouble(Trails.get(i2)[i1]);
                            ////System.out.println(Mean[w]+"Mean");
                        }
                        //System.out.print(",");
                        printers[i].print(",");
                    }
                    double[] mean = median(Mean);

                    //System.out.println();
                    for (int i2 = 0; i2 < mean.length; i2++) {
                       // System.out.print(mean[i2]+",");
                    }

                    avg[i1] = mean[(w-empty)/2];
                    Max[i1] = mean[(int)(((double)w-empty)*0.975)-1];
                    Min[i1] = mean[(int)(0.025*((double)w))+empty+3];
                    GlobalAvg[i][i1] = mean[(w-empty)/2];
                    GlobalHigh[i][i1] = mean[(int)(((double)w-empty)*0.975)-1];
                    GlobalLow[i][i1] = mean[(int)(0.025*((double)w))+empty+3];

                    if(i1==0){
                        printers[i].print("Median,Up,Low");
                    }
                    else {
                        printers[i].print(avg[i1]+","+Mean[(int)(((double)w)*0.975)]+","+Mean[(int)(0.025*((double)w))+empty]+",");
                    }
                    printers[i].println();
                }
                /*GlobalAvg[i] = avg;
                GlobalHigh[i] = Max;
                GlobalLow[i] = Min;*/
            }
            for (int i1 = 0; i1 < printers.length; i1++) {
                printers[i1].close();
            }
            MedianPrinter.println(firstline);
            MeanPrinter.println(firstline2);
            MaxPrinter.println(firstline2);
            MinPrinter.println(firstline2);
            for (int i = 0; i < observation; i++) {
                MedianPrinter.print(dates.get(i+1)+",");
                MeanPrinter.print(dates.get(i+1)+",");
                MaxPrinter.print(dates.get(i+1)+",");
                MinPrinter.print(dates.get(i+1)+",");
                for (int i1 = 0; i1 < IO.Countryname.size(); i1++) {
                    MedianPrinter.print(GlobalAvg[i1][i]+","+GlobalHigh[i1][i]+","+GlobalLow[i1][i]+",");
                    MeanPrinter.print(GlobalAvg[i1][i]+",");
                    MaxPrinter.print(GlobalHigh[i1][i]+",");
                    MinPrinter.print(GlobalLow[i1][i]+",");
                }
                MedianPrinter.println();
                MeanPrinter.println();
                MaxPrinter.println();
                MinPrinter.println();
            }
            MedianPrinter.close();
            MeanPrinter.close();
            MaxPrinter.close();
            MinPrinter.close();
        }

    }

    public READCSV(){
        int observation = 270;
        //int observation = 236;
        //int datenum = 4;
        double[] avg = new double[observation];
        double[] Max = new double[observation];
        double[] Min = new double[observation];
        double[][] GlobalAvg = new double[250][observation];
        double[][] GlobalHigh = new double[250][observation];
        double[][] GlobalLow = new double[250][observation];
        double[] corr = new double[observation];
        ArrayList<String> countrycode = IO.Countrycode;
        ArrayList<String> country = IO.Countryname;
        ArrayList<Double> PearsonProduct = new ArrayList<>();
        ArrayList<Integer> Dev = new ArrayList<>();
        ArrayList<Double> PercentageDev = new ArrayList<>();
        File files[] = new File[country.size()];
        File outpfiles[] = new File[country.size()];
        Scanner countryscan[] = new Scanner[country.size()];
        PrintWriter printers[] = new PrintWriter[country.size()];
        //String GlobalDeathPath = "E:/Global Model/gl.csv";
        File datefile = new File("E:/Date.csv");
        Scanner datescan = null;
        try {
            datescan = new Scanner(datefile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ArrayList<String> dates = new ArrayList<>();
        while (datescan.hasNext()) {
            dates.add(datescan.nextLine());
        }
        String firstline = "Date,";
        String firstline2 = "Date,";
        for (int i = 0; i < IO.Countryname.size(); i++) {
            firstline += (IO.Countryname.get(i)+" Median,"+IO.Countryname.get(i)+" Max," + IO.Countryname.get(i)+" Min,");
            firstline2 += (IO.Countryname.get(i)+",");
        }
        //String Input[] = {"Daily Infected"};
        String Input[] = {"Infected", "Exportation", "Importation", "Exportation OutBound", "Importation OutBound", "Death", "FlightsCancelled","Mobility Index","Daily Infected"};
        for (int S = 0; S < Input.length; S++) {
            int counter = 0;
            for (int i = 0; i < countrycode.size(); i++) {
                String path1 = Parameters.PathDefault+"Output/Country " + Input[S] +"/";
                String path2 = Input[S] +".csv";
                String code = IO.Countryname.get(i) + "  " + countrycode.get(i) +" ";
                String path =path1 + code + path2;
                if(countrycode.get(i).equals("BL")||countrycode.get(i).equals("CI")){
                    path = path1 + countrycode.get(i) + ".csv";
                }
                String outpath;
                outpath =path1 + code + "TR" + path2;
                if(countrycode.get(i).equals("BL")||countrycode.get(i).equals("CI")){
                    outpath = path1 + countrycode.get(i) + " TR.csv";
                }
                files[i] = new File(path);
                outpfiles[i] = new File(outpath);
                try {
                    countryscan[i] = new Scanner(files[i]);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                try {
                    printers[i] = new PrintWriter(outpfiles[i]);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
            String GlobalPath = Parameters.PathDefault+"Output/Country " + Input[S] +"/Global 95%CI " + Input[S] + ".csv";
            String Global = Parameters.PathDefault+"Output/Country " + Input[S] +"/Global Median " + Input[S] + ".csv";
            String GlobalMax = Parameters.PathDefault+"Output/Country " + Input[S] +"/Global Max " + Input[S] + ".csv";
            String GlobalMin = Parameters.PathDefault+"Output/Country " + Input[S] +"/Global Min " + Input[S] + ".csv";
            File GlobalMedianFile = new File(GlobalPath);
            File GlobalFile = new File(Global);
            File MaxFile = new File(GlobalMax);
            File MinFile = new File(GlobalMin);
            PrintWriter MedianPrinter = null;
            try {
                MedianPrinter = new PrintWriter(GlobalMedianFile);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            PrintWriter MeanPrinter = null;
            try {
                MeanPrinter = new PrintWriter(GlobalFile);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            PrintWriter MaxPrinter = null;
            try {
                MaxPrinter = new PrintWriter(MaxFile);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            PrintWriter MinPrinter = null;
            try {
                MinPrinter = new PrintWriter(MinFile);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            for (int i = 0; i < countrycode.size(); i++) {
                ArrayList<String> line = new ArrayList<>();
                while (countryscan[i].hasNext()) {
                    line.add(countryscan[i].nextLine());
                }
                ArrayList<String[]> Trails = new ArrayList<>();
                for (int i1 = 0; i1 < w; i1++) {
                    Trails.add(new String[observation]);
                }
                String[] names = new String[w];
                for (int i1 = 0; i1 < line.size(); i1++) {
                    String buffering = line.get(i1);
                    String name = buffering.substring(0,buffering.indexOf(","));
                    buffering.substring(buffering.indexOf(",")+1);
                    names[i1] = name;
                    for (int i2 = 0; i2 < observation; i2++) {
                        String number = buffering.substring(0,buffering.indexOf(","));
                        String[] buff = Trails.get(i1);
                        buff[i2] = number;
                        buffering  = buffering.substring(buffering.indexOf(",")+1);
                        Trails.set(i1,buff);
                    }
                }
                String naming = names[0].substring(0,names[0].indexOf("Trail")-1);
                //System.out.println(naming);
                for (int i1 = 0; i1 < observation; i1++) {
                    double Mean[] = new double[w];
                    double lineaverage = 0;
                    if(i1==0){
                        printers[i].print("Date");
                    }
                    else {
                        printers[i].print(dates.get(i1)+"");
                    }
                    for (int i2 = 0; i2 < w; i2++) {
                        //System.out.print(Trails.get(i2)[i1]);
                        if(i2!=0){
                            printers[i].print(Trails.get(i2)[i1]);
                        }
                        if(i1!=0){
                            lineaverage += Double.parseDouble(Trails.get(i2)[i1])/(double)(w);
                            Mean[i2] = Double.parseDouble(Trails.get(i2)[i1]);
                            ////System.out.println(Mean[w]+"Mean");
                        }
                        //System.out.print(",");
                        printers[i].print(",");
                    }
                    double[] mean = median(Mean);

                    //System.out.println();
                    for (int i2 = 0; i2 < mean.length; i2++) {
                        // System.out.print(mean[i2]+",");
                    }

                    avg[i1] = mean[(w-empty)/2];
                    Max[i1] = mean[(int)(((double)w-empty)*0.975)-1];
                    Min[i1] = mean[(int)(0.025*((double)w))+empty+3];
                    GlobalAvg[i][i1] = mean[(w-empty)/2];
                    GlobalHigh[i][i1] = mean[(int)(((double)w-empty)*0.975)-1];
                    GlobalLow[i][i1] = mean[(int)(0.025*((double)w))+empty+3];

                    if(i1==0){
                        printers[i].print("Median,Up,Low");
                    }
                    else {
                        printers[i].print(avg[i1]+","+Mean[(int)(((double)w)*0.975)]+","+Mean[(int)(0.025*((double)w))+empty]+",");
                    }
                    printers[i].println();
                }
                /*GlobalAvg[i] = avg;
                GlobalHigh[i] = Max;
                GlobalLow[i] = Min;*/
            }
            for (int i1 = 0; i1 < printers.length; i1++) {
                printers[i1].close();
            }
            MedianPrinter.println(firstline);
            MeanPrinter.println(firstline2);
            MaxPrinter.println(firstline2);
            MinPrinter.println(firstline2);
            for (int i = 0; i < observation; i++) {
                MedianPrinter.print(dates.get(i+1)+",");
                MeanPrinter.print(dates.get(i+1)+",");
                MaxPrinter.print(dates.get(i+1)+",");
                MinPrinter.print(dates.get(i+1)+",");
                for (int i1 = 0; i1 < IO.Countryname.size(); i1++) {
                    MedianPrinter.print(GlobalAvg[i1][i]+","+GlobalHigh[i1][i]+","+GlobalLow[i1][i]+",");
                    MeanPrinter.print(GlobalAvg[i1][i]+",");
                    MaxPrinter.print(GlobalHigh[i1][i]+",");
                    MinPrinter.print(GlobalLow[i1][i]+",");
                }
                MedianPrinter.println();
                MeanPrinter.println();
                MaxPrinter.println();
                MinPrinter.println();
            }
            MedianPrinter.close();
            MeanPrinter.close();
            MaxPrinter.close();
            MinPrinter.close();
        }

    }

    public static double getPearsonCorrelationScore(double[] xData, double[] yData) {
        if (xData.length != yData.length)
            throw new RuntimeException("数据不正确！");
        double xMeans;
        double yMeans;
        double numerator = 0;// 求解皮尔逊的分子
        double denominator = 0;// 求解皮尔逊系数的分母

        double result = 0;
        // 拿到两个数据的平均值
        xMeans = getMeans(xData);
        yMeans = getMeans(yData);
        // 计算皮尔逊系数的分子
        numerator = generateNumerator(xData, xMeans, yData, yMeans);
        // 计算皮尔逊系数的分母
        denominator = generateDenomiator(xData, xMeans, yData, yMeans);
        // 计算皮尔逊系数
        result = numerator / denominator;
        return result;
    }

    /**
     * 计算分子
     *
     * @param xData
     * @param xMeans
     * @param yData
     * @param yMeans
     * @return
     */
    private static double generateNumerator(double[] xData, double xMeans, double[] yData, double yMeans) {
        double numerator = 0.0;
        for (int i = 0; i < xData.length; i++) {
            numerator += (xData[i] - xMeans) * (yData[i] - yMeans);
        }
        return numerator;
    }

    /**
     * 生成分母
     *
     * @param yMeans
     * @param yData
     * @param xMeans
     * @param xData
     * @return 分母
     */
    private static double generateDenomiator(double[] xData, double xMeans, double[] yData, double yMeans) {
        double xSum = 0.0;
        for (int i = 0; i < xData.length; i++) {
            xSum += (xData[i] - xMeans) * (xData[i] - xMeans);
        }
        double ySum = 0.0;
        for (int i = 0; i < yData.length; i++) {
            ySum += (yData[i] - yMeans) * (yData[i] - yMeans);
        }
        return Math.sqrt(xSum) * Math.sqrt(ySum);
    }

    /**
     * 根据给定的数据集进行平均值计算
     *
     * @param
     * @return 给定数据集的平均值
     */
    private static double getMeans(double[] datas) {
        double sum = 0.0;
        for (int i = 0; i < datas.length; i++) {
            sum += datas[i];
        }
        return sum / datas.length;
    }
    public static double[] median(double []nums){
        double nums2[]  = nums;
        bubbleSort(nums2);
        return nums2;
    }

    public static void bubbleSort(double[] array) {
        int size = array.length;
        for (int i = size - 1; i > 0; i--) {
            for (int j = 0; j < i; j++) {
                if (array[j] > array[j + 1]) {
                    exchangeElements(array, j, j + 1);
                }
            }
        }
    }
    public static void exchangeElements(double[] array, int index1, int index2) {
        double temp = array[index1];
        array[index1] = array[index2];
        array[index2] = temp;
    }



    private static int partition(int nums[], int start, int end){
        int left=start;
        int right=end;
        int pivot=nums[left];
        while(left<right){
            while(left<right&&nums[right]>=pivot){
                right--;
            }
            if(left<right){
                nums[left]=nums[right];
                left++;
            }
            while(left<right&&nums[left]<=pivot){
                left++;
            }
            if(left<right){
                nums[right]=nums[left];
                right--;
            }
        }
        nums[left]=pivot;
        return left;
    }

}

