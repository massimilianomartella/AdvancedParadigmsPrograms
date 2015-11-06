package pap.lab06.executors;

import java.util.*;
import java.util.concurrent.*;
import static java.util.Arrays.asList;

public class Sums {
    
    static class Sum implements Callable<Long> {
        private final long from;
        private final long to;
            Sum(long from, long to) {
            this.from = from;
            this.to = to;
        }
        
        public Long call() throws Exception  {
            long acc = 0;
            for (long i = from; i <= to; i++) {
                acc = acc + i;
            }
            return acc;
        }                
    }
    
    public static void main(String[] args) throws Exception {
        
        ExecutorService executor = Executors.newFixedThreadPool(2);
        List <Future<Long>> results = executor.invokeAll(asList(
            new Sum(0, 10), new Sum(100, 1000), new Sum(10000, 1000000)
        ));
        executor.shutdown();
        
        for (Future<Long> result : results) {
            System.out.println(result.get());
        }                
    }    
}
