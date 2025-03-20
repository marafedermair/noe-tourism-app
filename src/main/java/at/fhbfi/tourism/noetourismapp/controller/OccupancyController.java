package at.fhbfi.tourism.noetourismapp.controller;

import at.fhbfi.tourism.noetourismapp.model.Occupancy;
import at.fhbfi.tourism.noetourismapp.service.OccupancyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Controller für die Verwaltung von Belegungsdaten
 *
 * Dieser Controller stellt REST-API-Endpunkte für die Verwaltung von Belegungsdaten bereit
 * und implementiert Validierung und Fehlerbehandlung.
 *
 * TODO: Implementiere den OccupancyController
 *
 * Folgende Schritte sind notwendig:
 * 1. Implementiere CRUD-Endpunkte (Create, Read, Update, Delete)
 * 2. Implementiere Endpunkte für die Suche nach Belegungen nach Zeitraum und Standort
 * 3. Implementiere Validierung und Fehlerbehandlung
 * 4. Implementiere Transaktionssicherheit
 */
@RestController
@RequestMapping("/api/occupancies")
public class OccupancyController {

    @Autowired
    private OccupancyService occupancyService;

    /**
     * Gibt alle Belegungen zurück
     *
     * @return Eine Liste aller Belegungen
     */
    @GetMapping
    public ResponseEntity<List<Occupancy>> getAllOccupancies() {
        List<Occupancy> occupancies = occupancyService.findAllOccupancies();
        return ResponseEntity.ok(occupancies);
    }

    /**
     * Gibt eine Belegung anhand ihrer ID zurück
     *
     * @param id Die ID der zu findenden Belegung
     * @return Die gefundene Belegung oder 404 Not Found, wenn keine Belegung gefunden wurde
     */
    @GetMapping("/{id}")
    public ResponseEntity<Occupancy> getOccupancyById(@PathVariable Long id) {
        return occupancyService.findOccupancyById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Erstellt eine neue Belegung
     *
     * @param occupancy Die zu erstellende Belegung
     * @return Die erstellte Belegung und 201 Created
     */
    @PostMapping
    public ResponseEntity<Occupancy> createOccupancy(@RequestBody Occupancy occupancy) {
        try {
            Occupancy savedOccupancy = occupancyService.saveOccupancy(occupancy);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedOccupancy);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Aktualisiert eine bestehende Belegung
     *
     * @param id Die ID der zu aktualisierenden Belegung
     * @param occupancy Die aktualisierten Daten der Belegung
     * @return Die aktualisierte Belegung oder 404 Not Found, wenn keine Belegung gefunden wurde
     */
    @PutMapping("/{id}")
    public ResponseEntity<Occupancy> updateOccupancy(@PathVariable Long id, @RequestBody Occupancy occupancy) {
        try {
            return occupancyService.findOccupancyById(id)
                    .map(existingOccupancy -> {
                        existingOccupancy.setLocationId(occupancy.getLocationId());
                        existingOccupancy.updateTimeRange(occupancy.getStartTime(), occupancy.getEndTime());
                        existingOccupancy.updateStatus(occupancy.getStatus());
                        existingOccupancy.setNotes(occupancy.getNotes());
                        Occupancy updatedOccupancy = occupancyService.saveOccupancy(existingOccupancy);
                        return ResponseEntity.ok(updatedOccupancy);
                    })
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Löscht eine Belegung anhand ihrer ID
     *
     * @param id Die ID der zu löschenden Belegung
     * @return 204 No Content, wenn die Belegung erfolgreich gelöscht wurde, oder 404 Not Found, wenn keine Belegung gefunden wurde
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOccupancy(@PathVariable Long id) {
        if (occupancyService.existsOccupancyById(id)) {
            occupancyService.deleteOccupancyById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Sucht Belegungen nach Standort und Zeitraum
     *
     * @param locationId Die ID des Standorts
     * @param startTime Die Startzeit des Zeitraums
     * @param endTime Die Endzeit des Zeitraums
     * @return Eine Liste der gefundenen Belegungen
     */
    @GetMapping("/search")
    public ResponseEntity<List<Occupancy>> searchOccupancies(
            @RequestParam Long locationId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        try {
            List<Occupancy> occupancies = occupancyService.findOccupanciesByLocationAndTimeRange(locationId, startTime, endTime);
            return ResponseEntity.ok(occupancies);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Aktualisiert den Status einer Belegung
     *
     * @param id Die ID der zu aktualisierenden Belegung
     * @param status Der neue Status
     * @return Die aktualisierte Belegung oder 404 Not Found, wenn keine Belegung gefunden wurde
     */
    @PatchMapping("/{id}/status")
    public ResponseEntity<Occupancy> updateOccupancyStatus(@PathVariable Long id, @RequestParam String status) {
        try {
            return occupancyService.findOccupancyById(id)
                    .map(existingOccupancy -> {
                        existingOccupancy.updateStatus(status);
                        Occupancy updatedOccupancy = occupancyService.saveOccupancy(existingOccupancy);
                        return ResponseEntity.ok(updatedOccupancy);
                    })
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}