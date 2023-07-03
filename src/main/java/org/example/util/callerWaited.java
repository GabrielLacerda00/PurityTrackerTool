package org.example.util;

public class callerWaited {

    private String lineCall;
    private String callNameMethod;
    public callerWaited(String lineCall, String callNameMethod){
        this.lineCall = lineCall;
        this.callNameMethod = callNameMethod;
    }

    public String getLineCall() {
        return lineCall;
    }

    public String getCallNameMethod() {
        return callNameMethod;
    }

    @Override
    public String toString() {
        return "callerWaited{" +
                "lineCall='" + lineCall + '\'' +
                ", callNameMethod='" + callNameMethod + '\'' +
                '}';
    }
}
