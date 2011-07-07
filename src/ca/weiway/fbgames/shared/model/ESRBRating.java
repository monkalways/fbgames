package ca.weiway.fbgames.shared.model;

public enum ESRBRating {
	
	EARLY_CHILDHOOD("EC - Early ChildHood"), 
	EVERYONE("E - Everyone"),
	EVERYONE_10_PLUS("E10+ - Everyone 10+"),
	TEEN("T - Teen"),
	MATURE("M - Mature"),
	ADULTS_ONLY("AO - Adult Only"),
	RATING_PENDING("RP - Rating Pending");
	
	private String name;
	
	private ESRBRating(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return name;
	}
}
