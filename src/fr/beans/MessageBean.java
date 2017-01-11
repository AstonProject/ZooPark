package fr.beans;

public class MessageBean {
	private int id;
	private String title;
	private String content;
	private String turn;
	private int player_id;
	
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
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getTurn() {
		return turn;
	}
	public void setTurn(String turn) {
		this.turn = turn;
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
		builder.append("MessageBean [id=")
			.append(id)
			.append(", title=")
			.append(title)
			.append(", content=")
			.append(content)
			.append(", turn=")
			.append(turn)
			.append(", player_id=")
			.append(player_id)
			.append("]");
		return builder.toString();
	}
}
