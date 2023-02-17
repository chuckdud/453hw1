import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Array;
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
    public static double sumAge25Higher = 0;
    public static double totalAge25Higher = 0;
    public static double averageAge25Higher = 0;

    public static double sumLessOneOldest = 0;
    public static double totalLessOneOldest = 0;

    public static double averageAgeLessOneOldest = 0;

    public static double sumAgeNot26 = 0;
    public static double totalAgeNot26 = 0;

    public static double averageAgeNot26 = 0;

   public  static double sumNoYoungest = 0;
    public static double totalNoYoungest = 0;
    public static double averageAgeNoYoungest = 0;

    public static double sumAgePeople = 0;
    public static double averageAgePeople = 0;
    public static double totalPeople = 0;




    public static ArrayList<Person> removeOldestAge = new ArrayList<>();
    public static ArrayList<Person> noAge26 = new ArrayList<>();
    public static ArrayList<Person> removeAnyYoungestAge = new ArrayList<>();
    public static int sensitivity = 0;

    public static int privacy_parameter; // epsilon
    public static void main(String[] args) {

//        List<List<String>> records = new ArrayList<>();
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
        System.out.println( LaplaceNoiseGenerator.generateNoise(1, 0.5));
        System.out.println("DONE");

    }

//    public class LaplaceNoiseGenerator {
//        public static double generateNoise(double sensitivity, double epsilon) {
//            Random random = new Random();
//            double u = random.nextDouble() - 0.5;
//            return -sensitivity * Math.signum(u) * Math.log(1 - 2 * Math.abs(u)) / epsilon;
//        }
//    }

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

    }

    public static void computeLaplaceNoise(){
        System.out.println(String.format("Noisy output of dataset with age greater than 26 using  e = 0.5: \n"));
        for (int i = 0; i < 1000; i++) {
            System.out.println(String.format(" %s: "));

        }
        System.out.print("-------------------------------------------------------\n");
        System.out.println(String.format("Dataset without one OLDEST AGE RECORD: \n"));
        System.out.print("-------------------------------------------------------\n");
        for (int i = 0; i < 1000; i++) {

        }
        System.out.print("-------------------------------------------------------\n");
        System.out.println(String.format("Dataset with age greater than 26: \n"));
        System.out.print("-------------------------------------------------------\n");
        for (int i = 0; i < 1000; i++) {

        }
        System.out.print("-------------------------------------------------------\n");
        System.out.println(String.format("Dataset with age greater than 26: \n"));
        System.out.print("-------------------------------------------------------\n");
        for (int i = 0; i < 1000; i++) {

        }
    }




}