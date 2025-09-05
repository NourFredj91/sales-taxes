package com.itsf.sales.taxes.adapter.persistance.repository;

import com.itsf.sales.taxes.adapter.persistance.entity.PurchaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseRepository extends JpaRepository<PurchaseEntity, Long> {
}
