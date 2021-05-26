package com.xuxiaojin.common.keepalive;

import com.xuxiaojin.common.OperationResult;
import lombok.Data;

@Data
public class KeepaliveOperationResult extends OperationResult {
    private final long time;
}
