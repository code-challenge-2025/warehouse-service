package br.com.dantebasso.warehouse.services.impl;

import br.com.dantebasso.warehouse.config.WarehouseKafkaConfig;
import br.com.dantebasso.warehouse.model.DataMeasure;
import br.com.dantebasso.warehouse.services.DataMeasureProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class DataMeasureProducerImpl implements DataMeasureProducer {
    
    private final KafkaTemplate<String, DataMeasure> kafkaTemplate;
    
    private final WarehouseKafkaConfig warehouseKafkaConfig;
    
    @Override
    public void addMessageToTopic(final DataMeasure dataMeasure) {
        log.info("Sending data to Kafka: {}", dataMeasure);
        kafkaTemplate.send(warehouseKafkaConfig.getTopic(), dataMeasure)
                .whenComplete((result, ex) -> {
                if (ex != null) {
                    log.error("Error to add message to the topic. Error: {}", ex.getMessage());
                } else {
                    log.info(
                        "Message send to the topic {} on offset {}",
                        result.getRecordMetadata().topic(),
                        result.getRecordMetadata().offset()
                    );
                }
            });
    }
    
}
