package org.example.refactoringMiner.MinerUtils;

import org.example.util.callerWaited;

import java.util.ArrayList;

public class leftSideCallers {
    private ArrayList<callerWaited> leftSideCallers = new ArrayList<>();

    public leftSideCallers(ArrayList<callerWaited> leftSideCallers) {
        this.leftSideCallers = leftSideCallers;
    }

    public ArrayList<callerWaited> getLeftSideCallers() {
        return leftSideCallers;
    }

    public void setLeftSideCallers(ArrayList<callerWaited> leftSideCallers) {
        this.leftSideCallers = leftSideCallers;
    }

    public void addAllInArray(ArrayList<callerWaited> callers){
        leftSideCallers.addAll(callers);
    }

    @Override
    public String toString() {
        return "leftSideCallers{" +
                "leftSideCallers=" + leftSideCallers +
                '}';
    }
}
