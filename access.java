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
    private static HashMap<Integer, Integer> zipVisits = new HashMap<Integer, Integer>();
   //zip code, lat/long
    private static HashMap<Integer, Double[]> zips = new HashMap<Integer, Double[]>();
    private static HashMap<Double, Integer> distanceVisits = new HashMap<Double, Integer>();

    //private static HashMap<Integer, Double> zipPoverty = new HashMap<Integer, Double>();

    private static HashMap<Integer, Integer> tractToZip = new HashMap<Integer, Integer>();
    private static ArrayList<VisitRecord> visitList = new ArrayList<VisitRecord>();

    public static void readInTractToZip() throws FileNotFoundException, IOException {
        //change this file
        BufferedReader reader = new BufferedReader(new FileReader("ZipToTract.csv"));

        reader.readLine(); //skip line 1
        String line = reader.readLine();

        while (line != null && !line.isEmpty()) {

            String[] fields = line.split(",");
            try {

              int zipcode = Integer.valueOf(fields[0].trim());
              int tract = Integer.valueOf(fields[1].trim());
              System.out.println();

              if(! tractToZip.containsKey(tract)){
                tractToZip.put(tract, zipcode);
              }
            }

            catch (NumberFormatException e) {
            }
            line = reader.readLine();
    }
        reader.close();
    }

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


	public static double distanceToClinic( int zipCode) {

    double R = 3963.1676;
    double clinicLat = 35.228700;
    double clinicLong = -80.920360;
		double phi1 = Math.toRadians(clinicLat);
		double phi2 = Math.toRadians(zips.get(zipCode)[0]);
		double pi1 = Math.toRadians(clinicLong);
		double pi2 = Math.toRadians(zips.get(zipCode)[1]);
		double distance = (R * ( Math.acos( (Math.sin(phi1)*Math.sin(phi2)) +
				(Math.cos(phi1)*Math.cos(phi2)*Math.cos(pi1 - pi2)))));
		return distance;
    }

    public static void readInCSV() throws FileNotFoundException, IOException {
        //change this file
        BufferedReader reader = new BufferedReader(new FileReader("MedAssist.csv"));

        reader.readLine(); //skip line 1
        String line = reader.readLine();

        while (line != null && !line.isEmpty()) {
            String[] fields = line.split(",");
            try {
              int zipcode = Integer.valueOf(fields[0].trim());

              if((zips.containsKey(zipcode)){
                int totalAA = Integer.valueOf(fields[1].trim());
                int totalAI = Integer.valueOf(fields[2].trim());
                int totalA = Integer.valueOf(fields[3].trim());
                int totalC = Integer.valueOf(fields[4].trim());
                int totalH = Integer.valueOf(fields[5].trim());
                int totalOther = Integer.valueOf(fields[6].trim());
                int sum = totalAA + totalAI + totalA + totalC + totalH + totalOther;
                zipVisits.put(zipcode, sum);
                System.out.println(1);
                VisitRecord rec = new VisitRecord(zipcode, sum);
                visitList.add(rec);
              }

            }
            catch (NumberFormatException e) {
            }
            line = reader.readLine();
    }
        reader.close();
    }

    public static void calculateDistances(){
      for (Integer i: zipVisits.keySet()) {
        double d = distanceToClinic(i);
        distanceVisits.put(d, zipVisits.get(i));
      }
    }


    public static void main(String[] args) throws FileNotFoundException, IOException {
      readInZipCodes();
      readInCSV();
      //readInTractToZip();
      Collections.sort(list);
      for (int i = 0; i < 10; i++) {
        System.out.println(visitList.get(i).getZipcode());
      }
    }
 }
