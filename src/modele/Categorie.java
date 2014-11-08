package modele;

public class Categorie {
	int id_categorie;
	String label;
	String couleur;
	
	public Categorie() {
		super();
	}
	public Categorie(int id_categorie, String label, String couleur) {
		super();
		this.id_categorie = id_categorie;
		this.label = label;
		this.couleur = couleur;
	}
	public int getId_categorie() {
		return id_categorie;
	}
	public void setId_categorie(int id_categorie) {
		this.id_categorie = id_categorie;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getCouleur() {
		return couleur;
	}
	public void setCouleur(String couleur) {
		this.couleur = couleur;
	}
	@Override
	public String toString() {
		return "Categorie [id_categorie=" + id_categorie + ", label=" + label
				+ ", couleur=" + couleur + "]";
	}

	
}
