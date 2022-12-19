package dddstart.shop.integration;

public interface OffsetStore {
    long get();
    void update(long nextOffset);
}
