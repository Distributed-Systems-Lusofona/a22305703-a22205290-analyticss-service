package pt.ulusofona.cd.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pt.ulusofona.cd.store.model.RestaurantMetrics;

import java.util.List;

@Repository
public interface RestaurantMetricsRepository extends JpaRepository<RestaurantMetrics, Long> {

    List<RestaurantMetrics> findTop10ByOrderByConfirmedCountDesc();

    List<RestaurantMetrics> findTop10ByOrderByCreatedCountDesc();

    List<RestaurantMetrics> findTop10ByOrderByCancelledCountDesc();

    List<RestaurantMetrics> findTop10ByOrderByNotifiedCountDesc();
}
