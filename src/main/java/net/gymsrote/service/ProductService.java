package net.gymsrote.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import net.gymsrote.config.login.UserDetailsImpl;
import net.gymsrote.controller.advice.exception.InvalidInputDataException;
import net.gymsrote.controller.payload.request.product.CreateProductReq;
import net.gymsrote.controller.payload.request.product.CreateVariationReq;
import net.gymsrote.controller.payload.request.product.UpdateProductRequest;
import net.gymsrote.controller.payload.response.BaseResponse;
import net.gymsrote.controller.payload.response.DataResponse;
import net.gymsrote.controller.payload.response.ListResponse;
import net.gymsrote.controller.payload.response.ListWithPagingResponse;
import net.gymsrote.dto.ProductDetailDTO;
import net.gymsrote.dto.ProductGeneralDetailDTO;
import net.gymsrote.entity.MediaResource;
import net.gymsrote.entity.EnumEntity.EFolderMediaResource;
import net.gymsrote.entity.EnumEntity.EProductCategoryStatus;
import net.gymsrote.entity.EnumEntity.EProductStatus;
import net.gymsrote.entity.EnumEntity.EUserRole;
import net.gymsrote.entity.product.Product;
import net.gymsrote.entity.product.ProductCategory;
import net.gymsrote.entity.product.ProductImage;
import net.gymsrote.entity.product.ProductVariation;
import net.gymsrote.entity.user.User;
import net.gymsrote.repository.ProductCategoryRepo;
import net.gymsrote.repository.ProductRepo;
import net.gymsrote.repository.ProductVariationRepo;
import net.gymsrote.service.thirdparty.CloudinaryService;
import net.gymsrote.service.utils.ServiceUtils;
import net.gymsrote.utility.PlatformPolicyParameter;


@Service
public class ProductService {
	@Autowired
	ProductRepo productRepo;

	@Autowired
	ProductImageService productImageService;

	@Autowired
	MediaResourceService mediaResourceService;
	
	@Autowired
	CloudinaryService cloudinaryService;

	@Autowired
	ProductVariationService productVariationService;

	@Autowired
	ProductCategoryRepo productCategoryRepo;

	@Autowired
	ProductVariationRepo productVariationRepo;

	@Autowired
	ServiceUtils serviceUtils;
	
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void updateProductStatus(Long idCategory, EProductStatus status) {
		productRepo.updateStatusProduct(idCategory, status);
	}

	public ListResponse<ProductGeneralDetailDTO> getTopSaleProduct() {
		return serviceUtils.convertToListResponse(
				productRepo.findTop10ByStatusOrderByMaxDiscountDesc(EProductStatus.ENABLED),
				ProductGeneralDetailDTO.class);
	}

	public ListResponse<ProductGeneralDetailDTO> getTopLastedProduct() {
		return serviceUtils.convertToListResponse(
				productRepo.findTop10ByStatusOrderByCreatedDateDesc(EProductStatus.ENABLED),
				ProductGeneralDetailDTO.class);
	}

	public ListResponse<ProductGeneralDetailDTO> getTopVisitProduct() {
		return serviceUtils.convertToListResponse(
				productRepo.findTop10ByStatusOrderByNvisitDesc(EProductStatus.ENABLED),
				ProductGeneralDetailDTO.class);
	}

	public ListResponse<ProductGeneralDetailDTO> getTopSoldProduct() {
		return serviceUtils.convertToListResponse(
				productRepo.findTop10ByStatusOrderByNsoldDesc(EProductStatus.ENABLED),
				ProductGeneralDetailDTO.class);
	}

	@SuppressWarnings("null")
	@Transactional
	public DataResponse<ProductDetailDTO> getById(Long id, boolean isBuyer) {
		Product p = productRepo.findById(id).orElseThrow(() -> new InvalidInputDataException("No product found with given id"));
		
		if (isBuyer && 
				(p.getStatus() == EProductStatus.DISABLED ))
			throw new InvalidInputDataException("No product found with given id");
		else {
			if (isBuyer)
				productRepo.updateVisitCount(id);
		}
		
		return serviceUtils.convertToDataResponse(
				p,
				ProductDetailDTO.class
			);
	}

