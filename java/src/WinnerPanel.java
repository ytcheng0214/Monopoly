import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class WinnerPanel extends JPanel{
	private Player winner;
	private JLabel backgroundLabel, winnerLabel;
	private JPanel winnerPanel, infoPanel;
	private JButton exitBtn;
	
	public WinnerPanel(Player winner) {
		this.winner = winner;
		setBounds(0, 0, 1090, 690);
		setBackground(new Color(201, 231, 255));
		createComp();
		setVisible(true);
	}
	public void createComp() {
		this.backgroundLabel = new JLabel();
		this.winnerLabel = new JLabel();
		this.winnerLabel.setFont(new Font("Sarif", Font.BOLD, 50));
		this.winnerLabel.setText("The winner is "+winner.getName()+". Congratulations!");
		ImageIcon icon = new ImageIcon("img/winnerstage.png");
		icon.setImage(icon.getImage().getScaledInstance(1090, 690, Image.SCALE_DEFAULT));//1025, 649
		this.backgroundLabel = new JLabel(icon);
		this.exitBtn = new JButton("Exit");
		this.exitBtn.setFont(new Font("Sarif", Font.BOLD, 40));
		
		
		
		this.winnerPanel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.CENTER;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 0;
		c.gridheight = 100;
		c.insets = new Insets(0, 0, 500, 0);
		this.winnerPanel.add(winnerLabel,c);
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.insets = new Insets(50, 50, 0, 0);
		this.winnerPanel.add(exitBtn,c);
		//c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 0;
		this.winnerPanel.add(backgroundLabel,c);

		
		//Ä¹®a¥x¤W
		
		this.infoPanel = new JPanel();
		//
		add(winnerPanel);
		add(infoPanel);
		
		class ExitListener implements ActionListener{

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.exit(0);
			}
			
		}
		ExitListener exitListener = new ExitListener();
		exitBtn.addActionListener(exitListener);
	}
	
}
