

## 1. Modellklasse (Occupancy)

Die Modellklasse `Occupancy` repräsentiert Belegungsdaten im System und implementiert Validierung und Transaktionssicherheit.

### Implementierungsschritte:

1. **JPA-Annotationen hinzufügen:**
    - `@Entity` für die Klasse
    - `@Table(name = "occupancies")` für die Tabellendefinition
    - `@Id` und `@GeneratedValue` für den Primärschlüssel
    - `@Column` für die Spalten mit entsprechenden Einschränkungen
    - `@Version` für optimistische Sperren

2. **Attribute definieren:**
    - `id`: Primärschlüssel
    - `locationId`: ID des Standorts
    - `startTime`: Startzeit der Belegung
    - `endTime`: Endzeit der Belegung
    - `status`: Status der Belegung (z.B. "RESERVED", "OCCUPIED", "AVAILABLE")
    - `lastModified`: Zeitpunkt der letzten Änderung
    - `notes`: Optionale Notizen
    - `version`: Versionsnummer für optimistische Sperren

3. **Validierungsmethoden implementieren:**
    - `validateTimeRange()`: Prüft, ob der Zeitraum gültig ist (Endzeit nach Startzeit)
    - `updateTimeRange()`: Aktualisiert den Zeitraum und validiert ihn
    - `updateStatus()`: Aktualisiert den Status und validiert ihn

4. **Konstruktoren, Getter und Setter implementieren:**
    - Standardkonstruktor (für JPA)
    - Konstruktor mit Pflichtfeldern
    - Getter und Setter für alle Attribute

5. **equals(), hashCode() und toString() implementieren:**
    - `equals()` und `hashCode()` basierend auf der ID
    - `toString()` für eine lesbare Darstellung

## 2. Repository (OccupancyRepository)

Das Repository `OccupancyRepository` stellt Methoden für den Datenzugriff auf Belegungsdaten bereit.

### Implementierungsschritte:

1. **Interface definieren:**
    - Interface erstellen, das von `JpaRepository<Occupancy, Long>` erbt
    - Mit `@Repository` annotieren

2. **Abfragemethoden definieren:**
    - `findByLocationIdAndTimeRange()`: Findet Belegungen nach Standort und Zeitraum
    - `findByStatus()`: Findet Belegungen nach Status
    - `findByLocationId()`: Findet Belegungen nach Standort

3. **JPQL-Abfragen implementieren:**
    - JPQL-Abfrage für `findByLocationIdAndTimeRange()` mit Überlappungsprüfung

## 3. Service (OccupancyService)

Der Service `OccupancyService` enthält die Geschäftslogik für die Verwaltung von Belegungsdaten und implementiert Validierung und Transaktionssicherheit.

### Implementierungsschritte:

1. **CRUD-Operationen implementieren:**
    - `saveOccupancy()`: Speichert oder aktualisiert eine Belegung
    - `findAllOccupancies()`: Findet alle Belegungen
    - `findOccupancyById()`: Findet eine Belegung anhand ihrer ID
    - `deleteOccupancyById()`: Löscht eine Belegung anhand ihrer ID
    - `existsOccupancyById()`: Prüft, ob eine Belegung existiert

2. **Suchmethoden implementieren:**
    - `findOccupanciesByLocationAndTimeRange()`: Findet Belegungen nach Standort und Zeitraum
    - `findOccupanciesByStatus()`: Findet Belegungen nach Status
    - `findOccupanciesByLocationId()`: Findet Belegungen nach Standort

3. **Validierungsmethoden implementieren:**
    - `validateOccupancy()`: Validiert eine Belegung
    - `validateTimeRange()`: Validiert einen Zeitraum

4. **Transaktionssicherheit implementieren:**
    - `@Transactional` für Methoden, die Daten ändern

## 4. Controller (OccupancyController)

Der Controller `OccupancyController` stellt REST-API-Endpunkte für die Verwaltung von Belegungsdaten bereit und implementiert Validierung und Fehlerbehandlung.

### Implementierungsschritte:

1. **CRUD-Endpunkte implementieren:**
    - `getAllOccupancies()`: Gibt alle Belegungen zurück
    - `getOccupancyById()`: Gibt eine Belegung anhand ihrer ID zurück
    - `createOccupancy()`: Erstellt eine neue Belegung
    - `updateOccupancy()`: Aktualisiert eine bestehende Belegung
    - `deleteOccupancy()`: Löscht eine Belegung anhand ihrer ID

2. **Suchendpunkte implementieren:**
    - `searchOccupancies()`: Sucht Belegungen nach Standort und Zeitraum

3. **Spezielle Endpunkte implementieren:**
    - `updateOccupancyStatus()`: Aktualisiert den Status einer Belegung

4. **Validierung und Fehlerbehandlung implementieren:**
    - Validierung der Eingabedaten
    - Fehlerbehandlung mit entsprechenden HTTP-Statuscodes

## 5. Testen

Die Implementierung sollte mit Unit-Tests und Integrationstests getestet werden.

### Testschritte:

1. **Unit-Tests für die Modellklasse:**
    - Testen der Validierungsmethoden
    - Testen der Konstruktoren und Methoden

2. **Unit-Tests für den Service:**
    - Testen der CRUD-Operationen
    - Testen der Suchmethoden
    - Testen der Validierungsmethoden

3. **Integrationstests für den Controller:**
    - Testen der REST-API-Endpunkte
    - Testen der Fehlerbehandlung

## 6. Beispiele

### Beispiel für die Erstellung einer Belegung:

```java
// Erstellen einer neuen Belegung
Occupancy occupancy = new Occupancy(
    1L, // locationId
    LocalDateTime.of(2023, 6, 1, 12, 0), // startTime
    LocalDateTime.of(2023, 6, 1, 14, 0), // endTime
    "RESERVED" // status
);
occupancy.setNotes("Reservierung für Veranstaltung");

// Speichern der Belegung
Occupancy savedOccupancy = occupancyService.saveOccupancy(occupancy);
```

### Beispiel für die Suche nach Belegungen:

```java
// Suche nach Belegungen für einen Standort und Zeitraum
List<Occupancy> occupancies = occupancyService.findOccupanciesByLocationAndTimeRange(
    1L, // locationId
    LocalDateTime.of(2023, 6, 1, 0, 0), // startTime
    LocalDateTime.of(2023, 6, 2, 0, 0) // endTime
);

// Ausgabe der gefundenen Belegungen
for (Occupancy occupancy : occupancies) {
    System.out.println(occupancy);
}
```

## 7. Hinweise

- Achten Sie auf die Validierung der Eingabedaten, insbesondere bei der Erstellung und Aktualisierung von Belegungen.
- Verwenden Sie Transaktionen, um die Datenintegrität zu gewährleisten.
- Implementieren Sie optimistische Sperren, um Konflikte bei gleichzeitigen Änderungen zu vermeiden.
- Dokumentieren Sie die API-Endpunkte mit Swagger oder einer ähnlichen Technologie.
- Implementieren Sie Logging, um Fehler und wichtige Ereignisse zu protokollieren.

## 8. Erweiterungsmöglichkeiten

- Implementierung von Benachrichtigungen bei Änderungen an Belegungen
- Implementierung von wiederkehrenden Belegungen
- Implementierung von Konflikterkennung und -lösung
- Implementierung von Berechtigungsprüfungen für verschiedene Benutzerrollen
- Implementierung von Statistiken und Berichten