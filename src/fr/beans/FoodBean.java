package fr.beans;

public class FoodBean {

	private int id;
	private String title;
	private int quantity;
	private int player_id;
	
	public FoodBean() {
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public int getQuantity() {
		return quantity;
	}
	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
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
		builder.append(", title=");
		builder.append(title);
		builder.append(", quantity=");
		builder.append(quantity);
		builder.append(", player_id=");
		builder.append(player_id);
		builder.append("]");
		return builder.toString();
	}
	
}
