package open.diogorosa.model;

import open.diogorosa.constant.DBConnectionConstants;
import open.diogorosa.controller.MainController;
import open.diogorosa.customjcomponent.TabbedPaneContainer;
import open.diogorosa.view.MainView;
import open.diogorosa.view.modal.AddConnectionModal;
import org.apache.commons.dbutils.DbUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;

/**
 * Created by diogorosa on 15/04/17.
 */
public class MainModel {
    private DBConnection connection;
    private ConnectionListModel connectionList;

    private MainController controller;
    private MainView view;

    private boolean isWorkerCancel = false;


    public MainModel(MainController cont){
        init(cont);
    }

    private void init(MainController cont) {
        controller = cont;
        view = cont.getView();

        connectionList = new ConnectionListModel();
        connection = new DBConnection();
    }

    private JDialog getProgressDialog(String message){
        JButton btCancel = new JButton("Cancel");
        JProgressBar progressBar;
        JPanel panel;

        progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);

        panel = new JPanel(new BorderLayout(5, 5));
        panel.add(progressBar, BorderLayout.CENTER);
        panel.add(btCancel, BorderLayout.SOUTH);
        panel.setBorder(BorderFactory.createEmptyBorder(11, 11, 11, 11));

        final JDialog dlg = new JDialog(view, message, true);
        dlg.getContentPane().add(panel);
        dlg.setResizable(false);
        dlg.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        dlg.setMinimumSize(new Dimension(500, 100));
        dlg.setLocationRelativeTo(view);
        dlg.pack();

        return dlg;
    }

    private void addActionToDialogCancelBt(JDialog dlg, SwingWorker worker, Connection connection){
        ((JButton)((JPanel)dlg.getContentPane().getComponent(0)).getComponent(1)).addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isWorkerCancel = true;

                worker.cancel(true);

                if(connection != null) {
                    try {
                        connection.close();
                    } catch (SQLException e1) {
                        return;
                    }
                }
            }
        });
    }

    private void addActionToDialogCancelBt(JDialog dlg, SwingWorker worker){
        ((JButton)((JPanel)dlg.getContentPane().getComponent(0)).getComponent(1)).addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                worker.cancel(true);
                dlg.setVisible(false);
            }
        });
    }

    public boolean addNewConnection(String connName, String tech, String user, String password, String hostName, String port, String sidOrDBName) {
        try {
            final JDialog dlg = getProgressDialog("Fetching DB tables info...");
            Connection conn;
            try {
                conn = testConnection(tech, user, password, hostName, port, sidOrDBName, true);
            }catch (Exception e){
                view.showPopUpMessage(null, e.getMessage()
                        , DBConnectionConstants.DB_ADD_CONNECTION_TITLE, JOptionPane.ERROR_MESSAGE
                        , isWorkerCancel);

                return false;
            }

            Connection finalConn = conn;

            if(finalConn != null) {
                SwingWorker<Boolean, Void> worker = new SwingWorker<Boolean, Void>() {
                    @Override
                    protected Boolean doInBackground() throws Exception {
                        try {
                            ConnectionModel connectionModel = new ConnectionModel(connName, user, password, finalConn);
                            if (connectionList.addConnection(connectionModel)) {
                                view.getTreeConnections().setModel(connectionList);
                                view.getTabOpenConn().add(connName, new JScrollPane(new TabbedPaneContainer(conn)));
                            } else
                                return false;

                            view.getTreeConnections().setSelectionRow(view.getTreeConnections().getRowCount() - 1);

                            return true;
                        }catch (Exception e){
                            view.showPopUpMessage(null, e.getMessage()
                                    , DBConnectionConstants.DB_ADD_CONNECTION_TITLE, JOptionPane.ERROR_MESSAGE
                                    , isWorkerCancel);
                            return false;
                        }
                    }

                    @Override
                    protected void done() {
                        isWorkerCancel = false;
                        dlg.setVisible(false);
                    }
                };

                addActionToDialogCancelBt(dlg, worker, finalConn);

                worker.execute();

                dlg.setVisible(true);

                return worker.get();
            }

            return false;
        } catch (InterruptedException | ExecutionException e) {
            return false;
        }
    }

    public Connection testConnection(String tech, String user, String password, String hostName, String port, String sidOrDBName, boolean isAdding){
        SwingWorker<Connection, Void> worker;

        try {
            final JDialog dlg = getProgressDialog(isAdding ? DBConnectionConstants.DB_ADD_CONNECTION_TITLE : DBConnectionConstants.DB_TEST_CONNECTION_TITLE);

            worker = new SwingWorker<Connection, Void>() {
                Connection conn;

                @Override
                protected Connection doInBackground() throws Exception {
                    try {
                        if(tech.compareTo(DBConnectionConstants.DB_TECH_OPTIONS[0]) == 0){
                            conn = connection.getMyySqlConnection(user, password
                                    , hostName, port, sidOrDBName);

                            return conn;
                        }else if(tech.compareTo(DBConnectionConstants.DB_TECH_OPTIONS[1]) == 0){
                            conn = connection.getOracleConnection(user, password
                                    , hostName, port, sidOrDBName);

                            return conn;
                        }

                        throw new Exception(DBConnectionConstants.DB_TEST_CONNECTION_ERROR);
                    }catch (Exception e){
                        if(isAdding)
                            view.showPopUpMessage(null, e.getMessage()
                                    , DBConnectionConstants.DB_ADD_CONNECTION_TITLE, JOptionPane.ERROR_MESSAGE
                                    , isWorkerCancel);
                        else
                            view.showPopUpMessage(null, e.getMessage()
                                    , DBConnectionConstants.DB_TEST_CONNECTION_TITLE, JOptionPane.ERROR_MESSAGE
                                    , isWorkerCancel);

                        return null;
                    }
                }

                @Override
                protected void done() {
                    if(isWorkerCancel)
                        isWorkerCancel = false;

                    dlg.setVisible(false);
                }
            };

            addActionToDialogCancelBt(dlg, worker, null);

            worker.execute();

            dlg.setVisible(true);

            if(worker.get() != null){
                if(!isAdding)
                    view.showPopUpMessage(null, DBConnectionConstants.DB_CONNECTION_NO_ERROR
                            , DBConnectionConstants.DB_TEST_CONNECTION_TITLE, JOptionPane.INFORMATION_MESSAGE
                            , isWorkerCancel);
            }

            return worker.get();
        } catch (InterruptedException | CancellationException | ExecutionException e) {
            return null;
        }
    }

    public void editConnection(AddConnectionModal modal, ConnectionModel connection){
        modal.getComboBoxDB().setSelectedIndex(connection.getConnectionTech());
        modal.getTextFieldConnName().setText(connection.getConnectionName());
        modal.getTextFieldHostName().setText(connection.getHostName());
        modal.getTextFieldPort().setText(connection.getPort());
        modal.getTextFieldDB().setText(connection.getDbName());
        modal.getTextFieldUserName().setText(connection.getUser());
        modal.getPasswordField().setText(connection.getPass());
        modal.setVisible(true);
    }

    public void delConnection(int i){
        try {
            connectionList.getConnection(i).closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        connectionList.removeConnection(i);
        view.getTreeConnections().setModel(connectionList);
        view.getTabOpenConn().remove(i);
    }
}
