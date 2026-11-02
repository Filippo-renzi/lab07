package it.unibo.inner.impl;

import java.util.LinkedList;
import java.util.List;

import it.unibo.inner.api.Predicate;

public class PredictateImpl<T> implements Predicate<T>{

    List<T> e;
    public PredictateImpl(T[] e){
        this.e = new LinkedList<>();
        for(int i = 0; i <= e.length; i++){
            this.e.add(e[i]);
        }
    }
    @Override
    public boolean test(T elem) {
        if(e.contains(elem)){return true;}
       return false;
    }

}
