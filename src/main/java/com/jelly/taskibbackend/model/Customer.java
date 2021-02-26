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
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;
    private String firstName;
    private String lastName;
    private String personalCode;
    private int creditModifier;
}
