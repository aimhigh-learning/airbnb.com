package org.airbnb.com.main.customer;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.util.StringUtils;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.time.*;
import java.time.temporal.ChronoField;
import java.util.TimeZone;
import java.util.UUID;

/**
 * {@link javax.persistence.Entity} for customer
 * @author sandeep.rana
 */
@Entity
@Table(name = "customers")
@Data @Slf4j
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

    @Transient
    private boolean canWeCall;

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

    /**
     * Logic to check whether that user is available for call or now
     * @return
     */
    public boolean getCanWeCall() {
        if(getStatus() !=null && !getStatus().name().equalsIgnoreCase(Status.confirmed.name())) {
            try{
                Instant instant = Instant.now();
                ZoneId zone = ZoneId.of(getTimeZone());
                LocalDateTime date = LocalDateTime.ofInstant(instant, zone);
                DayOfWeek day = DayOfWeek.of(date.get(ChronoField.DAY_OF_WEEK));
                return  date.getHour() >=8 && date.getHour() <=20 && !(day == DayOfWeek.SUNDAY || day == DayOfWeek.SATURDAY);
            }catch (Exception e) {
                log.error("Exception {}", e);
            }
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

}
