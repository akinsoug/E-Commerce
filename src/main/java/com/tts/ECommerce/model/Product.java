package com.tts.ECommerce.model;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "product_id")
    private Long id;
    
    private int quantity  ; 
    private Double price ; 
    private String description ; 
    private String name ; 
    private String brand  ; 
    private String category  ; 
    private String picture  ; // Url 
    

//	@ManyToOne(fetch = FetchType.LAZY, optional = false)
//	@JoinColumn(name = "user_id")
//	@OnDelete(action = OnDeleteAction.CASCADE)
//	private User user;
    
    
    /*
     * @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "product_id")
    private Long productId;
    
    private int productQuantity  ; 
    private Double productPrice ; 
    private String productDescription ; 
    private String productName ; 
    private String productBrand  ; 
    private String productCategory  ; 
    private String productPictureUrl  ; 
     */

}