import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
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

    public static double sensitivity1 = 0;


    public static double sumLessOneOldest = 0;
    public static double totalLessOneOldest = 0;

    public static double averageAgeLessOneOldest = 0;
    public static double sensitivity2 = 0;


    public static double sumAgeNot26 = 0;
    public static double totalAgeNot26 = 0;

    public static double averageAgeNot26 = 0;
    public static double sensitivity3 = 0;


   public  static double sumNoYoungest = 0;
    public static double totalNoYoungest = 0;
    public static double averageAgeNoYoungest = 0;
    public static double sensitivity4 = 0;


    public static double sumAgePeople = 0;
    public static double averageAgePeople = 0;
    public static double totalPeople = 0;
    public static double sensitivity5 = 0;








    public static void main(String[] args) {

//        sList<List<String>> records = new ArrayList<>();
        ArrayList<Person> people = new ArrayList<>();
        age25Higher = new DataQuery(0, 0, 0, 0);
        ageNot26 = new DataQuery(0,0,0,0);
        ageNotYoungest = new DataQuery(0,0,0,0);
        ageLessOneOldest = new DataQuery(0,0,0,0);
        ageOriginal = new DataQuery(0,0,0,0);


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
        computeLaplaceNoise(sensitivity1, sensitivity2, sensitivity3, sensitivity4, 0.5);
        System.out.println("DONE");

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
        averageAgeNot26 = sumAgeNot26 / totalAgeNot26;
        averageAgeNoYoungest = sumNoYoungest / totalNoYoungest;
        averageAgeLessOneOldest = ( sumAgePeople - 90) / (totalPeople - 1);
        averageAgePeople = sumAgePeople / totalPeople;

    }

    public static void computeSensitivities(){
        sensitivity1 = averageAgePeople - averageAge25Higher;
        sensitivity2 = averageAgePeople - averageAgeLessOneOldest;
        sensitivity3 = averageAgePeople - averageAgeNot26;
        sensitivity4 = averageAgePeople - averageAgeNoYoungest;
    }

    public static double roundTwoDecimalPlaces(double toRound){
        DecimalFormat df = new DecimalFormat("#.##");
        return Double.parseDouble(df.format(toRound));
    }

    public static void computeLaplaceNoise(double sensitivity1, double sensitivity2, double sensitivity3, double sensitivity4 , double epsilon){
        System.out.print("\n-------------------------------------------------------\n");
        System.out.print(String.format("Noisy output of dataset with age greater than 25 using. s1 = %s  e = %s: \n \n", sensitivity1, epsilon ));
        System.out.print("-------------------------------------------------------\n");

        for (int i = 0; i < 1000; i++) {
            System.out.print(String.format("%s, ", roundTwoDecimalPlaces(averageAge25Higher + LaplaceNoiseGenerator.generateNoise(sensitivity1, 0.5))) );
        }
        System.out.print("\n-------------------------------------------------------\n");
        System.out.print(String.format("Noisy output of dataset without one oldest age record. s2 = %s  e = %s: \n \n", sensitivity2, epsilon ));
        System.out.print("-------------------------------------------------------\n");
        for (int i = 0; i < 1000; i++) {
            System.out.print(String.format("%s, ", roundTwoDecimalPlaces(averageAgeLessOneOldest + LaplaceNoiseGenerator.generateNoise(sensitivity2, 0.5))) );
        }
        System.out.print("\n-------------------------------------------------------\n");
        System.out.print(String.format("Noisy output of dataset without without any age 26. s3 = %s  e = %s: \n \n", sensitivity3, epsilon));
        System.out.print("-------------------------------------------------------\n");
        for (int i = 0; i < 1000; i++) {
            System.out.print(String.format("%s, ", roundTwoDecimalPlaces(averageAgeNot26 + LaplaceNoiseGenerator.generateNoise(sensitivity3, 0.5))) );
        }
        System.out.print("\n-------------------------------------------------------\n");
        System.out.print(String.format("Noisy output of dataset without any youngest age. s4 = %s  e = %s: \n \n", sensitivity4, epsilon));
        System.out.print("-------------------------------------------------------\n");
        for (int i = 0; i < 1000; i++) {
            System.out.print(String.format("%s, ", roundTwoDecimalPlaces(averageAgeNoYoungest + LaplaceNoiseGenerator.generateNoise(sensitivity4, 0.5))) );
        }
    }




}