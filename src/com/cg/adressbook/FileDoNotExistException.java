package com.cg.adressbook;

public class FileDoNotExistException extends Throwable {
    public FileDoNotExistException(String msg) {
        super(msg);
    }
}
