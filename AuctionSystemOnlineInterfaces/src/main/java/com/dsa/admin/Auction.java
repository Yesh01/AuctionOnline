    package com.dsa.admin;

    import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.dsa.customer.Bid;
import com.dsa.util.AuctionTimer;

    public class Auction {

        //initialization -->
        private int id;
        private AuctionItem auctionItem;
        private Date startTime;
        private Date endTime;
        private Bid currentBid;
        private List<Bid> bidHistory = new ArrayList<Bid>();
        private AuctionTimer auctionTimer;

        // 7/11 Contructor na ko
        public void Auction(int id, AuctionItem auctionItem) {
            this.id = id;
            this.auctionItem = auctionItem;
            this.bidHistory = new ArrayList<>();

        }

        // Retrive & Update as usual -->

        public int getId() {
            return id;
        }

        public AuctionItem getAuctionItem() {
            return auctionItem;
        }

        public void setAuctionItem(AuctionItem auctionItem) {
            this.auctionItem = auctionItem;
        }

        public Date getStartTime() {
            return startTime;

        }

        public void setStartTime(Date startTime) {
            this.startTime = startTime;
        }

        public Date getEndTime() {
            return endTime;

        }

        public void setEndTime(Date endTime) {
            this.endTime = endTime;
        }

        public Bid getCurrentBid() {
            return currentBid;
        }

        public void updateCurrentBid(Bid bid) {
            // TO DO: implement update current bid logic
        }

        public List<Bid> getBidHistory() {
            return bidHistory;
        }

        public void addBidToHistory(Bid bid) {
            // TO DO: implement add bid to history logic
        }

        public AuctionTimer getAuctionTimer() {
            return auctionTimer;
        }

        public void setAuctionTimer(AuctionTimer auctionTimer) {
            this.auctionTimer = auctionTimer;
        }

        public void startAuction() {
            // TO DO: implement start auction logic
        }

        public void stopAuction() {
            // TO DO: implement stop auction logic
        }

        public void resetAuction() {
            // TO DO: implement reset auction logic
        }

    }
