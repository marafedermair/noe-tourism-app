package at.fhbfi.tourism.noetourismapp.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Modellklasse für Belegungsdaten
 *
 * Diese Klasse repräsentiert Belegungsdaten im System und implementiert
 * Validierung und Transaktionssicherheit.
 *
 * TODO: Implementiere die Occupancy-Klasse
 *
 * Folgende Schritte sind notwendig:
 * 1. Füge die notwendigen JPA-Annotationen hinzu (@Entity, @Table, @Id, @GeneratedValue, etc.)
 * 2. Implementiere die Attribute (id, locationId, startTime, endTime, status, lastModified, etc.)
 * 3. Implementiere Validierungsmethoden für die Datenintegrität
 * 4. Implementiere Konstruktoren, Getter und Setter
 * 5. Implementiere equals() und hashCode() Methoden
 */
@Entity
@Table(name = "occupancies")
public class Occupancy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long locationId;

    @Column(nullable = false)
    private LocalDateTime startTime;

    @Column(nullable = false)
    private LocalDateTime endTime;

    @Column(nullable = false)
    private String status; // z.B. "RESERVED", "OCCUPIED", "AVAILABLE"

    @Column(nullable = false)
    private LocalDateTime lastModified;

    @Column
    private String notes;

    @Version
    private Long version; // Für optimistische Sperren

    /**
     * Standardkonstruktor (wird von JPA benötigt)
     */
    public Occupancy() {
    }

    /**
     * Konstruktor mit Pflichtfeldern
     *
     * @param locationId Die ID des Standorts
     * @param startTime Die Startzeit der Belegung
     * @param endTime Die Endzeit der Belegung
     * @param status Der Status der Belegung
     */
    public Occupancy(Long locationId, LocalDateTime startTime, LocalDateTime endTime, String status) {
        this.locationId = locationId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
        this.lastModified = LocalDateTime.now();
        validateTimeRange();
    }

    /**
     * Validiert den Zeitraum der Belegung
     * Wirft eine IllegalArgumentException, wenn der Zeitraum ungültig ist
     */
    private void validateTimeRange() {
        if (startTime == null || endTime == null) {
            throw new IllegalArgumentException("Start- und Endzeit dürfen nicht null sein");
        }
        if (endTime.isBefore(startTime)) {
            throw new IllegalArgumentException("Die Endzeit darf nicht vor der Startzeit liegen");
        }
    }

    /**
     * Aktualisiert den Zeitraum der Belegung und validiert ihn
     *
     * @param startTime Die neue Startzeit
     * @param endTime Die neue Endzeit
     */
    public void updateTimeRange(LocalDateTime startTime, LocalDateTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.lastModified = LocalDateTime.now();
        validateTimeRange();
    }

    /**
     * Aktualisiert den Status der Belegung
     *
     * @param status Der neue Status
     */
    public void updateStatus(String status) {
        if (status == null || status.trim().isEmpty()) {
            throw new IllegalArgumentException("Status darf nicht leer sein");
        }
        this.status = status;
        this.lastModified = LocalDateTime.now();
    }

    // Getter und Setter

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
        validateTimeRange();
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
        validateTimeRange();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getLastModified() {
        return lastModified;
    }

    public void setLastModified(LocalDateTime lastModified) {
        this.lastModified = lastModified;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Long getVersion() {
        return version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Occupancy occupancy = (Occupancy) o;
        return Objects.equals(id, occupancy.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Occupancy{" +
                "id=" + id +
                ", locationId=" + locationId +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", status='" + status + '\'' +
                ", lastModified=" + lastModified +
                "}";
    }
}