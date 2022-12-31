package net.gymsrote.service.thirdparty.payment.momo;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@Component
@ConfigurationProperties(prefix = "net.gymsrote.service.payment.momo")
public class MomoConfig {
	private String storeName;	
	private String secretKey;	
	private String accessKey;	
	private String partnerCode;	
	private Url url;
	
	@Getter @Setter
	@NoArgsConstructor
	public static class Url {
		private String paymentCreate;
		private String paymentConfirm;
		private String paymentRefund;
		private String notify;
		private String callback;
	}
}
