import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

/**
 * How to build freq:
 * <p>
 * 1. 19, Bachelors, Never-married, white
 * 2. 23, PHD, Widowed, White
 * 3. 19, Bachelors, Never-married, black
 * 4. 19, HighSchool, Never-married, white
 * <p>
 * freq: [(19, 3, [1, 3, 4]), (23, 1, [2])]
 * <p>
 * freq: < (19, 3, [1,2,4]), (23, 1, [2]), (Bachelors, 2, [1,3]), (PHD, 1, [2]) ... >
 */

public class Main {
    /**
     * Increase the generalization of <age> from <degree> to <degree+1>
     * @param people List of People objects containing all data from adult.data
     * @param degree Current degree of generalization
     * @return
     */
    public static ArrayList<Person> generalizeAge(ArrayList<Person> people, int degree) {
        for (Person p : people) {
            int age = p.getAge();
            if (degree == 0) {
                if (age >= 1 && age < 12) p.setStr_age("[01,12)");
                else if (age >= 12 && age < 24) p.setStr_age("[12,24)");
                else if (age >= 24 && age < 36) p.setStr_age("[24,36)");
                else if (age >= 36 && age < 48) p.setStr_age("[36,48)");
                else if (age >= 48 && age < 60) p.setStr_age("[48,60)");
                else if (age >= 60 && age < 72) p.setStr_age("[60,72)");
                else if (age >= 72 && age < 84) p.setStr_age("[72,84)");
                else p.setStr_age("[84,99]");
            } else if (degree == 1) {
                if (age >= 1 && age < 24) p.setStr_age("[01,24)");
                else if (age >= 24 && age < 48) p.setStr_age("[24,48)");
                else if (age >= 48 && age < 72) p.setStr_age("[48,72)");
                else p.setStr_age("[72,99]");
            } else if (degree == 2) {
                if (age >= 1 && age < 48) p.setStr_age("[1,48)");
                else p.setStr_age("[48,99]");
            } else p.setStr_age("*");
        }
        return people;
    }

    /**
     * Increase the generalization of <education> from <degree> to <degree+1>
     * @param people List of People objects containing all data from adult.data
     * @param degree Current degree of generalization
     * @return
     */
    public static ArrayList<Person> generalizeEducation(ArrayList<Person> people, int degree) {
        for (Person p : people) {
            int educationNum = p.getEducationNum();
            if (degree == 0) {
                if (educationNum >= 1 && educationNum < 3) p.setEducation("[Preschool,6th]");
                else if (educationNum >= 3 && educationNum < 6) p.setEducation("[7th,10th]");
                else if (educationNum >= 6 && educationNum < 9) p.setEducation("[11th,HS-Grad]");
                else if (educationNum >= 9 && educationNum < 12) p.setEducation("[Some-College,Assoc-acdm]");
                else p.setEducation("[Bachelor,Doctorate]");
            } else if (degree == 1) {
                if (educationNum >= 1 && educationNum < 6) p.setEducation("[Preschool,10th]");
                else p.setEducation("[11th,HS-Grad]");
            } else p.setEducation("*");
        }
        return people;
    }

    /**
     * Married-civ-spouse, Divorced, Never-married, Separated, Widowed, Married-spouse-absent, Married-AF-spouse.
     * Increase the generalization of <maritalStatus> from <degree> to <degree+1>
     * @param people List of People objects containing all data from adult.data
     * @param degree Current degree of generalization
     * @return
     */
    public static ArrayList<Person> generalizeMaritalStatus(ArrayList<Person> people, int degree) {
        for (Person p : people) {
            String maritalStatus = p.getMaritalStatus();
            if (degree == 0) {
                if (maritalStatus.equals("Married-civ-spouse") ||
                        maritalStatus.equals("Married-spouse-absent") ||
                        maritalStatus.equals("Married-AF-spouse"))
                    p.setMaritalStatus("Married");
                else p.setMaritalStatus("Not Married");
            } else p.setMaritalStatus("*");
        }
        return people;
    }

    /**
     * Increase the generalization of <race> from <degree> to <degree+1>
     * @param people List of People objects containing all data from adult.data
     * @param degree Current degree of generalization
     * @return
     */
    public static ArrayList<Person> generalizeRace(ArrayList<Person> people, int degree) {
        for (Person p : people) {
            p.setRace("*");
        }
        return people;
    }