	@SuppressWarnings("null")
	@Transactional(rollbackFor = { InvalidInputDataException.class })
	public DataResponse<ProductDetailDTO> create(CreateProductReq product,
			MultipartFile avatar, List<MultipartFile> images) throws IOException {
		//a list to save PublicId to handle before rollback
		List<String> tempCloudianaryImage = new ArrayList<>();
		
		try{
			ProductCategory productCategory = productCategoryRepo.findById(
					product.getCategoryId()).orElseThrow(() -> new InvalidInputDataException("Category not found"));
			
			if (product.getVariations().size() < PlatformPolicyParameter.MIN_ALLOWED_PRODUCT_VARIATION) {
				throw new InvalidInputDataException(
						String.format("At least %d product variation(s) is required",
								PlatformPolicyParameter.MIN_ALLOWED_PRODUCT_VARIATION));
			}
			
			Product p = new Product(productCategory,
						product.getName(), 
						product.getDescription(),
						mediaResourceService.save(avatar.getBytes(), EFolderMediaResource.ThumnailProduct), 
						EProductStatus.ENABLED 
						);
			tempCloudianaryImage.add(p.getAvatar().getPublicId());
			
			p = productRepo.saveAndFlush(p);
			List<ProductImage> productImage= p.getImages();
			List<ProductVariation> productVariation = p.getVariations();
			
			//create ProductImage
			if(images != null){
				for(MultipartFile image: images) {
					MediaResource media = mediaResourceService.save(image.getBytes(), EFolderMediaResource.ProductImage);
					if(media != null)
						productImage.add(new ProductImage(p,media));
					tempCloudianaryImage.add(media.getPublicId());
				}
			}
			
			//create ProductVariation
			for(CreateVariationReq var : product.getVariations()) {
				ProductVariation proVar = productVariationService.create(p.getId(), var);
				productVariation.add(proVar);
				tempCloudianaryImage.add(proVar.getAvatar().getPublicId());
			}
			return serviceUtils.convertToDataResponse(p, ProductDetailDTO.class);
			
		}catch(Exception e){
			//handle cloudinary before rollback
			if(tempCloudianaryImage.size() > 0) {
				for(String publicId : tempCloudianaryImage) {
					cloudinaryService.delete(publicId);
				}
			}
			throw new InvalidInputDataException(e.getMessage());
		}
	}
	
	@Transactional
	public DataResponse<ProductDetailDTO> update(Long id, UpdateProductRequest data,
			MultipartFile avatar) {
		Product p = productRepo.findById(id)
				.orElseThrow(() -> new InvalidInputDataException("No product found with given id"));
		if (data != null) {
			if (StringUtils.isNotEmpty(data.getName().trim()) && !p.getName().equals(data.getName().trim()))
				p.setName(data.getName());
			if (StringUtils.isNotEmpty(data.getDescription().trim())
					&& !p.getDescription().equals(data.getDescription().trim()))
				p.setDescription(data.getDescription());
			if (data.getIdCategory() != null
					&& !p.getCategory().getId().equals(data.getIdCategory())) {
			if (!productCategoryRepo.existsById(data.getIdCategory()))
					throw new InvalidInputDataException("No category found with given id");
				p.setCategory(productCategoryRepo.getReferenceById(data.getIdCategory()));
			}
			if (data.getStatus() != null && !p.getStatus().equals(data.getStatus()))
				p.setStatus(data.getStatus());
		}
		if (avatar != null) {
			serviceUtils.updateAvatar(p, avatar, EFolderMediaResource.ThumnailProduct);
		}
		p = productRepo.save(p);
		return serviceUtils.convertToDataResponse(p, ProductDetailDTO.class);
	}

