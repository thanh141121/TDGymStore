package net.gymsrote.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.v3.oas.annotations.Hidden;
import net.gymsrote.service.PaymentService;
import net.gymsrote.service.thirdparty.payment.momo.MomoService;

@Hidden
@RestController
@RequestMapping("/api/payment/momo/notify")
public class MomoNotifyController {
	@Autowired
	PaymentService paymentService;
	
	@Autowired
	MomoService momoService;
	
	@PostMapping
	public void notifyReciever(HttpServletRequest req, HttpServletResponse resp) {
		Map<String, Object> m = null;
		try {
			String body = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));

			m = new ObjectMapper().readValue(body, HashMap.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		int resultCode = -1;
		
		try {
			resultCode = (int)m.get("resultCode");
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		
		if (resultCode == 9000) {
			String momoOrderId = m.get("orderId").toString();
			String amount = m.get("amount").toString();
			String transId = m.get("transId").toString();
			
			if (StringUtils.isNotBlank(momoOrderId)) {
				Long idOrder = decode(momoOrderId);
				momoService.confirmPayment(momoOrderId, amount, transId, idOrder, paymentService.confirm(idOrder));
			}
		}
		resp.setStatus(204);
	}
	
	private Long decode(String encodedId) {
		return Long.valueOf(encodedId.substring(0, encodedId.indexOf("-")));
	}
}
