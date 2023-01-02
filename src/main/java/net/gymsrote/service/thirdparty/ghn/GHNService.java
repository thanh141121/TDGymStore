package net.gymsrote.service.thirdparty.ghn;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cloudinary.Cloudinary;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.gymsrote.controller.advice.exception.UnknownException;
import net.gymsrote.entity.EnumEntity.EPaymentMethod;
import net.gymsrote.entity.order.Order;
import net.gymsrote.service.thirdparty.ghn.GHNPOJOCreate.Item;
import net.gymsrote.service.utils.HttpRequest;

@Getter @Setter
@Service
public class GHNService {	
	
	@Autowired
	HttpRequest httpRequest;
	
	@Autowired
	GHNConfig ghnConfig;
	
	public String createShipmentGHN(Order o) {
		try {
			String[] address = o.getAddressDetail().split(", ");
			int payment_type_id = 1;
			String client_order_code = o.getId().toString();
			String to_name = o.getReceiverName();
			String to_phone = o.getReceiverPhone();
			String to_address = address[0];
			String to_ward_name = address[1];
			String to_district_name = address[2];
			String to_province_name = address[3];
			int cod_amount = 0;
			int insurance_value = Integer.parseInt(o.getPrice().toString()) ;
			if(o.getPaymentMethod().equals(EPaymentMethod.OFFLINE_CASH_ON_DELIVERY))
			{
				cod_amount = insurance_value;
				payment_type_id = 2;
			}
			List<Item> items = new ArrayList<>();
			o.getOrderDetails().stream().forEach( od -> {
				items.add(
						new Item(od.getProductVariation().getVariationName(),
								Integer.parseInt(od.getQuantity().toString())
						)
						);
			});
			
			int service_id ;
			
			HttpHeaders headers = new HttpHeaders();
			headers.set("Token", ghnConfig.getToken());
			
			service_id = getServiceID(headers, ghnConfig.getShopId(), ghnConfig.getFromDistrict(), o.getToDistrict());
			
			GHNPOJOCreate bean = new GHNPOJOCreate(
					payment_type_id, 
					client_order_code, 
					to_name, 
					to_phone, 
					to_address, 
					to_ward_name, 
					to_district_name, 
					to_province_name, 
					cod_amount, 
					to_province_name, 
					insurance_value, 
					service_id,
					items);
			HttpEntity<GHNPOJOCreate> request = new HttpEntity<>(bean, headers);
			ResponseEntity<CreateResponse> resp = httpRequest.postWithClassResponse(
					ghnConfig.getUrl().getCreate(),
					request,
					CreateResponse.class
					);
			String order_code = resp.getBody().getData().getOrder_code();
//			Map<String, Object> m = new ObjectMapper().readValue(resp, HashMap.class);
//			String m1 = m.get("data").toString();
//			Map<String, Object> n = new ObjectMapper().readValue(m.get("data").toString(), HashMap.class);
			return order_code;
			
		}catch(Exception e) {
			throw new UnknownException("Somgthing was wrong in process of creating order with GHN API!" + e.getMessage());
		}
		
	}
	
	@Getter @Setter
	@NoArgsConstructor
	public static class AvailableService {
		private int service_id;
		private String service_type_id;
		private String short_name;
	}
	
	@Getter @Setter
	@AllArgsConstructor
	public static class GetServiceIDRequest {
		private int shop_id;
		private int from_district;
		private int to_district;
	}
	
	@Getter @Setter
	@AllArgsConstructor
	@NoArgsConstructor
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class ServiceIDRespones {
		private String message;
		private List<AvailableService> data;
	}
	
	@Getter @Setter
	@AllArgsConstructor
	@NoArgsConstructor
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class CreateDataResponse{
		private String order_code;
	}
	
	@Getter @Setter
	@AllArgsConstructor
	@NoArgsConstructor
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class CreateResponse {
		private CreateDataResponse data;
	}
	
	public int getServiceID(HttpHeaders headers, int shopId, int from_district, int to_district) {
		try {
			GetServiceIDRequest bean  =  new GetServiceIDRequest(shopId, from_district, to_district);
			HttpEntity<GetServiceIDRequest> request = new HttpEntity<>(bean, headers);
			ResponseEntity<ServiceIDRespones> resp = 
					httpRequest.postWithClassResponse(ghnConfig.getUrl().getAvailableService(), 
							request, 
							ServiceIDRespones.class);
			return resp.getBody().getData().get(0).getService_id();
		}catch (Exception e) {
			throw new UnknownException("Something was wrong in getting service id: " + e.getMessage());
		}
	}
}
