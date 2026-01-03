package pt.ulusofona.cd.store.events;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import pt.ulusofona.cd.store.service.AnalyticsService;

@Component
@RequiredArgsConstructor
public class AnalyticsConsumer {

    private final AnalyticsService analyticsService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @KafkaListener(
            topics = {
                    "${kafka.topics.reservation-created}",
                    "${kafka.topics.reservation-confirmed}",
                    "${kafka.topics.reservation-cancelled}",
                    "${kafka.topics.restaurant-notified}"
            },
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void onMessage(ConsumerRecord<String, String> record) {
        try {
            if (record.value() == null || record.value().isBlank()) return;

            EventEnvelope<?> envelope =
                    objectMapper.readValue(record.value(), EventEnvelope.class);

            if (envelope.getEventType() == null) return;

            analyticsService.processEvent(envelope);

        } catch (Exception ex) {
            throw new RuntimeException("Failed to parse/process kafka message", ex);
        }
    }
}
