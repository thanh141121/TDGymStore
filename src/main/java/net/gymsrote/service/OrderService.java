package net.gymsrote.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.gymsrote.controller.payload.request.order.CreateOrderRequest;
import net.gymsrote.controller.payload.response.DataResponse;
import net.gymsrote.dto.OrderDTO;
import net.gymsrote.entity.EnumEntity.EOrderStatus;
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
import net.gymsrote.service.utils.ServiceUtils;

@Service
public class OrderService {
	@Autowired
	OrderRepo orderRepo;

	@Autowired
	OrderDetailRepo orderDetailRepo;

	@Autowired
	UserRepo userRepo;
//
//	@Autowired
//	PaymentService paymentService;
//
	@Autowired
	UserAddressRepo userAddressRepo;

	@Autowired
	ProductVariationRepo productVariationRepo;

	@Autowired
	ServiceUtils serviceUtils;
//
//	public ListWithPagingResponse<?> search(OrderFilter filter, PagingInfo pagingInfo,
//			boolean isBuyer) {
//		if (isBuyer) {
//			return serviceUtils.convertToListResponse(orderRepo.search(filter, pagingInfo),
//					BuyerOrderDTO.class);
//		} else {
//			return serviceUtils.convertToListResponse(orderRepo.search(filter, pagingInfo),
//					AdminOrderDTO.class);
//		}
//	}
//
	public DataResponse<?> get(Long id, Long idBuyer) {
		return null;
//		Order order = orderRepo.findById(id)
//				.orElseThrow(() -> new InvalidInputDataException("No order found with given id"));
//		if (idBuyer != null && !order.getBuyer().getId().equals(idBuyer))
//			throw new InvalidInputDataException("Can not read order of other buyers");
//		if (idBuyer != null) {
//			return serviceUtils.convertToDataResponse(order, BuyerDetailedOrderDTO.class);
//		} else {
//			return serviceUtils.convertToDataResponse(order, AdminDetailedOrderDTO.class);
//		}
	}

