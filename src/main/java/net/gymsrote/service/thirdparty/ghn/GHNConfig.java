package net.gymsrote.service.thirdparty.ghn;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.gymsrote.service.thirdparty.payment.momo.MomoConfig.Url;

@Getter @Setter
@NoArgsConstructor
@Component
@ConfigurationProperties(prefix = "net.gymsrote.service.ghn")
public class GHNConfig {
	private int ShopId;
	private String Token;
	

	private UrlGHN url;
	
	@Getter @Setter
	@NoArgsConstructor
	public static class UrlGHN {
		private String create;
		
	}
}
