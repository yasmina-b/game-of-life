package producer;

import events.CellEvents;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import serializer.KafkaJsonSerializer;

import java.util.Properties;

public final class KProducer {
    private static KProducer INSTANCE;
    private static Properties props = new Properties();
    private static KafkaProducer<String, CellEvents> kafkaProducer;

    private KProducer(){
        this.props.put("bootstrap.servers", "localhost:9092");
        //Set acknowledgements for producer requests.
        this.props.put("acks", "all");

        //If the request fails, the producer can automatically retry,
        this.props.put("retries", 0);

        //Specify buffer size in config
        this.props.put("batch.size", 16384);

        //Reduce the no of requests less than 0
        this.props.put("linger.ms", 1);

        //The buffer.memory controls the total amount of memory available to the producer for buffering.
        this.props.put("buffer.memory", 33554432);

        this.props.put("key.serializer",
                "org.apache.kafka.common.serialization.StringSerializer");

        this.props.put("value.serializer",
                "org.apache.kafka.common.serialization.StringSerializer");

        this.kafkaProducer = new KafkaProducer<>(props, new StringSerializer(), new KafkaJsonSerializer());
    }

    public static<K,V> void send(org.apache.kafka.clients.producer.ProducerRecord<K, V> record){
        if(INSTANCE == null) {
            INSTANCE = new KProducer();
        }
        kafkaProducer.send((ProducerRecord<String, CellEvents>) record);
    }


}
