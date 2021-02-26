package com.jelly.taskibbackend.repository;

import com.jelly.taskibbackend.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
Customer findByPic(String pic);
}
