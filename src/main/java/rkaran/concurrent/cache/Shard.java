package rkaran.concurrent.cache;

import rkaran.concurrent.cache.ops.*;
import rkaran.lockfree.Queue;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class Shard<K,V> {

    private final Map<K,V> map = new HashMap<>();
    private final Queue<Operation<K,V>> que = new Queue<>();

    public Shard() {
        new Thread(() -> {
            while(true) {
                try {
                    Operation<K,V> front = que.front();
                    front.apply(map);
                    que.deque();
                } catch(Exception e) {
                    try {
                        Thread.sleep(100);
                    } catch(InterruptedException e1) {}
                }
            }
        }).start();
    }

    public CompletableFuture<Boolean> contains(K key) {
        Operation<K,V> containsOp = new ContainsOperation<>(key);
        this.que.enque(containsOp);
        return containsOp.getFuture();
    }

    public CompletableFuture<V> get(K key) {
        Operation<K,V> getOp = new GetOperation<>(key);
        this.que.enque(getOp);
        return getOp.getFuture();
    }

    public CompletableFuture<V> put(K key, V value) {
        Operation<K,V> putOp = new PutOperation<>(key, value);
        this.que.enque(putOp);
        return putOp.getFuture();
    }

    public CompletableFuture<V> remove(K key) {
        Operation<K,V> removeOp = new RemoveOperation<>(key);
        this.que.enque(removeOp);
        return removeOp.getFuture();
    }
}
