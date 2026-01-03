package pt.ulusofona.cd.store.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pt.ulusofona.cd.store.dto.RestaurantMetricsResponse;
import pt.ulusofona.cd.store.dto.TopRestaurantDto;
import pt.ulusofona.cd.store.model.ProcessedEvent;
import pt.ulusofona.cd.store.model.RestaurantMetrics;
import pt.ulusofona.cd.store.repository.ProcessedEventRepository;
import pt.ulusofona.cd.store.repository.RestaurantMetricsRepository;

import java.time.Instant;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AnalyticsService {

    private final RestaurantMetricsRepository restaurantMetricsRepository;
    private final ProcessedEventRepository processedEventRepository;

    /**
     * Atualiza métricas a partir de um evento reservation.* (created/confirmed/cancelled).
     * Idempotência: usa eventKey = reservationId + ":" + eventType
     */
    @Transactional
    public void recordReservationEvent(String eventType, Long reservationId, Long restaurantId) {

        if (eventType == null || eventType.isBlank()) {
            throw new IllegalArgumentException("eventType is required");
        }
        if (reservationId == null) {
            throw new IllegalArgumentException("reservationId is required");
        }
        if (restaurantId == null) {
            throw new IllegalArgumentException("restaurantId is required");
        }

        String eventKey = reservationId + ":" + eventType;

        if (processedEventRepository.existsById(eventKey)) {
            return; // já processado
        }

        // marcar como processado
        ProcessedEvent pe = new ProcessedEvent();
        pe.setEventKey(eventKey);
        pe.setEventType(eventType);
        pe.setReservationId(reservationId);
        pe.setProcessedAt(Instant.now());
        processedEventRepository.save(pe);

        // obter ou criar métricas do restaurante
        RestaurantMetrics metrics = restaurantMetricsRepository.findById(restaurantId)
                .orElseGet(() -> {
                    RestaurantMetrics m = new RestaurantMetrics();
                    m.setRestaurantId(restaurantId);
                    m.setCreatedCount(0);
                    m.setConfirmedCount(0);
                    m.setCancelledCount(0);
                    m.setNotifiedCount(0);
                    m.setUpdatedAt(Instant.now());
                    return m;
                });

        switch (eventType) {
            case "reservation.created" -> metrics.setCreatedCount(metrics.getCreatedCount() + 1);
            case "reservation.confirmed" -> metrics.setConfirmedCount(metrics.getConfirmedCount() + 1);
            case "reservation.cancelled" -> metrics.setCancelledCount(metrics.getCancelledCount() + 1);
            default -> {
                // se vier outro eventType, ignora (ou lança erro se preferires)
                return;
            }
        }

        metrics.setUpdatedAt(Instant.now());
        restaurantMetricsRepository.save(metrics);
    }

    /**
     * Atualiza métricas quando chega restaurant.notified.
     * Nota: para contar por restaurante, o consumer tem de te passar o restaurantId.
     */
    @Transactional
    public void recordRestaurantNotified(Long reservationId, Long restaurantId) {

        String eventType = "restaurant.notified";

        if (reservationId == null) {
            throw new IllegalArgumentException("reservationId is required");
        }
        if (restaurantId == null) {
            throw new IllegalArgumentException("restaurantId is required");
        }

        String eventKey = reservationId + ":" + eventType;

        if (processedEventRepository.existsById(eventKey)) {
            return;
        }

        ProcessedEvent pe = new ProcessedEvent();
        pe.setEventKey(eventKey);
        pe.setEventType(eventType);
        pe.setReservationId(reservationId);
        pe.setProcessedAt(Instant.now());
        processedEventRepository.save(pe);

        RestaurantMetrics metrics = restaurantMetricsRepository.findById(restaurantId)
                .orElseGet(() -> {
                    RestaurantMetrics m = new RestaurantMetrics();
                    m.setRestaurantId(restaurantId);
                    m.setCreatedCount(0);
                    m.setConfirmedCount(0);
                    m.setCancelledCount(0);
                    m.setNotifiedCount(0);
                    m.setUpdatedAt(Instant.now());
                    return m;
                });

        metrics.setNotifiedCount(metrics.getNotifiedCount() + 1);
        metrics.setUpdatedAt(Instant.now());
        restaurantMetricsRepository.save(metrics);
    }

    @Transactional(readOnly = true)
    public RestaurantMetricsResponse getMetricsByRestaurantId(Long restaurantId) {
        RestaurantMetrics m = restaurantMetricsRepository.findById(restaurantId)
                .orElseGet(() -> {
                    RestaurantMetrics empty = new RestaurantMetrics();
                    empty.setRestaurantId(restaurantId);
                    empty.setCreatedCount(0);
                    empty.setConfirmedCount(0);
                    empty.setCancelledCount(0);
                    empty.setNotifiedCount(0);
                    empty.setUpdatedAt(Instant.now());
                    return empty;
                });

        RestaurantMetricsResponse res = new RestaurantMetricsResponse();
        res.setRestaurantId(m.getRestaurantId());
        res.setCreatedCount(m.getCreatedCount());
        res.setConfirmedCount(m.getConfirmedCount());
        res.setCancelledCount(m.getCancelledCount());
        res.setNotifiedCount(m.getNotifiedCount());
        res.setUpdatedAt(m.getUpdatedAt());
        return res;
    }

    @Transactional(readOnly = true)
    public List<TopRestaurantDto> getTopRestaurantsByConfirmed(int limit) {

        int safeLimit = Math.max(1, limit);

        return restaurantMetricsRepository.findAll().stream()
                .sorted(Comparator.comparingLong(RestaurantMetrics::getConfirmedCount).reversed())
                .limit(safeLimit)
                .map(m -> {
                    TopRestaurantDto dto = new TopRestaurantDto();
                    dto.setRestaurantId(m.getRestaurantId());
                    dto.setConfirmedCount(m.getConfirmedCount());
                    return dto;
                })
                .toList();
    }
}
