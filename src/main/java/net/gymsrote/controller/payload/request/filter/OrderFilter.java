package net.gymsrote.entity.EnumEntity.filter;

import java.util.Date;

import net.gymsrote.entity.EnumEntity.EOrderStatus;
import net.gymsrote.entity.EnumEntity.EPaymentMethod;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrderFilter {
	Long idBuyer;

	Long idSeller;

	Long idDeliveryAddress;

	Date createTime;

	EOrderStatus status;

	EPaymentMethod paymentMethod;

	public OrderFilter(Long idBuyer, Long idSeller, Long idDeliveryAddress, Date createTime,
			EOrderStatus status, EPaymentMethod paymentMethod) {
		this.idBuyer = idBuyer;
		this.idSeller = idSeller;
		this.idDeliveryAddress = idDeliveryAddress;
		this.createTime = createTime;
		this.status = status;
		this.paymentMethod = paymentMethod;
	}
}
