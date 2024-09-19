package com.dsa;

import com.dsa.admin.AdminPanel;
import com.dsa.customer.CustomerPanel;

public class AuctionSystem {

    // --> new initalization 
    private AdminPanel adminPanel;
    private CustomerPanel customerPanel;
    private WelcomeFrame welcomeFrame;

    public AuctionSystem() {

        // using this. must better to call out -->
        this.welcomeFrame = new WelcomeFrame(this);
        this.adminPanel = new AdminPanel(this);
        this.customerPanel = new CustomerPanel(this);
    }

    // Simple Method to Call out in Main Entry Point File
    public void lunch() {
        welcomeFrame.showWelcomeScreen();
    }

    // IFs lead buttons -->
    public void showAdminPanel() {
        welcomeFrame.setVisible(false);
        // customerPanel.showCustomerPanel();
    }

    // Return to Main Menu of Options [Admin] || [Customer] -->
    public void returnToWelcomeScreen() {
        adminPanel.setVisible(false);
        customerPanel.setVisible(false);
        welcomeFrame.setVisible(true);
    }
}


