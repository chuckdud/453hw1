import java.util.ArrayList;

public class ClusterEntry {
    private int age;
    private String str_age;
    private String education;
    private String maritalStatus;
    private String race;
    private int freq;
    private ArrayList<Integer> sids;

    ClusterEntry(Person p) {
        age = p.getAge();
        str_age = p.getStr_age();
        education = p.getEducation();
        maritalStatus = p.getMaritalStatus();
        race = p.getRace();
        freq = 1;
        sids = new ArrayList<>();
        sids.add(p.getSid());
    }

    public void increaseCount(int sid) {
        freq++;
        sids.add(sid);
    }

    public int getAge() {
        return age;
    }

    public String getStr_age() {
        return str_age;
    }

    public String getEducation() {
        return education;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public String getRace() {
        return race;
    }

    public int getFreq() {
        return freq;
    }

    public ArrayList<Integer> getSids() {
        return sids;
    }
}