	public ListWithPagingResponse<ProductDetailDTO> getAllProducts(Pageable pageable, Boolean isBuyer, Long categoryId) {
		if(categoryId != null) {
			return serviceUtils.convertToListResponse(productRepo.findAllByCategoryId(categoryId, pageable), ProductDetailDTO.class);
		}else {
			if (Boolean.TRUE.equals(isBuyer)) {
				return serviceUtils.convertToListResponse(productRepo.findAllByStatus(EProductStatus.ENABLED, pageable), ProductDetailDTO.class);
			} else {
				return serviceUtils.convertToListResponse(productRepo.findAll(pageable), ProductDetailDTO.class);
			}
		}
	}
	
	public ListWithPagingResponse<ProductGeneralDetailDTO> search(String keyword, Pageable pageable) {
		// Gọi phương thức search() của repository để lấy ra các sản phẩm thỏa mãn điều kiện keyword và status = 0.
        List<Product> products = productRepo.search(keyword);

        // Lọc các sản phẩm theo điều kiện status của Category
        List<Product> filteredProducts = products.stream()
                .filter(product -> product.getCategory().getStatus() == EProductCategoryStatus.ENABLED)
                .collect(Collectors.toList());
		return serviceUtils.convertToListResponse(
				convertListToPage(filteredProducts, pageable),
				ProductGeneralDetailDTO.class);
	}

	public Page<Product> convertListToPage(List<Product> productList, Pageable pageable) {

        int currentPage = pageable.getPageNumber() + 1; // Lấy trang hiện tại từ Pageable
        int pageSize = pageable.getPageSize();

        // Tính tổng số trang và tạo đối tượng PageImpl<Product> cho trang hiện tại
        int start = (currentPage - 1) * pageSize;
        int end = Math.min((start + pageSize), productList.size());
        if (start > productList.size() || start < 0 || start > end) {
            return new PageImpl<>(Collections.emptyList(), pageable, 0);
        }

        List<Product> pageContent = productList.subList(start, end);
        long totalElements = productList.size();
        int totalPages = (int) Math.ceil((double) totalElements / (double) pageSize);

        return new PageImpl<>(pageContent, pageable, totalElements);
    }

	public BaseResponse updateStatus(Long id, EProductStatus status) {
		if(productRepo.updateStatus(id, status) == 1) {
			return new BaseResponse();
		} else {
			throw new InvalidInputDataException("Update status failed");
		}
	}
	
	public ListResponse<ProductGeneralDetailDTO> recommendContentBased(int id) throws InterruptedException{
	    StringBuilder resultBuilder = new StringBuilder();
	    List<Long> recommendations = new ArrayList<>();
	    try {
	        // Xây dựng quá trình thực thi file Python
	        ProcessBuilder processBuilder = new ProcessBuilder("python", "D:/F8/testpython/contentBasedFilter2.py", String.valueOf(id));
	        Process process = processBuilder.start();

	        // Đọc dữ liệu từ InputStream và ErrorStream của quá trình thực thi
	        BufferedReader inputStreamReader = new BufferedReader(new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8));
	        BufferedReader errorStreamReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));

	        String line = inputStreamReader.readLine(); // Đọc dòng duy nhất từ file Python
	        String[] productIdStrings = line.split(","); // Phân tách chuỗi thành mảng các chuỗi productId

	        // Chuyển đổi các chuỗi productId thành số nguyên và thêm vào danh sách recommendations
	        for (String productIdString : productIdStrings) {
	            recommendations.add(Long.parseLong(productIdString.trim()));
	        }
	        
	        //Chuyển sang list product
	        
	        List<Product> products = new ArrayList<>();
	        for (Long idPro : recommendations) {
	            Product product = productRepo.findById(idPro).orElse(null);
	            if (product != null) {
	                products.add(product);
	            }
	        }
	        
	        return serviceUtils.convertToListResponse(products, ProductGeneralDetailDTO.class);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
        return null;
    }

}
