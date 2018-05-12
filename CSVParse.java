import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * This class contains all the methods required to interact with the CSV file
 * that is passed in for the 'create' command. It reads all the lines from the
 * file and then creates a List<Passenger> that the DBOperations class can then
 * use to write to the database table.
 */
public class CSVParse {

    String fileName;
    File CSV;
    private List<Passenger> Passengers;

    public CSVParse(String fileName) {
        this.fileName = fileName;
        try {
            CSV = new File(fileName);
            Passengers = parsePassengers();
        } catch(Exception f){
            f.printStackTrace();
        }
        if (!CSV.exists()) {
            System.out.println("File does not exist. Exiting...");
        }
    }

    /**
     * The below method parses the CSV file so that the resulting
     * List can be used to populate the table via the DBOperations class
     * @return list of passenger objects
     * @throws IOException
     */
    public List<Passenger> parsePassengers () throws IOException {
        Scanner sc = new Scanner(CSV);
        List<Passenger> allPassengers = new ArrayList<>();
        sc.nextLine(); //to skip the first line with the headings

        try {
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] lineArray = line.split(",", -1);

                Integer id = getInt(lineArray[0]);
                Integer survived = getInt(lineArray[1]);
                Integer pClass = getInt(lineArray[2]);
                String name = getString(lineArray[3]);
                String sex = getString(lineArray[4]);
                Float age = getFloat(lineArray[5]);
                Integer sibSp = getInt(lineArray[6]);
                Integer parch = getInt(lineArray[7]);
                String ticket = getString(lineArray[8]);
                Float fare = getFloat(lineArray[9]);
                String cabin = getString(lineArray[10]);
                String embarked = getString(lineArray[11]);

                allPassengers.add(new Passenger(id, survived, pClass, name,
                        sex, age, sibSp, parch, ticket, fare,
                        cabin, embarked));
            }

        } catch(NumberFormatException e){
            e.printStackTrace();
        } finally {
            sc.close();
        }
        return allPassengers;
    }

    /**
     * The below three methods account for empty Strings being passed in from
     * the CSV file -- for example,
     * "18,1,2,"Williams  Mr. Charles Eugene",male,,0,0,244373,13,,S"
     * the above record is missing the 'cabin' value.
     *
     * In order to be able to pass these kinds of missing values to a
     * passenger object(since you cannot parse an empty string), you
     * need to be able to return a 'null' value.
     *
     * The below three methods account for this by checking if the string
     * is empty. If it is, they return null. If not, they parse the value.
     */
    public String getString(String s){
        return s.equals("") ? null : s;
    }

    public Float getFloat(String s) {
        return s.equals("") ? null : Float.parseFloat(s);
    }

    public Integer getInt(String s){
        return s.equals("") ? null : Integer.parseInt(s);
    }

    //GETTER METHOD FOR PASSENGER LIST -- used in DBOperations class
    public List<Passenger> getPassengers() {
        return Passengers;
    }
}
