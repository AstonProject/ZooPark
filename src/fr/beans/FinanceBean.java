package fr.beans;

public class FinanceBean {

	private int id;
	private int player_id;
	private String type_action; // achat, vente ou emprunt
	private long somme; // positive ou negative suivant type_action
	private String libelle; // enclos ou animal
	private String turn; // tour de jeu
	private int payMonthly; // mensualite de pret a rembourser
	private int animals_number;
	private int enclosure_id;
	
	public FinanceBean() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPlayer_id() {
		return player_id;
	}

	public void setPlayer_id(int player_id) {
		this.player_id = player_id;
	}

	public String getType_action() {
		return type_action;
	}

	public void setType_action(String type_action) {
		this.type_action = type_action;
	}

	public long getSomme() {
		return somme;
	}

	public void setSomme(long somme) {
		this.somme = somme;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public String getTurn() {
		return turn;
	}

	public void setTurn(String turn) {
		this.turn = turn;
	}

	public int getPayMonthly() {
		return payMonthly;
	}

	public void setPayMonthly(int payMonthly) {
		this.payMonthly = payMonthly;
	}

	public int getAnimals_number() {
		return animals_number;
	}

	public void setAnimals_number(int animals_number) {
		this.animals_number = animals_number;
	}

	public int getEnclosure_id() {
		return enclosure_id;
	}

	public void setEnclosure_id(int enclosure_id) {
		this.enclosure_id = enclosure_id;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("FinanceBean [id=");
		builder.append(id);
		builder.append(", player_id=");
		builder.append(player_id);
		builder.append(", type_action=");
		builder.append(type_action);
		builder.append(", somme=");
		builder.append(somme);
		builder.append(", libelle=");
		builder.append(libelle);
		builder.append(", turn=");
		builder.append(turn);
		builder.append(", payMonthly=");
		builder.append(payMonthly);
		builder.append(", animals_number=");
		builder.append(animals_number);
		builder.append(", enclosure_id=");
		builder.append(enclosure_id);
		builder.append("]");
		return builder.toString();
	}
	
}
