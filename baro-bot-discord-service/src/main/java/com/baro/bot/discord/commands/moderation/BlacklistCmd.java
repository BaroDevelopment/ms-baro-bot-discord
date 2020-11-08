package com.baro.bot.discord.commands.moderation;

import com.baro.bot.discord.commands.ACommand;
import com.baro.bot.discord.commands.CommandCategory;
import com.baro.bot.discord.commands.CommandContext;
import com.baro.bot.discord.commands.ICommand;
import com.baro.bot.discord.model.entities.CommandDisabledEntity;
import com.baro.bot.discord.model.entities.CommandDisabledEntityId;
import com.baro.bot.discord.repository.CommandDisabledRepository;
import com.baro.bot.discord.util.ColorUtil;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import com.jagrosh.jdautilities.menu.Paginator;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.exceptions.PermissionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class BlacklistCmd extends ACommand implements ICommand {

    public static final Logger LOGGER = LoggerFactory.getLogger(BlacklistCmd.class);
    public static final String COMMAND_NAME = "blacklist";
    private final CommandDisabledRepository commandDisabledRepository;
    private final Paginator.Builder pbuilder;

    public BlacklistCmd(EventWaiter ew, CommandDisabledRepository commandDisabledRepository) {
        this.commandDisabledRepository = commandDisabledRepository;
        this.pbuilder = new Paginator.Builder()
                .setColumns(1)
                .setItemsPerPage(30)
                .showPageNumbers(true)
                .waitOnSinglePage(false)
                .useNumberedItems(false)
                .setFinalAction(m -> {
                    try {
                        m.clearReactions().queue();
                    } catch (PermissionException ignored) {
                    }
                })
                .setEventWaiter(ew)
                .setTimeout(1, TimeUnit.MINUTES);
    }

    @Override
    public void execute(CommandContext ctx) {
        if (ctx.getArgs().isEmpty()) {
            display(ctx);
            return;
        }
        try {
            verifyArguments(ctx);
        } catch (IllegalArgumentException e) {
            return;
        }

        String addOrRemove = ctx.getArgs().split(" ")[0].toLowerCase();
        String type = ctx.getArgs().split(" ")[1].toLowerCase();
        String commandName = ctx.getArgs().split(" ")[2].toLowerCase();
        String id = ctx.getArgs().split(" ")[3];
        CommandDisabledEntity entity = new CommandDisabledEntity(new CommandDisabledEntityId(id, commandName, type, ctx.getEvent().getGuild().getId()));

        if (addOrRemove.equals("add") || addOrRemove.equals("a")) {
            commandDisabledRepository.save(entity);
        } else {
            commandDisabledRepository.delete(entity);
        }
        sendSuccess(ctx, "Done");
    }

    private void display(CommandContext ctx) {
        int page = 1;
        pbuilder.clearItems();
        ColorUtil colorUtil = new ColorUtil();
        String serverId = ctx.getEvent().getGuild().getId();
        List<CommandDisabledEntity> result = commandDisabledRepository.findByCommandDisabledEntityIdServerId(serverId);
        int length = Math.min(result.size() - 1, 25);
        for (int i = 0; i <= length; i++) {
            CommandDisabledEntityId cde = result.get(i).getCommandDisabledEntityId();
            String text = "Command **" + cde.getName() + "** is disabled for **" + cde.getType() + " (" + cde.getId() + "**)";
            pbuilder.addItems(text);
        }
        Paginator p = pbuilder.setColor(colorUtil.getRandomColor())
                .setText("Disabled Commands")
                .setUsers(ctx.getEvent().getAuthor())
                .build();
        p.paginate(ctx.getEvent().getChannel(), page);
    }

    private void verifyArguments(CommandContext ctx) throws IllegalArgumentException {
        // verify length
        if (ctx.getArgs().split(" ").length != 4) {
            sendError(ctx, "Expected 4 arguments - found: " + ctx.getArgs().split(" ").length);
            throw new IllegalArgumentException();
        }
        // verify first argument which should be <add|remove>
        String firstArgument = ctx.getArgs().split(" ")[0].toLowerCase();
        if (!firstArgument.equals("add") && !firstArgument.equals("remove")) {
            sendError(ctx, "First argument must be either **add** or **remove**");
            throw new IllegalArgumentException();
        }
        // verify second argument which should be <server|channel|role>
        String secondArgument = ctx.getArgs().split(" ")[1].toLowerCase();
        if (!secondArgument.equals("server")
                && !secondArgument.equals("guild")
                && !secondArgument.equals("channel")
                && !secondArgument.equals("role")) {
            sendError(ctx, "Second argument must be either **server** or **channel** or **role**");
            throw new IllegalArgumentException();
        }

        // verify command name
        String commandName = ctx.getArgs().split(" ")[2].toLowerCase();
        ICommand command = ctx.getCommandManager().getCommands().get(commandName);
        if (command == null) {
            sendError(ctx, "Command not found. Did you type it wrong?");
            throw new IllegalArgumentException();
        }

        String id = ctx.getArgs().split(" ")[3];
        // verify id
        try {
            // check if id is a long
            Long.parseLong(id);
        } catch (NumberFormatException e) {
            sendError(ctx, "Provided ID is invalid!");
            throw new IllegalArgumentException();
        }

        // verify id for server
        if ((secondArgument.equals("server") || secondArgument.equals("guild"))
                && !ctx.getEvent().getGuild().getId().equals(id)) {
            sendError(ctx, "The server id must be: " + ctx.getEvent().getGuild().getId());
            throw new IllegalArgumentException();
        }
        // verify id for channel
        if (secondArgument.equals("channel") && ctx.getEvent().getGuild().getGuildChannelById(id) == null) {
            sendError(ctx, "Provided id doesn't belong to any channel.");
            throw new IllegalArgumentException();
        }
        // verify id for role
        if (secondArgument.equals("role") && ctx.getEvent().getGuild().getRoleById(id) == null) {
            sendError(ctx, "Provided id doesn't belong to any role.");
            throw new IllegalArgumentException();
        }
    }

    @Override
    public String getName() {
        return COMMAND_NAME;
    }

    @Override
    public String getDescription() {
        return "Enable or disable commands in a server/channel for roles";
    }

    @Override
    public CommandCategory getCategory() {
        return CommandCategory.MODERATION;
    }

    @Nullable
    @Override
    public EnumSet<Permission> getMemberPermissions() {
        return EnumSet.of(Permission.MANAGE_CHANNEL);
    }

    @Nullable
    @Override
    public EnumSet<Permission> getBotPermissions() {
        return EnumSet.of(Permission.MANAGE_CHANNEL);
    }

    /**
     * blacklist <add|remove> <server|channel|role> <cmd_name> id
     */
    @Override
    public List<String> getExamples() {
        return Arrays.asList(
                "blacklist <add|remove> <server|channel|role> <command_name> id",
                "**blacklist**\nlists all blacklisted commands",
                "**blacklist add server lock 391946504509587476**\ndisable the lock command in server with id 391946504509587476",
                "**blacklist remove server lock 391946504509587476**\nenable the lock command in server with id 391946504509587476",
                "**blacklist add channel lock 391946504509587476**\ndisable the lock command in channel with id 391946504509587476",
                "**blacklist remove channel lock 391946504509587476**\nenable the lock command in channel with id 391946504509587476",
                "**blacklist add role lock 391946504509587476**\ndisable the lock command for members with role id 391946504509587476",
                "**blacklist remove role lock 391946504509587476**\nenable the lock command for members with role id 391946504509587476"
        );
    }
}
