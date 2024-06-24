package com.starl0stgaming.gregicalitystarbound.api.telemetry.encryption;

import java.util.Objects;
import java.util.UUID;

public class AuthKey {

    protected UUID key;
    private int id;
    protected String keyIdentifier;

    public AuthKey(int id, String keyIdentifier) {
        key = UUID.randomUUID();
        this.id = id;
        this.keyIdentifier = keyIdentifier;
    }

    public UUID getKey(String keyIdentifier) {
        if(Objects.equals(this.keyIdentifier, keyIdentifier)) {
            return key;
        } else {
            return null;
        }
    }

    public void setKey(UUID key, String keyIdentifier) {
        if(Objects.equals(this.keyIdentifier, keyIdentifier)) {
            this.key = key;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKeyIdentifier() {
        return keyIdentifier;
    }

    public void setKeyIdentifier(String keyIdentifier) {
        this.keyIdentifier = keyIdentifier;
    }
}