	@Transactional
	public DataResponse<OrderDTO> create(Long idUser, CreateOrderRequest data) {
		UserAddress address =
				userAddressRepo.getReferenceById(data.getIdAddress());
		User user = userRepo.getReferenceById(idUser);
		Order order = new Order(user,
				EOrderStatus.WAIT_FOR_CONFIRM ,
				data.getPaymentMethod(), 
				address.toString(),
				address.getReceiverPhone(),
				address.getReceiverName());
		data.getProducts().stream().forEach( product -> {
			ProductVariation productvariation = productVariationRepo.getReferenceById(product.getId());
			Long total = productvariation.getFinalPrice()*product.getQuantity();
			order.setPayPrice(order.getPayPrice() + total);
			orderDetailRepo.save(new OrderDetail(order, 
					productvariation, 
					product.getQuantity(),
					total
					));
		});
		order.setPayPrice(order.getPayPrice() + data.getShipPrice());
		return serviceUtils.convertToDataResponse(orderRepo.save(order), OrderDTO.class);
	}
//
//	public void checkAndCompleteOrder(Long id) {
//		if (!orderDetailRepo.existsByOrderAndReviewed(orderRepo.getReferenceById(id), false)) {
//			Order order = orderRepo.getReferenceById(id);
//			order.setStatus(EOrderStatus.COMPLETED);
//			orderRepo.save(order);
//		}
//	}
//
//	@Transactional
//	public DataResponse<OrderDTO> updateStatus(Long id, Long idBuyer, EOrderStatus newStatus) {
//		Order order = orderRepo.findById(id)
//				.orElseThrow(() -> new InvalidInputDataException("No order found with given id "));
//		if (idBuyer != null && !order.getBuyer().getId().equals(idBuyer))
//			throw new InvalidInputDataException("Can not update other buyer's orders");
//		if (newStatus == EOrderStatus.CANCELLED)
//			return cancelOrder(order);
//		if (newStatus == EOrderStatus.WAIT_FOR_SEND
//				&& order.getStatus() == EOrderStatus.WAIT_FOR_CONFIRM) {
//			order.getOrderDetails().stream().forEach(od -> {
//				if (productVariationRepo.reduceStock(od.getProductVariation().getId(),
//						od.getQuantity()) == 0) {
//					throw new InvalidInputDataException("Product variation "
//							+ od.getProductVariation().getId() + " is out of stock");
//				}
//			});
//		}
//		order.setStatus(newStatus);
//		return serviceUtils.convertToDataResponse(orderRepo.save(order), OrderDTO.class);
//	}
//
//	@Transactional
//	private DataResponse<OrderDTO> cancelOrder(Order order) {
//		switch (order.getStatus()) {
//			case WAIT_FOR_CONFIRM:
//				if (order.getPaymentMethod().equals(EPaymentMethod.ONLINE_PAYMENT_MOMO)
//						|| order.getPaymentMethod().equals(EPaymentMethod.ONLINE_PAYMENT_PAYPAL)) {
//					paymentService.refundPayment(order);
//				}
//				order.setStatus(EOrderStatus.CANCELLED);
//				break;
//			case WAIT_FOR_SEND:
//				if (order.getPaymentMethod().equals(EPaymentMethod.ONLINE_PAYMENT_MOMO)
//						|| order.getPaymentMethod().equals(EPaymentMethod.ONLINE_PAYMENT_PAYPAL)) {
//					paymentService.refundPayment(order);
//				}
//				// revert stock when order is cancelled
//				order.getOrderDetails().stream().forEach(od -> productVariationRepo
//						.refundStock(od.getProductVariation().getId(), od.getQuantity()));
//				order.setStatus(EOrderStatus.CANCELLED);
//				break;
//			case WAIT_FOR_PAYMENT:
//				order.setStatus(EOrderStatus.CANCELLED);
//				break;
//			default:
//				throw new CommonRuntimeException("Order cannot be cancelled");
//		}
//		return serviceUtils.convertToDataResponse(orderRepo.save(order), OrderDTO.class);
//	}
//
//	private List<BuyerCartDetail> getBuyerCartDetails(Buyer buyer, CreateBuyerOrderRequest data) {
//		List<Long> idps = new ArrayList<>();
//		List<BuyerCartDetail> cartDetails = new ArrayList<>();
//		if (data.getClass().equals(CreateBuyerOrderFromCartRequest.class)) {
//			idps.addAll(((CreateBuyerOrderFromCartRequest) data).getIdProductVariations());
//			cartDetails = buyerCartDetailRepo
//					.findAllById(idps.stream().map(id -> new BuyerCartDetailPK(buyer.getId(), id))
//							.collect(Collectors.toList()));
//			List<Long> idpvFounds = cartDetails.stream().map(cd -> cd.getProductVariation().getId())
//					.collect(Collectors.toList());
//			idps.stream().forEach(id -> {
//				if (!idpvFounds.contains(id)) {
//					throw new InvalidInputDataException(
//							"No Product Variation found with given id " + id);
//				}
//			});
//			buyerCartDetailRepo.deleteAllInBatch(cartDetails); // delete cart item from cart
//		} else if (data.getClass().equals(CreateBuyerOrderFromProductRequest.class)) {
//			idps.add(((CreateBuyerOrderFromProductRequest) data).getIdProductVariation());
//			cartDetails.add(new BuyerCartDetail(buyer,
//					productVariationRepo.findById(idps.get(0))
//							.orElseThrow(() -> new InvalidInputDataException(
//									"No Product Variation found with given id " + idps.get(0))),
//					((CreateBuyerOrderFromProductRequest) data).getQuantity()));
//		}
//		return cartDetails;
//	}
//
//	private OrderDetail createOrderDetail(BuyerCartDetail cartDetail, Order order) {
//		checkAvailable(cartDetail);
//		return new OrderDetail(order, cartDetail.getProductVariation(), cartDetail.getQuantity(),
//				cartDetail.getProductVariation().getPrice());
//	}
//
//	private void checkAvailable(BuyerCartDetail cartDetail) {
//		if (cartDetail.getProductVariation().getStatus() != EProductVariationStatus.ENABLED) {
//			throw new InvalidInputDataException("Product Variation with id "
//					+ cartDetail.getProductVariation().getId() + " is not available");
//		}
//		if (cartDetail.getProductVariation().getProduct().getStatus() != EProductStatus.ENABLED) {
//			throw new InvalidInputDataException("Product with id "
//					+ cartDetail.getProductVariation().getProduct().getId() + " is not available");
//		}
//		if (cartDetail.getProductVariation().getAvailableQuantity() < cartDetail.getQuantity()) {
//			throw new InvalidInputDataException("Stock is not enough for product variation with id "
//					+ cartDetail.getProductVariation().getId());
//		}
//	}
}
