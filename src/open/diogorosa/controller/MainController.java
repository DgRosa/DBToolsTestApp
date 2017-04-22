    package open.diogorosa.controller;

    import open.diogorosa.model.ConnectionListModel;
    import open.diogorosa.model.MainModel;
    import open.diogorosa.view.MainView;
    import open.diogorosa.view.modal.AddConnectionModal;

    import javax.swing.*;
    import javax.swing.event.TreeSelectionEvent;
    import javax.swing.event.TreeSelectionListener;
    import java.awt.event.ActionEvent;
    import java.util.HashMap;
    import java.util.List;
    import java.util.Map;

    /**
     * Created by Diogo Rosa on 09/04/2017.
     */
    public class MainController {
        private MainView view;
        private MainModel model;
        private AddConnectionModal connectionModal;

        private Map<String, List<Object>> jComponentsChanges = new HashMap<>();

        public MainController(MainView view){
            init(view);
        }

        private void init(MainView view){
            this.view = view;
            this.model = new MainModel(this);

            connectionModal = new AddConnectionModal(view);

            view.addBtAddConnActionListener(new BtAddConnActionListener());
            connectionModal.addButtonOkListener(new BtOkActionListener());
            connectionModal.addButtonCancelListener(new BtCancelActionListener());
            connectionModal.addButtonTestListener(new BtTestActionListener());
            view.addTreeConnectionsListener(new TreeConnectionsListener());
            view.addJTreePopUpEditListener(new JTreePopUpEditListener());
            view.addJTreePopUpDelListener(new JTreePopUpDelListener());
        }

        public void newConnectionModal(){
            connectionModal.setVisible(true);
        }

        public MainView getView() {
            return view;
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

                if(model.addNewConnection(formData[0], formData[1], formData[2], formData[3], formData[4], formData[5], formData[6]))
                    connectionModal.setVisible(false);
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
                model.testConnection(formData[1], formData[2], formData[3], formData[4], formData[5], formData[6], false);
            }
        }

        private class TreeConnectionsListener implements TreeSelectionListener{

            @Override
            public void valueChanged(TreeSelectionEvent e) {
                /*if(e.getNewLeadSelectionPath().getParentPath().toString().compareTo("[" + model.connectionList.getRoot().toString() + "]") != 0) {
                    //Selects table
                }else{
                    //Selects connection
                }*/
            }
        }

        //private class JComponentsChangeListener implements ComponentListener{
        //}

        private class JTreePopUpEditListener extends AbstractAction{

            @Override
            public void actionPerformed(ActionEvent e) {
                ConnectionListModel treeModel = (ConnectionListModel) view.getTreeConnections().getModel();
                int index = view.getTreeConnections().getSelectionCount();

                if(index > 0) {
                    index = view.getTreeConnections().getSelectionRows()[0];
                    model.editConnection(connectionModal, treeModel.getConnection(index));
                }
            }
        }

        private class JTreePopUpDelListener extends AbstractAction{

            @Override
            public void actionPerformed(ActionEvent e) {
                int index = view.getTreeConnections().getSelectionCount();
                index = index - 1;

                if(index >= 0) {
                    model.delConnection(index);
                }
            }
        }

    }
