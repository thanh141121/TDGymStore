package net.gymsrote.controller.payload.request;

import org.hibernate.validator.constraints.Range;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class CreateCartDetailRequest {

    @Range(min = 1, message = "so luong phai > 0")
    private Long quantity;
}
