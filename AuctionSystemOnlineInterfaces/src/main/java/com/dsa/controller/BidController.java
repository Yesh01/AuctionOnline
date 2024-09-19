// main/java/com/dsa/controller/BidController.java

package com.dsa.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.dsa.admin.AuctionItem;
import com.dsa.customer.Bid;
import com.dsa.database.DatabaseConnector;

public class BidController {

    private DatabaseConnector databaseConnector;

    public BidController() {
        this.databaseConnector = new DatabaseConnector();
    }

    // Adds a new bid to the database
    public void addBid(Bid bid) {
        String query = "INSERT INTO Bid (bidderName, bidPrice, auctionItemId) VALUES (?, ?, ?)";

        try (Connection connection = databaseConnector.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, bid.getBidderName());
            stmt.setDouble(2, bid.getBidPrice());
            stmt.setInt(3, bid.getAuctionItemId());

            stmt.executeUpdate();
            System.out.println("Bid added to the database.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Retrieves the currently auctioned item (the one being bid on)
    public AuctionItem getCurrentAuctionItem() {
        String query = "SELECT * FROM AuctionItem WHERE isSold = 0 ORDER BY itemId LIMIT 1"; // Assuming only one item is being auctioned at a time
        AuctionItem currentItem = null;

        try (Connection connection = databaseConnector.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                currentItem = new AuctionItem(
                    rs.getString("itemName"),
                    rs.getDouble("startingPrice"),
                    rs.getString("imagePath")
                );
                currentItem.setItemId(rs.getInt("itemId"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return currentItem;
    }

    // Retrieves all bids for a given auction item (for listing in the CustomerPanel table)
    public ResultSet getBidsForAuctionItem(int auctionItemId) {
        String query = "SELECT * FROM Bid WHERE auctionItemId = ? ORDER BY bidPrice DESC";

        try {
            Connection connection = databaseConnector.getConnection();
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, auctionItemId);

            return stmt.executeQuery(); // Return the result set of bids

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;  // If an error occurs, return null
    }
}
