package com.xuxiaojin.common.keepalive;

import com.xuxiaojin.common.Operation;
import lombok.Data;
import lombok.extern.java.Log;

@Data
@Log
public class KeepaliveOperation extends Operation {
    private long time;

    public KeepaliveOperation(){
        this.time=System.nanoTime();
    }
    @Override
    public KeepaliveOperationResult execute() {
        KeepaliveOperationResult operationResult=new KeepaliveOperationResult(this.time);
        return operationResult;
    }
}
