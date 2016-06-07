package org.bubblecloud.zigbee.simple;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Future implementation for asynchronous methods.
 *
 * @author Tommi S.E. Laukkanen
 */
public class FutureImpl<V> implements Future<V> {

    /**
     * The result.
     */
    private V result;

    /**
     * Sets result.
     * @param result the result
     */
    public void set(final V result) {
        this.result = result;
    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isCancelled() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isDone() {
        return result != null;
    }

    @Override
    public V get() throws InterruptedException, ExecutionException {
        synchronized (this) {
            if (result != null) {
                return result;
            }
            this.wait();
            return result;
        }
    }

    @Override
    public V get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        synchronized (this) {
            if (result != null) {
                return result;
            }
            unit.timedWait(this, timeout);
            return result;
        }
    }
}
