package net.gymsrote.config;

import java.util.List;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import net.gymsrote.dto.CartDetailDTO;
import net.gymsrote.dto.MediaResourceDTO;
import net.gymsrote.dto.ProductCartDetailDTO;
import net.gymsrote.dto.ProductDetailDTO;
import net.gymsrote.dto.ProductImageDTO;
import net.gymsrote.dto.ProductVariationDTO;
import net.gymsrote.dto.UserAddressDTO;
import net.gymsrote.dto.UserDTO;
import net.gymsrote.dto.UserRoleDTO;
import net.gymsrote.dto.address.DistrictDTO;
import net.gymsrote.dto.address.ProvinceDTO;
import net.gymsrote.dto.address.WardDTO;
import net.gymsrote.entity.MediaResource;
import net.gymsrote.entity.address.District;
import net.gymsrote.entity.address.Province;
import net.gymsrote.entity.address.Ward;
import net.gymsrote.entity.cart.CartDetail;
import net.gymsrote.entity.product.Product;
import net.gymsrote.entity.product.ProductImage;
import net.gymsrote.entity.product.ProductVariation;
import net.gymsrote.entity.user.User;
import net.gymsrote.entity.user.UserAddress;
import net.gymsrote.entity.user.UserRole;

@Configuration
public class ModelMapperConfig {
	@Bean
	public ModelMapper modelMapper() {
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

		Object lstProductImageCvt = generateListConverter(ProductImage.class, ProductImageDTO.class, mapper);
		// var lstProductReviewImageCvt = generateListConverter(ProductReviewImage.class,
		// ProductReviewImageDTO.class, mapper);
		Object lstProductVariationCvt =
		generateListConverter(ProductVariation.class, ProductVariationDTO.class, mapper);
		
		Object lstUserAddressCvt = 
				generateListConverter(UserAddress.class, UserAddressDTO.class, mapper);
		
		
		//adress
		mapper.createTypeMap(District.class, DistrictDTO.class);
		mapper.createTypeMap(Ward.class, WardDTO.class);
		mapper.createTypeMap(Province.class, ProvinceDTO.class);
		
		//USERDTO
		mapper.createTypeMap(User.class, UserDTO.class).addMappings(m -> {
			m.map(src -> src.getRole().getName(), UserDTO::setRole);
			m.using((Converter<?, ?>) lstUserAddressCvt).map(src -> src.getUserAddress(), UserDTO::setUserAddress);
		});		
		
		//USER ADDRESS DTO
		mapper.createTypeMap(UserAddress.class, UserAddressDTO.class).addMappings(m -> {
			m.map(src -> src.toString(),
					UserAddressDTO::setAddressString);
		});
		
		//MEDIA RESOURCE
		mapper.createTypeMap(MediaResource.class, MediaResourceDTO.class);
		
		//PRODUCT CART DETAIL -> CART DETAIL
		mapper.createTypeMap(Product.class, ProductCartDetailDTO.class);
		
		// mapper.createTypeMap(UserRole.class, UserRoleDTO.class).addMappings(m -> {
		// 	m.map(src -> src.getRoles().getName(), UserRoleDTO::setName);
		// 	m.map(src -> src.getRoles().getId(), UserRoleDTO::setId);
		// });
		
		//PRODUCT DETAIL DTO
		 mapper.createTypeMap(Product.class, ProductDetailDTO.class).addMappings(m -> {
		 m.using((Converter<?, ?>) lstProductImageCvt).map(Product::getImages, ProductDetailDTO::setImages);
		 m.using((Converter<?, ?>) lstProductVariationCvt).map(Product::getVariations,
		 ProductDetailDTO::setVariations);
		 });
		 
		 //PRODUCT IMAGED DTO
		 mapper.createTypeMap(ProductImage.class, ProductImageDTO.class).addMappings(m -> {
		 m.map(src -> src.getProduct().getId(), ProductImageDTO::setProductId);
		 m.map(src -> src.getMedia().getUrl(), ProductImageDTO::setUrl);
		 m.map(src -> src.getMedia().getResourceType(), ProductImageDTO::setResourceType);
		 });
		 
		 //PRODUCT VARIATION DTO
		mapper.createTypeMap(ProductVariation.class, ProductVariationDTO.class).addMappings(m ->
		{
		m.map(src -> src.getProduct().getId(), ProductVariationDTO::setIdProduct);
		});
		 
		// m.using(lstProductVariationCvt).map(Product::getVariations,
		// ProductDetailDTO::setVariations);
		// m.using(mediaResourceCvt).map(Product::getAvatar, ProductDetailDTO::setAvatar);
		// m.using(mediaResourceCvt).map(Product::getAvatar, ProductDetailDTO::setAvatar);
		// m.using(tierVariationsCvt).map(Product::getVariations,
		// ProductDetailDTO::setTierVariations);
		// m.<List<ProductCategoryGeneralDTO>> map(Product::getParents,
		// (dst, value) -> dst.getCategory().setParents(value));
		// m.map(Product::getTotalRatingTimes, ProductDetailDTO::setTotalRatingTimes);
		// });
		
		// mapper.createTypeMap(Product.class, ProductGeneralDetailDTO.class).addMappings(m -> {
		// m.using(mediaResourceCvt).map(Product::getAvatar, ProductGeneralDetailDTO::setAvatar);
		// });
		 
		// mapper.createTypeMap(ProductReview.class, ProductReviewDTO.class).addMappings(m -> {
		// m.map(src -> src.getBuyer().getId(), ProductReviewDTO::setIdBuyer);
		// m.map(src -> src.getBuyer().getUsername(), ProductReviewDTO::setBuyerUsername);
		// m.using(mediaResourceCvt).map(src -> src.getBuyer().getAvatar(),
		// ProductReviewDTO::setBuyerAvatar);
		// m.using(lstProductReviewImageCvt).map(ProductReview::getImages,
		// ProductReviewDTO::setImages);
		// });/***/
		 
		// mapper.createTypeMap(ProductReviewImage.class, ProductReviewImageDTO.class)
		// .addMappings(m -> {
		// m.map(src -> src.getMedia().getUrl(), ProductReviewImageDTO::setUrl);
		// m.map(src -> src.getMedia().getResourceType(),
		// ProductReviewImageDTO::setResourceType);
		// });
		
		//CART DETAIL DTO
		 mapper.createTypeMap(CartDetail.class, CartDetailDTO.class).addMappings(m -> {
		 m.map(src -> src.getBuyer().getId(), CartDetailDTO::setUserId);
		 m.map(src -> src.getProductVariation().getProduct(),
					 CartDetailDTO::setProductDetail);
		 });
		// m.map(src -> src.getProductVariation().getProduct(),
		// BuyerCartDetailDTO::setProductDetail);
		// });
		
		// mapper.createTypeMap(BuyerFavouriteProduct.class, BuyerFavouriteProductDTO.class)
		// .addMappings(m -> {
		// m.map(BuyerFavouriteProduct::getBuyer, BuyerFavouriteProductDTO::setBuyer);
		// });
		
		// mapper.createTypeMap(Log.class, LogDTO.class).addMappings(m -> {
		// m.map(src -> src.getUser().getUsername(), LogDTO::setUsername);
		// });
		// /*******/
		
		// mapper.createTypeMap(Product.class, OrderProductGeneralDetailDTO.class).addMappings(m ->
		// {
		// m.using(mediaResourceCvt).map(Product::getAvatar,
		// OrderProductGeneralDetailDTO::setAvatar);
		// });
		
		// mapper.createTypeMap(Order.class, AdminOrderDTO.class).addMappings(m -> {
		// m.map(src -> src.getDeliveryAddress().getId(), AdminOrderDTO::setIdDeliveryAddress);
		// m.using(lstOrderDetailCvt).map(Order::getOrderDetails, AdminOrderDTO::setOrderDetails);
		// });
		
		// mapper.createTypeMap(Order.class, BuyerOrderDTO.class).addMappings(m -> {
		// m.map(src -> src.getDeliveryAddress().getId(), BuyerOrderDTO::setIdDeliveryAddress);
		// m.using(lstOrderDetailCvt).map(Order::getOrderDetails, BuyerOrderDTO::setOrderDetails);
		// });
		
		// mapper.createTypeMap(Order.class, AdminDetailedOrderDTO.class).addMappings(m -> {
		// m.using(lstOrderDetailCvt).map(Order::getOrderDetails,
		// AdminDetailedOrderDTO::setOrderDetails);
		// });
		
		// mapper.createTypeMap(Order.class, BuyerDetailedOrderDTO.class).addMappings(m -> {
		// m.using(lstOrderDetailCvt).map(Order::getOrderDetails,
		// BuyerDetailedOrderDTO::setOrderDetails);
		// });
		
		return mapper;
	}

	private <T, V> Converter<List<T>, List<V>> generateListConverter(Class<T> src, Class<V> dst,
			ModelMapper mapper) {
		return c -> {
			if (c.getSource() == null)
				return null;
			else
				return c.getSource().stream().map(m -> mapper.map(m, dst)).toList();
		};
	}
}
