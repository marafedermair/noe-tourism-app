package at.fhbfi.tourism.noetourismapp.service;

import at.fhbfi.tourism.noetourismapp.model.Occupancy;
import at.fhbfi.tourism.noetourismapp.repository.OccupancyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Service-Klasse für die Verwaltung von Belegungsdaten
 *
 * Diese Klasse enthält die Geschäftslogik für die Verwaltung von Belegungsdaten
 * und implementiert Validierung und Transaktionssicherheit.
 *
 * TODO: Implementiere den OccupancyService
 *
 * Folgende Schritte sind notwendig:
 * 1. Implementiere CRUD-Operationen (Create, Read, Update, Delete)
 * 2. Implementiere Methoden für die Suche nach Belegungen nach Zeitraum und Standort
 * 3. Implementiere Validierungsmethoden für die Datenintegrität
 * 4. Implementiere Transaktionssicherheit
 */
@Service
public class OccupancyService {

    @Autowired
    private OccupancyRepository occupancyRepository;

    /**
     * Speichert eine neue Belegung oder aktualisiert eine bestehende Belegung
     *
     * @param occupancy Die zu speichernde oder zu aktualisierende Belegung
     * @return Die gespeicherte oder aktualisierte Belegung
     * @throws IllegalArgumentException wenn die Belegungsdaten ungültig sind
     */
    @Transactional
    public Occupancy saveOccupancy(Occupancy occupancy) {
        // Validierung der Belegungsdaten
        validateOccupancy(occupancy);

        // Setze lastModified auf den aktuellen Zeitpunkt
        occupancy.setLastModified(LocalDateTime.now());

        return occupancyRepository.save(occupancy);
    }

    /**
     * Findet alle Belegungen
     *
     * @return Eine Liste aller Belegungen
     */
    public List<Occupancy> findAllOccupancies() {
        return occupancyRepository.findAll();
    }

    /**
     * Findet eine Belegung anhand ihrer ID
     *
     * @param id Die ID der zu findenden Belegung
     * @return Ein Optional, das die gefundene Belegung enthält, oder ein leeres Optional, wenn keine Belegung gefunden wurde
     */
    public Optional<Occupancy> findOccupancyById(Long id) {
        return occupancyRepository.findById(id);
    }

    /**
     * Löscht eine Belegung anhand ihrer ID
     *
     * @param id Die ID der zu löschenden Belegung
     */
    @Transactional
    public void deleteOccupancyById(Long id) {
        occupancyRepository.deleteById(id);
    }

    /**
     * Findet Belegungen nach Standort und Zeitraum
     *
     * @param locationId Die ID des Standorts
     * @param startTime Die Startzeit des Zeitraums
     * @param endTime Die Endzeit des Zeitraums
     * @return Eine Liste der gefundenen Belegungen
     * @throws IllegalArgumentException wenn der Zeitraum ungültig ist
     */
    public List<Occupancy> findOccupanciesByLocationAndTimeRange(Long locationId, LocalDateTime startTime, LocalDateTime endTime) {
        // Validierung des Zeitraums
        validateTimeRange(startTime, endTime);

        return occupancyRepository.findByLocationIdAndTimeRange(locationId, startTime, endTime);
    }

    /**
     * Findet Belegungen nach Status
     *
     * @param status Der Status der Belegung
     * @return Eine Liste der gefundenen Belegungen
     */
    public List<Occupancy> findOccupanciesByStatus(String status) {
        return occupancyRepository.findByStatus(status);
    }

    /**
     * Findet Belegungen nach Standort
     *
     * @param locationId Die ID des Standorts
     * @return Eine Liste der gefundenen Belegungen
     */
    public List<Occupancy> findOccupanciesByLocationId(Long locationId) {
        return occupancyRepository.findByLocationId(locationId);
    }

    /**
     * Prüft, ob eine Belegung mit der angegebenen ID existiert
     *
     * @param id Die ID der zu prüfenden Belegung
     * @return true, wenn eine Belegung mit der angegebenen ID existiert, sonst false
     */
    public boolean existsOccupancyById(Long id) {
        return occupancyRepository.existsById(id);
    }

    /**
     * Validiert eine Belegung
     *
     * @param occupancy Die zu validierende Belegung
     * @throws IllegalArgumentException wenn die Belegungsdaten ungültig sind
     */
    private void validateOccupancy(Occupancy occupancy) {
        if (occupancy == null) {
            throw new IllegalArgumentException("Belegung darf nicht null sein");
        }

        if (occupancy.getLocationId() == null) {
            throw new IllegalArgumentException("Standort-ID darf nicht null sein");
        }

        validateTimeRange(occupancy.getStartTime(), occupancy.getEndTime());

        if (occupancy.getStatus() == null || occupancy.getStatus().trim().isEmpty()) {
            throw new IllegalArgumentException("Status darf nicht leer sein");
        }
    }

    /**
     * Validiert einen Zeitraum
     *
     * @param startTime Die Startzeit des Zeitraums
     * @param endTime Die Endzeit des Zeitraums
     * @throws IllegalArgumentException wenn der Zeitraum ungültig ist
     */
    private void validateTimeRange(LocalDateTime startTime, LocalDateTime endTime) {
        if (startTime == null || endTime == null) {
            throw new IllegalArgumentException("Start- und Endzeit dürfen nicht null sein");
        }

        if (endTime.isBefore(startTime)) {
            throw new IllegalArgumentException("Die Endzeit darf nicht vor der Startzeit liegen");
        }
    }
}