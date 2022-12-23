package net.gymsrote.controller.payload.request;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class CreateCartDetailRequest {

    @NotNull
    private Long quantity;
}
