package at.fhbfi.tourism.noetourismapp.repository;

import at.fhbfi.tourism.noetourismapp.model.Occupancy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository-Schnittstelle für den Datenzugriff auf Belegungsdaten
 */
@Repository
public interface OccupancyRepository extends JpaRepository<Occupancy, Long> {

    /**
     * Findet Belegungen nach Standort und Zeitraum
     *
     * @param locationId Die ID des Standorts
     * @param startTime Die Startzeit des Zeitraums
     * @param endTime Die Endzeit des Zeitraums
     * @return Eine Liste der gefundenen Belegungen
     */
    @Query("SELECT o FROM Occupancy o WHERE o.locationId = :locationId AND "
            + "((o.startTime <= :endTime AND o.endTime >= :startTime) OR "
            + "(o.startTime >= :startTime AND o.startTime <= :endTime) OR "
            + "(o.endTime >= :startTime AND o.endTime <= :endTime))")
    List<Occupancy> findByLocationIdAndTimeRange(
            @Param("locationId") Long locationId,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime);

    /**
     * Findet Belegungen nach Status
     *
     * @param status Der Status der Belegung
     * @return Eine Liste der gefundenen Belegungen
     */
    List<Occupancy> findByStatus(String status);

    /**
     * Findet Belegungen nach Standort
     *
     * @param locationId Die ID des Standorts
     * @return Eine Liste der gefundenen Belegungen
     */
    List<Occupancy> findByLocationId(Long locationId);

    /**
     * Prüft, ob eine Belegung mit der angegebenen ID existiert
     *
     * @param id Die ID der zu prüfenden Belegung
     * @return true, wenn eine Belegung mit der angegebenen ID existiert, sonst false
     */
    boolean existsById(Long id);
}