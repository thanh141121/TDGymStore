package net.gymsrote.entity.order;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.gymsrote.entity.EnumEntity.EOrderStatus;
import net.gymsrote.entity.EnumEntity.EPaymentMethod;
import net.gymsrote.entity.EnumEntity.ETransportation;
import net.gymsrote.entity.user.User;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "order_")
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "total")
	private Long total;
	
	
	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	private EOrderStatus  status;
	
	@Column(name = "payment_method")
	@Enumerated(EnumType.STRING)
	private EPaymentMethod paymentMethod;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;
	
	@Column(name="address_detail")
	private String addressDetail;
	
	@Column(name="receiver_phone")
	private String receiverPhone;
	
	@Column(name = "receiver_name")
	private String receiverName;
	
	@Column(name = "create_time")
	private Date createTime;

	@Column(name = "price")
	private Long price = 0L;

	@Column(name = "shipPrice")
	private Long shipPrice = 0L;
	
	@OneToMany(mappedBy = "order", cascade = CascadeType.PERSIST)
	private List<OrderDetail> orderDetails = new ArrayList<>();

	public Order(User user, EOrderStatus status, EPaymentMethod paymentMethod, String addressDetail,
			String receiverPhone, String receiverName) {
		super();
		this.status = status;
		this.paymentMethod = paymentMethod;
		this.user = user;
		this.addressDetail = addressDetail;
		this.receiverPhone = receiverPhone;
		this.receiverName = receiverName;
		this.createTime = new Date();
	}

}
