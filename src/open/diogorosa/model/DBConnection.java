package open.diogorosa.model;

import open.diogorosa.constant.DBConnectionConstants;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Created by Diogo Rosa on 09/04/2017.
 */
public class DBConnection {

    public Connection getOracleConnection(String user, String password, String hostName, String port, String sid) throws Exception {
        Class.forName(DBConnectionConstants.DB_ORACLE_CLASS);
        Connection conn = DriverManager.getConnection(DBConnectionConstants.DB_ORACLE_JDBC_INIT + hostName + ":" + port + ":" + sid
                , user, password);

        if(conn != null)
            return conn;
        else
            throw new Exception("Unable to connect to Oracle DB!");
    }

    public Connection getMyySqlConnection(String user, String password, String hostName, String port, String dbName) throws Exception {
        Class.forName(DBConnectionConstants.DB_MYSQL_CLASS);
        Connection conn = DriverManager.getConnection(DBConnectionConstants.DB_MYSQL_JDBC_INIT + hostName + ":" + port + "/" + dbName
                , user, password);

        if(conn != null)
            return conn;
        else
            throw new Exception("Unable to connect to Oracle DB!");
    }

}
