package net.gymsrote.repository.search;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import static org.springframework.data.jpa.domain.Specification.where;

import net.gymsrote.controller.payload.response.ListWithPagingResponse;
import net.gymsrote.dto.ProductDetailDTO;
import net.gymsrote.entity.product.Product;
import net.gymsrote.entity.product.ProductCategory_;
import net.gymsrote.entity.product.Product_;
import net.gymsrote.repository.ProductRepo;
import net.gymsrote.service.utils.ServiceUtils;

@Component
public class CustomProductRepository {
	@Autowired
	ProductRepo productRepo;
	
	@Autowired
	ServiceUtils serviceUtils;
	
    private Specification<Product> nameLike(String name){
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get(Product_.NAME), "%"+name+"%");
    }


    private Specification<Product> pricesAreBetween(Double min, Double max){
        return (root, query, criteriaBuilder)-> criteriaBuilder.between(root.get(Product_.MIN_PRICE), min, max);
    }

    private Specification<Product> belongsToCategory(Long categoryId){
    	
        return (root, query, criteriaBuilder)-> criteriaBuilder.equal(root.get(Product_.CATEGORY).get(ProductCategory_.ID), categoryId);
    }
	
    public ListWithPagingResponse<ProductDetailDTO> getQueryResult(List<Filter> filters){
        if(filters.size()>0) {
            return serviceUtils.convertToListResponse(
            		productRepo.findAll(getSpecificationFromFilters(filters), PageRequest.of(0,1)),
            		ProductDetailDTO.class);
            		
        }else {
//            return productRepo.findAll();
        	return null;
        }
    }
    
    private Specification<Product> getSpecificationFromFilters(List<Filter> filter) {
        Specification<Product> specification = where(createSpecification(filter.remove(0)));
        for (Filter input : filter) {
            specification = specification.and(createSpecification(input));
        }
        return specification;
    }
    
    private Specification<Product> createSpecification(Filter input) {
        switch (input.getOperator()){
            case EQUALS:
                return (root, query, criteriaBuilder) ->
                        criteriaBuilder.equal(root.get(input.getField()),
                        castToRequiredType(root.get(input.getField()).getJavaType(), input.getValue()));
            case NOT_EQ:
                return (root, query, criteriaBuilder) ->
                        criteriaBuilder.notEqual(root.get(input.getField()),
                        castToRequiredType(root.get(input.getField()).getJavaType(), input.getValue()));
            case GREATER_THAN:
                return (root, query, criteriaBuilder) ->
                        criteriaBuilder.gt(root.get(input.getField()),
                        (Number) castToRequiredType(root.get(input.getField()).getJavaType(), input.getValue()));
            case LESS_THAN:
                return (root, query, criteriaBuilder) ->
                        criteriaBuilder.lt(root.get(input.getField()),
                        (Number) castToRequiredType(root.get(input.getField()).getJavaType(), input.getValue()));
            case LIKE:
                return (root, query, criteriaBuilder) ->
                        criteriaBuilder.like(root.get(input.getField()), "%"+input.getValue()+"%");
            case IN:
                return (root, query, criteriaBuilder) ->
                        criteriaBuilder.in(root.get(input.getField()))
                        .value(castToRequiredType(root.get(input.getField()).getJavaType(), input.getValues()));
            default:
                throw new RuntimeException("Operation not supported yet");
        }
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
	private Object castToRequiredType(Class fieldType, String value) {
        if(fieldType.isAssignableFrom(Double.class)){
            return Double.valueOf(value);
        }else if(fieldType.isAssignableFrom(Integer.class)){
            return Integer.valueOf(value);
        }else if(Enum.class.isAssignableFrom(fieldType)){
            return Enum.valueOf(fieldType, value);
        }else if(fieldType.isAssignableFrom(Long.class)){
            return Long.valueOf(value);
        }
        return null;
    }

    private Object castToRequiredType(Class fieldType, List<String> value) {
        List<Object> lists = new ArrayList<>();
        for (String s : value) {
            lists.add(castToRequiredType(fieldType, s));
        }
        return lists;
    }

}
