package net.gymsrote.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import net.gymsrote.entity.EnumEntity.ELogType;

@Getter @Setter
public class LogDTO {
	private Long id;
	private String username;
	private Date date;
	private ELogType logType;
	private String content;
}
