package com.itsf.sales.taxes.adapter.persistance.repository;

import com.itsf.sales.taxes.adapter.persistance.entity.OrderLineEntity;
import com.itsf.sales.taxes.adapter.persistance.entity.OrderLineId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderLineRepository extends JpaRepository<OrderLineEntity, OrderLineId> {

  @Query("select o from OrderLineEntity o where o.id.purchaseId =:idPurchase")
  List<OrderLineEntity> findAllByIdPurchase(@Param("idPurchase") long idPurchase);
}
