import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * This class contains a method that is used to connect to the database,
 * and add three views that are used to run the various queries later on
 **/

public class DatabaseConnect {
    private Statement st;
    private Connection con;

    public DatabaseConnect(Statement st, Connection con) {
            this.st = st;
            this.con = con;
    }

    //Method using modified coded from studres: CS1003/Examples/W06-1-JDBC/JDBCExample3.java
    public void createTable() throws SQLException {

        /**
         * table name: passengers
         * passengerID(int, primary key), survived(boolean, stored as an int)
         * pClass(int), name(string), sex(string), age(float)
         * sibSp(int), parch(int), ticket(string), fare(float)
         * cabin(int), embarked(int)
         */
        st.executeUpdate("DROP TABLE IF EXISTS passengers");
        st.executeUpdate("CREATE TABLE passengers (\n"
                + " `passengerId` int(11) ,"
                + " `survived` int(11) ,"
                + " `pClass` int(11) ,"
                + " `name` varchar(100) ,"
                + " `sex` varchar(100) ,"
                + " `age` float(11) ,"
                + " `sibSp` int(11) ,"
                + " `parch` int(11) ,"
                + " `ticket` varchar(100) ,"
                + " `fare` float(11) ,"
                + " `cabin` varchar(100) ,"
                + " `embarked` varchar(100) ,"
                + " PRIMARY KEY (`passengerID`)"
                + ")"
        );

        //Set up views for later query execution in DBOperations class
        st.executeUpdate("DROP VIEW IF EXISTS query2");
        st.executeUpdate("DROP VIEW IF EXISTS query3");
        st.executeUpdate("DROP VIEW IF EXISTS query4");

        st.executeUpdate("CREATE VIEW query2 " +
                "AS SELECT COUNT(*) AS TOTAL" +
                " FROM passengers " +
                "WHERE survived=1");
        st.executeUpdate("CREATE VIEW query3 " +
                "AS " +
                "SELECT pClass, survived, COUNT(*) " +
                "FROM passengers GROUP BY pClass, survived");
        st.executeUpdate("CREATE VIEW query4 " +
                "AS " +
                "SELECT sex, survived, MIN(age) " +
                "FROM passengers GROUP BY sex, survived");

        //close statement
        st.close();
        System.out.println("OK");
    }

}
