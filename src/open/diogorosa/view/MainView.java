/*
 * Created by JFormDesigner on Sun Apr 09 11:48:40 CEST 2017
 */

package open.diogorosa.view;

import java.awt.*;
import javax.swing.*;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.*;

import net.miginfocom.swing.*;
import open.diogorosa.controller.MainController;
import open.diogorosa.customjcomponent.CustomJTree;

/**
 * @author Diogo Rosa
 */
public class MainView extends JFrame {
    private MainController controller;
    private JPopupMenu popupMenu;
    private JMenuItem itEdit;
    private JMenuItem itDel;

    public MainView() {
        initComponents();
    }

    public MainController getController() {
        return controller;
    }

    public void setController(MainController controller) {
        this.controller = controller;
    }

    public JMenuBar getMenuBar1() {
        return menuBar1;
    }

    public void setMenuBar1(JMenuBar menuBar1) {
        this.menuBar1 = menuBar1;
    }

    public JMenu getMenu1() {
        return menu1;
    }

    public void setMenu1(JMenu menu1) {
        this.menu1 = menu1;
    }

    public JButton getBtAddConn() {
        return btAddConn;
    }

    public void setBtAddConn(JButton btAddConn) {
        this.btAddConn = btAddConn;
    }

    public JScrollPane getScrollPane1() {
        return scrollPane1;
    }

    public void setScrollPane1(JScrollPane scrollPane1) {
        this.scrollPane1 = scrollPane1;
    }

    public JTree getTreeConnections() {
        return treeConnections;
    }

    public void setTreeConnections(JTree treeConnections) {
        this.treeConnections = treeConnections;
    }

    public JTabbedPane getTabOpenConn() {
        return tabOpenConn;
    }

    public void setTabOpenConn(JTabbedPane tabOpenConn) {
        this.tabOpenConn = tabOpenConn;
    }

    public void addBtAddConnActionListener(AbstractAction listener){
        btAddConn.addActionListener(listener);
    }

    public void addTreeConnectionsListener(TreeSelectionListener listener){
        treeConnections.addTreeSelectionListener(listener);
    }

    public void showPopUpMessage(Component parent, String message, String title, int type){
        JOptionPane.showMessageDialog(parent, message, title, type);
    }

    public void addJTreePopUpEditListener(AbstractAction listener){
        itEdit.addActionListener(listener);
    }

    public void addJTreePopUpDelListener(AbstractAction listener){
        itDel.addActionListener(listener);
    }

    private void createUIComponents() {
        // TODO: add custom component creation code here
    }

    private void initComponents() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        popupMenu = new JPopupMenu();
        itEdit = new JMenuItem("Edit");
        itDel = new JMenuItem("Delete");

        popupMenu.add(itEdit);
        popupMenu.add(itDel);


        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Diogo Rosa
        menuBar1 = new JMenuBar();
        menu1 = new JMenu();
        btAddConn = new JButton();
        scrollPane1 = new JScrollPane();
        treeConnections = new CustomJTree(popupMenu);

        TreeSelectionModel selectionModel = new DefaultTreeSelectionModel();
        selectionModel.setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        treeConnections.setSelectionModel(selectionModel);
        treeConnections.setRootVisible(false);
        treeConnections.setModel(null);

        tabOpenConn = new JTabbedPane();

        //======== this ========
        Container contentPane = getContentPane();
        contentPane.setLayout(new MigLayout(
            "fill,insets 0,hidemode 3",
            // columns
            "[150:30%]" +
            "[grow,fill]",
            // rows
            "[fill]" +
            "[fill]"));

        //======== menuBar1 ========
        {

            //======== menu1 ========
            {
                menu1.setText("Fil");
            }
            menuBar1.add(menu1);
        }
        setJMenuBar(menuBar1);

        //---- btAddConn ----
        btAddConn.setText("Add Connection");
        contentPane.add(btAddConn, "cell 0 0,width 100%");

        //======== scrollPane1 ========
        {
            scrollPane1.setViewportView(treeConnections);
        }
        contentPane.add(scrollPane1, "cell 0 1,width 100%,height 100%");
        contentPane.add(tabOpenConn, "cell 1 0 1 2,width 100%,height 100%");
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - Diogo Rosa
    private JMenuBar menuBar1;
    private JMenu menu1;
    private JButton btAddConn;
    private JScrollPane scrollPane1;
    private JTree treeConnections;
    private JTabbedPane tabOpenConn;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
