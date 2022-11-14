package net.gymsrote.config;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {
	@Bean
	public ModelMapper modelMapper() {
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		// Converter<MediaResource, String> mediaResourceCvt = c -> {
		// if (c.getSource() == null)
		// return "";
		// else
		// return c.getSource().getUrl();
		// };
		// Converter<List<ProductVariation>, List<String>> tierVariationsCvt = c -> {
		// if (c.getSource() == null)
		// return null;
		// else
		// return c.getSource().stream().map(ProductVariation::getTier).distinct().toList();
		// };
		// Converter<String, Boolean> emptyPasswordCvt = c -> {
		// return StringUtils.isBlank(c.getSource());
		// };
		// var lstChildProductCategoryCvt =
		// generateListConverter(ProductCategory.class, ProductCategoryDTO.class, mapper);
		// var lstProductImageCvt =
		// generateListConverter(ProductImage.class, ProductImageDTO.class, mapper);
		// var lstProductReviewImageCvt = generateListConverter(ProductReviewImage.class,
		// ProductReviewImageDTO.class, mapper);
		// var lstProductVariationCvt =
		// generateListConverter(ProductVariation.class, ProductVariationDTO.class, mapper);
		// var lstOrderDetailCvt =
		// generateListConverter(OrderDetail.class, OrderDetailDTO.class, mapper);
		// mapper.createTypeMap(Buyer.class, BuyerDTO.class).addMappings(m -> {
		// m.using(mediaResourceCvt).map(Buyer::getAvatar, BuyerDTO::setAvatar);
		// m.using(emptyPasswordCvt).map(src -> src.getUser().getPassword(),
		// BuyerDTO::setEmptyPassword);
		// m.map(src -> src.getUser().getUsername(), BuyerDTO::setUsername);
		// m.map(src -> src.getUser().getFullname(), BuyerDTO::setFullname);
		// m.map(src -> src.getUser().getPhone(), BuyerDTO::setPhone);
		// m.map(src -> src.getUser().getEmail(), BuyerDTO::setEmail);
		// m.map(src -> src.getUser().getFullname(), BuyerDTO::setFullname);
		// });
		// mapper.createTypeMap(ProductCategory.class, ProductCategoryDTO.class).addMappings(m -> {
		// m.using(lstChildProductCategoryCvt).map(ProductCategory::getChild,
		// ProductCategoryDTO::setChild);
		// m.map(src -> src.getParent().getId(), ProductCategoryDTO::setIdParent);
		// });
		// mapper.createTypeMap(ProductCategory.class, ProductCategoryDetailDTO.class)
		// .addMappings(m -> {
		// m.map(src -> src.getParent().getId(), ProductCategoryDetailDTO::setIdParent);
		// });
		// mapper.createTypeMap(Product.class, ProductDetailDTO.class).addMappings(m -> {
		// m.using(lstProductImageCvt).map(Product::getImages, ProductDetailDTO::setImages);
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
		// mapper.createTypeMap(ProductImage.class, ProductImageDTO.class).addMappings(m -> {
		// m.map(src -> src.getProduct().getId(), ProductImageDTO::setIdProduct);
		// m.map(src -> src.getMedia().getUrl(), ProductImageDTO::setUrl);
		// m.map(src -> src.getMedia().getResourceType(), ProductImageDTO::setResourceType);
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
		// mapper.createTypeMap(ProductVariation.class, ProductVariationDTO.class).addMappings(m ->
		// {
		// m.map(src -> src.getProduct().getId(), ProductVariationDTO::setIdProduct);
		// });
		// mapper.createTypeMap(BuyerCartDetail.class, BuyerCartDetailDTO.class).addMappings(m -> {
		// m.map(src -> src.getBuyer().getId(), BuyerCartDetailDTO::setIdBuyer);
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
