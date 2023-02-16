import java.util.Arrays;

public class Person {
    private int sid;
    private int age;
    private String workClass;
    private int fnlwgt;
    private String education;
    private int educationNum;
    private String maritalStatus;
    private String occupation;
    private String relationship;
    private String race;
    private String sex;
    private int capital_gain;
    private int capital_loss;
    private int hours_per_week;
    private String native_country;

    public Person(String line, int sid) {
        String[] values = line.split(",");
        if (values.length != 15) System.out.println("Error reading person");

        this.sid = sid;

        age = Integer.parseInt(values[0].trim());
        workClass = values[1].trim();
        fnlwgt = Integer.parseInt(values[2].trim());
        education = values[3].trim();
        educationNum = Integer.parseInt(values[4].trim());
        maritalStatus = values[5].trim();
        occupation = values[6].trim();
        relationship = values[7].trim();
        race = values[8].trim();
        sex = values[9].trim();
        capital_gain = Integer.parseInt(values[10].trim());
        capital_loss = Integer.parseInt(values[11].trim());
        hours_per_week = Integer.parseInt(values[12].trim());
        native_country = values[13].trim();
    }
}
