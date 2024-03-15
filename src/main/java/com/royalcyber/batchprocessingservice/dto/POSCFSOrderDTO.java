package com.royalcyber.batchprocessingservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class POSCFSOrderDTO {
    private String posOrderId;
    private String cfsOrderSummaryId;
    private String cfsOrderNumber;
}
