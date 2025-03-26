package at.fhbfi.tourism.noetourismapp;

import at.fhbfi.tourism.noetourismapp.model.Hotel;
import at.fhbfi.tourism.noetourismapp.repository.HotelRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class NoeTourismAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(NoeTourismAppApplication.class, args);
	}

	@Bean
	CommandLineRunner initHotels(HotelRepository repo) {
		return args -> {
			repo.save(new Hotel("Hotel Sissi", 4, 2, 100, 200));
			repo.save(new Hotel("Hotel Mozart", 5, 1, 80, 150));
		};
	}

}
