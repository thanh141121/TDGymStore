package net.gymsrote.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.gymsrote.entity.EnumEntity.ELogType;
import net.gymsrote.entity.user.UserEntity;

@Getter @Setter
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "log")
public class Log {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToOne
	@JoinColumn(name = "id_user")
	private UserEntity user;
	
	@Column(name = "date")
	@CreatedDate
	private Date date;
	
	@Column(name = "log_type")
	@Enumerated(EnumType.STRING)
	private ELogType logType;
	
	private String content;

	public Log(UserEntity user, String content, ELogType logType) {
		this.user = user;
		this.content = content;
		this.logType = logType;
	}
}