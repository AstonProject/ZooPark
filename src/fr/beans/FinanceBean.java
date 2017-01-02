package fr.beans;

import java.util.Date;

public class FinanceBean {

	private int id;
	private int player_id;
	private String type_action; // ajout ou retrait
	private int somme; // positive ou négative suivant ajout ou retrait
	private String libelle; // enclos ou animal
	private Date date;
	private int specie_id;
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

	public int getSomme() {
		return somme;
	}

	public void setSomme(int somme) {
		this.somme = somme;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getSpecie_id() {
		return specie_id;
	}

	public void setSpecie_id(int specie_id) {
		this.specie_id = specie_id;
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
		builder.append(", date=");
		builder.append(date);
		builder.append(", specie_id=");
		builder.append(specie_id);
		builder.append(", animals_number=");
		builder.append(animals_number);
		builder.append(", enclosure_id=");
		builder.append(enclosure_id);
		builder.append("]");
		return builder.toString();
	}
	
}
