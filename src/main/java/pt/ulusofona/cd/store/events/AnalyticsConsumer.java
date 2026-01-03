package pt.ulusofona.cd.store.events;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import pt.ulusofona.cd.store.service.AnalyticsService;

@Component
@RequiredArgsConstructor
public class AnalyticsConsumer {

    private final AnalyticsService analyticsService;

    // Consome os 4 tópicos que interessam ao Analytics
    @KafkaListener(
            topics = {
                    "${kafka.topics.reservation-created}",
                    "${kafka.topics.reservation-confirmed}",
                    "${kafka.topics.reservation-cancelled}",
                    "${kafka.topics.restaurant-notified}"
            },
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void onMessage(ConsumerRecord<String, EventEnvelope<?>> record) {
        // Key deve ser reservationId (string). Mantém ordering por reserva.
        EventEnvelope<?> envelope = record.value();

        // defesa mínima (não rebentar consumer por null)
        if (envelope == null || envelope.getEventType() == null) {
            return;
        }

        // Passa para o service onde:
        // - faz idempotência (processed_events)
        // - atualiza restaurant_metrics
        analyticsService.processEvent(envelope);
    }
}
