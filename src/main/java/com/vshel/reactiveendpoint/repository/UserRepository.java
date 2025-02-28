package com.vshel.reactiveendpoint.repository;

import com.vshel.reactiveendpoint.model.User;
import java.util.UUID;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveCrudRepository<User, UUID> {

    Mono<User> getUserByExternalId(UUID externalId);
}
