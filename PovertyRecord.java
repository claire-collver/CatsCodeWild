import java.util.PriorityQueue;

public class PovertyRecord implements Comparable<PovertyRecord>{

    public int zipcode;
    public Double percentageInPoverty;

    public PovertyRecord(int zipCode, Double percentageInPoverty) {
        this.zipcode = zipCode;
        this.percentageInPoverty = percentageInPoverty;
    }


    @Override
    public int compareTo(PovertyRecord rec) {
        if (percentageInPoverty > rec.percentageInPoverty) {
            return -1;
        }
        else if (percentageInPoverty < rec.percentageInPoverty) {
            return 1;
        }
        return 0;
    }

    public int getZipcode() {
      return zipcode;
    }

    public Double getPercentageInPoverty() {
      return percentageInPoverty;
    }


}
