package pt.ulusofona.cd.store.events;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;
import pt.ulusofona.cd.store.service.AnalyticsService;

@Component
@RequiredArgsConstructor
public class AnalyticsConsumer {

    private final AnalyticsService analyticsService;
    private final ObjectMapper objectMapper;

    @KafkaListener(
            topics = {
                    "${kafka.topics.reservation-created}",
                    "${kafka.topics.reservation-confirmed}",
                    "${kafka.topics.reservation-cancelled}",
                    "${kafka.topics.restaurant-notified}"
            },
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void onMessage(ConsumerRecord<String, String> record, Acknowledgment ack) {
        try {
            String value = record.value();
            if (value == null || value.isBlank()) {
                ack.acknowledge();
                return;
            }

            EventEnvelope<?> envelope = objectMapper.readValue(value, EventEnvelope.class);

            if (envelope.getEventType() == null || envelope.getEventType().isBlank()) {
                ack.acknowledge();
                return;
            }

            analyticsService.processEvent(envelope);

            ack.acknowledge();

        } catch (Exception ex) {

            System.err.println("[analytics-service] Failed to parse/process message. Skipping. error=" + ex.getMessage());
            ack.acknowledge();
        }
    }
}
