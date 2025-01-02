package project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainMenu {
    public void show() {
        JFrame menuFrame = new JFrame("Main Menu");
        menuFrame.setSize(400, 300);
        menuFrame.setLocationRelativeTo(null);

        JButton btnParkCar = new JButton("Check-in Car");
        JButton btnCheckoutCar = new JButton("Checkout Car");
        JButton btnShowCars = new JButton("Show All Parked Cars");
        JButton btnUpdateCars = new JButton(" Update cars ");
        JButton btnsearchCar = new JButton("Search Car");
        menuFrame.setLayout(new GridLayout(3, 1));
        menuFrame.add(btnParkCar);
        menuFrame.add(btnCheckoutCar);
        menuFrame.add(btnShowCars);
        menuFrame.add(btnUpdateCars);
        menuFrame.add(btnsearchCar);
        btnParkCar.addActionListener(e -> new CarManagement().parkCar(menuFrame));
        btnCheckoutCar.addActionListener(e -> new CarManagement().checkoutCar(menuFrame));
        btnShowCars.addActionListener(e -> new CarManagement().showAllCars(menuFrame));
        btnUpdateCars.addActionListener(e -> new CarManagement().UpdateCars(menuFrame));
        btnsearchCar.addActionListener(e -> new CarManagement().searchCar(menuFrame));

        menuFrame.setVisible(true);
    }
}
