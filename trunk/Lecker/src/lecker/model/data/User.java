package lecker.model.data;

public class User {
	private final String NAME;
	
	
	
	public User(String name) {
		this.NAME = name;
	}
	
	
	
	public String getName() {
		synchronized(this.NAME) {
			return this.NAME;
		}
	}
}
