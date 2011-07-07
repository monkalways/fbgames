package ca.weiway.fbgames.shared.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.datanucleus.jpa.annotations.Extension;

@Entity
public class Price implements Serializable {
private static final long serialVersionUID = -4415279469780082174L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Extension(vendorName="datanucleus", key="gae.encoded-pk", value="true")
	private String id;

	private String link;
	
	@ManyToOne
	private Game game;

	private Double price;
	
	private Date createDate;
	
	private PriceSource source;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public PriceSource getSource() {
		return source;
	}

	public void setSource(PriceSource source) {
		this.source = source;
	}
	
}
