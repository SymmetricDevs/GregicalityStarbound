package com.starl0stgaming.gregicalitystarbound.api.telemetry.encryption;

import java.util.Objects;

//used for securely identifying objects, packets for example
public class Discriminator {

    protected AuthKey authKey;
    protected String keyID;

    protected String discriminator;

    private int id;

    //TODO: change key identifier to custom Identifier class
    public Discriminator(String discriminator, String keyID, int id) {
        this.authKey = new AuthKey(id, keyID);
        this.keyID = keyID;
        this.id = id;
        this.discriminator = discriminator;
    }

    public Discriminator(String discriminator, String keyID) {
        this(discriminator, keyID, 1);
    }

    public Discriminator(String discriminator, String keyID, AuthKey authKey, int id) {
        this.authKey = authKey;
        this.keyID = keyID;
        this.id = id;
        this.discriminator = discriminator;
    }

    public AuthKey getAuthKey() {
        return authKey;
    }

    public void setAuthKey(AuthKey authKey, String keyID) {
        if(Objects.equals(keyID, this.keyID)) {
            this.authKey = authKey;
        }
    }

    public String getKeyID() {
        return keyID;
    }

    public void setKeyID(String keyID) {
        this.keyID = keyID;
    }


    public String getDiscriminator(AuthKey key, String discriminatorIdentifier) {
        if(Objects.equals(key.getKey(key.getKeyIdentifier()), this.getAuthKey().getKey(this.getKeyID())) && Objects.equals(discriminatorIdentifier, this.keyID)) {
            return discriminator;
        } else {
            //I should make it, so it returns a randomized discriminator :troll:
            return null;
        }
    }

    public void setDiscriminator(String discriminator, AuthKey key) {
        if(Objects.equals(key.getKey(key.getKeyIdentifier()), this.getAuthKey().getKey(this.getKeyID()))) {
            this.discriminator = discriminator;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
