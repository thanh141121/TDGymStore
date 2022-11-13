package net.gymsrote.entity.address;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.gymsrote.entity.user.UserEntity;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "address")
public class AddressEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name="address_detail")
	private String address_detail;
	
	@Column(name="receiver_phone")
	private String receiver_phone;
	
	@Column(name = "receiver_name")
	private String receiver_name;
	
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
