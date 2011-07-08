package ca.weiway.fbgames.shared.model;

public enum GameCategory {
	
	ACTION("Action", null),
	ADVENTURE("Adventure", ACTION),
	CRIME("Crime", ACTION),
	FLIGHT("Flight", ACTION),
	HORROR("Horror", ACTION),
	PLATFORM("Platform", ACTION),
	TACTICAL("Tactical", ACTION),
	
	CASUAL("Casual", null),
	ACTION_ARCADE("Action Arcade", CASUAL),
	PUZZLE("Puzzle", CASUAL),
	RPG("RPG", CASUAL),
	SHOOTERS("Shooters", CASUAL),
	SPORT("Sport", CASUAL),
	
	FIGHTING("Fighting", null),
	
	MUSIC_PARTY("Music & Party", null),
	DANCE("Dance", MUSIC_PARTY),
	MUSIC("Music", MUSIC_PARTY),
	PARTY("Party", MUSIC_PARTY),
	SINGING("Singing", MUSIC_PARTY),
	
	ROLE_PLAYING("Role-Playing", null),
	MASSIVELY_MULTIPLAYER("Massively Multiplayer", ROLE_PLAYING),
	
	SIMULATION("Simulation", null),
	AVIATION("Aviation", SIMULATION),
	MILITARY("Military", SIMULATION),
	
	SPORTS("Sports", null),
	BASEBALL("BaseBall", SPORTS),
	BASKETBALL("Basketball", SPORTS),
	BOXING("Boxing", SPORTS),
	FISHING("Fishing", SPORTS),
	FITNESS("Fitness", SPORTS),
	FOOTBALL("Football", SPORTS),
	GOLF("Golf", SPORTS),
	HOCKEY("Hockey", SPORTS),
	HUNTING("Hunting", SPORTS),
	MULTI_SPORT("Multi-sport", SPORTS),
	RACING("Racing", SPORTS),
	SKATEBOARDING("Skateboarding", SPORTS),
	SNOWBOARDING("Snowboarding", SPORTS),
	SOCCER("Soccer", SPORTS),
	TENNIS("Tennis", SPORTS),
	WRESTLING("Wrestling", SPORTS),
	
	STRATEGY("Strategy", null),
	NONE("None", null);
	
	private String name;
	private GameCategory parent;
	
	private GameCategory(String name, GameCategory parent) {
		this.name = name;
		this.parent = parent;
	}
	
	public GameCategory getParent() {
		return this.parent;
	}
	
	public String getName() {
		return this.name;
	}
	
	@Override
	public String toString() {
		return name;
	}
}
