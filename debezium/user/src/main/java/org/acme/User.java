package org.acme;

import javax.persistence.Entity;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

@Entity(name = "drinker")
public class User extends PanacheEntity {
    @NotBlank
    public String username;
    
    @Min(18)
    public int age;
    
    @Email
    @NotBlank
    public String email;
}