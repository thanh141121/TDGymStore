package net.gymsrote.controller.payload.request;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CreateAddressRequest {
    @NotNull
	private Long WardCode;
    @NotNull
	private String WardName;	
    @NotNull
	private Long DistrictID;
    @NotNull
	private String DistrictName;
    @NotNull
	private Long ProvinceID;
    @NotNull
	private String ProvinceName;

    @NotNull
    private String addressDetail;
    @NotNull
    private String receiverName;
    @NotNull
    private String receiverPhone;
    @NotNull
    private Boolean isDefault;
}
