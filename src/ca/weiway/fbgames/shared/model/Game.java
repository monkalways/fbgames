package ca.weiway.fbgames.shared.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

import com.google.gwt.user.client.rpc.GwtTransient;

@Entity
@NamedQuery(
	    name="Game.getTotalOfGame",
	    query="select count(game.id) from Game game"
)
public class Game implements Serializable {
	
	private static final long serialVersionUID = -4415279469780082174L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	
	private String bestBuyImageLink;
	
	private String gameStopImageLink;

	private Date releaseDate;
	
	private Date createDate;
	
	private Date updateDate;
	
	private Boolean onSale;
	
	private Boolean recentPriceDrop;
	
	private Platform platform;
	
	private ESRBRating rating;
	
	private String publisher;
	
	private String developer;
	
	private GameCategory gameCategory;
	
	private Double latestLowestPrice;
	
	private PriceSource latestLowestPriceSource;
	
	@GwtTransient
	@OneToMany(mappedBy="game", cascade=CascadeType.ALL)
	private Set<Price> prices = new HashSet<Price>();
	
	@GwtTransient
	@OneToMany(mappedBy="game", cascade=CascadeType.ALL)
	private Set<GameDetail> gameDetails = new HashSet<GameDetail>();
	
	public Date getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public Boolean getOnSale() {
		return onSale;
	}

	public void setOnSale(Boolean onSale) {
		this.onSale = onSale;
	}

	public Boolean getRecentPriceDrop() {
		return recentPriceDrop;
	}

	public void setRecentPriceDrop(Boolean recentPriceDrop) {
		this.recentPriceDrop = recentPriceDrop;
	}

	public Platform getPlatform() {
		return platform;
	}

	public void setPlatform(Platform platform) {
		this.platform = platform;
	}


	public String getBestBuyImageLink() {
		return bestBuyImageLink;
	}

	public void setBestBuyImageLink(String bestBuyImageLink) {
		this.bestBuyImageLink = bestBuyImageLink;
	}

	public String getGameStopImageLink() {
		return gameStopImageLink;
	}

	public void setGameStopImageLink(String gameStopImageLink) {
		this.gameStopImageLink = gameStopImageLink;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Price> getPrices() {
		return prices;
	}

	public void setPrices(Set<Price> prices) {
		this.prices = prices;
	}

	public ESRBRating getRating() {
		return rating;
	}

	public void setRating(ESRBRating rating) {
		this.rating = rating;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getDeveloper() {
		return developer;
	}

	public void setDeveloper(String developer) {
		this.developer = developer;
	}

	public Set<GameDetail> getGameDetails() {
		return gameDetails;
	}

	public void setGameDetails(Set<GameDetail> gameDetails) {
		this.gameDetails = gameDetails;
	}
	
	public GameCategory getGameCategory() {
		return gameCategory;
	}

	public void setGameCategory(GameCategory gameCategory) {
		this.gameCategory = gameCategory;
	}
	
	public Double getLatestLowestPrice() {
		return latestLowestPrice;
	}

	public void setLatestLowestPrice(Double latestLowestPrice) {
		this.latestLowestPrice = latestLowestPrice;
	}

	public PriceSource getLatestLowestPriceSource() {
		return latestLowestPriceSource;
	}

	public void setLatestLowestPriceSource(PriceSource latestLowestPriceSource) {
		this.latestLowestPriceSource = latestLowestPriceSource;
	}

	@Override
	public String toString() {
		return name + " " + platform;
	}

}
