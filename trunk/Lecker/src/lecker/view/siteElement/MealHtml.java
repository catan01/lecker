package lecker.view.siteElement;



import lecker.model.data.Meal;
import lecker.model.data.comments.Comment;
import lecker.model.data.comments.CommentList;
import lecker.presenter.Handler;
import lecker.view.MainSiteElement;



public class MealHtml implements MainSiteElement {
	private final Meal MEAL;
	
	
	public MealHtml(String mealName) {
		this.MEAL = Handler.getInstance().getMealManager().getMeal(mealName);
	}
	
	
	
	@Override
	public String getNormalCode(String remoteAddr) {
		StringBuilder builder = new StringBuilder();
		int[] ratings = new int[Comment.MAX_RATING + 1];
		CommentList com = MEAL.getComments();
		while (com.next()) {
			System.out.println("BLA");
			try {
				ratings[com.get().getRating()]++;
			} catch (Exception exc) {
				System.out.println("TEST");
				ratings[0]++;
			}
		}
		com.setBeforeFirst();
		
		int count = 0, rate = 0;
		for (int i = 0; i < ratings.length; ++i) {
			count += ratings[i];
			rate += ratings[i] * i;
			for (int j = 0; j < ratings.length - 1; ++j) {
				if (j < i) {
					builder.append("<img src='images/star_green_small.png'>");
				} else {
					builder.append("<img src='images/star_gray_small.png'>");
				}
			}
			builder.append(" " + ratings[i] + "<br />");
		}
		builder.append("Ø " + (rate/count));
		
		
		
		
		return builder.toString();
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
		return MEAL.getName();
	}

}
