// For File Choosing
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.Timer;



import java.awt.EventQueue;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.TimerTask;

import javax.swing.border.EmptyBorder;

import java.awt.Font;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.table.DefaultTableModel;
import javax.swing.border.LineBorder;
import javax.swing.JTextPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;



public class Admin extends JFrame {

	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField newItemTF;
	private JTextField priceTF;
	private static JTable itemInfoTable;
	
	public static Timer timer;
	public static int timerCounter= -1;
	public static JTextPane timerTP;
	JButton btnNewButton_3;
	public static int selectedRow;
	public static int selectedRowCheck;
	public static int activeBidIndex;
	
	ArrayList<String> itemNameList = new ArrayList<String>();
	ArrayList<String> imageNameList = new ArrayList<String>();
	ArrayList<String> bidderNameList = new ArrayList<String>();
	ArrayList<Double> priceList = new ArrayList<Double>();
	ArrayList<Double> soldAtList = new ArrayList<Double>();
	ArrayList<Integer> counterList = new ArrayList<Integer>();
	
	Queue<Bidder> bids = new PriorityQueue<Bidder>(new BidderValueComparator());
	
	
	public static String imgName="";
	BufferedImage image;
	File selectedFile;
	
//	public static DefaultTableModel model = (DefaultTableModel) itemInfoTable.getModel();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Admin frame = new Admin(timerCounter);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @wbp.parser.constructor
	 */
	public Admin(int timerCounterParameter)
	{
		
		timerCounter = timerCounterParameter;
		if (timerCounter != -1) timerFunction(timerCounter);
		setTitle("Admin Panel");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 715, 442);
		contentPane = new JPanel();
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Admin Panel");
		lblNewLabel.setBounds(10, 10, 137, 19);
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("New Item:");
		lblNewLabel_1.setForeground(Color.WHITE);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_1.setBounds(10, 57, 129, 13);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("Price       :");
		lblNewLabel_1_1.setForeground(Color.WHITE);
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_1_1.setBounds(10, 93, 129, 13);
		contentPane.add(lblNewLabel_1_1);
		
		newItemTF = new JTextField();
		newItemTF.setBounds(100, 56, 96, 19);
		contentPane.add(newItemTF);
		newItemTF.setColumns(10);
		
		priceTF = new JTextField();
		priceTF.setBounds(100, 92, 96, 19);
		contentPane.add(priceTF);
		priceTF.setColumns(10);
		
		JButton btnNewButton_1 = new JButton("Add Item");
		btnNewButton_1.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				itemNameList.add(newItemTF.getText());
				imageNameList.add(imgName);
				priceList.add(Double.parseDouble(priceTF.getText()));
				bidderNameList.add("");
				soldAtList.add(0.0);
				
