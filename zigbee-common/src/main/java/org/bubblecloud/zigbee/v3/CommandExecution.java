package org.bubblecloud.zigbee.v3;

import java.util.concurrent.Future;

/**
 * Command execution value object.
 */
public class CommandExecution {
    /**
     * The command start time.
     */
    private long startTime;
    /**
     * The command.
     */
    private Command command;
    /**
     * The future.
     */
    private Future<CommandResult> future;
    /**
     * The command response listener.
     */
    private CommandListener commandListener;

    /**
     * Constructor which sets future and command listener.
     * @param startTime the start time
     * @param command the command
     * @param future the future
     */
    public CommandExecution(long startTime, Command command, Future<CommandResult> future) {
        this.startTime = startTime;
        this.command = command;
        this.future = future;
    }

    /**
     * The start time.
     * @return
     */
    public long getStartTime() {
        return startTime;
    }

    /**
     * The command.
     * @return
     */
    public Command getCommand() {
        return command;
    }

    /**
     * Gets future.
     * @return the future
     */
    public Future<CommandResult> getFuture() {
        return future;
    }

    /**
     * Sets command listener.
     * @param commandListener the command listener
     */
    public void setCommandListener(CommandListener commandListener) {
        this.commandListener = commandListener;
    }

    /**
     * Gets command listener.
     * @return the command listener
     */
    public CommandListener getCommandListener() {
        return commandListener;
    }
}
