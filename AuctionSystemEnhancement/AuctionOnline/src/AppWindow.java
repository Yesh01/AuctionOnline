import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;



public class AppWindow {

	public JFrame frmAuctionSystem;
	public static int timerCounter = -1;
	ArrayList<String> itemNameList = new ArrayList<String>();
	ArrayList<String> imageNameList = new ArrayList<String>();
	ArrayList<String> bidderNameList = new ArrayList<String>();
	ArrayList<Double> priceList = new ArrayList<Double>();
	ArrayList<Double> soldAtList = new ArrayList<Double>();
	ArrayList<Integer> counterList = new ArrayList<Integer>();
	
	Queue<Bidder> bids = new PriorityQueue<Bidder>(new BidderValueComparator());
	
	public static int activeBidIndex;
	
	Timer timer;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AppWindow window = new AppWindow(timerCounter);
					window.frmAuctionSystem.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public AppWindow(int timerCounterParameter) {
		if(timer != null)timer.stop();
		timerCounter = timerCounterParameter;
		
		System.out.println("Tmer counter is: " +timerCounter);
		initialize();
	}
	
	public AppWindow(int timerCounterParameter, int activeBidIndex, Queue<Bidder> bids,ArrayList<String> itemNameList, ArrayList<String> imageNameList, ArrayList<String> bidderNameList, ArrayList<Double> priceList, ArrayList<Double> soldAtList)
	{	
		this(timerCounterParameter);
		if (timerCounter != -1) timerFunction(timerCounter);
		
		this.bids = bids;
		this.itemNameList = itemNameList;
		this.imageNameList = imageNameList;
		this.bidderNameList = bidderNameList;
		this.priceList = priceList;
		this.soldAtList = soldAtList;
		this.activeBidIndex = activeBidIndex;
		// System.out.println("Testing biddernamelsit: " +bidderNameList.get(0));
	}

	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmAuctionSystem = new JFrame();
		frmAuctionSystem.getContentPane().setBackground(Color.DARK_GRAY);
		frmAuctionSystem.getContentPane().setForeground(Color.DARK_GRAY);
		frmAuctionSystem.setTitle("Auction System");
		frmAuctionSystem.setBounds(100, 100, 450, 156);
		frmAuctionSystem.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmAuctionSystem.getContentPane().setLayout(null);
		
		JButton btnNewButton_1 = new JButton("ADMIN");
		btnNewButton_1.setFont(new Font("Tahoma", Font.PLAIN, 8));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(timerCounter == -1)
				{
				Admin adminButton = new Admin(timerCounter, activeBidIndex, itemNameList, imageNameList, bidderNameList, priceList, soldAtList, counterList);
				adminButton.setVisible(true);
				if(timerCounter>-1)timer.stop();
				frmAuctionSystem.dispose();
				}else {JOptionPane.showMessageDialog(null, "Timer is not finished yet! Go to customer", "Timer Not Finished", JOptionPane.INFORMATION_MESSAGE);}
			}
		});

	
	
		btnNewButton_1.setBounds(102, 71, 85, 21);
		frmAuctionSystem.getContentPane().add(btnNewButton_1);
		
		JButton btnNewButton = new JButton("CUSTOMER");
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 8));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(timerCounter == -1)
				{
					JOptionPane.showMessageDialog(null, "There is no auction yet!", "No Auction", JOptionPane.INFORMATION_MESSAGE);	
				}
				
				else {
				if(!(itemNameList.isEmpty())) 
				{
				//	System.out.println("Transferring activeBidIndex: " + activeBidIndex);
					Customer customerButton = new Customer(timerCounter, activeBidIndex, bids,itemNameList, imageNameList, bidderNameList, priceList, soldAtList);
					customerButton.setVisible(true);
				}
				
				if(itemNameList.isEmpty())
				{
					Customer customerButton = new Customer(timerCounter, activeBidIndex);
					customerButton.setVisible(true);
				}
				

				System.out.println("Appwindow timer: " + timerCounter);
				timer.stop();
				timerCounter = -1;
				System.out.println("Timer running: " + timer.isRunning());
				frmAuctionSystem.dispose();
				}
			}
		});
		btnNewButton.setBounds(258, 71, 85, 21);
		frmAuctionSystem.getContentPane().add(btnNewButton);
		
		JLabel lblNewLabel = new JLabel("ONLINE AUCTION SYSTEM");
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setFont(new Font("Yu Gothic UI", Font.BOLD, 20));
		lblNewLabel.setVerticalAlignment(SwingConstants.BOTTOM);
		lblNewLabel.setBounds(102, 10, 249, 28);
		frmAuctionSystem.getContentPane().add(lblNewLabel);
	}
	
	public void timerFunction(int timerCounterParameter)
	{
		// Timer
			
			if(timerCounterParameter == -1){timerCounter = 60;}
			
			timer = new Timer(1000, new ActionListener() {
			    @Override
			    public void actionPerformed(ActionEvent e) {
			        timerCounter--;
			        
			        if(activeBidIndex == -1) {timer.stop(); timerCounter = -1; }
			        if(timerCounter <= -1)
					{
			        if(timerCounter <= -1)timer.stop();
					JOptionPane.showMessageDialog(null, "Timer has finished!", "Timer Finished", JOptionPane.INFORMATION_MESSAGE);
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
