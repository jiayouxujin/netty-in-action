package com.xuxiaojin.common.auth;

import com.xuxiaojin.common.OperationResult;
import lombok.Data;

@Data
public class AuthOperationResult extends OperationResult {
    private final boolean passAuth;
}