				DefaultTableModel model = (DefaultTableModel) itemInfoTable.getModel();
				int arraySize = itemNameList.size();
				int counter = arraySize-1;
				String data[] = {itemNameList.get(counter), imageNameList.get(counter), String.valueOf(priceList.get(counter)), bidderNameList.get(counter), String.valueOf(soldAtList.get(counter))}; 
	            model.addRow(data);
	            
	            
			}
		});
		
		
		btnNewButton_1.setBounds(62, 234, 85, 21);
		contentPane.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Back");
		btnNewButton_2.setFont(new Font("Tahoma", Font.PLAIN, 9));
		btnNewButton_2.setBackground(new Color(240, 240, 240));
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AppWindow backButton = new AppWindow(timerCounter, activeBidIndex, bids, itemNameList, imageNameList, bidderNameList, priceList, soldAtList);
				backButton.frmAuctionSystem.setVisible(true);
				if(timerCounter > -1) {timer.stop();}
				timerCounter = -1;
				dispose();
			}
		});
		btnNewButton_2.setBounds(219, 374, 85, 21);
		contentPane.add(btnNewButton_2);
		
		btnNewButton_3 = new JButton("Start Auction");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
			if(timerCounter <= -1)
			{
			selectedRowCheck = itemInfoTable.getSelectedRow();
			System.out.println(selectedRowCheck);
			if(selectedRowCheck > -1) {timerFunction(timerCounter); activeBidIndex = selectedRow;}
			else System.out.println("No selected row.");
			
			System.out.println("Selected Row: " + activeBidIndex);
			}
			else {JOptionPane.showMessageDialog(null, "There is an ongoing auction!!", "Ongoing auction", JOptionPane.INFORMATION_MESSAGE);}
            
			
			}
		});
		
		btnNewButton_3.setFont(new Font("Tahoma", Font.PLAIN, 9));
		btnNewButton_3.setBounds(606, 374, 85, 21);
		contentPane.add(btnNewButton_3);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
			}
		});
		scrollPane.setBounds(219, 35, 472, 329);
		contentPane.add(scrollPane);
		
		itemInfoTable = new JTable();
		itemInfoTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				
				selectedRow = itemInfoTable.getSelectedRow();
				System.out.println(selectedRow);
				DefaultTableModel model = (DefaultTableModel) itemInfoTable.getModel();
			//	selectedRowCheck = itemInfoTable.getSelectionModel().isSelectionEmpty(); 
			//	model.getValueAt(i, 0).toString();
			}
		});
		scrollPane.setViewportView(itemInfoTable);
		itemInfoTable.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Item Name", "Image", "Price", "Bidder Name", "Sold At"
			}
		));
		
		JLabel lblNewLabel_2 = new JLabel("TIMER:");
		lblNewLabel_2.setForeground(Color.WHITE);
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel_2.setBounds(219, 11, 96, 13);
		contentPane.add(lblNewLabel_2);
		
		JButton btnNewButton = new JButton("Select Image");
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 9));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
		            // Create an instance of ImageChooser
		            ImageChooser imageChooser = new ImageChooser();

		            // Choose an image file
		            selectedFile = imageChooser.chooseImageFile();

		            // Check if a file was selected
		            if (selectedFile != null) {
		                System.out.println("Selected file: " + selectedFile.getAbsolutePath());
		            } else {
		                System.out.println("No image file selected.");
		            }
		            
		            image = ImageIO.read(new File(selectedFile.getAbsolutePath()));
		            
		            /*
		            ImageIcon imgIcon = new ImageIcon(image);
		            JLabel label = new JLabel();
		            label.setIcon(imgIcon);
		            
		            imgPanel.remove(label);
		            imgPanel.add(label, BorderLayout.CENTER);
		            imgName = selectedFile.getAbsolutePath();
					*/
		            imgName = selectedFile.getAbsolutePath();
		            
		        } catch (Exception e1) {
		            System.err.println("An error occurred: " + e1.getMessage());
		        }
				
				
			}
		});
		btnNewButton.setBounds(49, 161, 111, 42);
		contentPane.add(btnNewButton);
		
		timerTP = new JTextPane();
		timerTP.setForeground(Color.WHITE);
		timerTP.setBackground(Color.DARK_GRAY);
		timerTP.setBounds(300, 7, 30, 19);
		contentPane.add(timerTP);
	}
	public Admin(int timerCounterParameter, int activeBidIndex, ArrayList<String> itemNameList, ArrayList<String> imageNameList, ArrayList<String> bidderNameList, ArrayList<Double> priceList, ArrayList<Double> soldAtList, ArrayList<Integer> counterList) {
		
		this(timerCounterParameter);
		
		this.activeBidIndex = activeBidIndex;
		this.bids = bids;
		this.itemNameList = itemNameList;
		this.imageNameList = imageNameList;
		this.bidderNameList = bidderNameList;
		this.priceList = priceList;
		this.soldAtList = soldAtList;
		this.counterList = counterList;
		
		// Loading the data for the table
		int arraySize;
		try {
		arraySize = itemNameList.size();}catch(Exception e) {System.out.println("Error: " + e);arraySize = 0;}
		
		DefaultTableModel model = (DefaultTableModel) itemInfoTable.getModel();
		for (int counter = 0; counter < arraySize; counter++)
		{
			
			String data[] = {itemNameList.get(counter), imageNameList.get(counter), String.valueOf(priceList.get(counter)), bidderNameList.get(counter), String.valueOf(soldAtList.get(counter))}; 
            model.addRow(data);
		}
	}




public void timerFunction(int timerCounterParameter)
{
	// Timer
		
		if(timerCounterParameter <= -1){timerCounter = 40;}
		
		timer = new Timer(1000, new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		        timerTP.setText(String.valueOf(timerCounter*(1)));
		        timerCounter--;
		        
		        System.out.println(activeBidIndex + " yoooo");
		        if(activeBidIndex == -1) {timer.stop(); timerCounter = -1; }
		        if(timerCounter == -1)
				{
				timer.stop();
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
