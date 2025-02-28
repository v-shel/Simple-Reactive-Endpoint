package com.vshel.reactiveendpoint.model;

import java.util.UUID;

public record ExternalUser(
    UUID id,
    String name,
    String lastName
) {
}
