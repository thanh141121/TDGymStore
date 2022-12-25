package net.gymsrote.controller.payload.request.order;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
import net.gymsrote.entity.EnumEntity.EPaymentMethod;

@Getter @Setter
public class CreateOrderRequest {
	@NotEmpty
	private List<CreateProductOrderRequest> products;
	
    @NotBlank
    private String address;

    @NotBlank
    private String receiverName;

    @NotBlank
    private String receiverPhone;
    
    @NotNull
    private EPaymentMethod paymentMethod;
    
    @NotNull
    private Long shipPrice;

}
