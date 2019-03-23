import java.util.HashMap;
import java.util.Map;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Program to calculate total visits per zip code for the Charlotte County Health Clinic.
 */

 public class csvImport {

    private static HashMap<Integer, Integer> cchcVisits = new HashMap<Integer, Integer>();

    public static void readInCCHC() throws FileNotFoundException, IOException {
        BufferedReader reader = new BufferedReader(new FileReader("CCHC.csv"));

        reader.readLine(); //skip line 1
        String line = reader.readLine();

        while (line != null && !line.isEmpty()) {
			      String[] fields = line.split(",");
            try {
              int zipcode = Integer.valueOf(fields[0].trim());
              int totalFemale = Integer.valueOf(fields[1].trim());
              int totalMale = Integer.valueOf(fields[2].trim());
              cchcVisits.put(zipcode, totalFemale + totalMale);
            }
            catch (NumberFormatException e) {
            }
            line = reader.readLine();
		}
        reader.close();
    }

    public static void main(String[] args) throws FileNotFoundException, IOException {
        readInCCHC();
        for (Integer i: cchcVisits.keySet()) {
            System.out.println("ZIP: " + i + ", Total Visits: " + cchcVisits.get(i));
        }
    }
 }
