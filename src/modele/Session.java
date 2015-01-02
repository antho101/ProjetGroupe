package modele;

import java.io.Serializable;

public class Session  implements Serializable{

	protected static UserDB user;

	public static UserDB getUser() {
		return user;
	}

	public static void setUser(UserDB user) {
		Session.user = user;
	}
}
