package com.fh.scm.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "product")
public class Product extends BaseEntity implements Serializable {

    @NotNull(message = "{product.name.notNull}")
    @Column(nullable = false, unique = true)
    private String name;

    @Builder.Default
    @NotNull(message = "{product.price.notNull}")
    @Column(nullable = false, precision = 11, scale = 2, columnDefinition = "decimal default 0.0")
    private BigDecimal price = BigDecimal.ZERO;

    @Column(length = 300)
    private String image;

    private String description;

    @NotNull(message = "{product.expiryDate.notNull}")
    @Column(name = "expiry_date", nullable = false)
    private Date expiryDate;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, optional = false)
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private Category category;

    @OneToMany(mappedBy = "product", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private Set<ProductUnit> productUnitSet;

    @OneToMany(mappedBy = "product", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private Set<ProductTag> productTagSet;

    @JsonIgnore
    @OneToMany(mappedBy = "product", cascade = {CascadeType.MERGE, CascadeType.REMOVE})
    private Set<OrderDetails> orderDetailsSet;

    @OneToMany(mappedBy = "product", cascade = {CascadeType.MERGE, CascadeType.REMOVE})
    private Set<SupplierCosting> supplierCostingSet;

    @JsonIgnore
    @OneToMany(mappedBy = "product", cascade = {CascadeType.MERGE, CascadeType.REMOVE})
    private Set<InventoryDetails> inventoryDetailsSet;
}
