package ca.weiway.fbgames.shared.model;

public enum Platform {
	XBOX360("XBOX 360"), PS3("PlayStation 3");
	
	private String name;
	
	private Platform(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return name;
	}
}
