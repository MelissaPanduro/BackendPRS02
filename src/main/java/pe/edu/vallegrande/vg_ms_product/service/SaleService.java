package pe.edu.vallegrande.vg_ms_product.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.edu.vallegrande.vg_ms_product.model.Sale;
import pe.edu.vallegrande.vg_ms_product.repository.SaleRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class SaleService {
    
    private final SaleRepository saleRepository;
    
    public Mono<Sale> createSale(Sale sale) {
        return saleRepository.save(sale);
    }
    
    public Mono<Sale> updateSale(Long id, Sale sale) {
        return saleRepository.findById(id)
                .flatMap(existingSale -> {
                    sale.setId(id);
                    return saleRepository.save(sale);
                });
    }
    
    public Mono<Void> deleteSale(Long id) {
        return saleRepository.deleteById(id);
    }
    
    public Flux<Sale> getAllSales() {
        return saleRepository.findAll();
    }
    
    public Mono<Sale> getSaleById(Long id) {
        return saleRepository.findById(id);
    }
}
