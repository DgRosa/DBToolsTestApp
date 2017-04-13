package open.diogorosa.main;

import open.diogorosa.controller.MainController;
import open.diogorosa.view.MainView;

/**
 * Created by Diogo Rosa on 09/04/2017.
 */
public class Main {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                MainView view = new MainView();
                MainController controller = new MainController(view);
                view.setController(controller);
                view.setVisible(true);
            }
        });
    }
}
