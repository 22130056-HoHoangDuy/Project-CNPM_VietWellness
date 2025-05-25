package org.example.Entity;

import jakarta.persistence.CascadeType;

public @interface OneToMany {
    String mappedBy();

    CascadeType cascade();

    boolean orphanRemoval();
}
