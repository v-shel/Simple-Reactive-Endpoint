package com.vshel.reactiveendpoint.controller;

import static java.util.Objects.isNull;

import com.vshel.reactiveendpoint.error.ExternalUserServiceException;
import com.vshel.reactiveendpoint.error.NotFoundException;
import com.vshel.reactiveendpoint.model.BaseResponse;
import com.vshel.reactiveendpoint.service.UserService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RequestMapping("/users")
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/by-external-id")
    public Mono<ResponseEntity<BaseResponse>> getUserDetails(@RequestParam(required = false) UUID externalId) {
        UUID extId = isNull(externalId) ? UUID.randomUUID() : externalId; // To call API without ID
        return userService.getUserDetails(extId)
            .map(user -> new BaseResponse(user, null))
            .map(response -> toResponseEntity(response, HttpStatus.OK))
            .doOnSuccess(response -> log.info("UserController.getUserDetails() completed."))
            .onErrorResume(ExternalUserServiceException.class, ex -> Mono
                .just(new BaseResponse(null, ex.getMessage()))
                .map(response -> toResponseEntity(response, HttpStatus.SERVICE_UNAVAILABLE)))
            .onErrorReturn(NotFoundException.class,
                toResponseEntity(new BaseResponse(null, "Requested user not found"), HttpStatus.NOT_FOUND));
    }

    private <T> ResponseEntity<T> toResponseEntity(T target, HttpStatus status) {
        return new ResponseEntity<>(target, status);
    }
}
