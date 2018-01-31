package rkaran.concurrent.cache.ops;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

@AllArgsConstructor
abstract public class Operation<K,V> {

    protected final K key;

    abstract public void apply(Map<K,V> map);

    abstract public <T> CompletableFuture<T> getFuture();
}
