package com.jelly.taskibbackend.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import net.bytebuddy.dynamic.loading.InjectionClassLoader;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@Table
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    private String firstName;
    private String lastName;

    @Column(unique = true)
    private String pic; //personal identification code
    private int creditModifier;
}
