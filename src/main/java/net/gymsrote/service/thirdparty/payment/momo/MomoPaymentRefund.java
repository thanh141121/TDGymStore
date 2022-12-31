package net.gymsrote.service.thirdparty.payment.momo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import net.gymsrote.controller.advice.exception.CommonRuntimeException;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MomoPaymentRefund {
	@JsonIgnore
	private String secretKey;
	
	private String partnerCode;
	private String requestId;
	private String orderId;
	private String transId;
	private String amount;
	private String signature;
	
	public MomoPaymentRefund(String accessKey, String secretKey, String partnerCode, String requestId, String orderId, String transId, String amount) {
		this.secretKey = secretKey;
		this.partnerCode = partnerCode;
		this.requestId = requestId;
		this.orderId = orderId;
		this.transId = transId;
		this.amount = amount;
		
		String concat = "accessKey=" + accessKey +
				"&amount=" + amount +
				"&description=" + 
				"&orderId=" + orderId +
				"&partnerCode=" + partnerCode +
				"&requestId=" + requestId +
				"&transId=" + transId;
		
		try {
			signature =  DataSigning.sign(secretKey, concat);
		} catch (Exception e) {
			throw new CommonRuntimeException("Error when signing data for Momo refund payment request");
		}
	}
}
