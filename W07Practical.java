import java.util.regex.*;
//Using regex to verify the file types

/**
 * The main class that brings everything together.
 * This class interprets the queries that are
 */

public class W07Practical {
    private static String dbFileName;
    private static String csvFileName;
    private static DBOperations dbops;

    public static void main(String[] args) {
        //create query
        if (args.length == 3){
            if (args[1].equalsIgnoreCase("create") && Pattern.matches("^\\w+.(db)$", args[0])) {
                try {
                    dbFileName = args[0];
                    csvFileName = args[2];
                    dbops = new DBOperations(dbFileName, csvFileName);
                    dbops.create();
                } catch (Exception e) {
                    System.err.println(e.getClass().getName() + ": " + e.getMessage());
                    System.exit(0);
                }
            } else {
                System.out.println("Usage: java -cp sqlite-jdbc.jar:. W07Practical <db_file> <action> [input_file]");
                System.exit(0);
            }
        //either run query1, query2, query3 or query4
        } else if (args.length == 2){
            if(Pattern.matches("^query[1-4]$", args[1]) && Pattern.matches("^\\w+.(db)$", args[0])) {
                try {
                    dbFileName = args[0];
                    dbops = new DBOperations(dbFileName);

                    if (args[1].equals("query1")) {
                        dbops.query1();
                    } else if (args[1].equals("query2")) {
                        dbops.query2();
                    } else if (args[1].equals("query3")) {
                        dbops.query3();
                    } else if (args[1].equals("query4")) {
                        dbops.query4();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Usage: java -cp sqlite-jdbc.jar:. W07Practical <db_file> <action> [input_file]");
                System.exit(0);
            }
            // if the wrong arguments are given, or not enough/too many are given, throw error and exit
        } else {
            System.out.println("Usage: java -cp sqlite-jdbc.jar:. W07Practical <db_file> <action> [input_file]");
            System.exit(0);
        }
    }
}