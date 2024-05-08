package com.nm.authrbac.entity;

import org.springframework.data.mongodb.core.mapping.DBRef;
import java.util.Date;

public class SecretAccessDetails {

    @DBRef
    private User secretAccessedBy;
    private Date accessedAt;

    public SecretAccessDetails(User secretAccessedBy, Date accessedAt) {
        this.secretAccessedBy = secretAccessedBy;
        this.accessedAt = accessedAt;
    }

    public User getSecretAccessedBy() {
        return secretAccessedBy;
    }

    public void setSecretAccessedBy(User secretAccessedBy) {
        this.secretAccessedBy = secretAccessedBy;
    }

    public Date getAccessedAt() {
        return accessedAt;
    }

    public void setAccessedAt(Date accessedAt) {
        this.accessedAt = accessedAt;
    }
}
