package com.tts.ECommerce.repository;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tts.ECommerce.model.Product;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {
	List<Product> findAll();

	Product findById(long id);

	List<Product> findByBrand(String brand);

	List<Product> findByCategory(String category);

	List<Product> findByBrandAndCategory(String brand, String category);

	@Query("SELECT DISTINCT p.brand FROM Product p")
	List<String> findDistinctBrands();

	@Query("SELECT DISTINCT p.category FROM Product p")
	List<String> findDistinctCategories();
	/*
	 * Hibernate: select product0_.product_id as product_1_1_, product0_.brand as brand2_1_, product0_.category as category3_1_, product0_.description 
	 * 		as descript4_1_, product0_.name as name5_1_, product0_.picture as picture6_1_, product0_.price as price7_1_, product0_.quantity as quantity8_1_ 
	 * 		from product product0_
	 * Hibernate: select product0_.product_id as product_1_1_, product0_.brand as brand2_1_, product0_.category as category3_1_, product0_.description as descript4_1_, product0_.name as name5_1_, product0_.picture as picture6_1_, product0_.price as price7_1_, product0_.quantity as quantity8_1_ from product product0_
	 * Hibernate: select product0_.product_id as product_1_1_, product0_.brand as brand2_1_, product0_.category as category3_1_, product0_.description as descript4_1_, product0_.name as name5_1_, product0_.picture as picture6_1_, product0_.price as price7_1_, product0_.quantity as quantity8_1_ from product product0_
	 * Hibernate: select product0_.product_id as product_1_1_, product0_.brand as brand2_1_, product0_.category as category3_1_, product0_.description 
	 * 		as descript4_1_, product0_.name as name5_1_, product0_.picture as picture6_1_, product0_.price as price7_1_, product0_.quantity as quantity8_1_ 
	 * 		from product product0_
	 */
}