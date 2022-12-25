package net.gymsrote.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import net.gymsrote.entity.EnumEntity.EOrderStatus;
import net.gymsrote.entity.EnumEntity.ETransportation;

@Getter 
@Setter
public class OrderDTO {
	private Long id;
	private EOrderStatus status;
	private Double total;
	private ETransportation transportation;
	private Long userId;

	private List<OrderDetailDTO> orderDetails = new ArrayList<>();

}
