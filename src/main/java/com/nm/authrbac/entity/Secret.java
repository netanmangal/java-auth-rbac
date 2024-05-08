package com.nm.authrbac.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "secrets")
public class Secret {

    @Id private String id;
    private String secret;
    @DBRef(lazy = false) private User secretPostedBy;
    private List<String> authorized_roles = new ArrayList<String>();
    private List<SecretAccessDetails> secretAccessDetails = new ArrayList<SecretAccessDetails>();

    public Secret(String secret, User secretPostedBy, List<String> authorized_roles) {
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

    public List<String> getAuthorized_roles() {
        return authorized_roles;
    }

    public void setAuthorized_roles(List<String> authorized_roles) {
        this.authorized_roles = authorized_roles;
    }

    public List<SecretAccessDetails> getSecretAccessDetails() {
        return secretAccessDetails;
    }

    public void setSecretAccessDetails(List<SecretAccessDetails> secretAccessDetails) {
        this.secretAccessDetails = secretAccessDetails;
    }
}
