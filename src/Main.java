import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

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

    public static int dataFly(ArrayList<Person> people) {
       ArrayList<Field2> freq = new ArrayList<>();

       DataType[] dataMembers = {DataType.AGE, DataType.WORKCLASS, DataType.EDUCATION, DataType.EDUCATION_NUM, DataType.MARITAL_STATUS, DataType.OCCUPATION, DataType.RELATION, DataType.RACE, DataType.SEX, DataType.CAPITAL_GAINS, DataType.CAPITAL_LOSS, DataType.HOURS_PER_WEEK, DataType.NATIVE_COUNTRY};

       int pos = 0;
       int total = 0;
       for (DataType dt : dataMembers){
           for (Person p : people) {
               Field2 f;
               if (dt == DataType.AGE) {
                   f = new Field2(p.getStr_age(), p.getSid());
               } else if (dt == DataType.EDUCATION) {
                   f = new Field2(p.getEducation(), p.getSid());
               } else if (dt == DataType.MARITAL_STATUS) {
                   f = new Field2(p.getMaritalStatus(), p.getSid());
               } else if (dt == DataType.RACE) {
                   f = new Field2(p.getRace(), p.getSid());
               } else continue;
                   boolean found = false;
                   for (Field2 inFreq : freq) {
                       if (f.getData().equals(inFreq.getData())) {
                           inFreq.increaseCount(p.getSid());
                           found = true;
                           break;
                       }
                   }
                   if (!found) freq.add(f);
               }
       }
       return 0;
   }
   public static void main(String[] args) {

        ArrayList<Person> people = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("adult.data"))) {
            int sid = 0;
            String line;
            while ((line = br.readLine()) != null) {
                Person p = new Person(line, sid);
                people.add(p);
                sid++;
            }
        } catch (Exception e) {

        }
        dataFly(people);
        System.out.println("DONE");
    }
}