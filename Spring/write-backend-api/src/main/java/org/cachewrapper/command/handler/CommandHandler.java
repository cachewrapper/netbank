package org.cachewrapper.command.handler;

import org.cachewrapper.command.domain.Command;

public interface CommandHandler<T extends Command> {
    void handle(T command);
}