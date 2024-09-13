//package com.careerstreet.apply_service.kafka.producer;
//
//import com.careerstreet.apply_service.dto.ApplyResponse;
//import lombok.RequiredArgsConstructor;
//import org.apache.kafka.clients.admin.NewTopic;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.core.annotation.Order;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.stereotype.Component;
//
//@Component
//@RequiredArgsConstructor
//public class ApplyKafkaProducer {
//    private final KafkaTemplate<String, ApplyResponse> kafkaTemplate;
//
//    @Value("${spring.kafka.topic.apply-update-status}")
//    private String topic;
//
//    @Value("${spring.kafka.replication.factor:1}")
//    private int replicationFactor;
//
//    @Value("${spring.kafka.partition.number:1}")
//    private int partitionNumber;
//
//    public void writeToKafka(ApplyResponse applyResponse, String uuid) {
//        kafkaTemplate.send(topic, uuid, applyResponse);
//    }
//
//    @Bean
//    @Order(-1)
//    public NewTopic createNewTopic() {
//        return new NewTopic(topic, partitionNumber, (short) replicationFactor);
//    }
//}
