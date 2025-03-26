package at.fhbfi.tourism.noetourismapp.model;

import jakarta.persistence.*;

@Entity
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int stars;
    private int branches;
    private int rooms;
    private int beds;

    public Hotel() {}

    public Hotel(String name, int stars, int branches, int rooms, int beds) {
        this.name = name;
        this.stars = stars;
        this.branches = branches;
        this.rooms = rooms;
        this.beds = beds;
    }

    // Getter + Setter

    public Long getId() { return id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getStars() { return stars; }
    public void setStars(int stars) { this.stars = stars; }

    public int getBranches() { return branches; }
    public void setBranches(int branches) { this.branches = branches; }

    public int getRooms() { return rooms; }
    public void setRooms(int rooms) { this.rooms = rooms; }

    public int getBeds() { return beds; }
    public void setBeds(int beds) { this.beds = beds; }
}
