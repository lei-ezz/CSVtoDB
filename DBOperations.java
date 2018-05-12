import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**This class contains all 5 database operations that can be
 * accessed through the main "W07practical" class.
 *
 * This class also contains instances of the CSVParse class and DatabaseConnect class
 * to carry out DB operations.
 * This way, only one class has to be instantiated as an object from the main class(this one)
 * as it uses all other necessary classes.
 *
 * This class provides abstraction so the main class need not view the
 * other classes.
 */

public class DBOperations {
    private Connection con;
    private Statement st;
    private PreparedStatement pst;
    private ResultSet rs;
    private DatabaseConnect dbc;
    private CSVParse csvp;
    private List<Passenger> passengers;

    /** Overloaded constructor.
     * @param DBFileName - used for all queries
     */
    public DBOperations(String DBFileName) {
        try {
            String url = "jdbc:sqlite:" + DBFileName;
            con = DriverManager.getConnection(url);
            st = con.createStatement();
            passengers = new ArrayList<Passenger>();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Overloaded constructor
     * this constructor is only run if the user invokes the 'create' command
     * it takes the CSV name and the name of the database file to modify
     * if the file does not exist, it creates it
     * @param dbFileName
     * @param csvFileName
     */
    public DBOperations(String dbFileName, String csvFileName) {
        try {
            String url = "jdbc:sqlite:" + dbFileName;
            con = DriverManager.getConnection(url);
            st = con.createStatement();
            dbc = new DatabaseConnect(st, con);
            csvp = new CSVParse(csvFileName);
            passengers = csvp.getPassengers();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //method to create table invokes the databaseconnect object
    public void create() throws SQLException {
        dbc.createTable();
        populateTable();
    }

    public void query1() throws SQLException {
            st = con.createStatement();
            rs = st.executeQuery("SELECT * FROM passengers");

            //Using MetaData, I can grab the column names directly from the database itself
            ResultSetMetaData rsmd = rs.getMetaData();
            int columns = rsmd.getColumnCount();
            for (int i = 1; i < columns; i++) {
                System.out.print(rsmd.getColumnName(i) + ", ");
            }
            System.out.println(rsmd.getColumnName(columns));

            while (rs.next()) {
                Passenger p = new Passenger(
                        rs.getInt("passengerID"),
                        rs.getString("survived"),
                        rs.getString("pClass"),
                        rs.getString("name"),
                        rs.getString("sex"),
                        rs.getString("age"),
                        rs.getString("sibSp"),
                        rs.getString("parch"),
                        rs.getString("ticket"),
                        rs.getString("fare"),
                        rs.getString("cabin"),
                        rs.getString("embarked")
                );
                passengers.add(p);
            }
            for (Passenger p : passengers) {
                System.out.println(p);
            }
    }

    public void query2() throws SQLException {
        System.out.println("Number of Survivors");
        st = con.createStatement();
        rs = st.executeQuery("SELECT * FROM query2");
        System.out.println(rs.getString("TOTAL"));
    }

    public void query3() throws SQLException {
        st = con.createStatement();
        rs = st.executeQuery("SELECT * FROM query3");
        System.out.println("pClass, survived, count");
        while (rs.next()) {
            System.out.println(rs.getInt(1) + ", " + rs.getInt(2) + ", " + rs.getInt(3));
        }
    }

    public void query4() throws SQLException {
        st = con.createStatement();
        rs = st.executeQuery("SELECT * FROM query4");
        System.out.println("sex, survived, minimum age");
        while (rs.next()) {
            System.out.println(rs.getString(1) + ", " + rs.getInt(2) + ", " + rs.getFloat(3));
        }
    }

    //The below method populates the table
    public void populateTable() {
        for(Passenger p : passengers){
            insertPassenger(p);
        }
    }

    /**
     * This method takes a passenger and inserts it's attributes into a preparedStatement
     * which it then executes, thereby inserting the passenger's information as a record
     * in the database
     * @param p
     */
    public void insertPassenger(Passenger p) {
        try {
            pst = con.prepareStatement("INSERT INTO passengers VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

            //passengerId, survived, pClass, name, sex, age, sibSp, parch, ticket, fare, cabin, embarked
            setInt(pst, 1, p.getId());
            setInt(pst, 2, p.isSurvived());
            setInt(pst, 3, p.getpClass());
            pst.setString(4, p.getName());
            pst.setString(5, p.getSex());
            setFloat(pst, 6, p.getAge());
            setInt(pst, 7, p.getSibSp());
            setInt(pst, 8, p.getParch());
            pst.setString(9, p.getTicket());
            setFloat(pst, 10, p.getFare());
            pst.setString(11, p.getCabin());
            pst.setString(12, p.getEmbarked());

            pst.executeUpdate();
            pst.close();
        } catch (SQLException sqle){
            sqle.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * This is to populate the table while accounting for null values
     * it adds the int passed in to the preparedstatement at the
     * index given, but sets it to Null if the value is null as well.
     * @param pst
     * @param index
     * @param number
     * @throws SQLException
     */
    public static void setInt(PreparedStatement pst, int index, Integer number) throws SQLException{
        if (number == null) {
            pst.setNull(index, Types.INTEGER);
        } else {
            pst.setInt(index, number);
        }
    }

    /**
     * This is to populate the table while accounting for null values
     * it adds the float passed in to the preparedstatement at the
     * index given, but sets it to Null if the value is null as well.
     * @param pst
     * @param index
     * @param number
     * @throws SQLException
     */
    public static void setFloat(PreparedStatement pst, int index, Float number) throws SQLException{
        if (number == null) {
            pst.setNull(index, Types.FLOAT);
        } else {
            pst.setFloat(index, number);
        }
    }

}
