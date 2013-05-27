package lecker.view.siteElement;



import lecker.view.MainSiteElement;



public class MealHtml implements MainSiteElement {
	private String mealName;
	
	
	public MealHtml(String mealName) {
		this.mealName = mealName;
	}
	
	
	
	@Override
	public String getNormalCode(String remoteAddr) {
		return "";
	}

	@Override
	public String getMobileCode(String remoteAddr) {
		return "";
	}

	@Override
	public String getNormalSkript(String remoteAddr) {
		return "";
	}

	@Override
	public String getMobileSkript(String remoteAddr) {
		return "";
	}



	@Override
	public String getTitle() {
		return mealName;
	}

}
