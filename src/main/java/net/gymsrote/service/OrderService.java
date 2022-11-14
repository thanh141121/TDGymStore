package net.gymsrote.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.gymsrote.controller.advice.exception.CommonRuntimeException;
import net.gymsrote.controller.advice.exception.InvalidInputDataException;
import net.gymsrote.controller.payload.dto.OrderDTO;
import net.gymsrote.controller.payload.dto.order.AdminDetailedOrderDTO;
import net.gymsrote.controller.payload.dto.order.AdminOrderDTO;
import net.gymsrote.controller.payload.dto.order.BuyerDetailedOrderDTO;
import net.gymsrote.controller.payload.dto.order.BuyerOrderDTO;
import net.gymsrote.controller.payload.request.buyerorder.CreateBuyerOrderFromCartRequest;
import net.gymsrote.controller.payload.request.buyerorder.CreateBuyerOrderFromProductRequest;
import net.gymsrote.controller.payload.request.buyerorder.CreateBuyerOrderRequest;
import net.gymsrote.controller.payload.response.DataResponse;
import net.gymsrote.controller.payload.response.ListWithPagingResponse;
import net.gymsrote.entity.order.Order;
import net.gymsrote.entity.order.OrderDetail;
import net.gymsrote.entity.pk.BuyerCartDetailPK;
import net.gymsrote.entity.user.buyer.Buyer;
import net.gymsrote.entity.user.buyer.BuyerCartDetail;
import net.gymsrote.entity.user.buyer.BuyerDeliveryAddress;
import net.gymsrote.repository.BuyerCartDetailRepo;
import net.gymsrote.repository.BuyerDeliveryAddressRepo;
import net.gymsrote.repository.BuyerRepo;
import net.gymsrote.repository.OrderDetailRepo;
import net.gymsrote.repository.OrderRepo;
import net.gymsrote.repository.ProductVariationRepo;
import net.gymsrote.service.util.ServiceUtils;
import net.gymsrote.entity.EnumEntity.EBuyerDeliveryAddressStatus;
import net.gymsrote.entity.EnumEntity.EOrderStatus;
import net.gymsrote.entity.EnumEntity.EPaymentMethod;
import net.gymsrote.entity.EnumEntity.EProductStatus;
import net.gymsrote.entity.EnumEntity.EProductVariationStatus;
import net.gymsrote.controller.payload.request.filter.OrderFilter;
import net.gymsrote.controller.payload.request.filter.PagingInfo;

@Service
public class OrderService {
	@Autowired
	OrderRepo orderRepo;

	@Autowired
	OrderDetailRepo orderDetailRepo;

	@Autowired
	BuyerRepo buyerRepo;

	@Autowired
	PaymentService paymentService;

	@Autowired
	BuyerDeliveryAddressRepo buyerDeliveryAddressRepo;

	@Autowired
	ProductVariationRepo productVariationRepo;

	@Autowired
	BuyerCartDetailRepo buyerCartDetailRepo;

	@Autowired
	ServiceUtils serviceUtils;

	public ListWithPagingResponse<?> search(OrderFilter filter, PagingInfo pagingInfo,
			boolean isBuyer) {
		if (isBuyer) {
			return serviceUtils.convertToListResponse(orderRepo.search(filter, pagingInfo),
					BuyerOrderDTO.class);
		} else {
			return serviceUtils.convertToListResponse(orderRepo.search(filter, pagingInfo),
					AdminOrderDTO.class);
		}
	}

	public DataResponse<?> get(Long id, Long idBuyer) {
		Order order = orderRepo.findById(id)
				.orElseThrow(() -> new InvalidInputDataException("No order found with given id"));
		if (idBuyer != null && !order.getBuyer().getId().equals(idBuyer))
			throw new InvalidInputDataException("Can not read order of other buyers");
		if (idBuyer != null) {
			return serviceUtils.convertToDataResponse(order, BuyerDetailedOrderDTO.class);
		} else {
			return serviceUtils.convertToDataResponse(order, AdminDetailedOrderDTO.class);
		}
	}

	@Transactional
	public DataResponse<OrderDTO> create(Long idBuyer, CreateBuyerOrderRequest data) {
		BuyerDeliveryAddress address =
				buyerDeliveryAddressRepo.getReferenceById(data.getIdAddress());
		Buyer buyer = buyerRepo.getReferenceById(idBuyer);
		if (Boolean.FALSE.equals(buyer.getPhoneConfirmed()))
			throw new CommonRuntimeException(
					"Please confirm your phone number before placing order");
		if (!buyerRepo.existsById(idBuyer)) {
			throw new InvalidInputDataException("No Buyer found with given id " + idBuyer);
		}
		if (!buyerDeliveryAddressRepo.existsById(data.getIdAddress())
				|| !address.getBuyer().getId().equals(idBuyer)
				|| !address.getStatus().equals(EBuyerDeliveryAddressStatus.ACTIVE)) {
			throw new InvalidInputDataException(
					"No Buyer Delivery Address found with given id " + data.getIdAddress());
		}
		Order order = new Order(buyer, address, data.getNote(), EOrderStatus.WAIT_FOR_CONFIRM,
				data.getPaymentMethod());
		List<BuyerCartDetail> cartDetails = getBuyerCartDetails(buyer, data);
		cartDetails.stream().forEach(cd -> {
			OrderDetail orderDetail = createOrderDetail(cd, order);
			order.getOrderDetails().add(orderDetail);
			order.setPrice(
					order.getPrice() + orderDetail.getUnitPrice() * orderDetail.getQuantity());
		});
		return serviceUtils.convertToDataResponse(orderRepo.save(order), OrderDTO.class);
	}

