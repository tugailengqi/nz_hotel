package com.lengqi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class ResultData<T> {
    private Integer code;
    private String msg;
    private T data;

    public static interface Code{
        Integer CODE_SUCC = 200;
        Integer CODE_ERROR = 300;
        Integer CODE_RELOGIN = 500;
    }
}
