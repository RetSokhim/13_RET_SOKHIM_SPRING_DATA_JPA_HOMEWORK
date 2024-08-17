package org.example.spring_data_jpa_homework.service.serviceimplement;

import org.example.spring_data_jpa_homework.model.entity.Customer;
import org.example.spring_data_jpa_homework.model.entity.Email;
import org.example.spring_data_jpa_homework.model.enumerations.SortDirection;
import org.example.spring_data_jpa_homework.model.request.CustomerRequest;
import org.example.spring_data_jpa_homework.model.response.CustomerResponse;
import org.example.spring_data_jpa_homework.repository.CustomerRepository;
import org.example.spring_data_jpa_homework.service.CustomerService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImplement implements CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerServiceImplement(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public CustomerResponse addNewCustomer(CustomerRequest customerRequest) {
        Email email = new Email();
        email.setEmail(customerRequest.getEmail());
        Customer customer = customerRequest.toCustomer();
        customer.setEmail(email);
        return customerRepository.save(customer).toCustomerResponse();
    }

    @Override
    public List<CustomerResponse> getAllCustomer(Integer pageNumber, Integer pageSize, String sortField, SortDirection sortDirection) {
        Sort sort = sortDirection.name().
                equalsIgnoreCase(Sort.Direction.ASC.name())?
                Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNumber-1,pageSize,sort);
        return toListCustomerResponse(customerRepository.findAll(pageable).getContent());
    }

    @Override
    public CustomerResponse getCustomerById(Long customerId) {
        return customerRepository.findById(customerId).orElseThrow().toCustomerResponse();
    }

    @Override
    public CustomerResponse updateCustomerById(Long customerId, CustomerRequest customerRequest) {
        Customer customer = customerRepository.findById(customerId).orElseThrow();
        customer.setCustomerName(customerRequest.getCustomerName());
        customer.setAddress(customerRequest.getAddress());
        customer.setPhoneNumber(customerRequest.getPhoneNumber());
        Email email = customer.getEmail();
        if (email != null) {
            email.setEmail(customerRequest.getEmail());
        } else {
            email = new Email();
            email.setEmail(customerRequest.getEmail());
            customer.setEmail(email);
        }
        Customer savedCustomer = customerRepository.save(customer);
        return savedCustomer.toCustomerResponse();
    }


    @Override
    public void deleteCustomerById(Long customerId) {
        customerRepository.deleteById(customerId);
    }

    public List<CustomerResponse> toListCustomerResponse(List<Customer> customers){
        return customers.stream().
                map(Customer::toCustomerResponse).
                collect(Collectors.toList());
    }
}
