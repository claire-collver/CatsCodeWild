import java.util.HashMap;
import java.util.Map;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Program to access provided data from Charlotte-Mecklenburg clinics.
 */

 public class access {

    private static HashMap<Integer, Double[]> zips = new HashMap<Integer, Double[]>();

    public static void readInZipCodes() throws FileNotFoundException, IOException {
        BufferedReader reader = new BufferedReader(new FileReader("zipcodes.txt")); 

        String line = reader.readLine();
        while (line != null && !line.isEmpty()) { 
			String[] fields = line.split(","); 
            int zipcode = Integer.valueOf(fields[0].trim());
            Double[] coords = new Double[2];
            coords[0] = Double.valueOf(fields[1].trim()); //lat
            coords[1] = Double.valueOf(fields[2].trim()); //lng
            zips.put(zipcode, coords);
            line = reader.readLine();
		} 
        reader.close();
    }
    
    public static void main(String[] args) throws FileNotFoundException, IOException {
        readInZipCodes();
        for (Integer i: zips.keySet()) {
            System.out.println("ZIP: " + i + ", LAT: " + zips.get(i)[0] + ", LNG: " + zips.get(i)[1]);
        }
    }
 }