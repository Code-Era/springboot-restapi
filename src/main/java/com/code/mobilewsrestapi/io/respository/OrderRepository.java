package com.code.mobilewsrestapi.io.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.code.mobilewsrestapi.io.entity.OrderEntity;
import com.code.mobilewsrestapi.io.entity.UserEntity;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long>{

}
