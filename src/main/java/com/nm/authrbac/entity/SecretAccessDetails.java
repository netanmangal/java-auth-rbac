package com.nm.authrbac.entity;

import org.springframework.data.mongodb.core.mapping.DocumentReference;
import java.util.Date;

public class SecretAccessDetails {

    @DocumentReference(collection = "users") private String secretAccessedBy;
    private Date accessedAt;

    public SecretAccessDetails(String secretAccessedBy, Date accessedAt) {
        this.secretAccessedBy = secretAccessedBy;
        this.accessedAt = accessedAt;
    }

    public String getSecretAccessedBy() {
        return secretAccessedBy;
    }

    public void setSecretAccessedBy(String secretAccessedBy) {
        this.secretAccessedBy = secretAccessedBy;
    }

    public Date getAccessedAt() {
        return accessedAt;
    }

    public void setAccessedAt(Date accessedAt) {
        this.accessedAt = accessedAt;
    }
}
