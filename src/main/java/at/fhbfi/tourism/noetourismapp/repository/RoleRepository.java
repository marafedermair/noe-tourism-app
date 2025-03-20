package at.fhbfi.tourism.noetourismapp.repository;

import at.fhbfi.tourism.noetourismapp.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository-Schnittstelle für den Datenzugriff auf Benutzerrollen
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    /**
     * Findet eine Rolle anhand ihres Namens
     *
     * @param name Der Name der zu findenden Rolle
     * @return Ein Optional, das die gefundene Rolle enthält, oder ein leeres Optional, wenn keine Rolle gefunden wurde
     */
    Optional<Role> findByName(String name);

    /**
     * Prüft, ob eine Rolle mit dem angegebenen Namen existiert
     *
     * @param name Der Name der zu prüfenden Rolle
     * @return true, wenn eine Rolle mit dem angegebenen Namen existiert, sonst false
     */
    boolean existsByName(String name);
}
