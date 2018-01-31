package rkaran.concurrent.cache.ops;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class GetOperation<K,V> extends Operation<K,V> {

    private final CompletableFuture<V> future = new CompletableFuture<>();

    public GetOperation(K key) {
        super(key);
    }

    @Override
    public void apply(Map<K,V> map) {
        this.future.complete(map.get(this.key));
    }

    @Override
    public CompletableFuture<V> getFuture() {
        return this.future;
    }
}
