package pt.ulusofona.cd.store.events;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import pt.ulusofona.cd.store.dto.SupplierDeactivatedEvent;

import java.util.UUID;

@Service
public class SupplierEventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${supplier.events.supplier-deactivated-events}")
    private String supplierDeactivatedEventsTopic;

    public SupplierEventProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendSupplierDeactivatedEvent(UUID supplierId) {
        SupplierDeactivatedEvent event = new SupplierDeactivatedEvent(supplierId.toString());

        kafkaTemplate.send(supplierDeactivatedEventsTopic, supplierId.toString(), event)
                .whenComplete((result, ex) -> {
                    if (ex == null) {
                        System.out.println("Sent message=[" + event + "]");
                    } else {
                        System.err.println("Unable to send message=[" + event + "] due to : " + ex.getMessage());
                    }
                });
    }
}