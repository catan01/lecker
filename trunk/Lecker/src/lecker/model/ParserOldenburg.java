package lecker.model;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import lecker.model.data.Kategorie;
import lecker.model.data.Label;
import lecker.model.data.Outlay;
import lecker.model.db.AddMealDBStatement;
import lecker.model.db.AddMealDateDBStatement;
import lecker.model.db.AddMealLabelDBStatement;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class ParserOldenburg {
	
	private static final String SW_URL = "http://sw-ol.capuno-software.de/output/speiseplan.php?sc=5&ot=21&osr=";
	
	private static final String CANTEEN_A_ID = "al-pasta";
	private static final String CANTEEN_B_ID = "uweg_auswahl";
	private static final String CANTEEN_CULINARIUM_ID = "culi";
	private static final String CANTEEN_WECHLOY_ID = "wechloy";
	private static final String CANTEEN_FH_ID = "fhol";
	
	private static final String CANTEEN_A = "A";
	private static final String CANTEEN_B = "B";
	private static final String CANTEEN_CULINARIUM = "Culinarium";
	private static final String CANTEEN_WECHLOY = "Wechloy";
	private static final String CANTEEN_FH = "Ofener Straße";
	
	private static final String CANTEEN_A_ALTERNATIVE = "Alternativ";
	private static final int CANTEEN_A_ALTERNATIVE_PRICE = 140;
	private static final String CANTEEN_A_PASTA = "Pasta";
	private static final int CANTEEN_A_PASTA_PRICE = 200;
	
	private static final String MAIN_MEAL = "Hauptgericht";
	
	private static final String EXTRA_MEAL_1 = "Schälchen";
	private static final String EXTRA_MEAL_2 = "Beilage";
	private static final int EXTRA_MEAL_PRICE = 35;
	
	
	private static final String MEAL_ENTRY = "speise_eintrag";
	private static final String MEAL_NAME = "speise_name";
	private static final String MEAL_PRICE = "speise_preis";
	
	private static final String INGREDIENT_V = "vegetarisch";
	private static final String INGREDIENT_VEGAN = "V+";
	private static final String INGREDIENT_BEEF = "Rindfleisch";
	private static final String INGREDIENT_PORK = "Schweinefleisch";
	private static final String INGREDIENT_FISH = "F";
	private static final String INGREDIENT_CHICKEN = "G";
	private static final String INGREDIENT_LAMB = "L";
	private static final String INGREDIENT_FARBSTOFF = "1";
	private static final String INGREDIENT_KONSERVIERUNGSSTOFF = "2";
	private static final String INGREDIENT_ANTIOXIDATIONSMITTEL = "3";
	private static final String INGREDIENT_GESCHMACKVERSTÄRKER = "4";
	private static final String INGREDIENT_GESCHWEFELT = "5";
	private static final String INGREDIENT_GESCHWAERZT = "6";
	private static final String INGREDIENT_GEWACHST = "7";
	private static final String INGREDIENT_SUESSUNGSMITTEL = "8";
	private static final String INGREDIENT_PHENYLALANINQUELLE = "9";
	private static final String INGREDIENT_PHOSPHAT = "10";
	private static final String INGREDIENT_ALKOHOL = "11";
	
	private static final String LABEL_V = "Vegetarisch";
	private static final String LABEL_VEGAN = "Vegan";
	private static final String LABEL_BEEF = "Rind";
	private static final String LABEL_PORK = "Schwein";
	private static final String LABEL_FISH = "Fisch";
	private static final String LABEL_CHICKEN = "Geflügel";
	private static final String LABEL_LAMB = "Lamm";
	private static final String LABEL_FARBSTOFF = "Farbstoff";
	private static final String LABEL_KONSERVIERUNGSSTOFF = "Konservierungsstoff";
	private static final String LABEL_ANTIOXIDATIONSMITTEL = "Antioxidationsmittel";
	private static final String LABEL_GESCHMACKVERSTÄRKER = "Geschmacksverstärker";
	private static final String LABEL_GESCHWEFELT = "geschwefelt";
	private static final String LABEL_GESCHWAERZT = "geschwärzt";
	private static final String LABEL_GEWACHST = "gewachst";
	private static final String LABEL_SUESSUNGSMITTEL = "Süßungsmittel";
	private static final String LABEL_PHENYLALANINQUELLE = "Phenylalaninquelle";
	private static final String LABEL_PHOSPHAT = "Phosphat";
	private static final String LABEL_ALKOHOL = "Alkohol";
	
	private final HashMap<String, String> labelMap = new HashMap<String, String>();
	
	private String name = "";
	private int preis = 0;
	private String kategorie = "";
	private Calendar datum = Calendar.getInstance();
	private String ausgabe = "";
	private ArrayList<String> labels = new ArrayList<String>(3);

	public ParserOldenburg() {
		
		labelMap.put(INGREDIENT_V, LABEL_V);
		labelMap.put(INGREDIENT_VEGAN, LABEL_VEGAN);
		labelMap.put(INGREDIENT_BEEF, LABEL_BEEF);
		labelMap.put(INGREDIENT_PORK, LABEL_PORK);
		labelMap.put(INGREDIENT_FISH, LABEL_FISH);
		labelMap.put(INGREDIENT_CHICKEN, LABEL_CHICKEN);
		labelMap.put(INGREDIENT_LAMB, LABEL_LAMB);
		labelMap.put(INGREDIENT_ALKOHOL, LABEL_ALKOHOL);
		labelMap.put(INGREDIENT_ANTIOXIDATIONSMITTEL, LABEL_ANTIOXIDATIONSMITTEL);
		labelMap.put(INGREDIENT_FARBSTOFF, LABEL_FARBSTOFF);
		labelMap.put(INGREDIENT_KONSERVIERUNGSSTOFF, LABEL_KONSERVIERUNGSSTOFF);
		labelMap.put(INGREDIENT_GESCHMACKVERSTÄRKER, LABEL_GESCHMACKVERSTÄRKER);
		labelMap.put(INGREDIENT_GESCHWAERZT, LABEL_GESCHWAERZT);
		labelMap.put(INGREDIENT_GEWACHST, LABEL_GEWACHST);
		labelMap.put(INGREDIENT_GESCHWEFELT, LABEL_GESCHWEFELT);
		labelMap.put(INGREDIENT_SUESSUNGSMITTEL, LABEL_SUESSUNGSMITTEL);
		labelMap.put(INGREDIENT_PHENYLALANINQUELLE, LABEL_PHENYLALANINQUELLE);
		labelMap.put(INGREDIENT_PHOSPHAT, LABEL_PHOSPHAT);
		
	}
	
	public void parse(int day) {
		try {
			datum = Calendar.getInstance();
			datum.add(Calendar.DAY_OF_MONTH, day);
			
			URL url = new URL(SW_URL + day);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			
			//read String
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String buffer;
			String page = "";
			
			while((buffer = reader.readLine()) != null) {
				buffer = buffer.trim();
				page += buffer + "\n";
			}
			
			reader.close();
			connection.disconnect();
			
			//clean html
			page = addHTMLTags(page);
			page = closeImgTags(page);
			page = replaceCharRefs(page);
			
			//parse String
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			dbf.setValidating(false);
			DocumentBuilder builder = dbf.newDocumentBuilder();
			
			StringReader sr = new StringReader(page);
			Document document = builder.parse(new InputSource(sr));

			//search for meals
			Node canteen_a = getCanteenDiv(document, CANTEEN_A_ID);
			ausgabe = CANTEEN_A;
			searchMeals(canteen_a);
			
			Node canteen_b = getCanteenDiv(document, CANTEEN_B_ID);
			ausgabe = CANTEEN_B;
			searchMeals(canteen_b);
			
			Node canteen_c = getCanteenDiv(document, CANTEEN_CULINARIUM_ID);
			ausgabe = CANTEEN_CULINARIUM;
			//use special method for culinarium
			searchMealsCulinarium(canteen_c);
			
			Node canteen_w = getCanteenDiv(document, CANTEEN_WECHLOY_ID);
			ausgabe = CANTEEN_WECHLOY;
			searchMeals(canteen_w);
			
			Node canteen_fh = getCanteenDiv(document, CANTEEN_FH_ID);
			ausgabe = CANTEEN_FH;
			searchMeals(canteen_fh);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private String addHTMLTags(String str) {
		return ("<html>" + str + "</html>");
	}
	
	/**
	 * Searches the given string for img-tags and adds an /img-tag at the end
	 * 
	 * @param str the string with unclosed img-tags
	 * @return the string with closed img-tags
	 */
	private String closeImgTags(String str) {
		int i = -1;
		//search for img-tags and save index in i
		while((i = str.indexOf("<img", i + 1)) != -1) {
			//add an </img> between img-tag and rest of the string
			str = str.substring(0, str.indexOf(">", i) + 1) + "</img>" + str.substring(str.indexOf(">", i) + 1);
		}
		return str;
	}
	
	/**
	 * Replaces html-specific character references with the associated characters
	 * 
	 * @param str the string with character-references
	 * @return the string without character-references
	 */
	private String replaceCharRefs(String str) {
		str = str.replaceAll("&euro;", "");
		str = str.replaceAll("&auml;", "ä");
		str = str.replaceAll("&Auml;", "Ä");
		str = str.replaceAll("&ouml;", "ö");
		str = str.replaceAll("&Ouml;", "Ö");
		str = str.replaceAll("&uuml;", "ü");
		str = str.replaceAll("&Uuml;", "Ü");
		str = str.replaceAll("&szlig;", "ß");
		return str;
	}
	
	private Node getCanteenDiv(Document doc, String canteen_id) {
		NodeList divs = doc.getElementsByTagName("div");
		for (int i = 0; i < divs.getLength(); i++) {
			Node div = divs.item(i);
			if(div.getAttributes().getNamedItem("id") != null) {
				//div has an id
				if(div.getAttributes().getNamedItem("id").getNodeValue().equals(canteen_id)) {
					return div;
				}
			}
		}
		return null;
	}
	
	private void searchMealsCulinarium(Node div) {
		NodeList list = div.getChildNodes();
		
		for(int i = 0; i < list.getLength(); i++) {
			Node node = list.item(i);

			if(node.getNodeName().equals("tr")) {
				Node main_meals = node.getNextSibling().getNextSibling();
				preis = 0;
				kategorie = MAIN_MEAL;
				
				searchMeals(main_meals);
				
				Node extra_meals = main_meals.getNextSibling().getNextSibling();
				
				preis = 0;
				kategorie = EXTRA_MEAL_2;
				searchMeals(extra_meals);
				break;
			}
			
			searchMealsCulinarium(node);
		}
	}
	
	private void searchMeals(Node div) {
		NodeList list = div.getChildNodes();
		
		for(int i = 0; i < list.getLength(); i++) {
			Node node = list.item(i);
			
			if(node.getNodeType() == Node.TEXT_NODE) {
				if(node.getParentNode().getNodeName().equals("strong")) {
					//strong-found: check for category
					if(node.getTextContent().contains(CANTEEN_A_ALTERNATIVE)) {
						preis = CANTEEN_A_ALTERNATIVE_PRICE;
						kategorie = CANTEEN_A_ALTERNATIVE;
					} else if(node.getTextContent().contains(CANTEEN_A_PASTA)) {
						preis = CANTEEN_A_PASTA_PRICE;
						kategorie = CANTEEN_A_PASTA;
					} else if(node.getTextContent().contains(EXTRA_MEAL_1)) {
						preis = EXTRA_MEAL_PRICE;
						kategorie = EXTRA_MEAL_2;
					} else if(node.getTextContent().contains(MAIN_MEAL)) {
						preis = 0;
						kategorie = MAIN_MEAL; 
					} else {
						preis = 0;
						kategorie = "";
					}
				}
			} else if(node.getNodeName().equals("div")) {
				if(node.getAttributes().getNamedItem("class") != null) {
					if(node.getAttributes().getNamedItem("class").getNodeValue().equals(MEAL_ENTRY)) {
						
						//meal_entry found
						name = "";
						labels.clear();
						
						handleMealEntry(node);
						parseIngredients();

						name = name.replace("\n", " ");
						name = name.trim();
						
						//add meal to db
						addMealToDB();
						
					}
				}
			}
			
			searchMeals(list.item(i));
		}

	}
	
	private void handleMealEntry(Node node) {
		NodeList list = node.getChildNodes();

		for(int i = 0; i < list.getLength(); i++) {
			Node child = list.item(i);
			
			if(child.getNodeType() == Node.TEXT_NODE) {
				//text found
				Node parent = child.getParentNode();
				if(parent.getNodeName().equals("div")) {
					//text following a div -> no price info
					if(kategorie.equals(MAIN_MEAL)) {
						//kein preis und Hauptgericht
						preis = 0;
					}
					name = child.getTextContent();
				} else if(parent.getNodeName().equals("td")) {
					//text following a td -> check if name or price
					Node classAttribute = parent.getAttributes().getNamedItem("class");
					if(classAttribute != null) {
						if(classAttribute.getNodeValue().equals(MEAL_NAME)) {
							name = child.getTextContent();
						} else if(classAttribute.getNodeValue().equals(MEAL_PRICE)) {
							preis = Integer.valueOf(child.getTextContent().replaceFirst(",",""));
						}
					}
				}
			} else if(child.getNodeName().equals("img")) {
				//img found
				Node titleAttribute = child.getAttributes().getNamedItem("title");
				if(titleAttribute != null) {
					String label = labelMap.get(titleAttribute.getNodeValue());
					if(label != null) {
						labels.add(label);
					}
				}
				
			}
			handleMealEntry(child);
		}
	}
	
	private void addMealToDB() {
		//add meal
		new AddMealDBStatement(name, preis, new Kategorie(kategorie)).postQuery();
		//add labels
		for(String s : labels) {
			new AddMealLabelDBStatement(name, new Label(s)).postQuery();
		}
		//add date
		new AddMealDateDBStatement(name, new Outlay(ausgabe), datum).postQuery();
		
	}
	
	/**
	 * check if the meal-name contains ingredients
	 * 
	 */
	private void parseIngredients() {
		for(String ingredient : labelMap.keySet()) {
			replaceIngredient(ingredient);
		}
	}
	
	private void replaceIngredient(String ingredient) {
		if(name.contains(" (" + ingredient + ")")) {
			labels.add(labelMap.get(ingredient));
			name = name.replace(" (" + ingredient + ")", "");
		} else if(name.contains("(" + ingredient + ")")) {
			labels.add(labelMap.get(ingredient));
			name = name.replace("(" + ingredient + ")", "");
		}
	}
	
}

