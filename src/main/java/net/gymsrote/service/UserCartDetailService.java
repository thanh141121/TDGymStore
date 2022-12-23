package net.gymsrote.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import net.gymsrote.controller.advice.exception.InvalidInputDataException;
import net.gymsrote.controller.payload.request.CreateCartDetailRequest;
import net.gymsrote.controller.payload.response.BaseResponse;
import net.gymsrote.controller.payload.response.DataResponse;
import net.gymsrote.controller.payload.response.ListResponse;
import net.gymsrote.dto.CartDetailDTO;
import net.gymsrote.entity.cart.CartDetail;
import net.gymsrote.entity.cart.CartDetailKey;
import net.gymsrote.entity.user.User;
import net.gymsrote.repository.ProductVariationRepo;
import net.gymsrote.repository.UserCartDetailRepo;
import net.gymsrote.repository.UserRepo;
import net.gymsrote.service.utils.ServiceUtils;

//import net.gymsrote.controller.advice.exception.InvalidInputDataException;
//import net.gymsrote.controller.payload.dto.BuyerCartDetailDTO;
//import net.gymsrote.controller.payload.request.buyercartdetail.CreateBuyerCartDetailRequest;
//import net.gymsrote.controller.payload.request.buyercartdetail.UpdateBuyerCartDetailRequest;
//import net.gymsrote.controller.payload.response.BaseResponse;
//import net.gymsrote.controller.payload.response.DataResponse;
//import net.gymsrote.controller.payload.response.ListResponse;
//import net.gymsrote.entity.pk.BuyerCartDetailPK;
//import net.gymsrote.entity.user.buyer.Buyer;
//import net.gymsrote.entity.user.buyer.BuyerCartDetail;
//import net.gymsrote.repository.BuyerCartDetailRepo;
//import net.gymsrote.repository.BuyerRepo;
//import net.gymsrote.repository.ProductVariationRepo;
//import net.gymsrote.service.util.ServiceUtils;

@Service
public class UserCartDetailService {
	@Autowired
	UserCartDetailRepo cartDetailRepo;

	@Autowired
	UserRepo userRepo;
	
	@Autowired
	ProductVariationRepo productVariationRepo;
	
	@Autowired
	ServiceUtils serviceUtils;


	public ListResponse<?> getAllByIdBuyer(Long userId) {
		if (!userRepo.existsById(userId)) {
			throw new InvalidInputDataException("Không tìm thấy người dùng với id: " + userId);
		}
		User user  = userRepo.getReferenceById(userId);
		return serviceUtils.convertToListResponse(
				cartDetailRepo.findAllByBuyer(user, Sort.by("productVariation.product")), 
				CartDetailDTO.class);
	}

	public DataResponse<?> create(Long idProductVariation, Long userId,
			CreateCartDetailRequest data) {
		if (!productVariationRepo.existsById(idProductVariation)) {
			throw new InvalidInputDataException(
					"Không tìm thấy Phân loại sản phẩm với id: " + idProductVariation);
		}
		CartDetail cartDetail;
		if(!cartDetailRepo.existsById(new CartDetailKey(userId, idProductVariation))) {
			cartDetail = new CartDetail(userRepo.getReferenceById(userId),
					productVariationRepo.getReferenceById(idProductVariation), data.getQuantity());
		}else {
			cartDetail = cartDetailRepo.getReferenceById(new CartDetailKey(userId, idProductVariation));
			cartDetail.setQuantity(cartDetail.getQuantity() + data.getQuantity());
		}
		return serviceUtils.convertToDataResponse(cartDetailRepo.save(cartDetail),
				CartDetailDTO.class);
	}
	
	public DataResponse<?> changeQuantity(Long idProductVariation, Long userId,
			CreateCartDetailRequest data) {
		if (!productVariationRepo.existsById(idProductVariation)) {
			throw new InvalidInputDataException(
					"Không tìm thấy Phân loại sản phẩm với id: " + idProductVariation);
		}
		CartDetail cartDetail = cartDetailRepo.getReferenceById(new CartDetailKey(userId, idProductVariation));
		Long quantity = cartDetail.getQuantity() + data.getQuantity();
		if(cartDetail.getProductVariation().getAvailableQuantity() >= quantity) {
			cartDetail.setQuantity(quantity);
			return serviceUtils.convertToDataResponse(cartDetailRepo.save(cartDetail),
					CartDetailDTO.class);
		}else
			throw new InvalidInputDataException(String.format("Vuợt quá số lượng sản phẩm có sẵn (có sẵn: %s)", 
					cartDetail.getProductVariation().getAvailableQuantity()));
	}
//
//	public DataResponse<BuyerCartDetailDTO> update(Long idProductVariation, Long idBuyer,
//			UpdateBuyerCartDetailRequest data) {
//		BuyerCartDetail cartDetail =
//				buyerCartDetailRepo.findById(new BuyerCartDetailPK(idBuyer, idProductVariation))
//						.orElseThrow(() -> new InvalidInputDataException(
//								"No Cart Detail found with given id " + idProductVariation));
//		cartDetail.setQuantity(data.getQuantity());
//		return serviceUtils.convertToDataResponse(buyerCartDetailRepo.save(cartDetail),
//				BuyerCartDetailDTO.class);
//	}

	public BaseResponse delete(Long idProductVariation, Long idUser) {
		var cartId = new CartDetailKey(idUser, idProductVariation);
		if (!cartDetailRepo.existsById(cartId)) {
			throw new InvalidInputDataException(
					"Không tồn tại sản phẩm với id: " + idProductVariation 
					+ " trong giỏ hàng của người dùng");
		}
		cartDetailRepo.deleteById(cartId);
		return new BaseResponse();
	}
	
	public Long countByUser(Long idUser) {
		return null;
		// return cartDetailRepo.countByUser(userRepo.getReferenceById(idUser));
	}
}
