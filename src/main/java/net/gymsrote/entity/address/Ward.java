package net.gymsrote.entity.address;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "tb_ward")
public class Ward {
	@Id
	private Long wardCode;
	
	@Column
	private String wardName;
	
	@ManyToOne
	@JoinColumn(name = "district_id")
	private District district;

	public Ward(Long wardCode, String wardName) {
		super();
		this.wardCode = wardCode;
		this.wardName = wardName;
	}

}
