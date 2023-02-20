import com.sun.source.tree.TryTree;

import java.io.*;
import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.util.*;
import java.util.Map;


/**
 * How to build freq:
 *
 * 1. 19, Bachelors, Never-married, white
 * 2. 23, PHD, Widowed, White
 * 3. 19, Bachelors, Never-married, black
 * 4. 19, HighSchool, Never-married, white
 *
 * freq: [(19, 3, [1, 3, 4]), (23, 1, [2])]
 *
 * freq: < (19, 3, [1,2,4]), (23, 1, [2]), (Bachelors, 2, [1,3]), (PHD, 1, [2]) ... >
 *
 *
 *
 */

public class Main {
    public static ArrayList<Person> peopleHigher25 = new ArrayList<>();

    public static DataQuery age25Higher;
    public static DataQuery ageLessOneOldest;
    public static DataQuery ageNot26;
    public static DataQuery ageNotYoungest;
    public static DataQuery ageOriginal;

    public static double sumAge25Higher = 0;
    public static double totalAge25Higher = 0;
    public static double averageAge25Higher = 0;
    public static double[] arr_averageAge25Higher = new double[1000];

    public static double sensitivity1 = 0;


    public static double sumLessOneOldest = 0;
    public static double totalLessOneOldest = 0;

    public static double averageAgeLessOneOldest = 0;
    public static double[] arr_averageAgeLessOneOldest = new double[1000];

    public static double sensitivity2 = 0;


    public static double sumAgeNot26 = 0;
    public static double totalAgeNot26 = 0;

    public static double averageAgeNot26 = 0;
    public static double[] arr_averageAgeNot26 = new double[1000];

    public static double sensitivity3 = 0;


   public  static double sumNoYoungest = 0;
    public static double totalNoYoungest = 0;
    public static double averageAgeNoYoungest = 0;
    public static double[] arr_averageAgeNoYoungest = new double[1000];

    public static double sensitivity4 = 0;


    public static double sumAgePeople = 0;
    public static double averageAgePeople = 0;
    public static double[] arr_averageAgePeople = new double[1000];

    public static double totalPeople = 0;
    public static double sensitivity5 = 0;

    public static double GS = 0;
    public static double LS = 0.001579;
    public static PrintWriter laplaceWriter = null;
    public static PrintWriter rmseTestWriter = null;
    public static PrintWriter kolmogorovSmirnovTestwriter = null;


    

    public static Map<Integer, Integer> educationFrequencies;
    public static Map<Integer, String> educationNumbers;





