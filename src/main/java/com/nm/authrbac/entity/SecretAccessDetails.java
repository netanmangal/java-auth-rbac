package com.nm.authrbac.entity;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
;
import java.time.LocalDateTime;

public class SecretAccessDetails {

    @DocumentReference(collection = "users") private ObjectId secretAccessedBy;
    private LocalDateTime accessedAt;

    public ObjectId getSecretAccessedBy() {
        return secretAccessedBy;
    }

    public void setSecretAccessedBy(ObjectId secretAccessedBy) {
        this.secretAccessedBy = secretAccessedBy;
    }

    public LocalDateTime getAccessedAt() {
        return accessedAt;
    }

    public void setAccessedAt(LocalDateTime accessedAt) {
        this.accessedAt = accessedAt;
    }
}
