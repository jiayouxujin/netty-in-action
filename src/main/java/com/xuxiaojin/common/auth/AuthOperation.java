package com.xuxiaojin.common.auth;

import com.xuxiaojin.common.Operation;
import com.xuxiaojin.common.OperationResult;
import lombok.Data;
import lombok.extern.java.Log;

@Data
@Log
public class AuthOperation extends Operation {

    private final String userName;
    private final String passWord;

    @Override
    public AuthOperationResult execute() {
        if ("admin".equalsIgnoreCase(this.userName)){
            AuthOperationResult operationResult=new AuthOperationResult(true);
            return operationResult;
        }
        return new AuthOperationResult(false);
    }
}
