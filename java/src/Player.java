import javax.swing.JPanel;

public class Player {
	private String name;
	private double cash, housePrice;
	private int house, position, count, lastPosition;
	// private String stposition;
	private JPanel mainP;
	private int playerID;
	
	public Player(int playerID) {
		this.cash = 0;
		this.house = 0;
		this.position=1;
		// this.stposition="Start";
		this.housePrice = 80;
		this.count = 0;
		this.lastPosition = 1;
		this.playerID = playerID;
	}
	
	public void countMinus() {
		this.count--;
	}
	public void countPlus() {
		this.count++;
	}
	public int getCount() {
		return this.count;
	}
	//public void setStPosition(String word) {
		// this.count ++;
		// this.stposition=word;
	// }
	public String getName() {
		return this.name;
	}
	public int getPlayerID() {
		return this.playerID;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getCash() {
		return this.cash;
	}
	public void setCash(double amount){
		this.cash += amount;
	}
	public double getHousePrice() {
		return this.housePrice;
	}
	public int getHouse() {
		return this.house;
	}
	public void setHouse(int num) {
		this.house+=num;
	}
	public int getPosition() {
		return this.position;
	}
	public int getLastPosition() {
		return this.lastPosition;
	}
	public void setPosition(int num) {
		this.lastPosition = this.position;
		this.position+=num;
	}
	public void rightAnswer() {
		this.cash += 100;
	}
	public void wrongAnswer() {
		this.cash -= 50;
	}
	public boolean buyHouse(int num) {
		if(this.cash >= this.housePrice*num) {
			setHouse(num);
			setCash(-num*this.housePrice);
			return true;
		}
		else {
			return false;
		}
	}
	public boolean sellHouse(int num) {
		if(this.house >= num) {
			setCash(this.housePrice*num);
			setHouse(-num);
			return true;
		}
		else {
			return false;
		}
	}

	public String gamble() {
		int num = (int)(Math.random()*100)+1;
		String info = "";
		if(num <=30) {
			this.housePrice *= 2;
			info += String.format("Now the housing market is booming, housing prices have doubled to %.2f!", this.housePrice);
		}
		else if(num>30 && num<=70) {
			setHouse(1);
			info += "Inherit one house.";
		}
		else if(num>70 && num<=90) {
			this.housePrice /= 2;
			info += String.format("Now that the housing policy is implemented, housing prices have been cut to %.2f!", this.housePrice);
		}
		else {
			setHouse(-1);
			info += "Due to earthquake, you lost one house you owned.";
		}
		return info;
	}
	public double calcAsset(){
		double asset = this.cash + this.house*this.housePrice;
		return asset;
	}
	public String getInfo() {
		String info = "";
		info += String.format("%-17s%-2s\n", "Name:",this.name);
		info += String.format("%-18s%-2.2f\n", "Cash:",this.cash);
		info += String.format("%-17s%-3s\n", "Position:",this.position);
		info += String.format("%-17s%-2d\n", "House:",this.house);
		info += String.format("%-14s%-2.2f", "HousePrice:",this.housePrice);
		return info;
	}
	public void setMainPanel(JPanel mainP) {
		this.mainP=mainP;
	}
	public JPanel getMainPanel() {
		return this.mainP;
	}
}
