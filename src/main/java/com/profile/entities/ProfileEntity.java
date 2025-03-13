package com.profile.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Data
@NoArgsConstructor
@Entity(name = "profile_entity")
@DynamicUpdate
@DynamicInsert
public class ProfileEntity {

    @Id
    @Column(name = "cpf")
    @Size(max = 11)
    private String cpf;

    @Column(name = "full_name")
    @Size(max = 120)
    private String name;

    @Column(name = "email")
    @Size(max = 50)
    private String email;

    @Column(name = "phone_number")
    @Size(max = 13)
    private String phone;

    @Column(name = "first_name")
    @Size(max = 50)
    private String firstName;

    @Column(name = "last_name")
    @Size(max = 70)
    private String lastName;

}
