package net.gymsrote.dto;

import java.util.Date;

import javax.persistence.MappedSuperclass;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@MappedSuperclass
public class BaseDTO {
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;

}