	public void checkAndCompleteOrder(Long id) {
		if (!orderDetailRepo.existsByOrderAndReviewed(orderRepo.getReferenceById(id), false)) {
			Order order = orderRepo.getReferenceById(id);
			order.setStatus(EOrderStatus.COMPLETED);
			orderRepo.save(order);
		}
	}

	@Transactional
	public DataResponse<OrderDTO> updateStatus(Long id, Long idBuyer, EOrderStatus newStatus) {
		Order order = orderRepo.findById(id)
				.orElseThrow(() -> new InvalidInputDataException("No order found with given id "));
		if (idBuyer != null && !order.getBuyer().getId().equals(idBuyer))
			throw new InvalidInputDataException("Can not update other buyer's orders");
		if (newStatus == EOrderStatus.CANCELLED)
			return cancelOrder(order);
		if (newStatus == EOrderStatus.WAIT_FOR_SEND
				&& order.getStatus() == EOrderStatus.WAIT_FOR_CONFIRM) {
			order.getOrderDetails().stream().forEach(od -> {
				if (productVariationRepo.reduceStock(od.getProductVariation().getId(),
						od.getQuantity()) == 0) {
					throw new InvalidInputDataException("Product variation "
							+ od.getProductVariation().getId() + " is out of stock");
				}
			});
		}
		order.setStatus(newStatus);
		return serviceUtils.convertToDataResponse(orderRepo.save(order), OrderDTO.class);
	}

	@Transactional
	private DataResponse<OrderDTO> cancelOrder(Order order) {
		switch (order.getStatus()) {
			case WAIT_FOR_CONFIRM:
				if (order.getPaymentMethod().equals(EPaymentMethod.ONLINE_PAYMENT_MOMO)
						|| order.getPaymentMethod().equals(EPaymentMethod.ONLINE_PAYMENT_PAYPAL)) {
					paymentService.refundPayment(order);
				}
				order.setStatus(EOrderStatus.CANCELLED);
				break;
			case WAIT_FOR_SEND:
				if (order.getPaymentMethod().equals(EPaymentMethod.ONLINE_PAYMENT_MOMO)
						|| order.getPaymentMethod().equals(EPaymentMethod.ONLINE_PAYMENT_PAYPAL)) {
					paymentService.refundPayment(order);
				}
				// revert stock when order is cancelled
				order.getOrderDetails().stream().forEach(od -> productVariationRepo
						.refundStock(od.getProductVariation().getId(), od.getQuantity()));
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

	private List<BuyerCartDetail> getBuyerCartDetails(Buyer buyer, CreateBuyerOrderRequest data) {
		List<Long> idps = new ArrayList<>();
		List<BuyerCartDetail> cartDetails = new ArrayList<>();
		if (data.getClass().equals(CreateBuyerOrderFromCartRequest.class)) {
			idps.addAll(((CreateBuyerOrderFromCartRequest) data).getIdProductVariations());
			cartDetails = buyerCartDetailRepo
					.findAllById(idps.stream().map(id -> new BuyerCartDetailPK(buyer.getId(), id))
							.collect(Collectors.toList()));
			List<Long> idpvFounds = cartDetails.stream().map(cd -> cd.getProductVariation().getId())
					.collect(Collectors.toList());
			idps.stream().forEach(id -> {
				if (!idpvFounds.contains(id)) {
					throw new InvalidInputDataException(
							"No Product Variation found with given id " + id);
				}
			});
			buyerCartDetailRepo.deleteAllInBatch(cartDetails); // delete cart item from cart
		} else if (data.getClass().equals(CreateBuyerOrderFromProductRequest.class)) {
			idps.add(((CreateBuyerOrderFromProductRequest) data).getIdProductVariation());
			cartDetails.add(new BuyerCartDetail(buyer,
					productVariationRepo.findById(idps.get(0))
							.orElseThrow(() -> new InvalidInputDataException(
									"No Product Variation found with given id " + idps.get(0))),
					((CreateBuyerOrderFromProductRequest) data).getQuantity()));
		}
		return cartDetails;
	}

	private OrderDetail createOrderDetail(BuyerCartDetail cartDetail, Order order) {
		checkAvailable(cartDetail);
		return new OrderDetail(order, cartDetail.getProductVariation(), cartDetail.getQuantity(),
				cartDetail.getProductVariation().getPrice());
	}

	private void checkAvailable(BuyerCartDetail cartDetail) {
		if (cartDetail.getProductVariation().getStatus() != EProductVariationStatus.ENABLED) {
			throw new InvalidInputDataException("Product Variation with id "
					+ cartDetail.getProductVariation().getId() + " is not available");
		}
		if (cartDetail.getProductVariation().getProduct().getStatus() != EProductStatus.ENABLED) {
			throw new InvalidInputDataException("Product with id "
					+ cartDetail.getProductVariation().getProduct().getId() + " is not available");
		}
		if (cartDetail.getProductVariation().getAvailableQuantity() < cartDetail.getQuantity()) {
			throw new InvalidInputDataException("Stock is not enough for product variation with id "
					+ cartDetail.getProductVariation().getId());
		}
	}
}
