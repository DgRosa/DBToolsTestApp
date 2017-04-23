package open.diogorosa.model;

import org.apache.commons.dbutils.DbUtils;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by Diogo Rosa on 17/04/17.
 */
public class ConnectionModel {
    private Connection connection;

    private int connectionTech;
    private String connectionName;
    private String hostName;
    private String port;
    private String dbName;
    private String user;
    private String pass;

    public ConnectionModel(){
        this(null, -1, null, null, null, null, null, null);
    }

    public ConnectionModel(String connectionName, String user, String pass, Connection conn) throws SQLException {
        this(conn, DBConnection.getConnectionTech(conn), connectionName, DBConnection.getConnectionHostName(conn)
                , DBConnection.getConnectionPort(conn), DBConnection.getConnectionDBName(conn)
                , user, pass);
    }

    public ConnectionModel(Connection connection, int connectionTech, String connectionName, String hostName, String port, String dbName, String user, String pass) {
        this.connection = connection;

        this.connectionTech = connectionTech;
        this.connectionName = connectionName;
        this.hostName = hostName;
        this.port = port;
        this.dbName = dbName;
        this.user = user;
        this.pass = pass;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public int getConnectionTech() {
        return connectionTech;
    }

    public void setConnectionTech(int connectionTech) {
        this.connectionTech = connectionTech;
    }

    public String getConnectionName() {
        return connectionName;
    }

    public void setConnectionName(String connectionName) {
        this.connectionName = connectionName;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public void closeConnection() throws SQLException {
        DbUtils.close(connection);
    }
}
