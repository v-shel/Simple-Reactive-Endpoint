package com.vshel.reactiveendpoint.service;

import com.vshel.reactiveendpoint.error.ExternalUserServiceException;
import com.vshel.reactiveendpoint.error.NotFoundException;
import com.vshel.reactiveendpoint.model.User;
import com.vshel.reactiveendpoint.repository.UserRepository;
import java.util.SplittableRandom;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private static final SplittableRandom RANDOM = new SplittableRandom();
    private final ExternalUserService externalUserService;
    private final UserRepository userRepository;
    private final TransactionalOperator transactionalOperator;

    public Mono<User> getUserDetails(UUID id) {
        return getUserByExternalId(id)
            .switchIfEmpty(externalUserService.getExternalUser(id)
                .map(ext -> new User(null, ext.id(), ext.name(), ext.lastName()))
                .flatMap(userRepository::save)
            )
            .map(user -> {
                int random = RANDOM.nextInt(1, 5);
                if (random == 1) {
                    throw new NotFoundException("Requested user not found.");
                } else if (random == 2) {
                    throw new ExternalUserServiceException("User service temporary unavailable.");
                }
                return user;
            })
            .doOnError(ex -> log.error("Failed to process getUserInfo. Reason: ", ex))
            .as(transactionalOperator::transactional);
    }

    private Mono<User> getUserByExternalId(UUID id) {
        return userRepository.getUserByExternalId(id);
    }
}
