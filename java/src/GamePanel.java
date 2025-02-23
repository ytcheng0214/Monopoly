import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;



public class GamePanel extends JPanel{
	private JLabel mapLabel;
	private JPanel mapPanel;
	private JPanel playerPanel;
	private JPanel mainPanel;
	private WinnerPanel winnerPanel;
	private JMenuBar menubar;
	private PlayerPanel p1;
	private PlayerPanel p2;
	
	
	private Image role1;
	private int role1_x = 290;
	private int role1_y = 505;
	private Image role2;
	private int role2_x = 260;
	private int role2_y = 515;
	private Timer t;
	private boolean triggerMovePlayer1 = false;
	private boolean triggerMovePlayer2 = false;
	
	
	
	private Image[] dice = new Image[6];
	private int currentdice_ID = 0;
	private Timer timer;
	
	
	
	public GamePanel(PlayerPanel p1, PlayerPanel p2, JPanel mainPanel, JMenuBar menubar) {
		this.p1 = p1;
		this.p2 = p2;
		this.mainPanel = mainPanel;
		this.menubar = menubar;
		createComp();
	}
	public void createComp() {
		setBackground(new Color(201, 231, 255));
		ImageIcon icon = new ImageIcon("img/map.png");
		icon.setImage(icon.getImage().getScaledInstance(700, 600, Image.SCALE_DEFAULT));
		this.mapLabel = new JLabel(icon);
		this.mapPanel = new JPanel();
		this.mapPanel.add(mapLabel);
		this.mapPanel.setBackground(new Color(201, 231, 255));
		this.playerPanel = new JPanel(new BorderLayout());
		this.playerPanel.add(p1, BorderLayout.NORTH);
		this.playerPanel.add(p2, BorderLayout.SOUTH);
		this.playerPanel.setBackground(new Color(201, 231, 255));
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.gridheight = 500;
		c.gridwidth = 350;
		c.anchor = GridBagConstraints.NORTH;
		add(this.mapPanel, c);
		c.gridx = 700;
		c.gridy = 0;
		c.gridheight = 150;
		c.gridwidth = 250;
		add(this.playerPanel, c);
		
		
		ImageIcon image1 = new ImageIcon("img/movePlayer1.png");
		image1.setImage(image1.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
		role1 = image1.getImage();
		ImageIcon image2 = new ImageIcon("img/movePlayer2.png");
		image2.setImage(image2.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
		role2 = image2.getImage();
		t = new Timer();
		t.schedule(new movetimerTask(), 1000, 200);
		
		initImage();
		timer = new Timer();
		timer.schedule(new dicetimerTask(), 1000, 50);
		
	}
	
	public int getCurrentDice_ID(){
		return this.currentdice_ID;
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2D = (Graphics2D) g;
		g2D.drawImage(role1, role1_x, role1_y, null);
		g2D.drawImage(role2, role2_x, role2_y, null);
		g2D.drawImage(dice[currentdice_ID],290,320,null);
	}
	class movetimerTask extends TimerTask {
		int countMove = 0;
		int dicePoint = 0;
		int countPosition = 0;
		public void run() {
			if(triggerMovePlayer1) {
				movingPlayer1();
			}
			else if(triggerMovePlayer2) {
				movingPlayer2();
			}
			repaint();
		}
		
		public void movingPlayer1() {
			if(countMove == 0) {
				dicePoint = p1.getDice().getPoint();
				countPosition = p1.getPlayer().getLastPosition();
			}
			if(countPosition <109 && countMove < dicePoint) {
				if(countPosition%36 < 10 && countPosition%36 > 0) {
					role1_x-=31;
					role1_y-=17;
				}
				else if(countPosition%36 == 10) {
					role1_x+=47;
					role1_y-=35;
				}
				else if(countPosition%36 > 10 && countPosition %36 < 19){
					role1_x+=28;
					role1_y-=19;
				}
				else if(countPosition%36 == 19) {
					role1_x+=38;
					role1_y+=20;
				}
				else if(countPosition%36 >19 && countPosition %36 < 28) {
					role1_x+=29.5;
					role1_y+=19;
				}
				else if(countPosition%36 == 28) {
					role1_x-=36;
					role1_y+=25;
				}
				else if(countPosition%36 >28 && countPosition%36 <= 35) {
					role1_x-=27;
					role1_y+=18;
				}
				else if(countPosition%36 == 0) {
					role1_x-=37;
					role1_y+=17;
				}
				countPosition ++;
				countMove ++;
			}
			else if(countPosition%36 == 28) {
				role1_x = 290;
				role1_y = 505;
				try {
					p1.getAction(p1.getPlayer().getPosition());
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				countMove = 0;
				triggerMovePlayer1 = false;
			}
			else if(countPosition >= 109) {
				result();
			}
			else {
				try {
					p1.getAction(p1.getPlayer().getPosition());
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				countMove = 0;
				triggerMovePlayer1 = false;
			}
		}
		public void movingPlayer2() {
			if(countMove == 0) {
				dicePoint = p2.getDice().getPoint();
				countPosition = p2.getPlayer().getLastPosition();
			}
			if(countPosition <109 && countMove < dicePoint) {
				if(countPosition%36 < 10 && countPosition%36 > 0) {
					role2_x-=29;
					role2_y-=18;
				}
				else if(countPosition%36 == 10) {
					role2_x+=39;
					role2_y-=39;
				}
				else if(countPosition%36 > 10 && countPosition %36 < 19){
					role2_x+=28;
					role2_y-=19;
				}
				else if(countPosition%36 == 19) {
					role2_x+=38;
					role2_y+=20;
				}
				else if(countPosition%36 >19 && countPosition %36 < 28) {
					role2_x+=29.5;
					role2_y+=19;
				}
				else if(countPosition%36 == 28) {
					role2_x-=36;
					role2_y+=25;
				}
				else if(countPosition%36 >28 && countPosition%36 <= 35) {
					role2_x-=27;
					role2_y+=18;
				}
				else if(countPosition%36 == 0) {
					role2_x-=47;
					role2_y+=30;
				}
				countPosition ++;
				countMove ++;
			}
			else if(countPosition%36 == 28) {
				role2_x = 260;
				role2_y = 515;
				try {
					p2.getAction(p2.getPlayer().getPosition());
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				countMove = 0;
				triggerMovePlayer2 = false;
			}
			else if(countPosition >= 109) {
				result();
			}
			else {
				try {
					p2.getAction(p2.getPlayer().getPosition());
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				countMove = 0;
				triggerMovePlayer2 = false;
			}
		}
		public void result() {
			if(p1.getPlayer().calcAsset()>p2.getPlayer().calcAsset()) {
				winnerPanel = new WinnerPanel(p1.getPlayer());
			}
			else if(p1.getPlayer().calcAsset()<p2.getPlayer().calcAsset()) {
				winnerPanel= new WinnerPanel(p2.getPlayer());
			}
			CardLayout cl = (CardLayout)mainPanel.getLayout();
			mainPanel.add(winnerPanel, "3");
			cl.show(mainPanel, "3");
			menubar.setVisible(false);
		}
		
	}
	class dicetimerTask extends TimerTask {
		int diceRound = 20;
		int countDiceRound = 0;
		public void run() {
			
			countDiceRound();
			if(diceRound <18) {
				currentdice_ID++;
				currentdice_ID %= 6;
				diceRound++;
			}else {
				diceRevealed();
			}
			repaint();

		}
		public void countDiceRound() {
			if(p1.getFlag() == true || p2.getFlag() == true) {
				if(countDiceRound ==0) {
					diceRound = 0;
					countDiceRound++;
				}else if(countDiceRound > 0 && countDiceRound < 19) {
					diceRound++;
					countDiceRound++;
				}else {
					diceRound = 20;
					countDiceRound = 0;
				}
			}else {
				diceRound = 20;
				countDiceRound = 0;
			}
		}
		public void diceRevealed() {
			if(p1.getFlag() == true || p2.getFlag() == true) {
				if(p1.getFlag() == true) {
					currentdice_ID = p1.getDice().getPoint()-1;
					p1.resetFlag();
					triggerMovePlayer1 = true;
				}else {
					currentdice_ID = p2.getDice().getPoint()-1;
					p2.resetFlag();
					triggerMovePlayer2 = true;
				}
			}
		}
	}
	
	private void initImage() {
		ImageIcon image1 = new ImageIcon("img/dice1.png");
		image1.setImage(image1.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
		dice[0] = image1.getImage();
		
		ImageIcon image2 = new ImageIcon("img/dice2.png");
		image2.setImage(image2.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
		dice[1] = image2.getImage();
		
		ImageIcon image3 = new ImageIcon("img/dice3.png");
		image3.setImage(image3.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
		dice[2] = image3.getImage();
		
		ImageIcon image4 = new ImageIcon("img/dice4.png");
		image4.setImage(image4.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
		dice[3] = image4.getImage();
		
		ImageIcon image5 = new ImageIcon("img/dice5.png");
		image5.setImage(image5.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
		dice[4] = image5.getImage();
		
		ImageIcon image6 = new ImageIcon("img/dice6.png");
		image6.setImage(image6.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
		dice[5] = image6.getImage();
	}
	
}
