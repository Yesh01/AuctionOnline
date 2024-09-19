package com.dsa.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import com.dsa.admin.AuctionItem;
import com.dsa.database.DatabaseConnector;

public class AuctionController {

    private DatabaseConnector databaseConnector;

    public AuctionController() {
        databaseConnector = new DatabaseConnector();
    }

    // Adds a new auction item to the database
    public void addItem(AuctionItem item) {
        String query = "INSERT INTO AuctionItem (itemName, startingPrice, imagePath, soldPrice, isSold) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = databaseConnector.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, item.getItemName());
            stmt.setDouble(2, item.getStartingPrice());
            stmt.setString(3, item.getImagePath());
            stmt.setDouble(4, 0.0);  // Initial soldPrice is 0
            stmt.setBoolean(5, false);  // Item is initially not sold

            stmt.executeUpdate();
            System.out.println("Auction item added to the database.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Starts an auction for the selected item
    public void startAuction(AuctionItem item) {
        // For future implementation: Auction start logic (e.g., setting a timer)
        System.out.println("Auction started for item: " + item.getItemName());
        
        // TODO: Update auction status in the database if needed
    }

    // Stops an auction (to be triggered by timer or admin manually stopping it)
    public void stopAuction(AuctionItem item, double finalBidPrice, String highestBidder) {
        item.updateSoldPrice(finalBidPrice);
        item.setBidderName(highestBidder);

        String query = "UPDATE AuctionItem SET soldPrice = ?, isSold = ?, bidderName = ? WHERE itemId = ?";

        try (Connection connection = databaseConnector.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setDouble(1, finalBidPrice);
            stmt.setBoolean(2, true);  // Item is marked as sold
            stmt.setString(3, highestBidder);
            stmt.setInt(4, item.getItemId());

            stmt.executeUpdate();
            System.out.println("Auction item updated with final bid.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Retrieves all auction items (for listing in the AdminPanel table)
    public List<AuctionItem> getAllItems() {
        // TODO: Implement database retrieval logic for listing all auction items
        return null;  // Placeholder
    }

    // Updates the auction item (e.g., after a bid has been placed)
    public void updateAuctionItem(AuctionItem item, double newBidPrice, String bidderName) {
        String query = "UPDATE AuctionItem SET soldPrice = ?, bidderName = ? WHERE itemId = ?";

        try (Connection connection = databaseConnector.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setDouble(1, newBidPrice);
            stmt.setString(2, bidderName);
            stmt.setInt(3, item.getItemId());

            stmt.executeUpdate();
            System.out.println("Auction item updated with new bid.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
