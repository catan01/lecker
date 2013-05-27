package lecker.model.data;



public class Outlay {
	private final String NAME;
	
	
	
	public Outlay(String name) {
		this.NAME = name;
	}
	
	
	
	public String getName() {
		synchronized (NAME) {
			return this.NAME;
		}
	}
}
