import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;

public class QuestionPanel extends JPanel{
	private String server, database, url, username, password;
	private Connection conn;
	private Statement stat;
	private ArrayList<Integer> usedNum;
	private String answer;
	private int ID;
	private JTextArea questionArea, answerArea;
	private JPanel answerP;
	private JRadioButton choiceA, choiceB, choiceC;
	private ButtonGroup group;
	private JButton submit, gotIt;
	private Player player;
	private JLabel title, backgroundLabel, answerTitle;
	private PlayerPanel p1, p2;
	
	public QuestionPanel(Player player) throws SQLException{
		this.usedNum=new ArrayList<Integer>();
		this.player = player;
		this.questionArea=new JTextArea(5, 5);
		this.answerArea=new JTextArea(3, 3);
		createQuestionPanel();
	}
	
	private boolean findQuestionNumber(int i) {
		for(Integer num:this.usedNum) {
			if(i==num) {
				return true;
			}
		}
		return false;
	}

	public void setQuestion() throws SQLException{
		this.server="jdbc:mysql://140.119.19.73:9306/";
		this.database="TG01";
		this.url=server + database;
		this.username="TG01";
		this.password="yEuBAm";  
		this.conn=DriverManager.getConnection(url, username, password);
		this.stat=conn.createStatement();
		PreparedStatement q=conn.prepareStatement("SELECT * from Questions WHERE ID=?");
		this.ID=(int)(Math.random()*100)+1;
		if(this.usedNum.size()==0) {
			this.usedNum.add(this.ID);
		}else {
			while(findQuestionNumber(this.ID)) {
				this.ID=(int)(Math.random()*100)+1;
			}
			this.usedNum.add(this.ID);
		}
		q.setInt(1, this.ID);
		ResultSet rs=q.executeQuery();
		rs.next();
		String qs = rs.getString("Quiz")+"\n\n";
		String as="A: "+rs.getString("A")+"\n\n";
		String bs="B: "+rs.getString("B")+"\n\n";
		String cs="C: "+rs.getString("C")+"\n\n";
		this.answer=rs.getString("Answer");
		String text=qs+as+bs+cs;
		this.questionArea.setText(text);
		this.questionArea.setFont(new Font("Sarif", Font.BOLD, 20));
		this.questionArea.setLineWrap(true);
		rs.close();
		q.close();
	}

	public void checkAnswer() throws SQLException, InterruptedException {
		Font font = new Font("Dialog", Font.PLAIN, 25);
		answerArea.setFont(font);
		if(getSelectedButtonText(group).equals(this.answer)){	
			answerArea.setText("Yeah, you are corrrect!\n\nCongratulations! You can get $100!");
			player.rightAnswer();			
			///Thread.sleep(5000);
		}else {
			PreparedStatement b=conn.prepareStatement("SELECT * from Questions WHERE ID=?");
			b.setInt(1, this.ID);
			ResultSet rs=b.executeQuery();
			rs.next();
			answerArea.setText("Sorry, you are wrong!\n\nThe answer is "+this.answer+": "+rs.getString(this.answer)+"\n\nYou lose $50.");
			player.wrongAnswer();
			///Thread.sleep(5000);
			rs.close();
			b.close();
		}
		group.clearSelection();
		conn.close();
	}
	
	public String getSelectedButtonText(ButtonGroup buttonGroup) {
		for (Enumeration<AbstractButton> buttons = buttonGroup.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();
            if (button.isSelected()) {
                return button.getText();
            }
        }
        return null;
	}
	
	public void createQuestionPanel() {
		try {
			setQuestion();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		questionArea.setBackground(new Color(244,237,227));
		this.title=new JLabel("Question");
		this.title.setFont(new Font("Sarif", Font.BOLD, 20));
		this.backgroundLabel = new JLabel("Question");
		ImageIcon icon = new ImageIcon("img/q&aBackground.png");
		icon.setImage(icon.getImage().getScaledInstance(500, 500, Image.SCALE_DEFAULT));
		this.backgroundLabel = new JLabel(icon);
		this.choiceA=new JRadioButton("A");
		this.choiceB=new JRadioButton("B");
		this.choiceC=new JRadioButton("C");
		Font f = new Font("Sarif", Font.BOLD, 18);
		choiceA.setFont(f);
		choiceB.setFont(f);
		choiceC.setFont(f);
		choiceA.setBackground(new Color(244,237,227));
		choiceB.setBackground(new Color(244,237,227));
		choiceC.setBackground(new Color(244,237,227));
		this.group = new ButtonGroup();
		this.group.add(choiceA);
		this.group.add(choiceB);
		this.group.add(choiceC);
		submit = new JButton("Submit");
		submit.setFont(new Font("Sarif", Font.BOLD, 15));
		addSubmitButtonListener();
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 0;
		c.insets = new Insets(62, 0, 428, 400);
		add(title,c);
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 3;
		c.insets = new Insets(285, 75, 0, 100);
		add(choiceA,c);
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 3;
		c.insets = new Insets(285, 175, 0, 100);
		add(choiceB,c);
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 3;
		c.insets = new Insets(285, 275, 0, 100);
		add(choiceC,c);
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		c.insets = new Insets(400, 212, 0, 0);
		add(submit,c);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 20;
		c.gridheight = 10;
		c.insets = new Insets(100, 0, 190, 0);
		add(questionArea,c);
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 0;
		c.insets = new Insets(0, 0, 0, 0);
		add(backgroundLabel,c);
	}
	
	public void addSubmitButtonListener(){
		class buttonListener implements ActionListener{
			public void actionPerformed(ActionEvent e) {
				try {
					checkAnswer();
					CardLayout cl = (CardLayout)player.getMainPanel().getLayout();
					if(player.getPlayerID() == 1) {
						cl.show(player.getMainPanel(), "6");
					}
					else if(player.getPlayerID() == 2) {
						cl.show(player.getMainPanel(), "8");
					}
					///Thread.sleep(3000);
					///setVisible(false);
				} catch (SQLException | InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
		ActionListener l=new buttonListener();
		this.submit.addActionListener(l);
	}
	
	public JPanel createAnswerP() {
		this.answerP=new JPanel();
		this.answerTitle = new JLabel("Answer:");
		Font font = new Font("Dialog", Font.BOLD, 35);
		this.answerTitle.setFont(font);
		this.gotIt = new JButton("Got it!");
		this.gotIt.setFont(new Font("Dialog", Font.BOLD, 20));
		this.answerP.setLayout(new BorderLayout());
		this.answerP.add(answerTitle, BorderLayout.NORTH);
		this.answerP.add(answerArea, BorderLayout.CENTER);
		//class ClickListener implements ActionListener{
			//public void actionPerformed(ActionEvent e) {	
				//CardLayout cl = (CardLayout)player.getMainPanel().getLayout();
				//cl.show(player.getMainPanel(), "2");
		//}
	//ActionListener l=new ClickListener();
	//this.gotIt.addActionListener(l);
	//this.answerP.add(gotIt, BorderLayout.SOUTH);
		return this.answerP;
	}
	
	public JPanel getAnswerP() {
		return this.answerP;
	}
	public JButton getGotIt() {
		return this.gotIt;
	}
}
