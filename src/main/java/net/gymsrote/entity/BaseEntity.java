package net.gymsrote.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {
	
	@Column(name = "created_date")
	@CreatedDate
	private Date created_date ;
	
	@Column(name = "modified_date")
	@LastModifiedDate
	private Date modified_date;
	
	@Column(name = "created_by")
	@CreatedBy
	private String created_by;
	
	@Column(name = "modified_by")
	@LastModifiedBy
	private String  modified_by;

}
