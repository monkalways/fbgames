package ca.weiway.fbgames.shared.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.datanucleus.jpa.annotations.Extension;

@Entity
public class GameDetail implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4884952798460673723L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Extension(vendorName="datanucleus", key="gae.encoded-pk", value="true")
	private String id;
	
	private String gameLink;
	
	private PriceSource source;
	
	private Double rating;
	
	private Integer numRating;
	
	@ManyToOne
	private Game game;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public PriceSource getSource() {
		return source;
	}

	public void setSource(PriceSource source) {
		this.source = source;
	}

	public Double getRating() {
		return rating;
	}

	public void setRating(Double rating) {
		this.rating = rating;
	}

	public Integer getNumRating() {
		return numRating;
	}

	public void setNumRating(Integer numRating) {
		this.numRating = numRating;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public String getGameLink() {
		return gameLink;
	}

	public void setGameLink(String gameLink) {
		this.gameLink = gameLink;
	}
	
}
