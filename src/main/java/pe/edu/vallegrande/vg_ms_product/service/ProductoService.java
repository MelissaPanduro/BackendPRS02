package pe.edu.vallegrande.vg_ms_product.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.vallegrande.vg_ms_product.model.ProductoModel;
import pe.edu.vallegrande.vg_ms_product.repository.ProductoRepository;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;

    @Autowired
    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    // Crear un nuevo producto con validación de fechas
    public Mono<ProductoModel> createProduct(ProductoModel product) {
        if (product.getExpiryDate() != null && product.getEntryDate() != null &&
                product.getExpiryDate().isBefore(product.getEntryDate())) {
            return Mono.error(new IllegalArgumentException("La fecha de caducidad no puede ser anterior a la fecha de entrada"));
        }
        return productoRepository.save(product);
    }

    // Obtener todos los productos
    public Flux<ProductoModel> getAllProducts() {
        return productoRepository.findAll();
    }

    // Eliminar un producto por su ID
    public Mono<Void> deleteProduct(Long id) {
        return productoRepository.deleteById(id);
    }

    // Eliminar un producto de forma lógica (estado "Inactivo")
    public Mono<ProductoModel> softDeleteProduct(Long id) {
        return productoRepository.findById(id)
                .flatMap(product -> {
                    product.setStatus("I"); // "I" de Inactivo
                    return productoRepository.save(product);
                });
    }

    // Restaurar un producto (estado "Activo")
    public Mono<ProductoModel> restoreProduct(Long id) {
        return productoRepository.findByIdAndStatus(id, "I")
                .flatMap(product -> {
                    product.setStatus("A"); // "A" de Activo
                    return productoRepository.save(product);
                });
    }

    // Actualizar un producto con validación de fechas y actualización de campos adicionales
    public Mono<ProductoModel> updateProduct(Long id, ProductoModel productDetails) {
        return productoRepository.findById(id)
                .flatMap(existingProduct -> {
                    if (productDetails.getExpiryDate() != null && productDetails.getEntryDate() != null &&
                            productDetails.getExpiryDate().isBefore(productDetails.getEntryDate())) {
                        return Mono.error(new IllegalArgumentException("La fecha de caducidad no puede ser anterior a la fecha de entrada"));
                    }

                    existingProduct.setType(productDetails.getType());
                    existingProduct.setDescription(productDetails.getDescription());
                    existingProduct.setPackageWeight(productDetails.getPackageWeight());
                    existingProduct.setStock(productDetails.getStock());
                    existingProduct.setEntryDate(productDetails.getEntryDate());
                    existingProduct.setExpiryDate(productDetails.getExpiryDate());
                    existingProduct.setTypeProduct(productDetails.getTypeProduct()); // Campo adicional
                    existingProduct.setStatus(productDetails.getStatus());           // Campo adicional

                    return productoRepository.save(existingProduct);
                });
    }
}

