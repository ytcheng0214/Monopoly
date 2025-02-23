import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

// import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class PlayerPanel extends JPanel{
	private Player player;
	private ImageIcon icon;
	private Dice dice;
	private JLabel yourTurn;
	private JLabel imageLabel;
	private JTextArea playerInfo;
	private JButton rollDice;
	private JPanel infoPanel, mainPanel;
	// private QuestionPanel questionFrame;
	private QuestionPanel questionP;
	private OppAndFateFrame oppAndFateFrame;
	private boolean flag = false;
	
	public PlayerPanel(ImageIcon icon, Player player) throws SQLException {
		this.player = player;
		// questionFrame = new QuestionFrame(player);
		this.questionP=new QuestionPanel(player);
		oppAndFateFrame = new OppAndFateFrame(player);
		addSellBuyBtn();
		this.icon = icon;
		this.dice = new Dice();
		createComp();
	}
	
	public void createComp() {
		this.playerInfo = new JTextArea();
		this.playerInfo.setFont(new Font("UD Digi Kyokasho NK-B", Font.BOLD, 18));
		this.playerInfo.setText(this.player.getInfo());
		this.playerInfo.setBackground(new Color(244, 237, 227));
		this.rollDice = new JButton("Roll the Dice");
		this.rollDice.setFont(new Font("UD Digi Kyokasho NK-B", Font.BOLD, 15));
		this.rollDice.setBackground(new Color(222, 114, 117));
		this.rollDice.setForeground(Color.WHITE);

		
		this.infoPanel = new JPanel(new BorderLayout());
		this.infoPanel.add(playerInfo, BorderLayout.NORTH);
		this.infoPanel.add(rollDice, BorderLayout.SOUTH);
		this.yourTurn = new JLabel("Are you ready?");
		this.yourTurn.setFont(new Font("Maiandra GD", Font.BOLD, 25));

		icon.setImage(icon.getImage().getScaledInstance(400, 210, Image.SCALE_DEFAULT));
		this.imageLabel = new JLabel(this.icon);
		setBackground(new Color(201, 231, 255));
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 0;
		c.gridheight = 1;
		c.insets = new Insets(5,130,0,0);
		add(this.yourTurn, c);
		c.gridx = 40;
		c.gridy = 20;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(20,125,0,0);
		add(this.infoPanel, c);
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 120;
		c.gridheight = 120;
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(0,0,30,40);
		add(this.imageLabel, c);
	}
	public void rollDiceBtnOperation(Player opponent, PlayerPanel opponentPanel) throws SQLException{
		class ClickListener implements ActionListener{
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				dice.rollDice();
				player.setPosition(dice.getPoint());
				setYourTurn("Dice point: " + dice.getPoint());
				flag = true;
			}
			
		}
		ActionListener listener = new ClickListener();
		this.rollDice.addActionListener(listener);
	}
	public JTextArea getPlayerInfo() {
		return this.playerInfo;
	}
	public Player getPlayer() {
		return this.player;
	}
	public void setYourTurn(String youtTurn) {
		this.yourTurn.setText(youtTurn);
	}
	public String getYourTurn() {
		return this.yourTurn.getText();
	}
	public Dice getDice() {
		return this.dice;
	}
	public JButton getRollDice() {
		return this.rollDice;
	}
	public void resetFlag() {
		this.flag = false;
	}
	public boolean getFlag() {
		return this.flag;
	}
	public void getAction(int position) throws SQLException {
		// free house
		if(player.getPosition()%36==2 || player.getPosition()%36==21) {
			// player.setStPosition("Freehouse");
			JOptionPane.showMessageDialog(null,"Congratulations! You are gifted one house for free!","Position: Free House",JOptionPane.PLAIN_MESSAGE,new ImageIcon("house.png"));
			player.setHouse(1);
			
		// Q&A
		}else if(player.getPosition()%36==3||player.getPosition()%36==5||player.getPosition()%36==14||player.getPosition()%36==17||player.getPosition()%36==20||player.getPosition()%36==22||player.getPosition()%36==25||player.getPosition()%36==29||player.getPosition()%36==31||player.getPosition()%36==35) {
			// player.setStPosition("Q&A");
			// JOptionPane.showMessageDialog(null,"You got a question. Please answer it.","Your recent Position",JOptionPane.QUESTION_MESSAGE);
			questionP.setQuestion();
			addSubmitButtonListener();
			CardLayout cl = (CardLayout)this.mainPanel.getLayout();
			if(player.getPlayerID() == 1) {
				cl.show(player.getMainPanel(), "5");
			}
			else if(player.getPlayerID() == 2) {
				cl.show(player.getMainPanel(), "7");
			}
			
		// $1K
		}else if(player.getPosition()%36==4 || player.getPosition()%36==12) {
			JOptionPane.showMessageDialog(null,"Wow! You just won a $1K lottery!","Position: $1K",JOptionPane.PLAIN_MESSAGE,new ImageIcon("cash.png"));
			player.setCash(1000);
			// player.setStPosition("$1K");
		// $500
		}else if(player.getPosition()%36==23) {
			JOptionPane.showMessageDialog(null,"You got $500 pocket money!","Position: $500",JOptionPane.PLAIN_MESSAGE,new ImageIcon("cash.png"));
			player.setCash(500);
		// chance
		}else if(player.getPosition()%36==6 || player.getPosition()%36==13 || player.getPosition()%36==32) {
			JOptionPane.showMessageDialog(null,"You can choose to buy or sell your houses.","Position: Chance",JOptionPane.PLAIN_MESSAGE,new ImageIcon("house.png"));
			oppAndFateFrame.setVisible(true);
			// player.setStPosition("chance");
		// opportunity
		}else if(player.getPosition()%36==7 || player.getPosition()%36==16 || player.getPosition()%36==26 || player.getPosition()%36==36) {
			JOptionPane.showMessageDialog(null, player.gamble(),"Position: Opportunity",0);
			// player.setStPosition("oppor?");
		// interest 0.1%
		}else if(player.getPosition()%36==8) {
			JOptionPane.showMessageDialog(null,"You earned an extra interest 0.1% on cash through a foreign account you own.","Position: Cash Interest 0.1%",JOptionPane.PLAIN_MESSAGE,new ImageIcon("cash.png"));
			player.setCash(0.001*player.getCash());
			// player.setStPosition("+$0.1%");
		// interest 0.05%
		}else if(player.getPosition()%36==15) {
			JOptionPane.showMessageDialog(null,"You earned interest 0.05% on cash through deposit.","Position: Cash Interest 0.05%",JOptionPane.PLAIN_MESSAGE,new ImageIcon("cash.png"));
			player.setCash(0.0005*player.getCash());
			// player.setStPosition("+$0.05%");
		// negative interest 0.05%
		}else if(player.getPosition()%36==30) {
			JOptionPane.showMessageDialog(null,"You encountered the economic recession and got negative interest 0.05% in deposit.","Position: Negative Cash Interest 0.05%",JOptionPane.PLAIN_MESSAGE,new ImageIcon("cash.png"));
			player.setCash(-0.0005*player.getCash());
			// player.setStPosition("-$0.05%");
		// negative interest 0.01%
		}else if(player.getPosition()%36==24) {
			JOptionPane.showMessageDialog(null,"You encountered the great depression and got negative interest 0.01% in deposit.","Position: Negative Cash Interest 0.01%",JOptionPane.PLAIN_MESSAGE,new ImageIcon("cash.png"));
			player.setCash(-0.001*player.getCash());
			// player.setStPosition("-$0.01%");
		// tax 10% on house
		}else if(player.getPosition()%36==9) {
			JOptionPane.showMessageDialog(null,"You paid 10% tax on real estate.","Position: 10% Tax on house",JOptionPane.PLAIN_MESSAGE,new ImageIcon("cash.png"));
			player.setCash(-player.getHouse()*player.getHousePrice()*0.1);
			// player.setStPosition("-$10% house");
		// tax 50% on house
		}else if(player.getPosition()%36==33) {
			JOptionPane.showMessageDialog(null,"You paid 50% tax on real estate.","Position: 50% Tax on house",JOptionPane.PLAIN_MESSAGE,new ImageIcon("cash.png"));
			player.setCash(-player.getHouse()*player.getHousePrice()*0.5);
			// player.setStPosition("-$50% house");
		// tax 10% on cash
		}else if(player.getPosition()%36==18) {
			JOptionPane.showMessageDialog(null,"You paid 10% income tax.","Position: 10% Income Tax",JOptionPane.PLAIN_MESSAGE,new ImageIcon("cash.png"));
			player.setCash(-player.getCash()*0.1);
			// player.setStPosition("-$10%");
		// tax 20% on cash
		}else if(player.getPosition()%36==27) {
			JOptionPane.showMessageDialog(null,"You paid 20% richman tax.","Position: 20% Richman Tax",JOptionPane.PLAIN_MESSAGE,new ImageIcon("cash.png"));
			player.setCash(-player.getCash()*0.2);
			// player.setStPosition("-$20%");
		// lost land
		}else if(player.getPosition()%36==10) {
			JOptionPane.showMessageDialog(null,"You arrived at a lostland full of richmen and want to stay there for a while.","Position: Lost Land",0);
			player.countPlus();
			// player.setStPosition("Lost land");
		// stop
		}else if(player.getPosition()%36==19) {
			JOptionPane.showMessageDialog(null,"You are involved in tax envasion. Stop for a round.","Position: Stop",0);
			player.countPlus();
			// player.setStPosition("Stop");
		// rent $200
		}else if(player.getPosition()%36==11) {
			JOptionPane.showMessageDialog(null,"You earned $200 rent from each houses you have.","Position: $200 Rent",0);
			if(player.getHouse()!=0) {
				player.setCash(player.getHouse()*200);
				// player.setStPosition("rent $200");
			}
		// rent $500
		}else if(player.getPosition()%36==34) {
			JOptionPane.showMessageDialog(null,"You earned $500 rent from each houses you have.","Position: $500 Rent",0);
			if(player.getHouse()!=0) {
				player.setCash(player.getHouse()*500);
				// player.setStPosition("rent $500");
			}
		// airport
		}else if(player.getPosition()%36==28){
			JOptionPane.showMessageDialog(null,"You took a trip to the START.","Position: Airport",0);
			player.setPosition(-27);
			// player.setStPosition("airport");
		}
		this.playerInfo.setText(this.player.getInfo());
	}
	
	public QuestionPanel getQuestionP() {
		
		return this.questionP;
	}
	
	public void setMainPanel(JPanel mainP) {
		this.mainPanel=mainP;
	}
	
	public void addSubmitButtonListener(){
		class buttonListener implements ActionListener{
			public void actionPerformed(ActionEvent e) {
					CardLayout cl = (CardLayout)player.getMainPanel().getLayout();
					cl.show(player.getMainPanel(), "2");
					playerInfo.setText(player.getInfo());
			}
		}
		ActionListener l=new buttonListener();
		this.questionP.getGotIt().addActionListener(l);
		this.questionP.getAnswerP().add(this.questionP.getGotIt(), BorderLayout.SOUTH);
	}
	
	public void addSellBuyBtn() {
		class buyBtnListener implements ActionListener{
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(player.buyHouse(Integer.parseInt(oppAndFateFrame.getBuyTF().getText()))) {
					JOptionPane.showMessageDialog(null, "Purchased "+oppAndFateFrame.getBuyTF().getText()+" house successfully!","Congratulations!", 0);
					oppAndFateFrame.getBuyTF().setText("");
				}else {
					JOptionPane.showMessageDialog(null, "Insufficient cash, please try again.", "Error", 0);
					oppAndFateFrame.getBuyTF().setText("");
				}
				playerInfo.setText(player.getInfo());
			}
			
		}
		ActionListener buyl = new buyBtnListener();
		oppAndFateFrame.getBuyBtn().addActionListener(buyl);
		class sellBtnListener implements ActionListener{
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(player.sellHouse(Integer.parseInt(oppAndFateFrame.getSellTF().getText()))) {
					JOptionPane.showMessageDialog(null, "Sold "+oppAndFateFrame.getSellTF().getText()+" house successfully!","Congratulations!", 0);
					oppAndFateFrame.getSellTF().setText("");
				}else {
					JOptionPane.showMessageDialog(null, "Insufficient house, please try again.", "Error", 0);
					oppAndFateFrame.getSellTF().setText("");
				}
				playerInfo.setText(player.getInfo());
			}
			
		}
		ActionListener sell = new sellBtnListener();
		oppAndFateFrame.getSellBtn().addActionListener(sell);
	}
	
}
