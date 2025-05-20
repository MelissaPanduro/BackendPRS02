package pe.edu.vallegrande.vg_ms_product;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pe.edu.vallegrande.vg_ms_product.model.Sale;
import pe.edu.vallegrande.vg_ms_product.repository.SaleRepository;
import pe.edu.vallegrande.vg_ms_product.service.SaleService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class SaleServiceTest {

    private SaleRepository saleRepository;
    private SaleService saleService;

    @BeforeEach
    void setUp() {
        saleRepository = Mockito.mock(SaleRepository.class);
        saleService = new SaleService(saleRepository);
    }

    @Test
    void testCreateSale() {
        Sale sale = new Sale();
        when(saleRepository.save(any(Sale.class))).thenReturn(Mono.just(sale));

        StepVerifier.create(saleService.createSale(sale))
                .expectNext(sale)
                .verifyComplete();

        System.out.println("Venta creada correctamente.");
        verify(saleRepository, times(1)).save(sale);
    }

    @Test
    void testUpdateSale() {
        Sale sale = new Sale();
        sale.setId(1L);

        when(saleRepository.findById(1L)).thenReturn(Mono.just(new Sale()));
        when(saleRepository.save(any(Sale.class))).thenReturn(Mono.just(sale));

        StepVerifier.create(saleService.updateSale(1L, sale))
                .expectNext(sale)
                .verifyComplete();

        System.out.println("Venta actualizada correctamente.");
        verify(saleRepository, times(1)).findById(1L);
        verify(saleRepository, times(1)).save(sale);
    }

    @Test
    void testDeleteSale() {
        when(saleRepository.deleteById(1L)).thenReturn(Mono.empty());

        StepVerifier.create(saleService.deleteSale(1L))
                .verifyComplete();

        System.out.println("Venta eliminada correctamente.");
        verify(saleRepository, times(1)).deleteById(1L);
    }

    @Test
    void testGetAllSales() {
        Sale s1 = new Sale();
        Sale s2 = new Sale();

        when(saleRepository.findAll()).thenReturn(Flux.just(s1, s2));

        StepVerifier.create(saleService.getAllSales())
                .expectNext(s1)
                .expectNext(s2)
                .verifyComplete();

        System.out.println("Todas las ventas recuperadas correctamente.");
        verify(saleRepository, times(1)).findAll();
    }

    @Test
    void testGetSaleById() {
        Sale sale = new Sale();
        when(saleRepository.findById(1L)).thenReturn(Mono.just(sale));

        StepVerifier.create(saleService.getSaleById(1L))
                .expectNext(sale)
                .verifyComplete();

        System.out.println("Venta recuperada por ID correctamente.");
        verify(saleRepository, times(1)).findById(1L);
    }
}
