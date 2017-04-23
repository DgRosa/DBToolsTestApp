package open.diogorosa.customjcomponent;

import open.diogorosa.constant.ResourcesPathConstants;

import javax.swing.*;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by diogorosa on 15/04/17.
 */
public class CustomJTree extends JTree {

    private JPopupMenu popupMenu;

    public CustomJTree(JPopupMenu popupMenu){
        setCellRenderer(new CustomRenderer());
        addMouseListener(new CustomMouseAdapter());

        this.popupMenu = popupMenu;
    }

    private class CustomRenderer extends DefaultTreeCellRenderer {
        @Override
        public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
            super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);

            if (sel) {
                setIcon(new ImageIcon(ResourcesPathConstants.JTREE_SELECTED_ROW));
            }else{
                setIcon(new ImageIcon(ResourcesPathConstants.JTREE_UNSELECTED_ROW));
            }

            return this;
        }
    }

    private class CustomMouseAdapter extends MouseAdapter{
        @Override
        public void mousePressed(MouseEvent e) {
            if(e.isPopupTrigger()) {
                int control = ((JTree) (e.getComponent())).getSelectionCount();

                if (control > 0) {
                    popupMenu.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            if(e.isPopupTrigger()) {
                int control = ((JTree) (e.getComponent())).getSelectionCount();

                if (control > 0) {
                    popupMenu.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        }
    }
}
