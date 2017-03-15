package org.launchcode.techjobs.console;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;//added by me
import java.util.List;
import org.apache.commons.lang3.StringUtils;


import static jdk.nashorn.internal.objects.NativeString.toLowerCase;


/**
 * Created by LaunchCode
 */
public class JobData {

    private static final String DATA_FILE = "resources/job_data.csv";
    private static Boolean isDataLoaded = false;

    private static ArrayList<HashMap<String, String>> allJobs;

    /**
     * Fetch list of all values from loaded data,
     * without duplicates, for a given column.
     *
     * @param field The column to retrieve values from
     * @return List of all of the values of the given field
     */
    public static ArrayList<String> findAll(String field) {//if (actionChoice.equals("list"). if
        //(columnChoice.equals("all"))

        // load data, if not already loaded
        loadData();

        ArrayList<String> values = new ArrayList<>();
        //for (HashMap<String, String> row : allJobs)
        for (HashMap<String, String> row : allJobs) {
            String aValue = row.get(field);//iterate over each row on the same coloumn field.

            if (!toLowerCase(values).contains(toLowerCase(aValue)))  {
                values.add(aValue);

            }
        }

        return values;
    }

    public static ArrayList<HashMap<String, String>> findAll() {//nothin in the argument. this task is complete.

        // load data, if not already loaded
        loadData();

        return allJobs;
    }
    /**
     * Returns results of search the jobs data by key/value, using
     * inclusion of the search term.
     *
     * For example, searching for employer "Enterprise" will include results
     * with "Enterprise Holdings, Inc".
     *
     * @param column   Column that should be searched.
     * @param value Value of teh field to search for
     * @return List of all jobs matching the criteria
     */
    public static ArrayList<HashMap<String, String>> findByColumnAndValue(String column, String value) {

        // load data, if not already loaded
        loadData();

        ArrayList<HashMap<String, String>> jobs = new ArrayList<>();

        for (HashMap<String, String> row : allJobs) {

            String aValue = row.get(column);
            if (StringUtils.containsIgnoreCase(aValue, value)) {
                jobs.add(row);
            }
        }

        return jobs;
    }



    /**
     * IMPLEMENT FINDBYVALUE
     *creates a new (public static) method that will search for a string within each of the
     *columns.
     *Returns results of search the jobs data by key/value, using
     * inclusion of the search term.
     *
     * For example, searching for employer "Enterprise" will include results
     * with "Enterprise Holdings, Inc".
     *@param value Value of teh field to search for
     * @return List of all jobs matching the criteria
     */

    public static ArrayList<HashMap<String, String>> findByValue(String value) {

        // load data, if not already loaded
        loadData();

        ArrayList<HashMap<String, String>> jobs = new ArrayList<>();


        for (HashMap<String, String> row : allJobs) {//alljobs is of this type---->>>ArrayList<HashMap<String,
            //String>>.
            //String aValue = row.get(value);
            for (Map.Entry<String, String> subhash : row.entrySet()){
                //for (Map.Entry<String, String> subhash : row.entrySet()) {
                //System.out.println(subhash.getKey() + " :" + subhash.getValue() );
                //String aValue = subhash.getValue();
                //if (toLowerCase(subhash.getValue()).contains(toLowerCase(value)))
                if(StringUtils.containsIgnoreCase(subhash.getValue(), value)) {
                //if ( toLowerCase(subhash.getValue()).contains(toLowerCase(value))) {
                    //containsIgnoreCase

                    //System.out.println("FindByValue-->over 'allJobs' search term value is: "+subhash.getValue
                    //());
                    jobs.add(row);
                    break;
                }


            }



        }



        return jobs;
    }

    /**
     * Read in data from a CSV file and store it in a list
     */
    private static void loadData() {

        // Only load data once
        if (isDataLoaded) {
            return;
        }

        try {

            // Open the CSV file and set up pull out column header info and records
            Reader in = new FileReader(DATA_FILE);
            CSVParser parser = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);
            List<CSVRecord> records = parser.getRecords();
            Integer numberOfColumns = records.get(0).size();
            String[] headers = parser.getHeaderMap().keySet().toArray(new String[numberOfColumns]);

            allJobs = new ArrayList<>();

            // Put the records into a more friendly format
            for (CSVRecord record : records) {
                HashMap<String, String> newJob = new HashMap<>();
                //System.out.println("Hello");

                //System.out.println("first for loop in the loadData, showing record: " + record);//printing
                //aded by me.
                for (String headerLabel : headers) {
                    newJob.put(headerLabel, record.get(headerLabel));
                }
                allJobs.add(newJob);

                isDataLoaded = true;

                //int count=0;

            }

        }


        catch (IOException e) {
            System.out.println("Failed to load job data");
            e.printStackTrace();
        }
    }

}