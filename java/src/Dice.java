
public class Dice {
	private int point = 1;
	public void rollDice() {
		this.point = (int)(Math.random()*6)+1;
	}
	public int getPoint() {
		return this.point;
	}
}
