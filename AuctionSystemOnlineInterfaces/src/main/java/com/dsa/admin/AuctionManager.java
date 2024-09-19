package com.dsa.admin;

import java.util.LinkedList;

import com.dsa.controller.AuctionController;
import com.dsa.customer.Bid;

public class AuctionManager {

    // FOR FUNCTIONALITY ---->

    // initialization -> 
    private AuctionController auctionController;  // Connectivity for Database
    // private LinkedList<Auction> auctions;
    private LinkedList<AuctionItem> auctionItems;

    // 24/7hrs Contruction Worker :>
    public AuctionManager(AuctionController auctionController) {
        this.auctionController = auctionController;
        // this.auctions = new LinkedList<>();
        this.auctionItems = new LinkedList<>();
    }

    public void startAuction(AuctionItem item) {
        // TO-DO: Implement start the Auction Logic. got it? 
    }

    public void stopAuction(AuctionItem item) {
        // TO-DO Implement stop the Auction Logic. Go and Cook already blud!
    }

    // public AuctionStatus getAuctionStatus(AuctionItem item) {
    //     // TO-DO implementation for Status. wait. 
    // }

    public void updateAuctionItem(AuctionItem item, Bid bid) {
        // TO-DO Implement an updatable for Item & Bid.
    }

    //Other Methods ------> Changabele and also not yet only this methods. 

    




}
