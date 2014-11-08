package modele;

import java.sql.Date;

public class Note {
	int id_note;
	String titre;
	String contenu;
	Date date_note;
	int id_carnet;
	int id_categorie;
	
	
	public Note(int id_note, String contenu, Date date_note, int id_carnet,
			int id_categorie) {
		super();
		this.id_note = id_note;
		this.contenu = contenu;
		this.date_note = date_note;
		this.id_carnet = id_carnet;
		this.id_categorie = id_categorie;
	}
	public Note(int id_note) {
		super();
		this.id_note = id_note;
	}
	public Note() {
		super();
	}
	public Note(int id_note, String titre, String contenu, Date date_note,
			int id_carnet, int id_categorie) {
		super();
		this.id_note = id_note;
		this.titre = titre;
		this.contenu = contenu;
		this.date_note = date_note;
		this.id_carnet = id_carnet;
		this.id_categorie = id_categorie;
	}
	public int getId_note() {
		return id_note;
	}
	public void setId_note(int id_note) {
		this.id_note = id_note;
	}
	public String getTitre() {
		return titre;
	}
	public void setTitre(String titre) {
		this.titre = titre;
	}
	public String getContenu() {
		return contenu;
	}
	public void setContenu(String contenu) {
		this.contenu = contenu;
	}
	public Date getDate_note() {
		return date_note;
	}
	public void setDate_note(Date date_note) {
		this.date_note = date_note;
	}
	public int getId_carnet() {
		return id_carnet;
	}
	public void setId_carnet(int id_carnet) {
		this.id_carnet = id_carnet;
	}
	public int getId_categorie() {
		return id_categorie;
	}
	public void setId_categorie(int id_categorie) {
		this.id_categorie = id_categorie;
	}
	
}
