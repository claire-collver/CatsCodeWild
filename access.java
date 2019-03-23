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

   //zip code --> total num
   private static HashMap<Integer, Integer> cchcVisits = new HashMap<Integer, Integer>();

   //zip code, lat/long
    private static HashMap<Integer, Double[]> zips = new HashMap<Integer, Double[]>();

    private static HashMap<Double, Integer> distanceVisits = new HashMap<Double, Integer>();


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


	public static double distanceToClinic(double clinicLat, double clinicLong, int zip) {

    double R = 3963.1676;

		double phi1 = Math.toRadians(clinicLat);
		double phi2 = Math.toRadians(zips.get(zip)[0]);
		double pi1 = Math.toRadians(clinicLong);
		double pi2 = Math.toRadians(zips.get(zip)[1]);
		double distance = (R * (    Math.acos(   (Math.sin(phi1)*Math.sin(phi2)) +
				(Math.cos(phi1)*Math.cos(phi2)*Math.cos(pi1 - pi2)))));
		return distance;
    }

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

    public static void calculateDistances(){

      double clinicLat1 = 35.319114;
      double clinicLong1 = -80.776729;
      double clinicLat2 = 35.232695;
      double clinicLong2 = -80.92763;

      for (Integer i: cchcVisits.keySet()) {
        double d1 = distanceToClinic(clinicLat1, clinicLong1, i);
        double d2 = distanceToClinic(clinicLat2, clinicLong2, i);
        if(d1 <= d2){
          distanceVisits.put(d1, cchcVisits.get(i));
        }
        else{
          distanceVisits.put(d2, cchcVisits.get(i));
        }

      }

    }


    public static void main(String[] args) throws FileNotFoundException, IOException {

      readInCCHC();
      for (Integer i: cchcVisits.keySet()) {
          System.out.println("ZIP: " + i + ", Total Visits: " + cchcVisits.get(i));
      }

        readInZipCodes();
        for (Integer i: zips.keySet()) {
            System.out.println("ZIP: " + i + ", LAT: " + zips.get(i)[0] + ", LNG: " + zips.get(i)[1]);
        }

        calculateDistances();
        for(Double i: distanceVisits.keySet()){
              System.out.println("DISTANCE: " + i + " miles, Total Visits: " + cchcVisits.get(i));
        }
    }
 }
