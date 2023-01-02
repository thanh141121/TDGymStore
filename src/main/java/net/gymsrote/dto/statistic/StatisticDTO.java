package net.gymsrote.dto.statistic;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class StatisticDTO{
	private String label;
	private Integer timeUnit;
	private Long nProduct;
	private Long nBuyer;
	private Long income;
	private Long nOrder;

	public StatisticDTO(Integer timeUnit, Long nProduct, Long nBuyer, Long income, Long nOrder) {
		this.timeUnit = timeUnit;
		this.nProduct = nProduct;
		this.nBuyer = nBuyer;
		this.income = income;
		this.nOrder = nOrder;
	}
}
