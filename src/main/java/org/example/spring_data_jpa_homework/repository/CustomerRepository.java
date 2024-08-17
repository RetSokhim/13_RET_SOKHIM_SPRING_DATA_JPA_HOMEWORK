package org.example.spring_data_jpa_homework.repository;

import org.example.spring_data_jpa_homework.model.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer,Long> {
}
