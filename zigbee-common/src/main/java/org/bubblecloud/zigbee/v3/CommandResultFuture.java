package org.bubblecloud.zigbee.v3;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Future implementation for asynchronous methods.
 */
public class CommandResultFuture implements Future<CommandResult> {

    /**
     * The simple ZigBee API.
     */
    private ZigBeeApi zigBeeApi;

    /**
     * The command execution.
     */
    private CommandExecution commandExecution;
    /**
     * The result.
     */
    private CommandResult result;

    /**
     * Constructor which sets the simple ZigBee API this future belongs to.
     * @param zigBeeApi the simple ZigBee API
     */
    public CommandResultFuture(ZigBeeApi zigBeeApi) {
        this.zigBeeApi = zigBeeApi;
    }

    /**
     * Sets the command execution.
     * @param commandExecution the command execution
     */
    public void setCommandExecution(CommandExecution commandExecution) {
        this.commandExecution = commandExecution;
    }

    /**
     * Sets result.
     * @param result the result
     */
    public void set(final CommandResult result) {
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
    public CommandResult get() throws InterruptedException, ExecutionException {
        synchronized (this) {
            if (result != null) {
                return result;
            }
            this.wait(10000);
            if (result == null) {
                set(new CommandResult());
                zigBeeApi.removeCommandExecution(commandExecution);
            }
            return result;
        }
    }

    @Override
    public CommandResult get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        synchronized (this) {
            if (result != null) {
                return result;
            }
            unit.timedWait(this, timeout);
            if (result == null) {
                set(new CommandResult());
                zigBeeApi.removeCommandExecution(commandExecution);
            }
            return result;
        }
    }
}
