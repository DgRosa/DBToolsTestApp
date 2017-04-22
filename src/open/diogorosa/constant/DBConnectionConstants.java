package open.diogorosa.constant;

/**
 * Created by Diogo Rosa on 09/04/2017.
 */
public class DBConnectionConstants {
    public static final String[] DB_TECH_OPTIONS = new String[]{"MYSQL", "ORACLE"};
    public static final String[] DB_TECH_JDBC_INIT = new String[]{"jdbc:mysql://", "jdbc:oracle:thin:@"};
    public static final String[] DB_TECH_JDBC_CLASS = new String[]{"com.mysql.jdbc.Driver"
            , "oracle.jdbc.driver.OracleDriver"};
    public static final String[] DB_TECH_JDBC_NAME_SEPERATOR = new String[]{"/", ":"};


    public static final String DB_ADD_CONNECTION_TITLE = "Adding...";
    public static final String DB_ADD_CONNECTION_ERROR = "Error adding new DB connection!";

    public static final String DB_TEST_CONNECTION_TITLE = "Testing...";
    public static final String DB_TEST_CONNECTION_ERROR = "Error connecting to DB!";
    public static final String DB_CONNECTION_NO_ERROR = "Success!!!";
}
