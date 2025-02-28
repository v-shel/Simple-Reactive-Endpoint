package com.vshel.reactiveendpoint.model;

public record BaseResponse(
    Object data,
    String errorMessage
) {
}
