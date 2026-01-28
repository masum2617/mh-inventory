package com.mh.inventory.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mh.inventory.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users", schema = "mh_inventory")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @Column(name = "username", nullable = false, length = 100, unique = true)
    private String username;
    
    @Column(name = "full_name", length = 150)
    private String fullName;
    
    @Column(name = "email", length = 150, unique = true)
    private String email;
    
    @Column(name = "password", length = 200)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    
    @Column(name = "role", length = 20)
    private String role;


    

}
