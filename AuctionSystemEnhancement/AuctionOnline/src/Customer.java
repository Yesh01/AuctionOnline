
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTable;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.TimerTask;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.table.DefaultTableModel;

import javax.swing.border.BevelBorder;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.Timer;
import javax.swing.border.LineBorder;

class Bidder
{
    String name="";
    double bid = 0;
    Bidder(String name, double bid)
    {
        this.name = name;
        this.bid = bid;
    }
}

class BidderValueComparator implements Comparator<Bidder> {
    @Override
    public int compare(Bidder o1, Bidder o2) {
        // Sort from h
        return Double.compare(o2.bid, o1.bid);
    }
}

public class Customer extends JFrame {
	
	// Admin Data
	ArrayList<String> itemNameList = new ArrayList<String>();
	ArrayList<String> imageNameList = new ArrayList<String>();
	ArrayList<String> bidderNameList = new ArrayList<String>();
	ArrayList<String> customerBidderNameList = new ArrayList<String>();
	ArrayList<Double> priceList = new ArrayList<Double>();
	ArrayList<Double> soldAtList = new ArrayList<Double>();
	ArrayList<Double> bidPriceList = new ArrayList<Double>();
	ArrayList<Double> customerBidPriceList = new ArrayList<Double>();
	ArrayList<Integer> counterList = new ArrayList<Integer>();

	
	public static int activeBidIndex = -1;
	// Admin Data End
	
	Queue<Bidder> bids = new PriorityQueue<Bidder>(new BidderValueComparator());
	Queue<Bidder> vipBids = new PriorityQueue<Bidder>(new BidderValueComparator());
	String vipChecker="";
	Boolean vipHasJoined=false;
	
	public static int timerCounter = -1;
	public static Timer timer;
	JTextPane timerTP;
	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField bidderNameTF;
	private JTextField bidPriceTF;
	private JTable bidInfoTable;
	
	JTextPane itemNameTP;
	JTextPane priceTP;
	
	JTextPane highestBidTP;
	JTextPane nameTP;
	
	JLabel label;
	ImageIcon imgIcon = new ImageIcon();
	JPanel imgPanel;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Customer frame = new Customer(timerCounter, activeBidIndex);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	
	public Customer(int timerCounterParameter, int activeBidIndex, Queue<Bidder> bids,ArrayList<String> itemNameList, ArrayList<String> imageNameList, ArrayList<String> bidderNameList, ArrayList<Double> priceList, ArrayList<Double> soldAtList)
	{
		this(timerCounterParameter, activeBidIndex);
		
		this.bidPriceList = bidPriceList;
		this.imageNameList = imageNameList;
		this.itemNameList = itemNameList;
		this.bidderNameList = bidderNameList;
		this.priceList = priceList;
		this.soldAtList = soldAtList;	
		this.counterList = counterList;
		this.bids = bids;
		
		if(activeBidIndex!=(-1)) { imgIcon = new ImageIcon(imageNameList.get(activeBidIndex));}
		label.setIcon(imgIcon);
		imgPanel.remove(label);
        imgPanel.add(label, BorderLayout.CENTER);
        
        itemNameTP.setText(itemNameList.get(activeBidIndex));
        priceTP.setText(String.valueOf(priceList.get(activeBidIndex)));
        
        timerCounter = timerCounterParameter;
		if (timerCounter != -1) timerFunction(timerCounter);
		System.out.println("Size: " + bidderNameList.size());
//		System.out.println("Element 0 : " + bidderNameList.get(0) + " " + bidPriceList.get(0));
		DefaultTableModel model = (DefaultTableModel) bidInfoTable.getModel();
		
		/*
		// Cleansing of nulls
		while (bidderNameList.contains(null))
		{
			bidderNameList.remove(bidderNameList.indexOf(null));
		}
		
		// Cleansing of nulls
		
		while (bidPriceList.contains(null))
		{
			bidPriceList.remove(bidPriceList.indexOf(null));
		}
		
		if(!(bidderNameList.isEmpty()))
		{
		for(int i = 0; i < bidderNameList.size(); i++ ) 
			{
			System.out.println("ah");
			String data[] = {bidderNameList.get(i), String.valueOf(bidPriceList.get(i))}; 
	        model.addRow(data);
			}
		}
		*/
	}
	
	/**
	 * @wbp.parser.constructor
	 */
	public Customer(int timerCounterParameter, int activeBidIndex) {
		this.activeBidIndex = activeBidIndex;
		
	//	System.out.println("This active :" + this.activeBidIndex);
		
		
		
		setTitle("Customer Panel");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 714, 461);
		contentPane = new JPanel();
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnNewButton = new JButton("ADD BID");
		btnNewButton.setBounds(17, 393, 85, 21);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				vipChecker = bidderNameTF.getText();
				
