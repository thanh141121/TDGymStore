package net.gymsrote.service.thirdparty.payment.momo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import  net.gymsrote.controller.advice.exception.CommonRuntimeException;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MomoPaymentConfirm {
	@JsonIgnore
	private String secretKey;
	
	private String partnerCode;
	private String requestId;
	private String orderId;
	private String requestType;
	private String amount;	
	private String signature;
	public MomoPaymentConfirm(String accessKey, String secretKey, String partnerCode, String requestId, String orderId, String amount,
			boolean isConfirm) {
		this.secretKey = secretKey;
		this.partnerCode = partnerCode;
		this.requestId = requestId;
		this.orderId = orderId;
		this.amount = amount;
		
		if (isConfirm)
			requestType = "capture";
		else 
			requestType = "cancel";

		String concat = "accessKey=" + accessKey +
				"&amount=" + amount +
				"&description=" + 
				"&orderId=" + orderId +
				"&partnerCode=" + partnerCode +
				"&requestId=" + requestId +
				"&requestType=" + requestType;
		
		try {
			signature =  DataSigning.sign(secretKey, concat);
		} catch (Exception e) {
			throw new CommonRuntimeException("Error when signing data for Momo confirm payment request");
		}
	}
}
