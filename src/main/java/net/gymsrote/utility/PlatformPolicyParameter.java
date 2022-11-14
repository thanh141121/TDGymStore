package net.gymsrote.utility;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PlatformPolicyParameter {
	public static int DEFAULT_PAGE_SIZE;
	public static int MIN_ALLOWED_PRODUCT_IMAGE;
	public static int MAX_ALLOWED_PRODUCT_IMAGE;
	public static int MIN_ALLOWED_PRODUCT_VARIATION;
	
	//public static int MINIMUM_PASSWORD_LENGTH;

    @Value("${net.gymstore.policy.default-page-size}")
    public void setDefaultPageSize(int value) {
    	PlatformPolicyParameter.DEFAULT_PAGE_SIZE = value;
    }
    
    @Value("${net.gymstore.policy.min-allowed-product-image}")
    public void setMinAllowedProductImage(int value) {
    	PlatformPolicyParameter.MIN_ALLOWED_PRODUCT_IMAGE = value;
    }
    
    @Value("${net.gymstore.policy.max-allowed-product-image}")
    public void setMaxAllowedProductImage(int value) {
    	PlatformPolicyParameter.MAX_ALLOWED_PRODUCT_IMAGE = value;
    }
    
    @Value("${net.gymstore.policy.min-allowed-product-variation}")
    public void setMinAllowedProductVariation(int value) {
    	PlatformPolicyParameter.MIN_ALLOWED_PRODUCT_VARIATION = value;
    }
    
   /* @Value("${com.lucistore.lucistorebe.policy.minimum-password-length}")
    public void setMinimumPasswordLength(int value) {
    	PlatformPolicyParameter.MINIMUM_PASSWORD_LENGTH = value;
    }*/
}
