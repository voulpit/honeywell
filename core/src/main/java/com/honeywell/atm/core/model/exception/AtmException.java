package com.honeywell.atm.core.model.exception;

import java.io.Serial;
import java.io.Serializable;

public class AtmException extends RuntimeException implements Serializable {
    @Serial
    private static final long serialVersionUID = 7729685098267757690L;

    public AtmException(String message) {
        super(message);
    }
}
