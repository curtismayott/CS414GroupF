package views;

import objects.Register;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by darkbobo on 9/28/15.
 */
public class MainMenuView extends MyJFrame {
    private JButton orderViewButton;
    private JButton makeLineViewButton;
    private JButton managerControlsButton;
    public JPanel mainMenuPanel;

    public MainMenuView(){
        setContentPane(mainMenuPanel);
        this.setPreferredSize(new Dimension(getToolkit().getScreenSize().width, getToolkit().getScreenSize().height));
        pack();
    }

    public void clearEmployee() {
        //breaking MVC pattern... sorry
        model.clearLoggedInEmployee();
    }

    public void addComponents(){
        controller.registerComponent("orderViewButton", orderViewButton);
        controller.registerComponent("makeLineViewButton", makeLineViewButton);
        controller.registerComponent("managerControlsButton", managerControlsButton);
        orderViewButton.addActionListener(controller);
        makeLineViewButton.addActionListener(controller);
        managerControlsButton.addActionListener(controller);
    }
}
