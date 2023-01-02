package net.gymsrote.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.gymsrote.controller.advice.exception.InvalidInputDataException;
import net.gymsrote.controller.payload.request.CreateCategoryRequest;
import net.gymsrote.controller.payload.response.DataResponse;
import net.gymsrote.controller.payload.response.ListResponse;
import net.gymsrote.controller.payload.response.ListWithPagingResponse;
import net.gymsrote.dto.ProductCategoryDTO;
import net.gymsrote.entity.EnumEntity.EProductCategoryStatus;
import net.gymsrote.entity.product.ProductCategory;
import net.gymsrote.repository.ProductCategoryRepo;
import net.gymsrote.service.utils.ServiceUtils;


@Service
public class ProductCategoryService {
	@Autowired
	ProductCategoryRepo productCategoryRepo;
	
	@Autowired
	ProductService productService;

	@Autowired
	ServiceUtils serviceUtils;

	public DataResponse<ProductCategoryDTO> get(Long id, boolean isBuyer) {
		ProductCategory category = productCategoryRepo.findById(id).orElseThrow(
				() -> new InvalidInputDataException("No product category found with given id"));
		if (isBuyer && serviceUtils.checkStatusProductCategory(category, EProductCategoryStatus.DISABLED))
			throw new InvalidInputDataException("Category has been disabled");
		return serviceUtils.convertToDataResponse(productCategoryRepo.save(category), ProductCategoryDTO.class);
	}

	public ListResponse<ProductCategoryDTO> getAllCategoriesForUser() {
		return serviceUtils.convertToListResponse(
				productCategoryRepo.findAllCategories(), ProductCategoryDTO.class);
	}
	
	public void updateProductStatus(Long idCategory, EProductCategoryStatus status) {
		ProductCategory category = productCategoryRepo.findById(idCategory).orElseThrow(
				() -> new InvalidInputDataException("No product category found with given id"));

		category.setStatus(status);
		productCategoryRepo.save(category);
	}
	
	@Transactional
	public DataResponse<?> update(Long id, String name){
		ProductCategory category = productCategoryRepo.findById(id).orElseThrow(
				() -> new InvalidInputDataException("No product category found with given id"));
		category.setName(name);
		return serviceUtils.convertToDataResponse(category, ProductCategoryDTO.class);
	}
	


	public ListWithPagingResponse<ProductCategoryDTO> getAll(Pageable pageable) {
		return serviceUtils.convertToListResponse(
				productCategoryRepo.findAll(pageable), ProductCategoryDTO.class);
	}

	public DataResponse<ProductCategoryDTO> create(CreateCategoryRequest data) {
		if (Boolean.TRUE.equals(productCategoryRepo.existsByName(data.getName())))
			throw new InvalidInputDataException("Category already exists");

		return serviceUtils.convertToDataResponse(productCategoryRepo.save(new ProductCategory(data.getName())),
				ProductCategoryDTO.class);
	}

}
