package com.aruoxi.ebookshop.exception;

public class ResourceNotFoundException extends Exception {

    public ResourceNotFoundException() {
        super("未找到资源");
    }

    public ResourceNotFoundException(String msg) {
        super(msg);
    }    
}
