package net.gymsrote.entity.order;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import net.gymsrote.entity.EnumEntity.ETransportation;
import net.gymsrote.entity.user.UserEntity;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class OrderEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "total")
	private Double total;
	
//	@Column(name = "created_date")
//	@CreatedDate
//	private Date created_date ;
	
	
	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	private EOrderStatus  status;
	
	@Column(name = "transportation")
	@Enumerated(EnumType.STRING)
	private ETransportation  transportation;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private UserEntity user;
	
	@OneToMany(mappedBy = "orders")
	private List<OrderDetailEntity> orderDetail = new ArrayList<>();

}
