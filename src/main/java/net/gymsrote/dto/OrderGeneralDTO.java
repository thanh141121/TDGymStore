package net.gymsrote.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import net.gymsrote.entity.EnumEntity.EOrderStatus;
import net.gymsrote.entity.EnumEntity.ETransportation;

@Getter 
@Setter
public class OrderGeneralDTO {
	private Long id;
	private EOrderStatus status;
	private String userFullname;
	private Date createTime;
	private String addressDetail;
	private String receiverName; 
	private String receiverPhone; 
	private Long total;
	private String note;
	private String paymentMethod;
	private Long price;
	private Long shipPrice;

}
