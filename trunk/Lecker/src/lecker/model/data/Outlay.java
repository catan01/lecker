package lecker.model.data;



public class Outlay implements Comparable<Outlay>{
	private final String NAME;
	
	
	
	public Outlay(String name) {
		this.NAME = name;
	}
	
	
	
	public String getName() {
		synchronized (NAME) {
			return this.NAME;
		}
	}
	
	@Override
	public boolean equals(Object obj) {
		try {
			return ((Outlay) obj).getName().equals(this.NAME);
		} catch (Exception exc) {
			return false;
		}
	}



	@Override
	public int compareTo(Outlay o) {
		return -(((char) o.getName().charAt(0)) - ((char) this.getName().charAt(0)));
	}
}
