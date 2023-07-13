package net.gymsrote.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConfigDTO {
	private Long id;
	private String value;
	private Boolean isSelected = false;
}