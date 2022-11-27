package net.gymsrote.entity.address;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "tb_district")
public class District {
	@Id
	private Long DistrictID;
	
	@Column
	private String DistrictName;
	
	@OneToMany(mappedBy = "district", fetch = FetchType.LAZY)
	private List<Ward> wards;
	
	@ManyToOne
	@JoinColumn(name = "province_id")
	private Province province;
}
