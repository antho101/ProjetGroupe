package modele;

public class User {
	int id_user;
	String pseudo;
	String email;
	String password;
	
	public User() {
		super();
	}
	
	public User(int id_user) {
		super();
		this.id_user = id_user;
	}


	public User( String pseudo,String email,
			String password) {
		super();
		this.pseudo = pseudo;
		this.email = email;
		this.password = password;
	}
	public User(int id_user, String pseudo,String email,
			String password) {
		super();
		this.id_user = id_user;
		this.pseudo = pseudo;
		this.email = email;
		this.password = password;
	}
	public int getId_user() {
		return id_user;
	}
	public void setId_user(int id_user) {
		this.id_user = id_user;
	}
	public String getPseudo() {
		return pseudo;
	}
	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "User [id_user=" + id_user + ", pseudo=" + pseudo + ", email="
				+ email + ", password=" + password + "]";
	}
	
}
