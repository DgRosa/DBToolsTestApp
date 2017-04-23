package open.diogorosa.model;

import javax.swing.table.DefaultTableModel;
import java.sql.*;

/**
 * Created by Diogo Rosa on 22/04/17.
 */
public class ConnectionTableModel extends DefaultTableModel {
    private static final String[] COLUMNS = new String[]{"#", "NAME", "TYPE"};

    private Connection connection;
    private String tableName;

    public ConnectionTableModel(Connection connection, String tableName) throws SQLException {
        this.connection = connection;
        this.tableName = tableName;



        init();
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }

    private void init() throws SQLException {
        DatabaseMetaData meta = connection.getMetaData();
        ResultSet rs = meta.getColumns(null, connection.getSchema(), tableName, "%");

        setColumnIdentifiers(COLUMNS);

        while(rs.next()){
            String colOrder = rs.getString(17);
            String colName = rs.getString(4);
            String colType = rs.getString(6) + "(" + rs.getString(7) + ")";

            String[] temp = new String[]{colOrder, colName, colType};
            addRow(temp);

            System.out.println("***********************************");
            for(int i = 1; i < rs.getMetaData().getColumnCount(); i++)
                System.out.println(rs.getString(i));
            System.out.println("***********************************");
        }
    }
}
