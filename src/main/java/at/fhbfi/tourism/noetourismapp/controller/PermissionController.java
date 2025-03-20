package at.fhbfi.tourism.noetourismapp.controller;

import at.fhbfi.tourism.noetourismapp.model.Permission;
import at.fhbfi.tourism.noetourismapp.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller für die Verwaltung von Berechtigungen
 *
 * TODO: Implementiere den PermissionController
 *
 * Dieser Controller stellt REST-API-Endpunkte für die Verwaltung von Berechtigungen bereit.
 *
 * Folgende Schritte sind notwendig:
 * 1. Implementiere CRUD-Endpunkte (Create, Read, Update, Delete)
 * 2. Implementiere Fehlerbehandlung für nicht gefundene Ressourcen
 */
@RestController
@RequestMapping("/api/permissions")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    /**
     * Gibt alle Berechtigungen zurück
     *
     * @return Eine Liste aller Berechtigungen
     */
    @GetMapping
    public ResponseEntity<List<Permission>> getAllPermissions() {
        // TODO: Implementiere diese Methode
        // 1. Rufe den PermissionService auf, um alle Berechtigungen zu finden
        // 2. Gib die gefundenen Berechtigungen zurück
        return null;
    }

    /**
     * Gibt eine Berechtigung anhand ihrer ID zurück
     *
     * @param id Die ID der zu findenden Berechtigung
     * @return Die gefundene Berechtigung oder 404 Not Found, wenn keine Berechtigung gefunden wurde
     */
    @GetMapping("/{id}")
    public ResponseEntity<Permission> getPermissionById(@PathVariable Long id) {
        // TODO: Implementiere diese Methode
        // 1. Rufe den PermissionService auf, um die Berechtigung anhand ihrer ID zu finden
        // 2. Gib die gefundene Berechtigung zurück oder 404 Not Found, wenn keine Berechtigung gefunden wurde
        return null;
    }

    /**
     * Erstellt eine neue Berechtigung
     *
     * @param permission Die zu erstellende Berechtigung
     * @return Die erstellte Berechtigung und 201 Created
     */
    @PostMapping
    public ResponseEntity<Permission> createPermission(@RequestBody Permission permission) {
        // TODO: Implementiere diese Methode
        // 1. Rufe den PermissionService auf, um die Berechtigung zu speichern
        // 2. Gib die erstellte Berechtigung und 201 Created zurück
        return null;
    }

    /**
     * Aktualisiert eine bestehende Berechtigung
     *
     * @param id Die ID der zu aktualisierenden Berechtigung
     * @param permission Die aktualisierten Daten der Berechtigung
     * @return Die aktualisierte Berechtigung oder 404 Not Found, wenn keine Berechtigung gefunden wurde
     */
    @PutMapping("/{id}")
    public ResponseEntity<Permission> updatePermission(@PathVariable Long id, @RequestBody Permission permission) {
        // TODO: Implementiere diese Methode
        // 1. Prüfe, ob die Berechtigung existiert
        // 2. Aktualisiere die Berechtigung
        // 3. Gib die aktualisierte Berechtigung zurück oder 404 Not Found, wenn keine Berechtigung gefunden wurde
        return null;
    }

    /**
     * Löscht eine Berechtigung anhand ihrer ID
     *
     * @param id Die ID der zu löschenden Berechtigung
     * @return 204 No Content oder 404 Not Found, wenn keine Berechtigung gefunden wurde
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePermission(@PathVariable Long id) {
        // TODO: Implementiere diese Methode
        // 1. Prüfe, ob die Berechtigung existiert
        // 2. Lösche die Berechtigung
        // 3. Gib 204 No Content zurück oder 404 Not Found, wenn keine Berechtigung gefunden wurde
        return null;
    }
}