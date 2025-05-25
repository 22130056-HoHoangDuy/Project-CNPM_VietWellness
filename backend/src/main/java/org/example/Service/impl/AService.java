package org.example.Service.impl;

import org.example.Service.Utils.LogObj;
import org.springframework.stereotype.Service;

@Service
public abstract class AService {
    protected final LogObj log = new LogObj(this.getClass().getSimpleName());
}