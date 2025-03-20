package at.fhbfi.tourism.noetourismapp.model;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Modellklasse für Benutzerrollen
 */
@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column
    private String description;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "role_permissions",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id")
    )
    private Set<Permission> permissions = new HashSet<>();

    /**
     * Standardkonstruktor (wird von JPA benötigt)
     */
    public Role() {
    }

    /**
     * Konstruktor mit Name
     *
     * @param name Der Name der Rolle
     */
    public Role(String name) {
        this.name = name;
    }

    /**
     * Konstruktor mit Name und Beschreibung
     *
     * @param name Der Name der Rolle
     * @param description Die Beschreibung der Rolle
     */
    public Role(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    /**
     * Fügt eine Berechtigung zur Rolle hinzu
     *
     * @param permission Die hinzuzufügende Berechtigung
     */
    public void addPermission(Permission permission) {
        this.permissions.add(permission);
    }

    /**
     * Entfernt eine Berechtigung von der Rolle
     *
     * @param permission Die zu entfernende Berechtigung
     */
    public void removePermission(Permission permission) {
        this.permissions.remove(permission);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return id != null && id.equals(role.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", permissions=" + permissions +
                '}';
    }
}
