package open.diogorosa.model;

import open.diogorosa.constant.DBConnectionConstants;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by Diogo Rosa on 09/04/2017.
 */
public class DBConnection {

    public static final int MYSQL_TECH = 0;
    public static final int ORACLE_TECH = 0;

    public Connection getOracleConnection(String user, String password, String hostName, String port, String sid) throws Exception {
        int i = 1;

        Class.forName(DBConnectionConstants.DB_TECH_JDBC_CLASS[i]);
        Connection conn = DriverManager.getConnection(DBConnectionConstants.DB_TECH_JDBC_INIT[i]
                        + hostName + ":" + port + DBConnectionConstants.DB_TECH_JDBC_NAME_SEPERATOR[i] + sid
                , user, password);

        if(conn != null)
            return conn;
        else
            throw new Exception("Unable to connect to Oracle DB!");
    }

    public Connection getMyySqlConnection(String user, String password, String hostName, String port, String dbName) throws Exception {
        int i = 0;

        Class.forName(DBConnectionConstants.DB_TECH_JDBC_CLASS[i]);
        Connection conn = DriverManager.getConnection(DBConnectionConstants.DB_TECH_JDBC_INIT[i]
                        + hostName + ":" + port + DBConnectionConstants.DB_TECH_JDBC_NAME_SEPERATOR[i] + dbName
                , user, password);

        if(conn != null)
            return conn;
        else
            throw new Exception("Unable to connect to Oracle DB!");
    }

    /*public String[] getConnectionSplitInfo(String connName, Connection conn) throws SQLException {
        String[] result = new String[6];

        String url = conn.getMetaData().getURL();

        int i = getConnectionTech(url);

        result[0] = connName;
        result[1] = String.valueOf(i);
        result[2] = getConnectionHostName(url);
        result[3] = getConnectionPort(url);
        result[4] = getConnectionDBName(url);
        result[5] = conn.getMetaData().getUserName().substring(0
                , conn.getMetaData().getUserName().indexOf("@"));

        for(int j = 0; j < result.length; j++)
            System.out.println(result[j]);

        return result;
    }*/

    public static int getConnectionTech(Connection conn) throws SQLException {
        String url = conn.getMetaData().getURL();
        String[] tech = DBConnectionConstants.DB_TECH_JDBC_INIT;

        for(int i = 0; i < tech.length; i++)
            if (url.startsWith(tech[i]))
                return i;

        return -1;
    }

    public static String getConnectionHostName(Connection conn) throws SQLException {
        String url = conn.getMetaData().getURL();
        String tech = DBConnectionConstants.DB_TECH_JDBC_INIT[getConnectionTech(conn)];
        url = url.replaceFirst(tech, "");

        return url.substring(0, url.indexOf(":"));
    }

    public static String getConnectionPort(Connection conn) throws SQLException {
        String url = conn.getMetaData().getURL();
        int i = getConnectionTech(conn);

        url = url.substring(url.indexOf(getConnectionHostName(conn)));
        url = url.substring(url.indexOf(":") + 1);

        return url.substring(0, url.indexOf(DBConnectionConstants.DB_TECH_JDBC_NAME_SEPERATOR[i]));
    }

    public static String getConnectionDBName(Connection conn) throws SQLException {
        String url = conn.getMetaData().getURL();
        int i = getConnectionTech(conn);

        url = url.substring(url.indexOf(getConnectionPort(conn)));

        return url.substring(url.indexOf(DBConnectionConstants.DB_TECH_JDBC_NAME_SEPERATOR[i]) + 1);
    }

}
