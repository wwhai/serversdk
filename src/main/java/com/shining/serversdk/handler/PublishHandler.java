package com.shining.serversdk.handler;

public abstract class PublishHandler {
    public abstract void onSuccess();

    public abstract void onException(Exception e);

}
