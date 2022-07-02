package org.airbnb.com.main.customer;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * {@link org.springframework.data.repository.Repository} for {@link CustomerEntity}
 * @author sandeep.rana
 */
@Repository
public interface CustomerRepository extends PagingAndSortingRepository<CustomerEntity, String> {
    
}
