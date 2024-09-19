package com.dsa;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AuctionSystem auctionSystem = new AuctionSystem();
            auctionSystem.lunch(); // --> Method na call out from Auction System.
        });
    }
}