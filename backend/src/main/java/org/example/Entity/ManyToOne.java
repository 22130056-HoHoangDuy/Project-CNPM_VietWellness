package org.example.Entity;

import jakarta.persistence.FetchType;

public @interface ManyToOne {
    FetchType fetch();
}
