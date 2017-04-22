package open.diogorosa.model;

import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
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
public class ConnectionListModel extends DefaultTreeModel {

    private List<ConnectionModel> list;
    private static final DefaultMutableTreeNode NODE = new DefaultMutableTreeNode("root");

    public ConnectionListModel(){
        this(null);
    }

    public ConnectionListModel(ConnectionModel connection){
        super(NODE);
        list = new ArrayList<>();
    }

    public boolean addConnection(ConnectionModel connection) throws Exception {
        if(!connectinonExists(connection)) {
            DefaultMutableTreeNode node = new DefaultMutableTreeNode(connection.getConnectionName());

            /*String[] TABLE_TYPES = {"TABLE"};
            DatabaseMetaData md = connection.getMetaData();
            ResultSet rs = md.getTables(null, null, "%", TABLE_TYPES);
            while (rs.next()) {
                if (filterSystemTables(rs.getString(3)))
                    node.add(new DefaultMutableTreeNode(rs.getString(3)));
            }

            DatabaseMetaData dbmd = connection.getConnection().getMetaData();
            ResultSet ctlgs = dbmd.getCatalogs();

            if(!ctlgs.isLast()) {
                while (ctlgs.next()) {
                    node.add(new DefaultMutableTreeNode(ctlgs.getString(1)));
                }
            }*/


            this.insertNodeInto(node, (MutableTreeNode) getRoot(), list.size());
            list.add(connection);

            return true;
        }else{
            throw new Exception("Connection already exists!");
        }
    }

    public boolean connectinonExists(ConnectionModel conn) throws SQLException {
        if(conn != null) {
            String url = conn.getConnection().getMetaData().getURL();

            Iterator<ConnectionModel> iterator = list.iterator();
            while (iterator.hasNext()) {
                ConnectionModel temp = iterator.next();
                String tempUrl = temp.getConnection().getMetaData().getURL();
                String tempName = temp.getConnectionName();

                if (tempUrl.compareTo(url) == 0 && tempName.compareToIgnoreCase(conn.getConnectionName()) == 0)
                    return true;
            }
        }

        return false;
    }

    public ConnectionModel getConnection(int i){
        return list.get(i);
    }

    public void removeConnection(int i) {
        list.remove(i);
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) getChild(getRoot(), i);
        removeNodeFromParent(node);
        reload();
    }
}
