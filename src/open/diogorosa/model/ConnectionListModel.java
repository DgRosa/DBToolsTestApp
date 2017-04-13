package open.diogorosa.model;

import javax.swing.tree.DefaultMutableTreeNode;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Diogo Rosa on 09/04/2017.
 */
public class ConnectionListModel extends DefaultMutableTreeNode {

    List<Connection> list;

    public ConnectionListModel(){
        super("root");
        list = new ArrayList<>();
    }

    public ConnectionListModel(Connection connection){
        super("root");
        list = new ArrayList<>();
    }

    public boolean addConnection(Connection connection) throws SQLException {
        if(!connectinonExists(connection)) {
            list.add(connection);

            DefaultMutableTreeNode node = new DefaultMutableTreeNode(connection.getSchema());

            String[] TABLE_TYPES = {"TABLE"};
            DatabaseMetaData md = connection.getMetaData();
            ResultSet rs = md.getTables(null, null, "%", TABLE_TYPES);
            while (rs.next()) {
                if (filterSystemTables(rs.getString(3)))
                    node.add(new DefaultMutableTreeNode(rs.getString(3)));
            }

            this.add(node);

            return true;
        }

        return false;
    }

    public boolean connectinonExists(Connection conn) throws SQLException {
        String db = conn.getMetaData().getDatabaseProductName();
        String url = conn.getMetaData().getURL();
        String user = conn.getMetaData().getUserName();

        Iterator<Connection> iterator = list.iterator();
        while(iterator.hasNext()){
            Connection temp = iterator.next();
            String tempDb = temp.getMetaData().getDatabaseProductName();
            String tempUrl = temp.getMetaData().getURL();
            String tempUser = temp.getMetaData().getUserName();

            if(tempDb.compareTo(db) == 0 && tempUrl.compareTo(url) == 0 && tempUser.compareTo(user) == 0)
                return true;
        }

        return false;
    }

    private boolean filterSystemTables(String tableName){
        return !tableName.startsWith("SDO_") && !tableName.startsWith("HELP")
                && !tableName.startsWith("DUAL") && !tableName.startsWith("HS_PARTITION")
                && !tableName.contains("$");
    }
}
