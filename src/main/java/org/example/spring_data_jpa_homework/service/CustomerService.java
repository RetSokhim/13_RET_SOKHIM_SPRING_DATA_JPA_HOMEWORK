package org.example.spring_data_jpa_homework.service;

import org.example.spring_data_jpa_homework.model.enumerations.SortDirection;
import org.example.spring_data_jpa_homework.model.request.CustomerRequest;
import org.example.spring_data_jpa_homework.model.response.CustomerResponse;

import java.util.List;

public interface CustomerService {
    CustomerResponse addNewCustomer(CustomerRequest customerRequest);
    List<CustomerResponse> getAllCustomer(Integer pageNumber, Integer pageSize, String sortField, SortDirection sortDirection);
    CustomerResponse getCustomerById(Long customerId);
    CustomerResponse updateCustomerById(Long customerId, CustomerRequest customerRequest);
    void deleteCustomerById(Long customerId);
}
