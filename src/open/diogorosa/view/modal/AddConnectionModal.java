/*
 * Created by JFormDesigner on Sun Apr 09 18:09:02 CEST 2017
 */

package open.diogorosa.view.modal;

import java.awt.*;
import javax.swing.*;

import net.miginfocom.swing.*;
import open.diogorosa.constant.DBConnectionConstants;

/**
 * @author Diogo Rosa
 */
public class AddConnectionModal extends JDialog {
    public AddConnectionModal(Frame owner) {
        super(owner);
        initComponents();
    }

    public AddConnectionModal(Dialog owner) {
        super(owner);
        initComponents();
    }

    public String[] getFormData(){
        return new String[]{comboBoxDB.getSelectedItem().toString(),
        textFieldUserName.getText(), String.valueOf(passwordField.getPassword()),
        textFieldHostName.getText(), textFieldPort.getText(), textFieldDB.getText()};
    }

    public void addButtonOkListener(AbstractAction listener){
        btOk.addActionListener(listener);
    }

    public void addButtonCancelListener(AbstractAction listener){
        btCancel.addActionListener(listener);
    }

    public void addButtonTestListener(AbstractAction listener){
        btTest.addActionListener(listener);
    }

    public JComboBox getComboBoxDB() {
        return comboBoxDB;
    }

    public JTextField getTextFieldHostName() {
        return textFieldHostName;
    }

    public JTextField getTextFieldPort() {
        return textFieldPort;
    }

    public JTextField getTextFieldDB() {
        return textFieldDB;
    }

    public JPasswordField getPasswordField() {
        return passwordField;
    }

    public JTextField getTextFieldUserName() {
        return textFieldUserName;
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Diogo Rosa
        dialogPane = new JPanel();
        contentPanel = new JPanel();
        comboBoxDB = new JComboBox(DBConnectionConstants.DB_TECH_OPTIONS);
        panel1 = new JPanel();
        lbHostName = new JLabel();
        lbPort = new JLabel();
        lbDBNameSID = new JLabel();
        textFieldHostName = new JTextField();
        textFieldPort = new JTextField();
        textFieldDB = new JTextField();
        panel2 = new JPanel();
        lbUser = new JLabel();
        lbPass = new JLabel();
        textFieldUserName = new JTextField();
        passwordField = new JPasswordField();
        buttonBar = new JPanel();
        btOk = new JButton();
        btCancel = new JButton();
        btTest = new JButton();

        //======== this ========
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== dialogPane ========
        {

            // JFormDesigner evaluation mark
            dialogPane.setBorder(new javax.swing.border.CompoundBorder(
                new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0),
                    "JFormDesigner Evaluation", javax.swing.border.TitledBorder.CENTER,
                    javax.swing.border.TitledBorder.BOTTOM, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12),
                    java.awt.Color.red), dialogPane.getBorder())); dialogPane.addPropertyChangeListener(new java.beans.PropertyChangeListener(){public void propertyChange(java.beans.PropertyChangeEvent e){if("border".equals(e.getPropertyName()))throw new RuntimeException();}});

            dialogPane.setLayout(new BorderLayout());

            //======== contentPanel ========
            {
                contentPanel.setLayout(new MigLayout(
                    "fill,insets dialog,hidemode 3",
                    // columns
                    "[fill]",
                    // rows
                    "[fill]" +
                    "[fill]" +
                    "[fill]"));

                //---- comboBoxDB ----
                comboBoxDB.setPrototypeDisplayValue("DBnnectionConstants.DB_TEH_OPTIONS");
                contentPanel.add(comboBoxDB, "cell 0 0");

                //======== panel1 ========
                {
                    panel1.setLayout(new MigLayout(
                        "fill,hidemode 3",
                        // columns
                        "[fill]" +
                        "[fill]" +
                        "[fill]",
                        // rows
                        "[]" +
                        "[fill]"));

                    //---- lbHostName ----
                    lbHostName.setText("HostName");
                    panel1.add(lbHostName, "cell 0 0");

                    //---- lbPort ----
                    lbPort.setText("Port");
                    panel1.add(lbPort, "cell 1 0");

                    //---- lbDBNameSID ----
                    lbDBNameSID.setText("DBName");
                    panel1.add(lbDBNameSID, "cell 2 0");

                    //---- textFieldHostName ----
                    textFieldHostName.setText("192.168.1.4");
                    panel1.add(textFieldHostName, "cell 0 1");

                    //---- textFieldPort ----
                    textFieldPort.setText("1521");
                    panel1.add(textFieldPort, "cell 1 1");

                    //---- textFieldDB ----
                    textFieldDB.setText("XE");
                    panel1.add(textFieldDB, "cell 2 1");
                }
                contentPanel.add(panel1, "cell 0 1");

                //======== panel2 ========
                {
                    panel2.setLayout(new MigLayout(
                        "fill,hidemode 3",
                        // columns
                        "[fill]" +
                        "[fill]",
                        // rows
                        "[]" +
                        "[fill]"));

                    //---- lbUser ----
                    lbUser.setText("Username");
                    panel2.add(lbUser, "cell 0 0");

                    //---- lbPass ----
                    lbPass.setText("Password");
                    panel2.add(lbPass, "cell 1 0");

                    //---- textFieldUserName ----
                    textFieldUserName.setText("COBL_OWN");
                    panel2.add(textFieldUserName, "cell 0 1");

                    //---- passwordField ----
                    passwordField.setText("COBL_OWN");
                    panel2.add(passwordField, "cell 1 1");
                }
                contentPanel.add(panel2, "cell 0 2");
            }
            dialogPane.add(contentPanel, BorderLayout.CENTER);

            //======== buttonBar ========
            {
                buttonBar.setLayout(new MigLayout(
                    "insets dialog",
                    // columns
                    "[button,fill]" +
                    "[button,fill]" +
                    "[fill]" +
                    "[fill]" +
                    "[fill]" +
                    "[fill]" +
                    "[fill]" +
                    "[fill]" +
                    "[fill]" +
                    "[fill]" +
                    "[fill]",
                    // rows
                    null));

                //---- btOk ----
                btOk.setText("OK");
                buttonBar.add(btOk, "cell 0 0");

                //---- btCancel ----
                btCancel.setText("Cancel");
                buttonBar.add(btCancel, "cell 1 0");

                //---- btTest ----
                btTest.setText("Test");
                btTest.setActionCommand("Test");
                buttonBar.add(btTest, "cell 10 0");
            }
            dialogPane.add(buttonBar, BorderLayout.SOUTH);
        }
        contentPane.add(dialogPane, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - Diogo Rosa
    private JPanel dialogPane;
    private JPanel contentPanel;
    private JComboBox comboBoxDB;
    private JPanel panel1;
    private JLabel lbHostName;
    private JLabel lbPort;
    private JLabel lbDBNameSID;
    private JTextField textFieldHostName;
    private JTextField textFieldPort;
    private JTextField textFieldDB;
    private JPanel panel2;
    private JLabel lbUser;
    private JLabel lbPass;
    private JTextField textFieldUserName;
    private JPasswordField passwordField;
    private JPanel buttonBar;
    private JButton btOk;
    private JButton btCancel;
    private JButton btTest;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
