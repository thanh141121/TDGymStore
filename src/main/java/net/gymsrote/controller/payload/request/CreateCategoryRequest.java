package net.gymsrote.controller.payload.request;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CreateCategoryRequest {
    @NotNull(message = "Name is required")
    private String name;
}
