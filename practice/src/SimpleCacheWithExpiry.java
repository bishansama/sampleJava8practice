import java.time.Instant;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.Map;
public class SimpleCacheWithExpiry<K, V> {
    private final ConcurrentHashMap<K, CacheEntry<V>> cache;
    private final AtomicInteger size;
    private final int maxSize;
    private final long defaultTTLSeconds;
    
    private static class CacheEntry<V> {
        private final V value;
        private final long expiryTime;
        
        CacheEntry(V value, long ttlSeconds) {
            this.value = value;
            this.expiryTime = Instant.now().plusSeconds(ttlSeconds).toEpochMilli();
        }
        
        boolean isExpired() {
            return System.currentTimeMillis() > expiryTime;
        }
        
        V getValue() {
            return value;
        }
        
        long getExpiryTime() {
            return expiryTime;
        }
    }
    
    public SimpleCacheWithExpiry() {
        this(1000, 300); // Default: 1000 entries, 5 minutes TTL
    }
    
    public SimpleCacheWithExpiry(int maxSize, long defaultTTLSeconds) {
        this.cache = new ConcurrentHashMap<>();
        this.size = new AtomicInteger(0);
        this.maxSize = maxSize;
        this.defaultTTLSeconds = defaultTTLSeconds;
    }
    
    // O(1) - Store entry with TTL
    public void put(K key, V value) {
        put(key, value, defaultTTLSeconds);
    }
    
    // O(1) - Store entry with custom TTL
    public void put(K key, V value, long ttlSeconds) {
        if (key == null || ttlSeconds <= 0) {
            throw new IllegalArgumentException("Invalid key or TTL");
        }
        
        // Check if we need to make space
        if (size.get() >= maxSize) {
            cleanup(); // Remove expired entries first
            if (size.get() >= maxSize) {
                evictOldest(); // If still full, evict oldest
            }
        }
        
        CacheEntry<V> entry = new CacheEntry<>(value, ttlSeconds);
        CacheEntry<V> oldEntry = cache.put(key, entry);
        
        if (oldEntry == null) {
            size.incrementAndGet();
        }
    }
    
    // O(1) average case - Get value, check expiration
    public V get(K key) {
        if (key == null) {
            return null;
        }
        
        CacheEntry<V> entry = cache.get(key);
        if (entry == null) {
            return null;
        }
        
        // Lazy expiration check
        if (entry.isExpired()) {
            cache.remove(key);
            size.decrementAndGet();
            return null;
        }
        
        return entry.getValue();
    }
    
    // O(1) - Check if key exists and is not expired
    public boolean containsKey(K key) {
        return get(key) != null;
    }
    
    // O(1) - Remove specific key
    public V remove(K key) {
        if (key == null) {
            return null;
        }
        
        CacheEntry<V> entry = cache.remove(key);
        if (entry != null) {
            size.decrementAndGet();
            return entry.getValue();
        }
        return null;
    }
    
    // O(n) - Remove all expired entries
    public void cleanup() {
        cache.entrySet().removeIf(entry -> {
            if (entry.getValue().isExpired()) {
                size.decrementAndGet();
                return true;
            }
            return false;
        });
    }
    
    // O(n) - Evict oldest entries when cache is full
    private void evictOldest() {
        long oldestTime = Long.MAX_VALUE;
        K oldestKey = null;
        
        // Find oldest entry
        for (Map.Entry<K, CacheEntry<V>> entry : cache.entrySet()) {
            if (entry.getValue().getExpiryTime() < oldestTime) {
                oldestTime = entry.getValue().getExpiryTime();
                oldestKey = entry.getKey();
            }
        }
        
        if (oldestKey != null) {
            cache.remove(oldestKey);
            size.decrementAndGet();
        }
    }
    
    // O(1) - Get current size (excluding expired entries)
    public int size() {
        return size.get();
    }
    
    // O(1) - Check if cache is empty
    public boolean isEmpty() {
        return size.get() == 0;
    }
    
    // O(n) - Clear all entries
    public void clear() {
        cache.clear();
        size.set(0);
    }
    
    // O(1) - Get cache statistics
    public CacheStats getStats() {
        return new CacheStats(size.get(), maxSize, cache.size());
    }
    
    public static class CacheStats {
        private final int activeEntries;
        private final int maxSize;
        private final int totalEntries;
        
        CacheStats(int activeEntries, int maxSize, int totalEntries) {
            this.activeEntries = activeEntries;
            this.maxSize = maxSize;
            this.totalEntries = totalEntries;
        }
        
        public int getActiveEntries() { return activeEntries; }
        public int getMaxSize() { return maxSize; }
        public int getTotalEntries() { return totalEntries; }
        public int getExpiredEntries() { return totalEntries - activeEntries; }
    }
}