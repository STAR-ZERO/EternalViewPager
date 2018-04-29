package com.star_zero.eternalviewpager;

import java.util.Objects;
import java.util.UUID;

class WrappedKey<T> {
    T key;
    private UUID uuid;

    WrappedKey(T key) {
        this.key = key;
        this.uuid = UUID.randomUUID();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WrappedKey<?> that = (WrappedKey<?>) o;
        return Objects.equals(key, that.key) &&
                Objects.equals(uuid, that.uuid);
    }

    @Override
    public int hashCode() {

        return Objects.hash(key, uuid);
    }
}
