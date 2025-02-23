import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class OppAndFateFrame extends JFrame{
	private Player player;
	private JPanel mainPanel, titlePanel, inputPanel, leavePanel, backgroundPanel;
	private JLabel backgroundLabel, titleLabel, buyLabel, sellLabel;
	private JTextField buyTf, sellTf;
	private JButton buyBtn, sellBtn, leaveBtn;
	
	public OppAndFateFrame(Player p) {
		this.player = p;
		setTitle("Opportunity or Fate ?!");
		setSize(500,500);
		setUndecorated(true);
		createComp();
		//setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	}
	public void createComp() {
		this.backgroundLabel = new JLabel();
		ImageIcon icon = new ImageIcon("img/buysellhouse.png");
		icon.setImage(icon.getImage().getScaledInstance(500, 500, Image.SCALE_DEFAULT));
		this.backgroundLabel = new JLabel(icon);
		this.titleLabel = new JLabel("Buy or Sell House ?");
		this.titleLabel.setFont(new Font("Sarif", Font.BOLD, 48));
		this.buyLabel = new JLabel("Buy ? houses :");
		this.buyLabel.setFont(new Font("Sarif", Font.PLAIN, 25));
		this.sellLabel = new JLabel("Sell ? houses :");
		this.sellLabel.setFont(new Font("Sarif", Font.PLAIN, 25));
		this.buyTf = new JTextField(15);
		this.buyTf.setFont(new Font("Sarif", Font.PLAIN, 20));
		this.sellTf = new JTextField(15);
		this.sellTf.setFont(new Font("Sarif", Font.PLAIN, 20));
		this.buyBtn = new JButton("Buy");
		this.buyBtn.setFont(new Font("Sarif", Font.PLAIN, 18));
		this.sellBtn = new JButton("Sell");
		this.sellBtn.setFont(new Font("Sarif", Font.PLAIN, 18));
		this.leaveBtn = new JButton("No, thank you.");
		this.leaveBtn.setFont(new Font("Sarif", Font.PLAIN, 20));
		addleaveBtnListener();
		
		this.mainPanel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 0;
		c.insets = new Insets(0, 0, 300, 0);
		this.mainPanel.add(titleLabel,c);
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		c.insets = new Insets(0, 50, 80, 280);
		this.mainPanel.add(buyLabel,c);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		c.gridheight = 2;
		c.gridwidth = 15;
		c.insets = new Insets(0, 250, 80, 180);
		this.mainPanel.add(buyTf,c);
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		c.insets = new Insets(0, 370, 80, 80);
		this.mainPanel.add(buyBtn,c);
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		c.insets = new Insets(20, 50, 20, 280);
		this.mainPanel.add(sellLabel,c);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		c.gridheight = 2;
		c.gridwidth = 15;
		c.insets = new Insets(20, 250, 20, 180);
		this.mainPanel.add(sellTf,c);
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		c.insets = new Insets(20, 370, 20, 80);
		this.mainPanel.add(sellBtn,c);
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		c.insets = new Insets(300, 0, 0, 0);
		this.mainPanel.add(leaveBtn,c);
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 0;
		c.insets = new Insets(0, 0, 0, 0);
		this.mainPanel.add(backgroundLabel,c);
		this.mainPanel.setBounds(0, 0, 500, 500);
		this.add(mainPanel);
	}
	public void addleaveBtnListener() {
		
		class ClickListener implements ActionListener{

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				setVisible(false);
			}
			
		}
		ActionListener listener = new ClickListener();
		this.leaveBtn.addActionListener(listener);
	}
	
	public JButton getBuyBtn(){
		return this.buyBtn;
	}
	
	public JButton getSellBtn() {
		return this.sellBtn;
	}
	
	public JTextField getBuyTF() {
		return this.buyTf;
	}
	
	public JTextField getSellTF() {
		return this.sellTf;
	}
}
