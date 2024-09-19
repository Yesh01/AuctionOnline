package com.dsa.customer;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.dsa.AuctionSystem;
import com.dsa.admin.AuctionItem;
import com.dsa.controller.BidController;
import com.dsa.util.ErrorHandler;

public class CustomerPanel extends JFrame {

    private AuctionSystem auctionSystem;
    private BidController bidController;

    private JTextField bidderNameField;
    private JTextField bidPriceField;
    private JLabel currentItemLabel;
    private JTable bidsTable;
    private DefaultTableModel tableModel;

    public CustomerPanel(AuctionSystem auctionSystem) {
        this.auctionSystem = auctionSystem;
        this.bidController = new BidController();

        setTitle("Customer Panel");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
    }

    private void initComponents() {
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));

        JLabel bidderNameLabel = new JLabel("Bidder Name:");
        bidderNameField = new JTextField(20);

        JLabel bidPriceLabel = new JLabel("Bid Price:");
        bidPriceField = new JTextField(20);

        JButton addBidButton = new JButton("Add Bid");
        JButton closeButton = new JButton("Close Panel");

        currentItemLabel = new JLabel("Current Item: [None]");

        leftPanel.add(bidderNameLabel);
        leftPanel.add(bidderNameField);
        leftPanel.add(bidPriceLabel);
        leftPanel.add(bidPriceField);
        leftPanel.add(addBidButton);
        leftPanel.add(closeButton);
        leftPanel.add(currentItemLabel);

        // Table to show bids
        bidsTable = new JTable();
        String[] columnNames = {"Bidder Name", "Bid Price"};
        tableModel = new DefaultTableModel(columnNames, 0);
        bidsTable.setModel(tableModel);

        JScrollPane scrollPane = new JScrollPane(bidsTable);

        // Action Listeners
        addBidButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleAddBid();
            }
        });

        closeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                auctionSystem.returnToWelcomeScreen();
            }
        });

        // Main layout for customer panel
        setLayout(new BorderLayout());
        add(leftPanel, BorderLayout.WEST);
        add(scrollPane, BorderLayout.CENTER);

        // Load the current auction item and bids when the panel opens
        loadCurrentItemAndBids();
    }

    private void handleAddBid() {
        String bidderName = bidderNameField.getText();
        String bidPriceText = bidPriceField.getText();
        double bidPrice;

        try {
            bidPrice = Double.parseDouble(bidPriceText);
        } catch (NumberFormatException e) {
            ErrorHandler.showError("Invalid bid price. Please enter a valid number.");
            return;
        }

        if (bidderName.isEmpty()) {
            ErrorHandler.showError("Please fill out the Bidder Name field.");
            return;
        }

        AuctionItem currentItem = bidController.getCurrentAuctionItem();

        if (currentItem == null) {
            ErrorHandler.showError("No item is currently being auctioned.");
            return;
        }

        Bid newBid = new Bid(bidderName, bidPrice, currentItem.getItemId());
        bidController.addBid(newBid);

        // Refresh bids table after adding the bid
        refreshBidsTable(currentItem.getItemId());
        JOptionPane.showMessageDialog(this, "Bid placed successfully.");
    }

    private void loadCurrentItemAndBids() {
        AuctionItem currentItem = bidController.getCurrentAuctionItem();
        if (currentItem != null) {
            currentItemLabel.setText("Current Item: " + currentItem.getItemName() + " (Starting Price: " + currentItem.getStartingPrice() + ")");
            refreshBidsTable(currentItem.getItemId());
        } else {
            currentItemLabel.setText("No current item being auctioned.");
        }
    }

    // Refreshes the bids table with the latest bids for the current item
    private void refreshBidsTable(int auctionItemId) {
        ResultSet bidsResultSet = bidController.getBidsForAuctionItem(auctionItemId);

        // Clear existing rows
        tableModel.setRowCount(0);

        try {
            while (bidsResultSet.next()) {
                String bidderName = bidsResultSet.getString("bidderName");
                double bidPrice = bidsResultSet.getDouble("bidPrice");
                Object[] row = {bidderName, bidPrice};
                tableModel.addRow(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void showCustomerPanel() {
        setVisible(true);
    }
}
