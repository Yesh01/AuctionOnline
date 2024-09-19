// main/java/com/dsa/customer/Bid.java

package com.dsa.customer;

public class Bid {

    private String bidderName;
    private double bidPrice;
    private int auctionItemId;

    // Constructor for creating a new bid
    public Bid(String bidderName, double bidPrice, int auctionItemId) {
        this.bidderName = bidderName;
        this.bidPrice = bidPrice;
        this.auctionItemId = auctionItemId;
    }

    // Getters and Setters for bid fields
    public String getBidderName() {
        return bidderName;
    }

    public void setBidderName(String bidderName) {
        this.bidderName = bidderName;
    }

    public double getBidPrice() {
        return bidPrice;
    }

    public void setBidPrice(double bidPrice) {
        this.bidPrice = bidPrice;
    }

    public int getAuctionItemId() {
        return auctionItemId;
    }

    public void setAuctionItemId(int auctionItemId) {
        this.auctionItemId = auctionItemId;
    }
}
