package lecker.model.data;

public class Kategorie {
	private final String NAME;
	
	
	
	public Kategorie(String name) {
		this.NAME = name;
	}
	
	
	
	public String getName() {
		return this.NAME;
	}
	
	@Override
	public String toString() {
		return this.NAME;
	}
}
