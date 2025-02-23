import java.awt.CardLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class GameStart {
	private static final int FRAME_WIDTH = 1100;
	private static final int FRAME_HEIGHT = 800;
	private static WinnerPanel winnerPanel = new WinnerPanel(new Player(0));
	
	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub
		
			JFrame mainFrame = new JFrame("Welcome to Monopoly Game");
			JPanel mainPanel = new JPanel();
			Player player1 = new Player(1);
			Player player2 = new Player(2);
			PlayerPanel p1 = new PlayerPanel(new ImageIcon("img/fire.png"), player1);
			PlayerPanel p2 = new PlayerPanel(new ImageIcon("img/noface.png"), player2);
			player1.countPlus();
			p1.rollDiceBtnOperation(player2, p2);
			p2.rollDiceBtnOperation(player1, p1);
			
			JMenuBar menubar = new JMenuBar();
			JMenu menu = new JMenu("Menu");
			JMenuItem logOutMenuItem = new JMenuItem("Log out");
			JMenuItem gameFinishedMenuItem = new JMenuItem("Game Finished");
			menu.add(logOutMenuItem);
			menu.add(gameFinishedMenuItem);
			menubar.add(menu);
			
			LoginPanel loginPanel = new LoginPanel(p1, p2);
			GamePanel gamePanel = new GamePanel(p1, p2, mainPanel, menubar);
			gamePanel.setOpaque(true);
			gamePanel.setBounds(0, 0, 1090, 690);
			gamePanel.setOpaque(true);
			CardLayout cl = new CardLayout();
			mainPanel.setLayout(cl);
			mainPanel.add(loginPanel, "1");
			mainPanel.add(gamePanel, "2");
			mainPanel.add(loginPanel.createInstructP(), "4");
			mainPanel.add(p1.getQuestionP(), "5");
			mainPanel.add(p1.getQuestionP().createAnswerP(), "6");
			mainPanel.add(p2.getQuestionP(), "7");
			mainPanel.add(p2.getQuestionP().createAnswerP(), "8");
			cl.show(mainPanel, "1");
			
			
			p1.setMainPanel(mainPanel);
			p2.setMainPanel(mainPanel);
			player1.setMainPanel(mainPanel);
			player2.setMainPanel(mainPanel);
			loginPanel.addButtonListener(mainPanel, menubar);
			
			class LogOutListener implements ActionListener{

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					cl.show(mainPanel, "1");
					menubar.setVisible(false);
				}
				
			}
			
			class GameFinishedListener implements ActionListener{

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					if(player1.calcAsset()>player2.calcAsset()) {
						winnerPanel = new WinnerPanel(player1);
					}
					else if(player1.calcAsset()<player2.calcAsset()) {
						winnerPanel= new WinnerPanel(player2);
					}
					else {
						Player noBody = new Player(0);
						noBody.setName("No Body");
						winnerPanel = new WinnerPanel(noBody);
					}
					mainPanel.add(winnerPanel, "3");
					cl.show(mainPanel, "3");
					menubar.setVisible(false);
				}
				
			}
			LogOutListener logOutListener = new LogOutListener();
			logOutMenuItem.addActionListener(logOutListener);
			GameFinishedListener gameFinishedListener = new GameFinishedListener();
			gameFinishedMenuItem.addActionListener(gameFinishedListener);

			
			mainFrame.setJMenuBar(menubar);
			menubar.setVisible(false);
			mainFrame.add(mainPanel);
			mainFrame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
			mainFrame.setVisible(true);
			mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			///while(true) {
				///whoseTurn(p1, player1);
				///whoseTurn(p2, player2);
			///}
		}
/*
	public static void whoseTurn(PlayerPanel p, Player player) {
		if(player.getCount()%2==1) {
			p.setYourTurn("Roll the Dice");
		}
		else {
			p.setYourTurn("It's not your turn");
		}
	}
	*/
}
