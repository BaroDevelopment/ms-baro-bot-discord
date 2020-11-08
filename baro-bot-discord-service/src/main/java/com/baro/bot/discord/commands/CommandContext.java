package com.baro.bot.discord.commands;

import com.baro.bot.discord.components.CommandManager;
import com.baro.bot.discord.service.BaroBot;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.Arrays;

public class CommandContext {

    private final String prefix;
    private final String invoke;
    private final String[] args;
    private final MessageReceivedEvent event;
    private final BaroBot bot;
    private final CommandManager commandManager;

    public CommandContext(BaroBot bot, String prefix, String[] args, MessageReceivedEvent event, String invoke, CommandManager commandManager) {
        this.bot = bot;
        this.prefix = prefix;
        this.invoke = invoke;
        this.args = args;
        this.event = event;
        this.commandManager = commandManager;
    }

    public String getArgs() {
        String[] subArray = Arrays.copyOfRange(args, 1, args.length);
        StringBuilder builder = new StringBuilder();
        for (String value : subArray) {
            builder.append(value + " ");
        }
        return builder.toString().trim();
    }

    public String getInvoke() {
        return invoke;
    }

    public String getPrefix() {
        return prefix;
    }

    public MessageReceivedEvent getEvent() {
        return event;
    }

    public BaroBot getBot() {
        return bot;
    }

    public CommandManager getCommandManager() {
        return commandManager;
    }
}
