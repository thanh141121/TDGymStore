package net.gymsrote.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//import net.gymsrote.controller.TestRequest;
//import net.gymsrote.controller.advice.exception.InvalidInputDataException;
//import net.gymsrote.controller.payload.dto.ProductCategoryDTO;
//import net.gymsrote.controller.payload.request.productcategory.CreateProductCategoryRequest;
//import net.gymsrote.controller.payload.request.productcategory.UpdateProductCategoryRequest;
//import net.gymsrote.controller.payload.response.DataResponse;
//import net.gymsrote.controller.payload.response.ListResponse;
//import net.gymsrote.entity.product.ProductCategory;
//import net.gymsrote.repository.ProductCategoryRepo;
//import net.gymsrote.service.util.ServiceUtils;
//import net.gymsrote.entity.EnumEntity.EProductCategoryStatus;

@Service
public class ProductCategoryService {
//	@Autowired
//	LogService logService;
//
//	@Autowired
//	ProductCategoryRepo productCategoryRepo;
//
//	@Autowired
//	ServiceUtils serviceUtils;
//
//	public DataResponse<ProductCategoryDTO> get(Long id, boolean isBuyer) {
//		ProductCategory category = productCategoryRepo.findById(id).orElseThrow(
//				() -> new InvalidInputDataException("No product category found with given id"));
//		if (isBuyer
//				&& serviceUtils.checkStatusProductCategory(category, EProductCategoryStatus.BANNED))
//			throw new InvalidInputDataException("Category has been banned");
//		return serviceUtils.convertToDataResponse(category, ProductCategoryDTO.class);
//	}
//
//	public ListResponse<ProductCategoryDTO> getAllRootCategories(boolean isBuyer) {
//		if (isBuyer)
//			return serviceUtils.convertToListResponse(
//					productCategoryRepo.buyerFindAllRootCategories(), ProductCategoryDTO.class);
//		else
//			return serviceUtils.convertToListResponse(productCategoryRepo.findAllRootCategories(),
//					ProductCategoryDTO.class);
//	}
//
//	public DataResponse<ProductCategoryDTO> create(Long idUser, CreateProductCategoryRequest data) {
//		ProductCategory category;
//		if (data.getIdParent() != null) {
//			ProductCategory parent = productCategoryRepo.findById(data.getIdParent()).orElseThrow(
//					() -> new InvalidInputDataException("Invalid parent product category id"));
//			category = new ProductCategory(parent, data.getName());
//		} else {
//			category = new ProductCategory(data.getName());
//		}
//		category = productCategoryRepo.saveAndFlush(category);
//		logService.logInfo(idUser,
//				String.format("New category has been created with id %d", category.getId()));
//		return serviceUtils.convertToDataResponse(category, ProductCategoryDTO.class);
//	}
//
//	public DataResponse<ProductCategoryDTO> update(Long idUser, Long id,
//			UpdateProductCategoryRequest data) {
//		ProductCategory category = productCategoryRepo.findById(id).orElseThrow(
//				() -> new InvalidInputDataException("No product category found with given id"));
//		if (data.getIdParent() != null
//				&& !category.getParent().getId().equals(data.getIdParent())) {
//			if (productCategoryRepo.existsById(data.getIdParent())) {
//				throw new InvalidInputDataException("Invalid parent product category id");
//			} else {
//				category.setParent(productCategoryRepo.getReferenceById(data.getIdParent()));
//			}
//		}
//		if (StringUtils.isNotBlank(data.getName()) && !category.getName().equals(data.getName())) {
//			category.setName(data.getName());
//		}
//		if (data.getStatus() != null && !category.getStatus().equals(data.getStatus())) {
//			category.setStatus(data.getStatus());
//		}
//		logService.logInfo(idUser,
//				String.format("Category with id %d has been edited", category.getId()));
//		return serviceUtils.convertToDataResponse(productCategoryRepo.save(category),
//				ProductCategoryDTO.class);
//	}
//	/** For data generation **/
//	/*
//	 * public ProductCategory create(TestRequest request) { ProductCategory prev = null; for (int i
//	 * = 0; i < request.getCategoryNames().size(); i++) { if (i == 0) {
//	 * 
//	 * while (true) { try { var pc =
//	 * productCategoryRepo.findByNameAndLevel(request.getCategoryNames().get(i), i); if (pc == null)
//	 * prev = productCategoryRepo.save(new ProductCategory(request.getCategoryNames().get(i))); else
//	 * prev = pc; break; } catch (Exception e) { } }
//	 * 
//	 * } else {
//	 * 
//	 * while (true) { try { var pc =
//	 * productCategoryRepo.findByNameAndLevel(request.getCategoryNames().get(i), i); if (pc == null)
//	 * prev = productCategoryRepo.save(new ProductCategory(prev,
//	 * request.getCategoryNames().get(i))); else prev = pc; break; } catch (Exception e) { } } } }
//	 * return prev; }
//	 */
}
