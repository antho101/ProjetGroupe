package modele;

public class Session {

	protected static UserDB user;

	public static UserDB getUser() {
		return user;
	}

	public static void setUser(UserDB user) {
		Session.user = user;
	}
}
