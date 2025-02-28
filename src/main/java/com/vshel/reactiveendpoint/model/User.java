package com.vshel.reactiveendpoint.model;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("users")
@Getter @Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @Column("id")
    private UUID id;
    @Column("external_id")
    private UUID externalId;
    @Column("first_name")
    private String firstName;
    @Column("last_name")
    private String lastName;
}
