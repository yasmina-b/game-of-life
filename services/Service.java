package services;

import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.List;
import java.util.Properties;

public class Service {

    public List<String> topics;

    Properties props = new Properties();

    //KafkaConsumer<> kafkaConsumer = new KafkaConsumer<>();

    public Service(List<String> topics) {
        this.topics = topics;
    }




}
