package com.xuxiaojin.common.order;

import com.xuxiaojin.common.Operation;
import com.xuxiaojin.common.OperationResult;
import lombok.Data;
import lombok.extern.java.Log;

@Data
@Log
public class OrderOperation extends Operation {

    private int tableId;
    private String dish;

    public OrderOperation(int tableId, String dish) {
        this.tableId = tableId;
        this.dish = dish;
    }

    @Override
    public OperationResult execute() {
        System.out.printf("order's executing startup with orderRequest: " + toString());
        System.out.printf("order's executing complete");
        OrderOperationResult operationResult = new OrderOperationResult(tableId, dish, true);
        return operationResult;
    }
}
