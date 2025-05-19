package pe.edu.vallegrande.vg_ms_product.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table("sale") // Mapea la clase a la tabla "sale" de la base de datos
public class Sale {

    @Id
    private Long id; // Identificador único de la venta (clave primaria)

    @Column("sale_date")
    private LocalDate saleDate; // Fecha en que se realizó la venta

    @Column("name")
    private String name; // Nombre del cliente o empresa

    @Column("ruc")
    private String ruc; // RUC del cliente (Registro Único de Contribuyentes)

    @Column("address")
    private String address; // Dirección del cliente

    @Column("product_id")
    private Integer productId; // ID del producto vendido (posible relación a tabla de productos)

    @Column("weight")
    private BigDecimal weight; // Peso unitario del producto (en kilogramos)

    @Column("packages")
    private Integer packages; // Número de paquetes vendidos

    @Column("total_weight")
    private BigDecimal totalWeight; // Peso total de la venta (peso unitario x paquetes)

    @Column("price_per_kg")
    private BigDecimal pricePerKg; // Precio por kilogramo del producto

    @Column("total_price")
    private BigDecimal totalPrice; // Precio total de la venta (peso total x precio por kg)
}
