package open.diogorosa.customjcomponent;

import net.miginfocom.swing.MigLayout;
import open.diogorosa.model.ConnectionTableModel;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Diogo Rosa on 22/04/17.
 */
public class TabbedPaneContainer extends JPanel {
    private Connection connection;

    public TabbedPaneContainer(Connection connection) throws SQLException {
        super(new MigLayout("", "[grow, fill]", "[]"));
        this.connection = connection;

        init();
    }

    public void init() throws SQLException {
        String[] TABLE_TYPES = {"TABLE"};
        DatabaseMetaData md = connection.getMetaData();
        ResultSet rs = md.getTables(null, null, "%", TABLE_TYPES);
        while (rs.next()) {
            if (filterSystemTables(rs.getString(3))) {
                String tableName = rs.getString(3);
                add(new JLabel(tableName), "wrap");
                //JTable tableTemp = new JTable(new ConnectionTableModel(connection, tableName));
                //add(new JScrollPane(tableTemp), "wrap, grow");
                add(new JSeparator(), "wrap");
            }
        }
    }

    private boolean filterSystemTables(String tableName){
        return !tableName.startsWith("SDO_") && !tableName.startsWith("HELP")
                && !tableName.startsWith("DUAL") && !tableName.startsWith("HS_PARTITION")
                && !tableName.contains("$");
    }
}
