    package open.diogorosa.controller;

    import open.diogorosa.constant.DBConnectionConstants;
    import open.diogorosa.model.DBConnection;
    import open.diogorosa.view.MainView;
    import open.diogorosa.view.modal.AddConnectionModal;
    import open.diogorosa.model.ConnectionListModel;

    import javax.swing.*;
    import javax.swing.event.TreeSelectionEvent;
    import javax.swing.event.TreeSelectionListener;
    import javax.swing.tree.DefaultTreeModel;
    import java.awt.*;
    import java.awt.event.ActionEvent;
    import java.sql.Connection;
    import java.util.concurrent.ExecutionException;

    /**
     * Created by Diogo Rosa on 09/04/2017.
     */
    public class MainController {
        private DBConnection connection;
        private ConnectionListModel connectionList;
        private MainView view;
        private AddConnectionModal connectionModal;

        public MainController(MainView view){
            this.view = view;
            connectionModal = new AddConnectionModal(view);
            init();
        }

        private void init(){
            connection = new DBConnection();
            connectionList = new ConnectionListModel();

            view.addBtAddConnActionListener(new BtAddConnActionListener());
            connectionModal.addButtonOkListener(new BtOkActionListener());
            connectionModal.addButtonCancelListener(new BtCancelActionListener());
            connectionModal.addButtonTestListener(new BtTestActionListener());
            view.addTreeConnectionsListener(new TreeConnectionsListener());
        }

        public void newConnectionModal(){
            connectionModal.setVisible(true);
        }

        public boolean addNewConnection(String tech, String user, String password, String hostName, String port, String sidOrDBName) throws Exception {
            Connection conn = testConnection("Adding...", tech, user, password, hostName, port, sidOrDBName);

            if(conn == null) {
                JOptionPane.showMessageDialog(null, DBConnectionConstants.DB_TEST_CONNECTION_ERROR, "Adding", JOptionPane.ERROR_MESSAGE);
                return false;
            }else{
                //JOptionPane.showMessageDialog(null, "Success!!!", "Testing", JOptionPane.INFORMATION_MESSAGE);

                final JDialog dlg = getProgressDialog("Fetching DB tables...");

                SwingWorker<Boolean, Void> worker = new SwingWorker<Boolean, Void>() {
                    @Override
                    protected Boolean doInBackground() throws Exception {
                        if(connectionList.addConnection(conn)) {
                            view.getTreeConnections().setModel(new DefaultTreeModel(connectionList));
                            view.getTabOpenConn().add(conn.getSchema(), new JPanel());
                        }else
                            return false;

                        return true;
                    }

                    @Override
                    protected void done() {
                        dlg.setVisible(false);
                    }
                };

                addActionToDialogCancelBt(dlg, worker);

                worker.execute();

                dlg.setVisible(true);

                return worker.get();
            }
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

        public Connection testConnection(String message, String tech, String user, String password, String hostName, String port, String sidOrDBName){
            final JDialog dlg = getProgressDialog(message);

            SwingWorker<Connection, Void> worker = new SwingWorker<Connection, Void>() {
                @Override
                protected Connection doInBackground() throws Exception {
                    try {
                        if(tech.compareTo(DBConnectionConstants.DB_TECH_OPTIONS[0]) == 0){
                            Connection conn = connection.getMyySqlConnection(user, password, hostName, port, sidOrDBName);

                            return conn;
                        }else if(tech.compareTo(DBConnectionConstants.DB_TECH_OPTIONS[1]) == 0){
                            Connection conn = connection.getOracleConnection(user, password, hostName, port, sidOrDBName);

                            return conn;
                        }else
                            return null;
                    }catch (Exception e){
                        System.out.println(e);
                        return null;
                    }
                }

                @Override
                protected void done() {
                    dlg.setVisible(false);
                    return;
                }
            };

            addActionToDialogCancelBt(dlg, worker);

            worker.execute();

            dlg.setVisible(true);

            try {
                return worker.get();
            } catch (InterruptedException e) {
                return null;
            } catch (ExecutionException e) {
                return null;
            }
        }

        private void addActionToDialogCancelBt(JDialog dlg, SwingWorker worker){
            ((JButton)((JPanel)dlg.getContentPane().getComponent(0)).getComponent(1)).addActionListener(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    worker.cancel(true);
                }
            });
        }

        private class BtAddConnActionListener extends AbstractAction{

            @Override
            public void actionPerformed(ActionEvent e) {
                newConnectionModal();
            }
        }

        private class BtOkActionListener extends AbstractAction{

            @Override
            public void actionPerformed(ActionEvent e) {
                String formData[] = connectionModal.getFormData();

                try {
                    if(addNewConnection(formData[0], formData[1], formData[2], formData[3], formData[4], formData[5]))
                        connectionModal.setVisible(false);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        }

        private class BtCancelActionListener extends AbstractAction{

            @Override
            public void actionPerformed(ActionEvent e) {
                connectionModal.setVisible(false);
            }
        }

        private class BtTestActionListener extends AbstractAction{

            @Override
            public void actionPerformed(ActionEvent e) {
                String formData[] = connectionModal.getFormData();

                if(testConnection("Testing...", formData[0], formData[1], formData[2], formData[3], formData[4], formData[5]) != null)
                    JOptionPane.showMessageDialog(null, DBConnectionConstants.DB_TEST_CONNECTION_NO_ERROR, "Testing", JOptionPane.INFORMATION_MESSAGE);
                else
                    JOptionPane.showMessageDialog(null, DBConnectionConstants.DB_TEST_CONNECTION_ERROR, "Testing", JOptionPane.ERROR_MESSAGE);
            }
        }

        private class TreeConnectionsListener implements TreeSelectionListener{

            @Override
            public void valueChanged(TreeSelectionEvent e) {
                if(e.getNewLeadSelectionPath().getParentPath().toString().compareTo("[" + connectionList.getRoot().toString() + "]") != 0) {
                    //Selects table
                }else{
                    //Selects connection
                }
            }
        }
    }
