package net.gymsrote.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

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
public class BuyerCartDetailService {
//	@Autowired
//	BuyerCartDetailRepo buyerCartDetailRepo;
//
//	@Autowired
//	BuyerRepo buyerRepo;
//
//	@Autowired
//	ProductVariationRepo productVariationRepo;
//
//	@Autowired
//	ServiceUtils serviceUtils;
//
//	public DataResponse<BuyerCartDetailDTO> get(Long idProductVariation, Long idBuyer) {
//		BuyerCartDetail cartDetail =
//				buyerCartDetailRepo.findById(new BuyerCartDetailPK(idBuyer, idProductVariation))
//						.orElseThrow(() -> new InvalidInputDataException(
//								"No Cart Detail found with given id " + idProductVariation));
//		return serviceUtils.convertToDataResponse(cartDetail, BuyerCartDetailDTO.class);
//	}
//
//	public ListResponse<BuyerCartDetailDTO> getAllByIdBuyer(Long idBuyer) {
//		if (!buyerRepo.existsById(idBuyer)) {
//			throw new InvalidInputDataException("No Buyer found with given id " + idBuyer);
//		}
//		Buyer buyer = buyerRepo.getReferenceById(idBuyer);
//		return serviceUtils.convertToListResponse(
//				buyerCartDetailRepo.findAllByBuyer(buyer, Sort.by("productVariation.product")),
//				BuyerCartDetailDTO.class);
//	}
//
//	public DataResponse<BuyerCartDetailDTO> create(Long idProductVariation, Long idBuyer,
//			CreateBuyerCartDetailRequest data) {
//		if (!productVariationRepo.existsById(idProductVariation)) {
//			throw new InvalidInputDataException(
//					"No Product Variation found with given id " + idProductVariation);
//		}
//		BuyerCartDetail cartDetail = new BuyerCartDetail(buyerRepo.getReferenceById(idBuyer),
//				productVariationRepo.getReferenceById(idProductVariation), data.getQuantity());
//		return serviceUtils.convertToDataResponse(buyerCartDetailRepo.save(cartDetail),
//				BuyerCartDetailDTO.class);
//	}
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
//
//	public BaseResponse delete(Long idProductVariation, Long idBuyer) {
//		var cartId = new BuyerCartDetailPK(idBuyer, idProductVariation);
//		if (!buyerCartDetailRepo.existsById(cartId)) {
//			throw new InvalidInputDataException(
//					"No Cart Detail found with given id " + idProductVariation);
//		}
//		buyerCartDetailRepo.deleteById(cartId);
//		return new BaseResponse();
//	}
//
//	public DataResponse<Long> countItemByBuyer(Buyer buyer) {
//		return new DataResponse<Long>(buyerCartDetailRepo.countByBuyer(buyer));
//	}
}
