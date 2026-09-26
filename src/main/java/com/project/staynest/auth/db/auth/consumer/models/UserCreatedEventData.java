package com.project.staynest.auth.db.auth.consumer.models;

import java.time.Instant;

public record UserCreatedEventData(
        long id,
        String publicId,
        String username,
        String email,
        short encryptionVersion,
        short encryptionKeyId,
        String status,
        Instant createdAt,
        Instant updatedAt,
        String op,
        String eventType
) {
}
