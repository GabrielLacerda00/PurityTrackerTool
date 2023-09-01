package org.example.refactoringTypes;

import org.example.util.TempObject;

public class inlineMethodObject {
    private TempObject delete;

    private TempObject move;

    public inlineMethodObject(TempObject delete, TempObject move){
        this.delete = delete;
        this.move= move;
    }

    public TempObject getDelete() {
        return delete;
    }


    public TempObject getMove() {
        return move;
    }

    @Override
    public String toString(){
        return "Inline Method{" +
                getDelete()+ '\'' +
                " ,"+
                getMove() + '\'' +
                '}';
    }

}


