package rkaran.concurrent.cache.ops;

import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class PutOperation<K,V> extends Operation<K,V> {

    private final V value;
    private final CompletableFuture<V> future = new CompletableFuture<>();

    public PutOperation(K key, V value) {
        super(key);
        this.value = value;
    }

    @Override
    public void apply(Map<K,V> map) {
        this.future.complete(map.put(this.key, this.value));
    }

    @Override
    public CompletableFuture getFuture() {
        return this.future;
    }
}
