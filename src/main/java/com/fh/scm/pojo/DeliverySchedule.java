package com.fh.scm.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fh.scm.enums.DeliveryMethodType;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "delivery_schedule")
public class DeliverySchedule extends _BaseEntity implements Serializable {

    @Column(nullable = false)
    @NotNull(message = "{deliverySchedule.scheduledDate.notNull}")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime scheduledDate;

    @Builder.Default
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull(message = "{deliverySchedule.method.notNull}")
    private DeliveryMethodType method = DeliveryMethodType.EXPRESS;

    @OneToMany(mappedBy = "deliverySchedule")
    private Set<Order> orderSet;

    @OneToMany(mappedBy = "deliverySchedule", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Shipment> shipmentSet;

    @Override
    public String toString() {
        return "com.fh.scm.pojo.DeliverySchedule[ id=" + this.id + " ]";
    }
}
