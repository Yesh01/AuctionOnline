package com.dsa.admin;
public class AuctionItem {

    private int itemId;
    private String itemName;
    private double startingPrice;
    private String imagePath;
    private double soldPrice;
    private boolean isSold;
    private String bidderName;

    // Constructor for creating new Auction Items
    public AuctionItem(String itemName, double startingPrice, String imagePath) {
        this.itemName = itemName;
        this.startingPrice = startingPrice;
        this.imagePath = imagePath;
        this.isSold = false;
        this.soldPrice = 0.0;
        this.bidderName = null;
    }

    // Getters and Setters for the auction item fields :> 
    // Retrive & Update --->

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getStartingPrice() {
        return startingPrice;
    }

    public void setStartingPrice(double startingPrice) {
        this.startingPrice = startingPrice;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public double getSoldPrice() {
        return soldPrice;
    }

    public void setSoldPrice(double soldPrice) {
        this.soldPrice = soldPrice;
    }

    public boolean isSold() {
        return isSold;
    }

    public void setSold(boolean sold) {
        isSold = sold;
    }

    public String getBidderName() {
        return bidderName;
    }

    public void setBidderName(String bidderName) {
        this.bidderName = bidderName;
    }

    // Returns the highest bid (for future implementation with bids) --> Simplest Approach
    public double getHighestBid() {
        return soldPrice;  // For now, returning sold price. Will be updated with actual bid logic.
    }

    // Marks the item as sold with the final price
    public void updateSoldPrice(double price) {
        this.soldPrice = price;
        this.isSold = true;
    }
}
