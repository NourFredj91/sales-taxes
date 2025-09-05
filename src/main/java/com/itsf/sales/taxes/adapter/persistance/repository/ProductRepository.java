package com.itsf.sales.taxes.adapter.persistance.repository;

import com.itsf.sales.taxes.adapter.persistance.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

  @Query("select distinct p from ProductEntity p inner join OrderLineEntity o ON p.id = o.id.productId where o.id.purchaseId =:purchaseId")
  List<ProductEntity> findProductsByIdPurchase(@Param("purchaseId") long purchaseId);
}
