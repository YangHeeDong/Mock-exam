package com.example.Mockexam.global.exceptions;

public class GlobalException extends RuntimeException{
    public GlobalException(String resultCode, String msg){
        super("resultCode="+resultCode+", msg="+msg);
    }
}
