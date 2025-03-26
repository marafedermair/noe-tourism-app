// at.fhbfi.tourism.noetourismapp.repository.HotelRepository.java
package at.fhbfi.tourism.noetourismapp.repository;

import at.fhbfi.tourism.noetourismapp.model.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {
}
