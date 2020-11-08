package com.baro.bot.discord.model.entities;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

/**
 * We are storing commands that are disabled for a server, channel or role in here.
 * The IdClass is needed because we have multiple primary keys.
 * https://www.baeldung.com/jpa-composite-primary-keys
 */
@Entity(name = "command_disabled")
public class CommandDisabledEntity {

    @EmbeddedId
    CommandDisabledEntityId commandDisabledEntityId;

    public CommandDisabledEntity() {
    }

    public CommandDisabledEntity(CommandDisabledEntityId commandDisabledEntityId) {
        this.commandDisabledEntityId = commandDisabledEntityId;
    }

    public CommandDisabledEntityId getCommandDisabledEntityId() {
        return commandDisabledEntityId;
    }

    public void setCommandDisabledEntityId(CommandDisabledEntityId commandDisabledEntityId) {
        this.commandDisabledEntityId = commandDisabledEntityId;
    }
}
