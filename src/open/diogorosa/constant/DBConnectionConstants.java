package open.diogorosa.constant;

/**
 * Created by Diogo Rosa on 09/04/2017.
 */
public class DBConnectionConstants {
    public static final String DB_ORACLE_CLASS = "oracle.jdbc.driver.OracleDriver";
    public static final String DB_ORACLE_JDBC_INIT = "jdbc:oracle:thin:@";

    public static final String DB_MYSQL_CLASS = "com.mysql.jdbc.Driver";
    public static final String DB_MYSQL_JDBC_INIT = "jdbc:mysql://";

    public static final String[] DB_TECH_OPTIONS = new String[]{"MYSQL", "ORACLE"};

    public static final String DB_TEST_CONNECTION_ERROR = "Error connecting to DB";
    public static final String DB_TEST_CONNECTION_NO_ERROR = "Success!!!";
}
