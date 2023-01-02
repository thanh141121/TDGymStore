package net.gymsrote.controller.payload.request;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class StatusRequest<T> {
    @NotNull T status;
}
