package main.stability;

import java.util.HashSet;
import java.util.Stack;

public class StackSet<T> extends Stack<T> {
    private HashSet<T> tracker;

    public StackSet(){
        super();
        this.tracker = new HashSet<>();
    }

    @Override
    public T push(T item){
        // Check if it's already on stack
        if(this.tracker.contains(item)) return null;
        else {
            this.tracker.add(item);
            return super.push(item);
        }
    }

    @Override
    public T pop(){
        T item = super.pop();
        this.tracker.remove(item);
        return item;
    }
}
