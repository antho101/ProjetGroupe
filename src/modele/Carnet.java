package modele;

public class Carnet {
	int id_carnet;
	String titre;
	int id_user;
	
	public Carnet() {
		super();
	}
	public Carnet(int id_carnet, String titre, int id_user) {
		super();
		this.id_carnet = id_carnet;
		this.titre = titre;
		this.id_user = id_user;
	}
	public int getId_carnet() {
		return id_carnet;
	}
	public void setId_carnet(int id_carnet) {
		this.id_carnet = id_carnet;
	}
	public String getTitre() {
		return titre;
	}
	public void setTitre(String titre) {
		this.titre = titre;
	}
	public int getId_user() {
		return id_user;
	}
	public void setId_user(int id_user) {
		this.id_user = id_user;
	}
	
}
