package net.gymsrote.service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import net.gymsrote.controller.advice.exception.CommonRuntimeException;
import net.gymsrote.controller.advice.exception.InvalidInputDataException;
import net.gymsrote.controller.payload.request.filter.OrderFilter;
import net.gymsrote.controller.payload.request.order.CreateOrderRequest;
import net.gymsrote.controller.payload.response.DataResponse;
import net.gymsrote.controller.payload.response.ListWithPagingResponse;
import net.gymsrote.dto.OrderDTO;
import net.gymsrote.entity.EnumEntity.EOrderStatus;
import net.gymsrote.entity.EnumEntity.EPaymentMethod;
import net.gymsrote.entity.order.Order;
import net.gymsrote.entity.order.OrderDetail;
import net.gymsrote.entity.product.ProductVariation;
import net.gymsrote.entity.user.User;
import net.gymsrote.entity.user.UserAddress;
import net.gymsrote.repository.OrderDetailRepo;
import net.gymsrote.repository.OrderRepo;
import net.gymsrote.repository.ProductVariationRepo;
import net.gymsrote.repository.UserAddressRepo;
import net.gymsrote.repository.UserRepo;
import net.gymsrote.service.thirdparty.ghn.GHNService;
import net.gymsrote.service.utils.ServiceUtils;
import net.gymsrote.utility.PagingInfo;

@Service
public class OrderService {
	@Autowired
	OrderRepo orderRepo;

	@Autowired
	OrderDetailRepo orderDetailRepo;

	@Autowired
	UserRepo userRepo;
	
	@Autowired
	PaymentService paymentService;
	
	@Autowired
	UserAddressRepo userAddressRepo;

	@Autowired
	ProductVariationRepo productVariationRepo;
	
	@Autowired
	GHNService ghnService;

	@Autowired
	ServiceUtils serviceUtils;

	public ListWithPagingResponse<?> getAll(Long idUser, PagingInfo pagingInfo, boolean isBuyer) {
		if (isBuyer) {
			return serviceUtils.convertToListResponse(orderRepo.findAllByUserId(idUser, pagingInfo.getPageable()),
					OrderDTO.class);
		} else {
			return serviceUtils.convertToListResponse(orderRepo.findAll(pagingInfo.getPageable()),
					OrderDTO.class);
		}
	}

	public DataResponse<?> get(Long id, Long idUser) {
		Order order = orderRepo.findById(id).orElseThrow(
				() -> new InvalidInputDataException("No order found with given id"));

		if(idUser != null && !order.getUser().getId().equals(idUser)) 
			throw new InvalidInputDataException("Can not read order of other buyers");
		
		return serviceUtils.convertToDataResponse(order, OrderDTO.class);
	}

	@Transactional
	public DataResponse<OrderDTO> create(Long idUser, CreateOrderRequest data, HttpServletRequest req) {
		User user = userRepo.getReferenceById(idUser);

		Order order = new Order(user,
				EOrderStatus.WAIT_FOR_PAYMENT,
				data.getPaymentMethod(),
				data.getAddress(),
				data.getReceiverPhone(),
				data.getReceiverName());

			data.getProducts().stream().forEach(p -> {
					ProductVariation productVariation = productVariationRepo.findById(p.getId()).orElseThrow( () -> new InvalidInputDataException("No product variation found with given id"));
					OrderDetail orderDetail = createOrderDetail(productVariation, p.getQuantity(), order);
					order.getOrderDetails().add(orderDetail);
					order.setPrice(order.getPrice() + orderDetail.getUnitPrice() * orderDetail.getQuantity());
				});

		order.setTotal(order.getPrice() + data.getShipPrice());
		order.setShipPrice(data.getShipPrice());

		DataResponse<OrderDTO> res = serviceUtils.convertToDataResponse(orderRepo.save(order), OrderDTO.class);
		
		res.getData().setPayUrl(paymentService.createPayment(res.getData().getId(), user.getId(), req));

		return res;
	}

	private OrderDetail createOrderDetail(ProductVariation productVariation, Long quantity, Order order) {

		return new OrderDetail(
			order,
			productVariation,
			quantity,
			(long)Math.ceil(productVariation.getPrice() * (1 - productVariation.getDiscount() / 100.0))
		);
	}

	@Transactional
	public DataResponse<OrderDTO> updateStatus(Long id, Long idBuyer, EOrderStatus newStatus) {
		Order order = orderRepo.findById(id).orElseThrow(
			() -> new InvalidInputDataException("No order found with given id "));
		
		if(idBuyer != null && !order.getUser().getId().equals(idBuyer))
			throw new InvalidInputDataException("Can not update other buyer's orders");
		
		if (newStatus == EOrderStatus.CANCELLED)
			return cancelOrder(order);

		if (newStatus == EOrderStatus.WAIT_FOR_SEND && order.getStatus() == EOrderStatus.WAIT_FOR_CONFIRM) {
			order.getOrderDetails().stream().forEach(od -> {
				if(productVariationRepo.reduceStock(od.getProductVariation().getId(), od.getQuantity()) == 0 ){
					throw new InvalidInputDataException("Product variation " + od.getProductVariation().getId() + " is out of stock");
				}
			});
			ghnService.createShipmentGHN(order);
		}

		// if(newStatus == EOrderStatus.COMPLETED) {
		// 	Long totalSpend = order.getUser().getTotalSpent() == null ? order.getPayPrice() : order.getUser().getTotalSpent() + order.getPayPrice();
		// 	Buyer buyer = order.getUser();
		// 	buyer.setTotalSpent(totalSpend);
		// 	if(buyer.getRank().getNextRank() != null && buyer.getTotalSpent() >= buyer.getRank().getThreshold()) {
		// 		buyerRankService.rankUp(buyerRepo.save(buyer).getId());
		// 	}else{
		// 		buyerRepo.save(buyer);
		// 	}
		// }
		
		order.setStatus(newStatus);
		return serviceUtils.convertToDataResponse(orderRepo.save(order), OrderDTO.class);
	}


	@Transactional
	private DataResponse<OrderDTO> cancelOrder(Order order) {
		switch (order.getStatus()) {
			case WAIT_FOR_CONFIRM:
				if (order.getPaymentMethod().equals(EPaymentMethod.ONLINE_PAYMENT_MOMO)
						|| order.getPaymentMethod().equals(EPaymentMethod.ONLINE_PAYMENT_PAYPAL)) {
					// paymentService.refundPayment(order);
				}
				order.setStatus(EOrderStatus.CANCELLED);
				break;
			case WAIT_FOR_SEND:
				if (order.getPaymentMethod().equals(EPaymentMethod.ONLINE_PAYMENT_MOMO)
						|| order.getPaymentMethod().equals(EPaymentMethod.ONLINE_PAYMENT_PAYPAL)) {
					// paymentService.refundPayment(order);
				}
				
				// revert stock when order is cancelled
				order.getOrderDetails().stream().forEach(od -> 
					productVariationRepo.refundStock(od.getProductVariation().getId(), od.getQuantity())
				);

				order.setStatus(EOrderStatus.CANCELLED);
				break;
			case WAIT_FOR_PAYMENT:
				order.setStatus(EOrderStatus.CANCELLED);
				break;
			default:
				throw new CommonRuntimeException("Order cannot be cancelled");
		}
		
		return serviceUtils.convertToDataResponse(orderRepo.save(order), OrderDTO.class);
	}

	
}
