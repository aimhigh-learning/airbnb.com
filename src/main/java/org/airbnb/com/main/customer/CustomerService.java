package org.airbnb.com.main.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 *
 * @author sandeep.rana
 */
@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;


    public String addCustomer(CustomerEntity entity) {
        if(!customerRepository.findById(entity.getCustomerId()).isEmpty()) {
            throw new RuntimeException("User already exits");
        }
        CustomerEntity ce =  customerRepository.save(entity);
        return ce.getCustomerId();
    }

    public Optional<CustomerEntity> getDetails(String customerId) {
        return customerRepository.findById(customerId);
    }

    public Page<CustomerEntity> getCustomers(String serachString) {
        return customerRepository.findAll(Pageable.ofSize(Integer.MAX_VALUE));
    }

    public String updateStatus(String id , CustomerEntity.Status status) {
        CustomerEntity ce =  customerRepository.findById(id).get();
        ce.setStatus(status);
        customerRepository.save(ce);
        return ce.getCustomerId();
    }
}

