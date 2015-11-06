package pap.lab06.quad1;

import java.util.*;

public class Bag<T> {

	private LinkedList<T> lists;
	
    public Bag(){
    	lists = new LinkedList<T>();
    }
    
    public synchronized void insert(T t) {
    	lists.addLast(t);
    	if (lists.size() == 1){
    		notifyAll();
    	}
    }
    
    public synchronized T remove() throws InterruptedException {
    	while (lists.size() == 0){
    		wait();
    	}
    	T t = lists.removeFirst();
    	return t;
    }
}
