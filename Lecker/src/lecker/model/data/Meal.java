package lecker.model.data;



import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import lecker.model.data.comments.Commentable;



/**
 * One complete meal.
 * 
 * @author LWagner
 *
 */
public class Meal extends Commentable {
	private ArrayList<Calendar> dates;
	private Kategorie kategorie;
	private ArrayList<Label> labels;
	private String name;
	private Integer price;
	
	
	
	public Meal(String name, int price, Kategorie kategorie, Calendar[] dates, Label[] labels) {
		this.dates = new ArrayList<Calendar>(Arrays.asList(dates));
		this.kategorie = kategorie;
		this.labels = new ArrayList<Label>(Arrays.asList(labels));
		this.name = name;
		this.price = price;
	}
	

	
	public Calendar[] getDates() {
		synchronized (dates) {
			return this.dates.toArray(new Calendar[0]);
		}
	}
	
	public Kategorie getKategorie() {
		synchronized(this.kategorie) {
			return this.kategorie;
		}
	}
	
	public Label[] getLabels() {
		synchronized(this.labels) {
			return this.labels.toArray(new Label[0]);
		}
	}
	
	public String getName() {
		synchronized (this.name) {
			return this.name;
		}
	}
	
	public int getPrice() {
		synchronized(this.price) {
			return this.price;
		}
	}
	
	
	
	public void addDate(Calendar date) {
		synchronized (this.dates) {
			this.dates.add(date);
		}
	}
	
	public void removeDate(Calendar date) {
		synchronized (this.dates) {
			this.dates.remove(date);
		}
	}
	
	public void addLabel(Label label) {
		synchronized (this.labels) {
			this.labels.add(label);
		}
	}
	
	public void removeLabel(Label label) {
		synchronized (this.labels) {
			this.labels.remove(label);
		}
	}
}
