package net.gymsrote.service.thirdparty.ghn;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import com.cloudinary.Cloudinary;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Getter;
import lombok.Setter;
import net.gymsrote.controller.advice.exception.UnknownException;
import net.gymsrote.entity.order.Order;
import net.gymsrote.service.utils.HttpRequest;

@Getter @Setter
@Service
public class GHNService {	
	
	@Autowired
	HttpRequest httpRequest;
	
	@Autowired
	GHNConfig ghnConfig;
	
	public void createShipmentGHN(Order o) {
		String resp = "";
		try {
			String[] address = o.getAddressDetail().split(", ");
			String client_order_code = o.getId().toString();
			String to_name = o.getReceiverName();
			String to_phone = o.getReceiverPhone();
			String to_address = address[0];
			String to_ward_name = address[1];
			
			HttpHeaders headers = new HttpHeaders();
			headers.set("Token", ghnConfig.getToken());
			GHNPOJOCreate bean = new GHNPOJOCreate();
			HttpEntity<GHNPOJOCreate> request = new HttpEntity<>(bean, headers);
			resp = httpRequest.postJson(
					ghnConfig.getUrl().getCreate(),
					request
					);

			Map<String, Object> m = new ObjectMapper().readValue(resp, HashMap.class);
			//System.out.println( (String)m.get("data"));;
			
		}catch(Exception e) {
			throw new UnknownException("Somgthing was wrong in process of creating order with GHN API!" + e.getMessage());
		}
		
	}
}
