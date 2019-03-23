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
    
    /*
	 * This method returns the distance between two places
	 * @param other: a place object
	 * @return distance: the distance between the place object and the "other" place object
	 */
	public static double distanceTo(int zip1, int zip2) {
        double R = 3963.1676;
		double phi1 = Math.toRadians(zips.get(zip1)[0]);
		double phi2 = Math.toRadians(zips.get(zip2)[0]);
		double pi1 = Math.toRadians(zips.get(zip1)[1]);
		double pi2 = Math.toRadians(zips.get(zip2)[1]);
		double distance = (R * (    Math.acos(   (Math.sin(phi1)*Math.sin(phi2)) +
				(Math.cos(phi1)*Math.cos(phi2)*Math.cos(pi1 - pi2)))));
		return distance;
    }
    
    public static void main(String[] args) throws FileNotFoundException, IOException {
        readInZipCodes();
        for (Integer i: zips.keySet()) {
            System.out.println("ZIP: " + i + ", LAT: " + zips.get(i)[0] + ", LNG: " + zips.get(i)[1]);
        }
        System.out.println("Distance between 28035 and 28036: " + distanceTo(28035, 28036));
    }
 }