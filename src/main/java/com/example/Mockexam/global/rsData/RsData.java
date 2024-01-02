package com.example.Mockexam.global.rsData;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@Getter
public class RsData<T> {
    private final String resultCode;
    private final String msg;
    private final T data;
    private final int statusCode;

    public static <T> RsData<T> of(String resultCode,String msg,T data){
        int statuesCode =Integer.parseInt(resultCode);
        return new RsData<>(resultCode,msg, data, statuesCode);
    }


    public boolean isSuccess(){
        return statusCode >= 200 && statusCode < 400;
    }

    public boolean isFail(){
        return !isSuccess();
    }

    public <T> RsData<T> of(T data){
        return RsData.of(resultCode,msg,data);
    }
}
