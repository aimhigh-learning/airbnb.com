package org.airbnb.com.main.customer;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.util.StringUtils;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.Instant;
import java.util.TimeZone;
import java.util.UUID;

/**
 * {@link javax.persistence.Entity} for customer
 * @author sandeep.rana
 */
@Entity
@Table(name = "customers")
@Data
public class CustomerEntity implements Serializable {

    @Id
    private String customerId;

    private String name;

    private String phNumber;

    private String address;

    @CreatedDate
    private Long regDate;

    private String timeZone;

    private Status status;

    public CustomerEntity() {
        if(!StringUtils.hasText(getCustomerId())) {
            this.setCustomerId(UUID.randomUUID().toString());
        }
        setRegDate(Instant.now().toEpochMilli());
        setStatus(Status.accepted);
    }


    public enum Status {
        accepted,
        confirmed,
        canceled,
        waiting
    }

}
