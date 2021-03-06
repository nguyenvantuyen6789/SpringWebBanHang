package com.tuyennguyen.entity;

import com.tuyennguyen.model.mapping.ProductMap;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_product")
@SqlResultSetMapping(name = "listProductMapName", classes = {
    @ConstructorResult(targetClass = ProductMap.class, columns = {
        @ColumnResult(name = "productId", type = Integer.class),
        @ColumnResult(name = "isVisible", type = Integer.class),
        @ColumnResult(name = "productName", type = String.class),
        @ColumnResult(name = "favourite", type = Integer.class),
        @ColumnResult(name = "price", type = String.class),
        @ColumnResult(name = "quantity", type = Integer.class),
        @ColumnResult(name = "sale", type = Integer.class),
        @ColumnResult(name = "menuName", type = String.class),
        @ColumnResult(name = "imageName", type = String.class),
        @ColumnResult(name = "giaConLai", type = String.class),
    })
})
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer productId;

    @Column(nullable = false)
    private String productName;

    @Column(nullable = true)
    private String price;

    @Column(nullable = true)
    private String description;

    @Column(nullable = true)
    private String imageName;

    @Column(nullable = false, columnDefinition = "SMALLINT UNSIGNED")
    private Integer quantity = 0;

    @Column(nullable = false)
    private Integer isVisible = 1;

    @Column(nullable = true)
    private Integer menuDongId;

    @Column(nullable = false, columnDefinition = "TINYINT(1) UNSIGNED")
    private Integer favourite = 0;

    @Column(nullable = false, columnDefinition = "TINYINT(1) UNSIGNED")
    private Integer sale = 0;

    @Column(nullable = true)
    private String salePercent;

    @Column(nullable = true)
    private String salePrice;

    @Column(nullable = true)
    private String giaConLai;

}
