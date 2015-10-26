package controllers;

import objects.Register;
import views.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import java.util.HashMap;
import java.util.Observable;

/**
 * Created by darkbobo on 10/25/15.
 */
public class WindowManager implements WindowStateListener {
    HashMap<String,MyJFrame> views;
    HashMap<String, MyActionListener> controllers;
    Register register;
    public static final String MAIN_MENU = "mainMenu";
    public static final String ORDER_EDIT = "orderEdit";
    public static final String COLLECT_PAYMENT = "collectPayment";
    public static final String CUSTOMER = "customer";
    public static final String MAKE_LINE = "makeline";
    public static final String ORDER_LIST = "orderList";

    public WindowManager(){
        register = new Register();
        init();
        registerMVC();
        registerManager();
        registerComponents();
        for(MyJFrame jFrame : views.values()){
            jFrame.setVisible(false);
            jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }
        // init main menu view
        views.get(MAIN_MENU).setVisible(true);
    }

    @Override
    public void windowStateChanged(WindowEvent windowEvent) {
        System.out.println(windowEvent.paramString());
        System.out.println(windowEvent.getNewState());
        System.out.println(windowEvent.getOldState());
    }

    public void activateWindow(String oldWindow, String newWindow){
        views.get(oldWindow).setVisible(false);
        views.get(newWindow).setVisible(true);
        if(oldWindow.equals(COLLECT_PAYMENT)){
            // function to refresh makeline

            ((OrderListView)views.get(ORDER_LIST)).setOrderList();
        }
    }

    public void init(){
        views = new HashMap<>();
        views.put(MAIN_MENU, new MainMenuView());
        views.put(ORDER_EDIT, new AddOrderView());
        views.put(COLLECT_PAYMENT, new CollectPaymentView());
        views.put(CUSTOMER, new CustomerView());
        views.put(MAKE_LINE, new MakelineView());
        views.put(ORDER_LIST, new OrderListView());

        controllers = new HashMap<>();
        controllers.put(MAIN_MENU, new MainMenuListener());
        controllers.put(ORDER_EDIT, new OrderEditListener());
        controllers.put(COLLECT_PAYMENT, new CollectPaymentListener());
        controllers.put(CUSTOMER, new CustomerListener());
        //controllers.put(MAKE_LINE, new MakelineListener());
        controllers.put(ORDER_LIST, new OrderListListener());
    }

    public void registerMVC(){
        for(MyJFrame jFrame : views.values()){
            jFrame.addModel(register);
        }

        for(String key : views.keySet()){
            views.get(key).addController(controllers.get(key));
        }

        for(MyActionListener listener : controllers.values()){
            listener.addModel(register);
        }
    }

    public void registerManager(){
        for(MyJFrame jFrame : views.values()){
            jFrame.addWindowManager(this);
        }
        for(MyActionListener listener : controllers.values()){
            listener.addWindowManager(this);
        }
    }

    public void registerComponents(){
        //((MainMenuView)views.get("mainMenuView")).addComponents();
        for(MyJFrame jFrame : views.values()){
            jFrame.addComponents();
        }
    }
    public void passOrderID(String toController, int orderID){
        (controllers.get(toController)).setOrderID(orderID);
    }
}