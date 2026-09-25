package com.project.staynest.auth.db.consumer;

import com.project.staynest.auth.db.consumer.enums.EventTypes;
import com.project.staynest.auth.db.consumer.models.UserCreatedEventData;
import com.project.staynest.auth.db.validation.Validation;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.time.Instant;

@Component
public class UsersCdcConsumer {

    private static final String KAFKA_DEBEZIUM_STAYNEST_USERS_CDC_TOPIC = "staynest.staynest.users";

    private static final String OPERATION_KEY = "op";
    private static final String CDC_EVENT_AFTER_KEY = "after";
    private static final String LOOKUP_ID_KEY = "lookup_id";
    private static final String PUBLIC_ID_KEY = "public_id";
    private static final String USERNAME_KEY = "username";
    private static final String EMAIL_KEY = "email";
    private static final String ENCRYPTION_VERSION_KEY = "encryption_version";
    private static final String ENCRYPTION_KEY_ID_KEY = "encryption_key_id";
    private static final String STATUS_KEY = "status";
    private static final String CREATED_AT_KEY = "created_at";
    private static final String UPDATED_AT_KEY = "updated_at";

    private static final String KAFKA_USER_EVENTS_TOPIC = "user-events";

    private final String CLASS_NAME = this.getClass().getSimpleName();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final KafkaTemplate<String, String> kafkaTemplate;
    public UsersCdcConsumer(
            KafkaTemplate<String, String> kafkaTemplate
    ){
        this.kafkaTemplate = kafkaTemplate;
    }

    @KafkaListener(topics = KAFKA_DEBEZIUM_STAYNEST_USERS_CDC_TOPIC)
    public void consume(String message, Acknowledgment acknowledgment){

        System.out.println();
        System.out.println();
        JsonNode jsonNode = objectMapper.readTree(message);

        String op = jsonNode.get(OPERATION_KEY).asString();
        Validation.validate(op, "op", CLASS_NAME);

        switch (op){
            case "r", "c" -> sendUserCreatedEvent(jsonNode, acknowledgment, op);
            default -> {
                acknowledgment.acknowledge();
                System.out.println("IGNORING UNSUPPORTED CDC OPERATION: " + op);

            }
        }
    }

    private void sendUserCreatedEvent(
            JsonNode jsonNode, Acknowledgment acknowledgment, String op
    ){

        JsonNode after = jsonNode.get(CDC_EVENT_AFTER_KEY);
        Validation.validate(after, "after", CLASS_NAME);

        JsonNode id = after.get(LOOKUP_ID_KEY);
        String publicId = after.get(PUBLIC_ID_KEY).asString();
        String username = after.get(USERNAME_KEY).asString();
        String email = after.get(EMAIL_KEY).asString();
        JsonNode encryptionVersion = after.get(ENCRYPTION_VERSION_KEY);
        JsonNode encryptionKeyId = after.get(ENCRYPTION_KEY_ID_KEY);
        String status = after.get(STATUS_KEY).asString();
        String createdAt = after.get(CREATED_AT_KEY).asString();
        String updatedAt = after.get(UPDATED_AT_KEY).asString();

        Validation.validate(id, "id", CLASS_NAME);
        Validation.validate(publicId, "publicId", CLASS_NAME);
        Validation.validate(username, "username", CLASS_NAME);
        Validation.validate(email, "email", CLASS_NAME);
        Validation.validate(encryptionVersion, "encryptionVersion", CLASS_NAME);
        Validation.validate(encryptionKeyId, "encryptionKeyId", CLASS_NAME);
        Validation.validate(status, "status", CLASS_NAME);
        Validation.validate(createdAt, "createdAt", CLASS_NAME);
        Validation.validate(updatedAt, "updatedAt", CLASS_NAME);


        UserCreatedEventData userCreatedEventData =
                new UserCreatedEventData(
                        id.asLong(),
                        publicId,
                        username,
                        email,
                        encryptionVersion.asShort(),
                        encryptionKeyId.asShort(),
                        status,
                        Instant.parse(createdAt),
                        Instant.parse(updatedAt),
                        op,
                        EventTypes.USER_CREATED.name()
                );

        String event = objectMapper.writeValueAsString(userCreatedEventData);

        this.kafkaTemplate
                .send(
                        KAFKA_USER_EVENTS_TOPIC,
                        event
                )
                .whenComplete((response, exception)->{
                    if(exception==null){
                        acknowledgment.acknowledge();
                        return;
                    }

                    System.out.println("FAILED TO PUBLISH USER EVENT");
                    exception.printStackTrace();
                });
    }
}