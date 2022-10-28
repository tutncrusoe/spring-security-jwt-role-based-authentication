package com.example.springsecurity.role.model;

import com.example.springsecurity.user.model.User;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Setter
@Getter
@SuperBuilder
@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true, length = 50)
    private String name;

    @ManyToMany(mappedBy = "roles")
    private Set<User> users = new LinkedHashSet<>();

    public Role() {
    }

    public Role(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
