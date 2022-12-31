package net.gymsrote.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import net.gymsrote.controller.advice.exception.CommonRuntimeException;
import net.gymsrote.controller.advice.exception.InvalidInputDataException;
import net.gymsrote.entity.EnumEntity.EOrderStatus;
import net.gymsrote.entity.order.Order;
import net.gymsrote.repository.OrderRepo;
import net.gymsrote.service.thirdparty.payment.Payment;
import net.gymsrote.service.thirdparty.payment.momo.MomoService;

@Service
public class PaymentService {
	@Autowired
	OrderRepo orderRepo;
	
	@Autowired
	MomoService momoService;
	
	@Value("${net.gymsrote.service.payment.return-url}")
	private String returnUrl;
	
	public String createPayment(Long idOrder, Long idBuyer, HttpServletRequest req) {
		Order o = orderRepo.getReferenceById(idOrder);
		if (!o.getUser().getId().equals(idBuyer))
			throw new InvalidInputDataException("Can not confirm payment of other buyer's order");
		
		if (!o.getStatus().equals(EOrderStatus.WAIT_FOR_PAYMENT)) {
			throw new InvalidInputDataException("Order status is not in wait for payment state");
		}
		
		Payment payment = null;
		switch (o.getPaymentMethod()) {
			case OFFLINE_CASH_ON_DELIVERY:
				if (confirm(idOrder))
					return null;
				else
					throw new CommonRuntimeException("Update order payment status failed. Please try again!");
			case ONLINE_PAYMENT_MOMO:
				payment = momoService;
				break;
			default:
				throw new CommonRuntimeException("Invalid payment method!");
		}
		
		return payment.createPayment(idOrder, o.getTotal().toString(), req);
	}
	
	public void refundPayment(Order order) {		
		if (!order.getStatus().equals(EOrderStatus.WAIT_FOR_CONFIRM) && !order.getStatus().equals(EOrderStatus.WAIT_FOR_SEND)) {
			throw new InvalidInputDataException("Refund only can be made when order is in wait for confirm or wait for send stage");
		}
		
		Payment payment = null;
		switch (order.getPaymentMethod()) {
		case OFFLINE_CASH_ON_DELIVERY:
			throw new InvalidInputDataException("Refund is not available for this order's payment method!");
		case ONLINE_PAYMENT_MOMO:
			payment = momoService;	
			break;
		default:
			throw new CommonRuntimeException("Invalid payment method!");
		}
		
		payment.refundPayment(order.getId());
	}
	
	public boolean confirm(Long idOrder) {
		return orderRepo.confirmPayment(idOrder) == 1;
	}
	
	public String getReturnUrl(boolean success) {
		return String.format("%s?success=%s", returnUrl, success ? "true" : "false");
	}
}
