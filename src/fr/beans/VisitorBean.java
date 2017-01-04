package fr.beans;

public class VisitorBean {

	private int id;
	private int satisfaction_gauge;
	private int coins;
	private int player_id;
	
	public VisitorBean() {
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getSatisfaction_gauge() {
		return satisfaction_gauge;
	}
	
	public void setSatisfaction_gauge(int satisfaction_gauge) {
		this.satisfaction_gauge = satisfaction_gauge;
	}
	
	public int getCoins() {
		return coins;
	}
	
	public void setCoins(int coins) {
		this.coins = coins;
	}
	
	public int getPlayer_id() {
		return player_id;
	}
	
	public void setPlayer_id(int player_id) {
		this.player_id = player_id;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("VisitorBean [id=");
		builder.append(id);
		builder.append(", satisfaction_gauge=");
		builder.append(satisfaction_gauge);
		builder.append(", coins=");
		builder.append(coins);
		builder.append(", player_id=");
		builder.append(player_id);
		builder.append("]");
		return builder.toString();
	}
	
}
