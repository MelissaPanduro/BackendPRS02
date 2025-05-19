package pe.edu.vallegrande.vg_ms_product.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import pe.edu.vallegrande.vg_ms_product.model.ProductoModel;
import reactor.core.publisher.Mono;

public interface ProductoRepository extends ReactiveCrudRepository<ProductoModel, Long> {
    Mono<ProductoModel> findByIdAndStatus(Long id, String status);
}