				if(Double.parseDouble(bidPriceTF.getText()) > priceList.get(activeBidIndex))
				{
				
				customerBidderNameList.add(bidderNameTF.getText());
				customerBidPriceList.add(Double.parseDouble(bidPriceTF.getText()));

				DefaultTableModel model = (DefaultTableModel) bidInfoTable.getModel();
				
				// Cleansing of nulls
				while (customerBidderNameList.contains(null))
				{
					customerBidderNameList.remove(customerBidderNameList.indexOf(null));
				}
				
				// Cleansing of nulls
				while (customerBidPriceList.contains(null))
				{
					customerBidPriceList.remove(customerBidPriceList.indexOf(null));
				}
				
	//			System.out.println("Array Size: " + arraySize);
	//			System.out.println("Array Size Elements: " + customerBidderNameList.get(0));
				
				int arraySize = customerBidderNameList.size();
				int counter = arraySize-1;
				
				System.out.println("bNL: " + customerBidderNameList.get(0) + " counter: " + counter);
				if(!(bids.isEmpty()) && vipBids.isEmpty() && !(vipChecker.contains("(VIP"))) 
				{if (customerBidPriceList.get(counter) > bids.peek().bid && timerCounter != -1) {System.out.println("Highest Bid. Resetting timer."); timerCounter = 40;}}
				
				else 
				{
					if(!vipChecker.contains("(VIP)")) {timerCounter=40;}
					if(vipChecker.contains("(VIP)") && vipHasJoined == false)
					{JOptionPane.showMessageDialog(null, "A VIP has entered the Auction! Sorry Non-VIP bidders!", "A VIP has joined", JOptionPane.INFORMATION_MESSAGE); vipHasJoined = true; timerCounter = 40;}
		            
					if (!vipBids.isEmpty() && vipChecker.contains("(VIP)"))
					{
						{if (customerBidPriceList.get(counter) > vipBids.peek().bid && timerCounter != -1) {System.out.println("Highest Bid. Resetting timer."); timerCounter = 40;}}
					}
					
				}
				
				String data[] = {customerBidderNameList.get(counter), String.valueOf(customerBidPriceList.get(counter))}; 
	            model.addRow(data);
	            
	//            System.out.println("Adding to Queue: " + customerBidderNameList.get(counter) + " " + customerBidPriceList.get(counter));
	            bids.offer(new Bidder(customerBidderNameList.get(counter), customerBidPriceList.get(counter)));
				
	            System.out.println("VIP Checker is: " + vipChecker.contains("(VIP)"));
				if(vipChecker.contains("(VIP)"))
				{
					vipBids.offer(new Bidder(customerBidderNameList.get(counter), customerBidPriceList.get(counter)));	
				}
				
				}
				else {JOptionPane.showMessageDialog(null, "Bid is less than or equal to price!", "Insufficient Fund", JOptionPane.INFORMATION_MESSAGE);}
	            
