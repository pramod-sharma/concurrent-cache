package rkaran.concurrent.cache;

import java.util.concurrent.CompletableFuture;

public class Cache<K,V> {

    private final Shard<K,V>[] shards;

    public Cache(int numShards) {
        this.shards = new Shard[numShards];
        for(int i = 0; i < numShards; ++i) {
            this.shards[i] = new Shard<>();
        }
    }

    public Cache() {
        this(Runtime.getRuntime().availableProcessors());
    }

    public CompletableFuture<Boolean> contains(K key) {
        return this.shard(key).contains(key);
    }

    public CompletableFuture<V> get(K key) {
        return this.shard(key).get(key);
    }

    public CompletableFuture<V> put(K key, V value) {
        return this.shard(key).put(key, value);
    }

    public CompletableFuture<V> remove(K key) {
        return this.shard(key).remove(key);
    }

    private Shard<K,V> shard(K key) {
        return this.shards[key.hashCode()%this.shards.length];
    }
}