    public static void main(String[] args) {

//        sList<List<String>> records = new ArrayList<>();
        ArrayList<Person> people = new ArrayList<>();
       


        try (BufferedReader br = new BufferedReader(new FileReader("adult.data"))) {
            int sid = 0;
            String line;
            while ((line = br.readLine()) != null) {
                Person p = new Person(line, sid);
                int age = p.getAge();
                addIfNot(age, 26);
                addIfNot(age, 17);
                addIfOlder(age, 25);
                addToTotal(age);
                people.add(p);

                sid++;
//                records.add(Arrays.asList(values));
            }
        } catch (Exception e) {

        }
        computeAverageAges();
        System.out.println(String.format("averageAgePeople: %s\n averageAge25Higher: %s\n averageAgeNot26: %s\n averageAgeLessOneOldest: %s\n averageAgeNoYoungest: %s\n",averageAgePeople, averageAge25Higher, averageAgeNot26, averageAgeLessOneOldest, averageAgeNoYoungest));
        computeSensitivities();
        System.out.printf("SumPeople %s TotalPeople %s averageAgeLessOneOldest %s", sumAgePeople - 90, totalPeople - 1 , averageAgeLessOneOldest);



        try {
             laplaceWriter = new PrintWriter("C:\\Users\\lira4\\Documents\\Repos\\453hw1\\src\\data_query_average-ages.txt", "UTF-8");
            LaplaceNoiseGenerator lng = new LaplaceNoiseGenerator(averageAge25Higher, averageAgeLessOneOldest, averageAgeNot26, averageAgeNoYoungest, LS, laplaceWriter);
            lng.computeLaplaceNoise(arr_averageAge25Higher, arr_averageAgeLessOneOldest, arr_averageAgeNoYoungest, arr_averageAgeNot26, 0.5);
        }catch(IOException e){
            System.out.println("file not found");
        }finally{
            laplaceWriter.close();
        }

        try{
            rmseTestWriter = new PrintWriter("src/rmse_indistinguishability_test.txt", "UTF-8");
            RMSE test = new RMSE(rmseTestWriter);
            test.computeResult(arr_averageAge25Higher, LS, 0.5, arr_averageAgeLessOneOldest, arr_averageAgeNot26, arr_averageAgeNoYoungest);
            test.computeResult(arr_averageAge25Higher, LS, 1.0, arr_averageAgeLessOneOldest, arr_averageAgeNot26, arr_averageAgeNoYoungest);

        }catch(IOException e){
            System.out.println("file not found");
        }finally{
            rmseTestWriter.close();
        }

        try{
            kolmogorovSmirnovTestwriter = new PrintWriter("C:\\Users\\lira4\\Documents\\Repos\\453hw1\\src\\kolmogorov-smirnov_indistinguishability_test", "UTF-8");

            SortDoubleArray.sortMultipleArrays(arr_averageAge25Higher, arr_averageAgeLessOneOldest, arr_averageAgeNot26, arr_averageAgeNoYoungest);
            KolmogorovSmirnovTest kst = new KolmogorovSmirnovTest(kolmogorovSmirnovTestwriter);
            kst.computeResult(arr_averageAge25Higher, LS, 0.5, arr_averageAgeLessOneOldest, arr_averageAgeNot26, arr_averageAgeNoYoungest);
            kst.computeResult(arr_averageAge25Higher, LS, 1.0, arr_averageAgeLessOneOldest, arr_averageAgeNot26, arr_averageAgeNoYoungest);
        }catch(IOException e){
            System.out.println("file not found");
        }finally{
            kolmogorovSmirnovTestwriter.close();
        }


    }
    public static void initializeHash(){
        educationFrequencies = new HashMap<>();
        educationNumbers = new HashMap<>();


        educationFrequencies.put(13, 0);
        educationFrequencies.put(10, 0);
        educationFrequencies.put(7, 0);
        educationFrequencies.put(9, 0);
        educationFrequencies.put(15, 0);
        educationFrequencies.put(12, 0);
        educationFrequencies.put(11, 0);
        educationFrequencies.put(5, 0);
        educationFrequencies.put(4, 0);
        educationFrequencies.put(8, 0);
        educationFrequencies.put(14, 0);
        educationFrequencies.put(2, 0);
        educationFrequencies.put(6, 0);
        educationFrequencies.put(16, 0);
        educationFrequencies.put(3, 0);
        educationFrequencies.put(1, 0);

        educationFrequencies.put(13, 0);
        educationFrequencies.put(10, 0);
        educationFrequencies.put(7, 0);
        educationFrequencies.put(9, 0);
        educationFrequencies.put(15, 0);
        educationFrequencies.put(12, 0);
        educationFrequencies.put(11, 0);
        educationFrequencies.put(5, 0);
        educationFrequencies.put(4, 0);
        educationFrequencies.put(8, 0);
        educationFrequencies.put(14, 0);
        educationFrequencies.put(2, 0);
        educationFrequencies.put(6, 0);
        educationFrequencies.put(16, 0);
        educationFrequencies.put(3, 0);
        educationFrequencies.put(1, 0);

//        educationFrequencies.put("Bachelors", 0);
//        educationFrequencies.put("Some-college", 0);
//        educationFrequencies.put("11th", 0);
//        educationFrequencies.put("HS-grad", 0);
//        educationFrequencies.put("Prof-school", 0);
//        educationFrequencies.put("Assoc-acdm", 0);
//        educationFrequencies.put("Assoc-voc", 0);
//        educationFrequencies.put("9th", 0);
//        educationFrequencies.put("7th-8th", 0);
//        educationFrequencies.put("12th", 0);
//        educationFrequencies.put("Masters", 0);
//        educationFrequencies.put("1st-4th", 0);
//        educationFrequencies.put("10th", 0);
//        educationFrequencies.put("Doctorate", 0);
//        educationFrequencies.put("5th-6th", 0);
//        educationFrequencies.put("Preschool", 0);
    }

    public static void addIfOlder(int actualAge, int ageToCompare){
        if(actualAge > ageToCompare){
            if(ageToCompare == 25)
            sumAge25Higher += actualAge;
            totalAge25Higher ++;
        }
    }
    public static void addIfNot(int actualAge, int ageToCompare){
        if(actualAge != ageToCompare){
            if(ageToCompare == 17){
                sumNoYoungest += actualAge;
                totalNoYoungest ++;
            }
            if(ageToCompare == 26){
                sumAgeNot26 += actualAge;
                totalAgeNot26 ++;
            }
        }

    }
    public static void addToTotal(int actualAge){
        sumAgePeople += actualAge;
        totalPeople ++;

    }




    public static void computeAverageAges(){
        averageAge25Higher = sumAge25Higher / totalAge25Higher;
        averageAgeNot26 = (sumAgeNot26) / (totalAgeNot26);
        averageAgeNoYoungest = (sumNoYoungest) / (totalNoYoungest);
        averageAgeLessOneOldest = ( sumAgePeople - 90) / (totalPeople - 1);
        averageAgePeople = sumAgePeople / totalPeople;

    }

    public static void computeSensitivities(){

        sensitivity1 = averageAgePeople - averageAge25Higher;
        sensitivity2 = averageAgePeople - averageAgeLessOneOldest;
        sensitivity3 = averageAgePeople - averageAgeNot26;
        sensitivity4 = averageAgePeople - averageAgeNoYoungest;

        GS = 0.00015067;
    }



    







}