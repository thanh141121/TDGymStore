package net.gymsrote.entity.user;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.gymsrote.entity.MediaResource;
import net.gymsrote.entity.address.Ward;
import net.gymsrote.entity.cart.CartDetail;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "user_address")
public class UserAddress {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "adress_detail", nullable = false)
	private String addressDetail;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name = "user_id", nullable = false)
	private User user;
	
	@ManyToOne
	@JoinColumn(name = "ward_id", nullable = false)
	private Ward ward;
	
	@Column(name = "receiver_name", nullable = false)
	private String receiverName;
	
	@Column(name = "receiver_phone", nullable = false)
	private String receiverPhone;

	public UserAddress(String addressDetail, User user, Ward ward, String receiverName, String receiverPhone) {
		super();
		this.addressDetail = addressDetail;
		this.user = user;
		this.ward = ward;
		this.receiverName = receiverName;
		this.receiverPhone = receiverPhone;
	}
	
	public String toString() {
		this.addressDetail = this.addressDetail.replaceAll(",", "-");
		return String.format("%s, %s, %s, %s",
				this.addressDetail,
				this.ward.getWardName(),
				this.ward.getDistrict().getDistrictName(),
				this.ward.getDistrict().getProvince().getProvinceName());
	}

}
