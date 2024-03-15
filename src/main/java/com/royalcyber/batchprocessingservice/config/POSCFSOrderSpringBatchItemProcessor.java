package com.royalcyber.batchprocessingservice.config;

import com.royalcyber.batchprocessingservice.dto.POSCFSOrderDTO;
import com.royalcyber.batchprocessingservice.entity.POSCFSOrder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class POSCFSOrderSpringBatchItemProcessor implements ItemProcessor<POSCFSOrderDTO, POSCFSOrder> {

    @Override
    public POSCFSOrder process(POSCFSOrderDTO item) throws Exception {
        var summaryId = Optional.ofNullable(item.getCfsOrderSummaryId()).isPresent()
                && !item.getCfsOrderSummaryId().isBlank() ?
                Integer.valueOf(item.getCfsOrderSummaryId()) : null;

        var posOrderId = Optional.ofNullable(item.getPosOrderId()).isPresent()
                && !item.getPosOrderId().isBlank() ?
                Integer.valueOf(item.getPosOrderId()) : null;

        var cfsOrderNumber = Optional.ofNullable(item.getCfsOrderNumber()).isPresent()
                && !item.getCfsOrderNumber().isBlank() ?
                item.getCfsOrderNumber() : null;

        return POSCFSOrder.builder()
                .Id(Optional.ofNullable(posOrderId).isPresent() ? posOrderId : null)
                .cfsOrderSummaryId(summaryId)
                .posOrderId(posOrderId)
                .cfsOrderNumber(cfsOrderNumber)
                .build();
    }
}
