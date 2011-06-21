package ca.weiway.fbgames.shared.model;

import java.io.Serializable;
import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(identityType=IdentityType.APPLICATION)
public class Rate implements Serializable {

	private static final long serialVersionUID = -4415279469780082174L;

	@PrimaryKey
	@Persistent(valueStrategy=IdGeneratorStrategy.IDENTITY)
	private Long id;

	@Persistent
	private RateType type;

	@Persistent
	private Date timeFetched;

	@Persistent
	private Double rate;

	public Rate() {
	}
    
	public enum RateType {
		Selling,
		Buying,
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public RateType getType() {
		return type;
	}

	public void setType(RateType type) {
		this.type = type;
	}

	public Date getTimeFetched() {
		return timeFetched;
	}

	public void setTimeFetched(Date timeFetched) {
		this.timeFetched = timeFetched;
	}

	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}
	
	
}
