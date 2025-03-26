// at.fhbfi.tourism.noetourismapp.service.HotelService.java
package at.fhbfi.tourism.noetourismapp.service;

import at.fhbfi.tourism.noetourismapp.model.Hotel;
import at.fhbfi.tourism.noetourismapp.repository.HotelRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelService {

    private final HotelRepository hotelRepository;

    public HotelService(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    public List<Hotel> getAllHotels() {
        return hotelRepository.findAll();
    }

    public Hotel saveHotel(Hotel hotel) {
        return hotelRepository.save(hotel);
    }
    public void deleteHotelById(Long id) {
        hotelRepository.deleteById(id);
    }
}
