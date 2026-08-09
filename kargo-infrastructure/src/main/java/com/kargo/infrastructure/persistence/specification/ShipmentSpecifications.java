package com.kargo.infrastructure.persistence.specification;

import com.kargo.domain.ShipmentStatus;
import com.kargo.infrastructure.persistence.entity.ShipmentEntity;
import org.springframework.data.jpa.domain.Specification;

import java.time.Instant;

public class ShipmentSpecifications {

    public static Specification<ShipmentEntity> hasStatus(ShipmentStatus status){
        if(status == null)
            return (root, query, cb) -> cb.conjunction();
        return (root, query, cb) -> cb.equal(root.get("status"),status);
    }

    public static Specification<ShipmentEntity> createdBetween(Instant from, Instant to){
        if(from == null || to == null)
            return (root, query, cb) -> cb.conjunction();
        return (root, query, cb) ->
                cb.between(root.get("createdAt"), from, to);
    }

    public static Specification<ShipmentEntity> hasBranch(Long branchId){
        if(branchId == null){
            return (root, query, cb) -> cb.conjunction();
        }
        return (root, query, cb) -> cb.equal(root.get("branch").get("id"),branchId);
    }

    public static Specification<ShipmentEntity> hasCourier(Long courierId){
        if(courierId == null){
            return (root, query, cb) -> cb.conjunction();
        }
        return (root, query, cb) -> cb.equal(root.get("courier").get("id"),courierId);
    }
}
