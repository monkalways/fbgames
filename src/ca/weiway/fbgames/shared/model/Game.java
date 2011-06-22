package ca.weiway.fbgames.shared.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.jdo.annotations.Element;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(identityType=IdentityType.APPLICATION)
public class Game implements Serializable {
	
	private static final long serialVersionUID = -4415279469780082174L;
	
	@PrimaryKey
	@Persistent(valueStrategy=IdGeneratorStrategy.IDENTITY)
	private Long id;
	
	@Persistent
	private String name;
	
	
	@Persistent
	private String imageLink;

	@Persistent
	private Date releaseDate;
	
	@Persistent
	private Date createDate;
	
	@Persistent
	private Date updateDate;
	
	@Persistent
	private Boolean onSale;
	
	@Persistent
	private Boolean recentPriceDrop;
	
	@Persistent
	private String platform;
	
	@Persistent(mappedBy = "game")
	@Element(dependent = "true")
	private Set<Price> prices = new HashSet<Price>();
	
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

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}


	public String getImageLink() {
		return imageLink;
	}

	public void setImageLink(String imageLink) {
		this.imageLink = imageLink;
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

}
