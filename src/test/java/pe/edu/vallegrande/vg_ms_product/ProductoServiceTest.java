package pe.edu.vallegrande.vg_ms_product;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pe.edu.vallegrande.vg_ms_product.model.ProductoModel;
import pe.edu.vallegrande.vg_ms_product.repository.ProductoRepository;
import pe.edu.vallegrande.vg_ms_product.service.ProductoService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class ProductoServiceTest {

    private ProductoRepository productoRepository;
    private ProductoService productoService;

    @BeforeEach
    void setUp() {
        productoRepository = Mockito.mock(ProductoRepository.class);
        productoService = new ProductoService(productoRepository);
    }

    @Test
    void testCreateProduct_withValidDates_shouldSaveProduct() {

        ProductoModel product = new ProductoModel();
        product.setEntryDate(LocalDate.of(2024, 5, 1));
        product.setExpiryDate(LocalDate.of(2024, 6, 1));

        when(productoRepository.save(any(ProductoModel.class))).thenReturn(Mono.just(product));

        StepVerifier.create(productoService.createProduct(product))
                .expectNext(product)
                .verifyComplete();

        verify(productoRepository, times(1)).save(product);

        System.out.println("Producto guardado correctamente con fechas válidas.");
    }

    @Test
    void testGetAllProducts_shouldReturnFlux() {
        
        ProductoModel product1 = new ProductoModel();
        ProductoModel product2 = new ProductoModel();

        when(productoRepository.findAll()).thenReturn(Flux.just(product1, product2));

        StepVerifier.create(productoService.getAllProducts())
                .expectNext(product1)
                .expectNext(product2)
                .verifyComplete();

        verify(productoRepository, times(1)).findAll();

        System.out.println("Todos los productos recuperados exitosamente.");
    }

    @Test
    void testDeleteProduct_shouldCallRepository() {

        when(productoRepository.deleteById(anyLong())).thenReturn(Mono.empty());

        StepVerifier.create(productoService.deleteProduct(1L))
                .verifyComplete();

        verify(productoRepository, times(1)).deleteById(1L);

        System.out.println("Producto eliminado correctamente.");
    }

    @Test
    void testSoftDeleteProduct_shouldUpdateStatus() {

        ProductoModel product = new ProductoModel();
        product.setStatus("A");

        when(productoRepository.findById(anyLong())).thenReturn(Mono.just(product));
        when(productoRepository.save(any(ProductoModel.class)))
                .thenAnswer(invocation -> Mono.just(invocation.getArgument(0)));

        StepVerifier.create(productoService.softDeleteProduct(1L))
                .assertNext(updatedProduct -> {
                    assert updatedProduct.getStatus().equals("I");
                    System.out.println("Estado actualizado a: " + updatedProduct.getStatus());
                })
                .verifyComplete();

        verify(productoRepository, times(1)).findById(1L);
        verify(productoRepository, times(1)).save(any(ProductoModel.class));

        System.out.println("Producto desactivado correctamente.");
    }

    @Test
    void testRestoreProduct_shouldChangeStatusToActive() {

        ProductoModel product = new ProductoModel();
        product.setStatus("I");

        when(productoRepository.findByIdAndStatus(anyLong(), eq("I"))).thenReturn(Mono.just(product));
        when(productoRepository.save(any(ProductoModel.class)))
                .thenAnswer(invocation -> Mono.just(invocation.getArgument(0)));

        StepVerifier.create(productoService.restoreProduct(1L))
                .assertNext(restoredProduct -> {
                    assert restoredProduct.getStatus().equals("A");
                    System.out.println("Estado actualizado a: " + restoredProduct.getStatus());
                })
                .verifyComplete();

        verify(productoRepository, times(1)).findByIdAndStatus(1L, "I");
        verify(productoRepository, times(1)).save(any(ProductoModel.class));

        System.out.println("Producto restaurado correctamente.");
    }
}
