package com.project.staynest.auth.db.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

@Component
public class UsersCdcConsumer {

    private static final String KAFKA_DEBEZIUM_STAYNEST_USERS_CDC_TOPIC = "staynest.staynest.users";

    private final ObjectMapper objectMapper = new ObjectMapper();

    @KafkaListener(topics = KAFKA_DEBEZIUM_STAYNEST_USERS_CDC_TOPIC)
    public void consume(String message){

        System.out.println();
        System.out.println();
        JsonNode jsonNode = objectMapper.readTree(message);

        String prettyJson = objectMapper
                .writerWithDefaultPrettyPrinter()
                .writeValueAsString(jsonNode);

        System.out.println("CDC EVENT:");
        System.out.println(prettyJson);
        System.out.println();
        System.out.println();
    }
}
