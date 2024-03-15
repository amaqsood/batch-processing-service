package com.royalcyber.batchprocessingservice.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(schema ="poscfsexportmapping",  name = "pos_cfs_order")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class POSCFSOrder {

    @Id
    //@GeneratedValue(strategy=GenerationType.SEQUENCE)
    //Need above only if we are not setting primaryKey ID value
    private Integer Id;

    @Column(name = "pos_order_id")
    private Integer posOrderId;

    @Column(name = "cfs_order_summary_id")
    private Integer cfsOrderSummaryId;

    @Column(name="cfs_order_number")
    private String cfsOrderNumber;
}
