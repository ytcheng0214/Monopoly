import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Font;
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
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class LoginPanel extends JPanel{
	private static final int FIELD_WIDTH = 10;
	private PlayerPanel p1;
	private PlayerPanel p2;
	private JLabel titleLabel;
	private JLabel player1NameLabel;
	private JLabel player2NameLabel;
	private JLabel instructLabel;
	private JTextField player1NameField;
	private JTextField player2NameField;
	private JButton loginButton;
	private JButton instructBtn;
	private JButton okBtn;
	private JTextArea infoArea;
	private JPanel instructP;
	
	public LoginPanel(PlayerPanel p1, PlayerPanel p2) {
		this.p1 = p1;
		this.p2 = p2;
		createComp();
	}
	
	public void createComp() {
		this.titleLabel = new JLabel("Welcome to Monopoly Game, Please Login");
		Font font = new Font("Dialog", Font.BOLD, 40);
		this.titleLabel.setFont(font);
		ImageIcon icon = new ImageIcon("img/winnerstage.png");//�Ȯɪ�
		icon.setImage(icon.getImage().getScaledInstance(900, 500, Image.SCALE_DEFAULT));
		this.player1NameLabel = new JLabel("Input player1's name: ");
		this.player1NameLabel.setFont(new Font("Dialog", Font.BOLD, 25));
		this.player2NameLabel = new JLabel("Input player2's name: ");
		this.player2NameLabel.setFont(new Font("Dialog", Font.BOLD, 25));
		this.player1NameField = new JTextField(FIELD_WIDTH);
		this.player1NameField.setFont(new Font("Dialog", Font.BOLD, 25));
		this.player2NameField = new JTextField(FIELD_WIDTH);
		this.player2NameField.setFont(new Font("Dialog", Font.BOLD, 25));
		this.loginButton = new JButton("Login");
		this.loginButton.setFont(new Font("Dialog", Font.BOLD, 20));
		this.okBtn=new JButton("OK");
		this.okBtn.setFont(new Font("Dialog", Font.BOLD, 20));
		this.instructBtn = new JButton("Instruction");
		this.instructBtn.setFont(new Font("Sarif", Font.PLAIN, 15));
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 0;
		c.insets = new Insets(0,0,300,0);
		add(this.titleLabel, c);
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		c.insets = new Insets(0, 10, 80, 290);
		add(this.player1NameLabel, c);
		c.gridx = 0;
		c.gridy = 0;
		c.gridheight = 2;
		c.gridwidth = 15;
		c.insets = new Insets(0, 320, 80, 130);
		add(this.player1NameField, c);
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		c.insets = new Insets(30, 10, 20, 290);
		add(this.player2NameLabel, c);
		c.gridx = 0;
		c.gridy = 0;
		c.gridheight = 2;
		c.gridwidth = 15;
		c.insets = new Insets(30, 320, 20, 130);
		add(this.player2NameField, c);
		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(300, 0, 0, 0);
		add(this.loginButton, c);
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.insets = new Insets(0,750,700,0);
		add(instructBtn,c);
		//c.gridx = 0;
		//c.gridy = 0;
		//c.gridwidth = 0;
		//add(this.loginLabel, c);

	}
	
	public void addButtonListener(JPanel panel, JMenuBar menubar) {
		
		class ClickListener implements ActionListener{

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				CardLayout cl = (CardLayout)panel.getLayout();
				p1.getPlayer().setName(player1NameField.getText());
				p2.getPlayer().setName(player2NameField.getText());
				p1.getPlayerInfo().setText(p1.getPlayer().getInfo());
				p2.getPlayerInfo().setText(p2.getPlayer().getInfo());
				player1NameField.setText("");
				player2NameField.setText("");
				p1.setYourTurn("Roll the dice!");
				cl.show(panel, "2");
				menubar.setVisible(true);
			}	
		}
		ActionListener listener = new ClickListener();
		this.loginButton.addActionListener(listener);
		
		class InstructBtn implements ActionListener{
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				CardLayout cl = (CardLayout)panel.getLayout();
				cl.show(panel, "4");
			}
		}
		ActionListener l = new InstructBtn();
		this.instructBtn.addActionListener(l);
		class OkBtn implements ActionListener{
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				CardLayout cl = (CardLayout)panel.getLayout();
				cl.show(panel, "1");
			}
		}
		ActionListener ol = new OkBtn();
		this.okBtn.addActionListener(ol);
	}
	
	public JPanel createInstructP() {
		this.instructP=new JPanel();
		this.instructLabel = new JLabel("Introduction of the Monopoly Game:");
		Font font = new Font("Dialog", Font.BOLD, 30);
		this.instructLabel.setFont(font);
		this.infoArea=new JTextArea(5, 5);
		font = new Font("Dialog", Font.PLAIN, 20);
		this.infoArea.setFont(font);
		this.infoArea.setLineWrap(true);
		JScrollPane s=new JScrollPane(infoArea);
		String t="In this game, asset equals the sum of cash and total house value. Game ends when a player's position gets to \n108 or when players click on 'Game Finished' in the menu, and"
				+" the player with the most asset wins. \n\nHouse Price starts at $80, cash and the number of house of a player starts at 0. \n\nBelow are the functions players will encounter in this game:\n\n"
				+"Free house: get a house for free.\n\n"
				+"Q&A: get $100 cash if answered the question correctly, lose $50 cash vice versa.\n\n"
				+"$1K: get $1000 cash.\n\n"
				+"Chance: can choose to buy or sell house.\n\n"
				+"Opportunity: there are four possible outcomes, house price*2, get one house for free, house price/2 or lose one \nhouse.\n\n"
				+"Interest 0.1%, 0.05%, -0.01%, -0.05%: get $(0.001*cash), get $(0.0005*cash), lose $(0.0001*cash), lose \n$(0.0005*cash).\n\n"
				+"Tax 10%, 50% on house: cash-(0.1*house*house price), cash-(0.5*house*house price).\n\n"
				+"Tax 10%, 20% on cash: lose $(0.1*cash), lose $(0.2*cash).\n\n"
				+"Lost land & stop: stop for a round.\n\n"
				+"Rent $200, $500: get $(200*house), get $(500*house).\n\n"
				+"Airport: back to START.\n\n";
		
		this.infoArea.setText(t);
		this.instructP.setLayout(new BorderLayout());
		this.instructP.add(this.instructLabel, BorderLayout.NORTH);
		this.instructP.add(s, BorderLayout.CENTER);
		this.instructP.add(this.okBtn, BorderLayout.SOUTH);
		
		return instructP;
	}
}
