package com.voronkov;

public enum MessageType {
    LIST("list"),
    FILE("file"),
    DROP("drop"),
    REQUEST_FILE("request_file");

    private final String name;

    MessageType(String name){
        this.name=name;
    }

    public String getName(){
        return name;
    }
}
