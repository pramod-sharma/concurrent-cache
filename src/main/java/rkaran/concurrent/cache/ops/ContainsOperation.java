package rkaran.concurrent.cache.ops;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class ContainsOperation<K,V> extends Operation<K,V> {

    private final CompletableFuture<Boolean> future = new CompletableFuture<>();

    public ContainsOperation(K key) {
        super(key);
    }

    @Override
    public void apply(Map<K,V> map) {
        this.future.complete(map.containsKey(this.key));
    }

    @Override
    public CompletableFuture<Boolean> getFuture() {
        return this.future;
    }
}
