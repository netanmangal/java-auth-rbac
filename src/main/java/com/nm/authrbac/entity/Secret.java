package com.nm.authrbac.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "secrets")
public class Secret {

    @Id private String id;
    private String secret;
    @DBRef(lazy = true) private User secretPostedBy;
    private String[] authorized_roles;
    private SecretAccessDetails[] secretAccessDetails;

    public Secret(String secret, User secretPostedBy, String[] authorized_roles) {
        this.secret = secret;
        this.secretPostedBy = secretPostedBy;
        this.authorized_roles = authorized_roles;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public User getSecretPostedBy() {
        return secretPostedBy;
    }

    public void setSecretPostedBy(User secretPostedBy) {
        this.secretPostedBy = secretPostedBy;
    }

    public String[] getAuthorized_roles() {
        return authorized_roles;
    }

    public void setAuthorized_roles(String[] authorized_roles) {
        this.authorized_roles = authorized_roles;
    }

    public SecretAccessDetails[] getSecretAccessDetails() {
        return secretAccessDetails;
    }

    public void setSecretAccessDetails(SecretAccessDetails[] secretAccessDetails) {
        this.secretAccessDetails = secretAccessDetails;
    }
}
