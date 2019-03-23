import java.util.PriorityQueue;

public class VisitRecord implements Comparable<VisitRecord>{
    
    private int zipcode;
    private int numVisits;

    public VisitRecord(int zipcode, int numVisits) {
        this.zipcode = zipcode;
        this.numVisits = numVisits;
    }

    @Override
    public int compareTo(VisitRecord rec) {
        if (numVisits > rec.numVisits) {
            return -1;
        }
        else if (numVisits < rec.numVisits) {
            return 1;
        }
        return 0;
    }

    public int getZipcode() {
        return zipcode;
    }
}