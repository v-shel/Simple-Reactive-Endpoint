package com.vshel.reactiveendpoint.service;

import com.vshel.reactiveendpoint.error.ExternalUserServiceException;
import com.vshel.reactiveendpoint.model.ExternalUser;
import jakarta.annotation.PostConstruct;
import java.util.UUID;
import org.ajbrown.namemachine.NameGenerator;
import org.ajbrown.namemachine.NameGeneratorOptions;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class ExternalUserService {

    private NameGenerator generator;

    @PostConstruct
    public void init() {
        NameGeneratorOptions options = new NameGeneratorOptions();
        generator = new NameGenerator( options );
    }

    public Mono<ExternalUser> getExternalUser(UUID id) {
        ExternalUser result = generator.generateNames(1).stream()
            .map(user -> new ExternalUser(id, user.getFirstName(), user.getLastName()))
            .findFirst()
            .orElseThrow(() -> new ExternalUserServiceException("Exception while processing user."));
        return Mono.just(result);
    }
}