    public static ArrayList<Person> dataFly(ArrayList<Person> people, int k, int suppressionBudget) {
        int ageGeneralization = 0;
        int educationGeneralization = 0;
        int maritalStatusGeneralization = 0;
        int raceGeneralization = 0;

        ArrayList<ClusterEntry> clusters = new ArrayList<>();

        // while we need to generalize further && we can generalize further
        while (ageGeneralization < 4 || educationGeneralization < 3 || maritalStatusGeneralization < 2 || raceGeneralization < 1) {
            clusters = new ArrayList<>();

            clusters.add(new ClusterEntry(people.get(0)));

            // separate people into clusters
            for (int i = 0; i < people.size(); i++) {
                Person p = people.get(i);
                if (p.getSid() == 0) continue;
                boolean found = false;
                for (int j = 0; j < clusters.size(); j++) {
                    ClusterEntry c = clusters.get(j);
                    if (p.matches(c)) {
                        c.increaseCount(p.getSid());
                        found = true;
                        break;
                    }
                }
                if (!found) clusters.add(new ClusterEntry(p));
            }

            if (checkK(people, k, suppressionBudget)) break;

            ArrayList<String> uniqueAge = new ArrayList<>();
            ArrayList<String> uniqueEducation = new ArrayList<>();
            ArrayList<String> uniqueMaritalStatus = new ArrayList<>();
            ArrayList<String> uniqueRace = new ArrayList<>();

            for (Person p : people) {
                if (!uniqueAge.contains(p.getStr_age())) uniqueAge.add(p.getStr_age());
                if (!uniqueEducation.contains(p.getEducation())) uniqueEducation.add(p.getEducation());
                if (!uniqueMaritalStatus.contains(p.getMaritalStatus())) uniqueMaritalStatus.add(p.getMaritalStatus());
                if (!uniqueRace.contains(p.getRace())) uniqueRace.add(p.getRace());
            }

            int a = ageGeneralization == 4 ? 0 : uniqueAge.size();
            int b = educationGeneralization == 42 ? 0 : uniqueEducation.size();
            int c = maritalStatusGeneralization == 2 ? 0 :  uniqueMaritalStatus.size();
            int d = raceGeneralization == 1 ? 0 : uniqueRace.size();
            int maxDistortion = Math.max(a, Math.max(b, Math.max(c, d)));

            if (maxDistortion == 0) return people;
            else if (maxDistortion == uniqueAge.size() && ageGeneralization < 4) {
                people = generalizeAge(people, ageGeneralization);
                ageGeneralization++;
            } else if (maxDistortion == uniqueEducation.size() && educationGeneralization < 3) {
                people = generalizeEducation(people, educationGeneralization);
                educationGeneralization++;
            } else if (maxDistortion == uniqueMaritalStatus.size() && maritalStatusGeneralization < 2) {
                people = generalizeMaritalStatus(people, maritalStatusGeneralization);
                maritalStatusGeneralization++;
            } else if (maxDistortion == uniqueRace.size() && raceGeneralization < 1){
                people = generalizeRace(people, raceGeneralization);
                raceGeneralization++;
            }
        }

        System.out.println(ageGeneralization);
        System.out.println(educationGeneralization);
        System.out.println(maritalStatusGeneralization);
        System.out.println(raceGeneralization);

        System.out.println(people.size());
        // untested - suppress if needed
        ArrayList<Person> toRemove = new ArrayList<>();
        for (ClusterEntry ce : clusters) {
//            if (ce.getFreq() < k) {
//                for (Person p : people) {
//                    if (p.getStr_age().equals(ce.getStr_age()) && p.getEducation().equals(ce.getEducation()) &&
//                            p.getMaritalStatus().equals(ce.getMaritalStatus()) && p.getRace().equals(ce.getRace())) {
//                        people.remove(p);
//                    }
//                }
//            }

            if (ce.getFreq() < k) {
                for (int i : ce.getSids()) {
                    toRemove.add(people.get(i));
                }
            }
        }
        people.removeAll(toRemove);
        System.out.println(people.size());
        return people;
    }

    /**
     * if total people in clusters which do not follow k anonymity is less than our suppression budget,then those people
     *  can just be suppressed to achieve desired k anonymity
     * @param people
     * @param k
     * @param suppressionBudget
     * @return
     */
    public static boolean checkK(ArrayList<Person> people, int k, int suppressionBudget) {
        ArrayList<ClusterEntry> clusters = new ArrayList<>();
        clusters.add(new ClusterEntry(people.get(0)));
        // separate people into clusters
        for (int i = 0; i < people.size(); i++) {
            Person p = people.get(i);
            if (p.getSid() == 0) continue;
            boolean found = false;
            for (int j = 0; j < clusters.size(); j++) {
                ClusterEntry c = clusters.get(j);
                if (p.matches(c)) {
                    c.increaseCount(p.getSid());
                    found = true;
                    break;
                }
            }
            if (!found) clusters.add(new ClusterEntry(p));
        }
        int minK = clusters.get(0).getFreq();
        // number of people who fall outside of a k-anonymized cluster
        // these people can be suppressed iff we have less than suppressionBudget
        int countBelow = 0;
        for (ClusterEntry ce : clusters) {
            if (ce.getFreq() < minK) minK = ce.getFreq();
            if (ce.getFreq() < k) countBelow += ce.getFreq();
        }
        return minK >= k || countBelow <= suppressionBudget;
    }

    public static void main(String[] args) {

        ArrayList<Person> people = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("adult.data"))) {
            int sid = 0;
            String line;
            while ((line = br.readLine()) != null && !line.equals("")) {
                Person p = new Person(line, sid);
                people.add(p);
                sid++;
            }
        } catch (Exception e) {

        }

        people = dataFly(people, 10, 105);
        System.out.println(checkK(people, 10, 0));
//        for (Person p : people) {
//            System.out.println(p.toString());
//        }
    }
}