package pe.edu.vallegrande.vg_ms_product.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import pe.edu.vallegrande.vg_ms_product.model.Sale;
import reactor.core.publisher.Flux;
import java.time.LocalDate;

@Repository
public interface SaleRepository extends ReactiveCrudRepository<Sale, Long> {
    
    Flux<Sale> findByName(String name);
    
    Flux<Sale> findBySaleDateBetween(LocalDate startDate, LocalDate endDate);
}