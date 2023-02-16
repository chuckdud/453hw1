import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    public static void main(String[] args) {

//        List<List<String>> records = new ArrayList<>();
        ArrayList<Person> people = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("adult.data"))) {
            int sid = 0;
            String line;
            while ((line = br.readLine()) != null) {
                Person p = new Person(line, sid);
                people.add(p);
                sid++;
//                records.add(Arrays.asList(values));
            }
        } catch (Exception e) {

        }
        System.out.println("DONE");
    }
}