	            // =====================================================================
	            
			}
		});
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("BACK");
		btnNewButton_1.setBounds(258, 393, 85, 21);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean timerFinishCheck = true;
				
				if(!(itemNameList.isEmpty())) 
				{
					
					if(timerCounter == -1)
					{
					AppWindow backButton = new AppWindow( timerCounter, activeBidIndex, bids, itemNameList,  imageNameList,  bidderNameList,  priceList,  soldAtList);
					backButton.frmAuctionSystem.setVisible(true);
					timer.stop();
					dispose();
					}
					
					else
					{
						timerFinishCheck = false;
						JOptionPane.showMessageDialog(null, "Timer is not finished yet!", "Timer Not Finished", JOptionPane.INFORMATION_MESSAGE);
					}
				}
				
				if(itemNameList.isEmpty()) 
				{
					AppWindow backButton = new AppWindow(timerCounter);
					backButton.frmAuctionSystem.setVisible(true);
					timer.stop();
				}
				
				if(timerFinishCheck != false) { if(timerCounter>-1)timer.stop();}
				
				if(timerFinishCheck != false)dispose();
				
			}
		});
		contentPane.add(btnNewButton_1);
		
		bidderNameTF = new JTextField();
		bidderNameTF.setBounds(112, 177, 96, 19);
		contentPane.add(bidderNameTF);
		bidderNameTF.setColumns(10);
		
		bidPriceTF = new JTextField();
		bidPriceTF.setBounds(112, 219, 96, 19);
		contentPane.add(bidPriceTF);
		bidPriceTF.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Bidder Name:");
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setBounds(6, 178, 96, 13);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Bid Price      :");
		lblNewLabel_1.setForeground(Color.WHITE);
		lblNewLabel_1.setBounds(6, 225, 96, 13);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("CUSTOMER PANEL");
		lblNewLabel_2.setBounds(10, 10, 156, 21);
		lblNewLabel_2.setForeground(Color.WHITE);
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 14));
		contentPane.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("TIMER:");
		lblNewLabel_3.setBounds(255, 17, 59, 13);
		lblNewLabel_3.setForeground(Color.WHITE);
		lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 15));
		contentPane.add(lblNewLabel_3);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(261, 221, 429, 154);
		contentPane.add(scrollPane);
		
		bidInfoTable = new JTable();
		scrollPane.setViewportView(bidInfoTable);
		bidInfoTable.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Bid Name", "Bid"
			}
		));
		
		JLabel lblNewLabel_4 = new JLabel("Item Name:");
		lblNewLabel_4.setForeground(Color.WHITE);
		lblNewLabel_4.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_4.setBounds(256, 37, 102, 13);
		contentPane.add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("Price         :");
		lblNewLabel_5.setForeground(Color.WHITE);
		lblNewLabel_5.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_5.setBounds(258, 69, 65, 13);
		contentPane.add(lblNewLabel_5);
		
		itemNameTP = new JTextPane();
		itemNameTP.setBounds(340, 35, 102, 19);
		contentPane.add(itemNameTP);
		
		priceTP = new JTextPane();
		priceTP.setBounds(340, 63, 102, 19);
		contentPane.add(priceTP);
		
		timerTP = new JTextPane();
		timerTP.setForeground(Color.WHITE);
		timerTP.setBackground(Color.DARK_GRAY);
		timerTP.setBounds(310, 12, 19, 19);
		contentPane.add(timerTP);
		
	//	ImageIcon imgIcon = new ImageIcon();
		System.out.println("Active Bid: " + activeBidIndex);
        label = new JLabel();
        
        
		imgPanel = new JPanel();
		imgPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		imgPanel.setBounds(492, 23, 190, 179);
        
		contentPane.add(imgPanel);
		
		JLabel lblNewLabel_6 = new JLabel("Highest Bid:");
		lblNewLabel_6.setForeground(Color.WHITE);
		lblNewLabel_6.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_6.setBounds(258, 120, 85, 13);
		contentPane.add(lblNewLabel_6);
		
		highestBidTP = new JTextPane();
		highestBidTP.setBounds(340, 116, 123, 19);
		contentPane.add(highestBidTP);
		
		JLabel lblNewLabel_6_1 = new JLabel("Name       :");
		lblNewLabel_6_1.setForeground(Color.WHITE);
		lblNewLabel_6_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_6_1.setBounds(258, 151, 85, 13);
		contentPane.add(lblNewLabel_6_1);
		
		nameTP = new JTextPane();
		nameTP.setBounds(340, 145, 123, 19);
		contentPane.add(nameTP);
		
		JLabel lblNewLabel_7 = new JLabel("Bidding History");
		lblNewLabel_7.setForeground(Color.WHITE);
		lblNewLabel_7.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_7.setBounds(324, 201, 156, 13);
		contentPane.add(lblNewLabel_7);
	}
	
	
	
	public void timerFunction(int timerCounterParameter)
	{
		// Timer
			
			if(timerCounterParameter == -1){timerCounter = 50;}
			
			timer = new Timer(1000, new ActionListener() {
			    @Override
			    public void actionPerformed(ActionEvent e) {
			        timerTP.setText(String.valueOf(timerCounter*(1)));
			        timerCounter--;
			        
			        if(vipBids.isEmpty())
			        {
				        if(!(bids.isEmpty())) 
				        {
				        highestBidTP.setText(String.valueOf(bids.peek().bid));
						nameTP.setText(bids.peek().name);
				        }
			        }
			        
			        else 
			        {
			        	highestBidTP.setText(String.valueOf(vipBids.peek().bid));
						nameTP.setText(vipBids.peek().name);
			        }
			        
			        
			        if (timerCounter == -1) timer.stop();
			        if(timerCounter == -1 && (!bids.isEmpty() || !vipBids.isEmpty()))
					{
			        
			        
			        timerCounter = -1;
			        
			        if(!(vipBids.isEmpty()))
			        {
			        	System.out.println("This code ran");
			        	soldAtList.set(activeBidIndex, vipBids.peek().bid);
			        	bidderNameList.set(activeBidIndex, vipBids.peek().name);
			        	vipBids.clear();
			        	bids.clear();
			        }
			        
			        else
			        {
			        	soldAtList.set(activeBidIndex, bids.peek().bid);
				        bidderNameList.set(activeBidIndex, bids.peek().name);
				        bids.clear();	
			        }
			        
					JOptionPane.showMessageDialog(null, "Timer has finished!", "Timer Finished", JOptionPane.INFORMATION_MESSAGE);
					activeBidIndex = -1;
					}
			        
			        if(timerCounter == 19)
			        {
			        	JOptionPane.showMessageDialog(null, "20 SECONDS LEFT", "Timer Notice", JOptionPane.INFORMATION_MESSAGE);	
			        }
			        
			    }
			});
			timer.start();
			
			
			
		// Timer end
		}
}
