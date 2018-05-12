import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * This class is intended to represent a single record from the database
 */
public class Passenger {

    //OBJECT VARIABLES
    //These are all the variables necessary for creating a Passenger record
    private Integer id;
    private Integer survived;
    private Integer pClass;
    private String name;
    private String sex;
    private Float age;
    private Integer sibSp;
    private Integer parch;
    private String ticket;
    private Float fare;
    private String cabin;
    private String embarked;

    //METHOD VARIABLES
    DecimalFormat df = new DecimalFormat("0.0###");

    //CONSTRUCTOR 1 -- Used when reading the CSV File
    public Passenger(Integer id, int survived, Integer pClass,
                     String name, String sex,
                     Float age, Integer sibSp, Integer parch,
                     String ticket, float fare,
                     String cabin, String embarked) {
        this.id = id;
        this.survived = survived;
        this.pClass = pClass;
        this.name = name;
        this.sex = sex;
        this.age = age;
        this.sibSp = sibSp;
        this.parch = parch;
        this.ticket = ticket;
        this.fare = fare;
        this.cabin = cabin;
        this.embarked = embarked;
    }

    //Constructor 2 -- Used when reading from the database
    //Pulling String values from the database instead of the
    //actual data types required makes it easier to account for
    //null values being passed into the database
    public Passenger(Integer id, String survived, String pClass,
                     String name, String sex,
                     String age, String sibSp, String parch,
                     String ticket, String fare,
                     String cabin, String embarked) {
        this.id = id;
        this.survived = setInt(survived);
        this.pClass = setInt(pClass);
        this.name = name;
        this.sex = sex;
        this.age = setFloat(age);
        this.sibSp = setInt(sibSp);
        this.parch = setInt(parch);
        this.ticket = ticket;
        this.fare = setFloat(fare);
        this.cabin = cabin;
        this.embarked = embarked;
    }

    /**
     * The below two methods are designed to convert the Strings passed
     * in through the second constructor(that takes almost all String
     * arguments) into the desired types -- Float and Integer types.
     *
     * They also take account of null values being passed in.
     */
    public Float setFloat(String f){
        return f == null ? null : Float.parseFloat(f);
    }

    public Integer setInt(String i){
        return i == null ? null : Integer.parseInt(i);
    }

    //GETTERS AND SETTERS
    public Integer getId() {
        return id;
    }

    public Integer isSurvived() {
        return survived;
    }

    public Integer getpClass() {
        return pClass;
    }

    public String getName() {
        return name;
    }

    public String getSex() {
        return sex;
    }

    public Float getAge() {
        return age;
    }

    public Integer getSibSp() {
        return sibSp;
    }

    public Integer getParch() {
        return parch;
    }

    public String getTicket() {
        return ticket;
    }

    public float getFare() {
        return fare;
    }

    public String getCabin() {
        return cabin;
    }

    public String getEmbarked() {
        return embarked;
    }

    //TO STRING: Used for printing records in the preferred format
    @Override
    public String toString(){
        df.setRoundingMode(RoundingMode.HALF_UP);
        //DecimalFormat object formats the fare to 4 decimal places as required by the spec
        String roundedFare = df.format(fare);
        return id + ", " + survived + ", " + pClass + ", " + name + ", "
                + sex + ", " + age + ", " + sibSp + ", " + parch + ", " + ticket
                + ", " + roundedFare + ", " + cabin + ", " + embarked;
    }

}
