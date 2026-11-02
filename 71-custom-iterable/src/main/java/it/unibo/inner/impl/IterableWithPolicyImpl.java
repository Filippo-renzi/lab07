package it.unibo.inner.impl;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import it.unibo.inner.api.IterableWithPolicy;
import it.unibo.inner.api.Predicate;

public class IterableWithPolicyImpl<T> implements IterableWithPolicy<T>{

    private List<T> lists;
    private Predicate<T> p;
    
    public IterableWithPolicyImpl(T[] elements){
        
        this(elements, new PredictateImpl<>(elements));
    }

    public IterableWithPolicyImpl(T[] elements, Predicate<T> p) {
        this.lists = new LinkedList<>();
        this.p = p;
        for(int i = 0; i < elements.length; i++){
            this.lists.add(elements[i]);
        } 
    }
    
    @Override
    public Iterator<T> iterator() {
         
        Iterator<T> i = lists.iterator();
        return i;
    }

    @Override
    public void setIterationPolicy(Predicate<T> filter) {
       this.p = filter;
    }
}