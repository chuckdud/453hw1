import java.util.Arrays;

public class Person {
    private int sid;

    // cha added
    private String str_age;
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

        // cha added
        str_age = values[0].trim();

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

    public boolean matches(ClusterEntry c) {
        if (this.str_age.equals(c.getStr_age()) &&
                this.education.equals(c.getEducation()) &&
                this.maritalStatus.equals(c.getMaritalStatus()) &&
                this.race.equals(c.getRace())) {
            return true;
        }
        else return false;
    }

    @Override
    public String toString() {
        return String.format("%s, %s, %s, %s, %d, %s, %s, %s, %s, %s, %s, %s, %s, %s", str_age, workClass, fnlwgt, education, educationNum, maritalStatus, occupation, relationship, race, sex, capital_gain, capital_loss, hours_per_week, native_country);
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public String getStr_age() {
        return str_age;
    }

    public void setStr_age(String str_age) {
        this.str_age = str_age;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getWorkClass() {
        return workClass;
    }

    public void setWorkClass(String workClass) {
        this.workClass = workClass;
    }

    public int getFnlwgt() {
        return fnlwgt;
    }

    public void setFnlwgt(int fnlwgt) {
        this.fnlwgt = fnlwgt;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public int getEducationNum() {
        return educationNum;
    }

    public void setEducationNum(int educationNum) {
        this.educationNum = educationNum;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getCapital_gain() {
        return capital_gain;
    }

    public void setCapital_gain(int capital_gain) {
        this.capital_gain = capital_gain;
    }

    public int getCapital_loss() {
        return capital_loss;
    }

    public void setCapital_loss(int capital_loss) {
        this.capital_loss = capital_loss;
    }

    public int getHours_per_week() {
        return hours_per_week;
    }

    public void setHours_per_week(int hours_per_week) {
        this.hours_per_week = hours_per_week;
    }

    public String getNative_country() {
        return native_country;
    }

    public void setNative_country(String native_country) {
        this.native_country = native_country;
    }
}
