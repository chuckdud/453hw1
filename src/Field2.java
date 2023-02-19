import java.util.ArrayList;

public class Field2 {
    private String data;
    private int freq;
    private ArrayList<Integer> sids;

    Field2(String data, int sid) {
        this.data = data;
        freq = 1;
        sids = new ArrayList<>();
        sids.add(sid);
    }

    public void increaseCount(int sid) {
        freq++;
        sids.add(sid);
    }

    public int getFreq() {
        return freq;
    }

    public ArrayList<Integer> getSids() {
        return sids;
    }

    public String getData() {
        return data;
    }
}
