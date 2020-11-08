package com.baro.bot.discord.commands.owner;

import com.baro.bot.discord.commands.ACommand;
import com.baro.bot.discord.commands.CommandCategory;
import com.baro.bot.discord.commands.CommandContext;
import com.baro.bot.discord.commands.ICommand;
import com.baro.bot.discord.service.BaroBot;
import com.baro.bot.discord.util.ColorUtil;
import com.jagrosh.jdautilities.menu.Paginator;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.exceptions.PermissionException;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class GuildListCmd extends ACommand implements ICommand {

    public static final String COMMAND_NAME = "guildlist";
    private final Paginator.Builder pbuilder;

    public GuildListCmd(BaroBot bot) {
        this.pbuilder = new Paginator.Builder()
                .setColumns(1)
                .setItemsPerPage(30)
                .showPageNumbers(true)
                .waitOnSinglePage(false)
                .useNumberedItems(true)
                .setFinalAction(m -> {
                    try {
                        m.clearReactions().queue();
                    } catch (PermissionException | IllegalStateException e) {
                        m.delete().queue();
                    }
                })
                .setEventWaiter(bot.getEventWaiter())
                .setTimeout(1, TimeUnit.MINUTES);
    }

    @Override
    public void execute(CommandContext ctx) {
        int page = 1;
        pbuilder.clearItems();
        ctx.getEvent().getJDA().getGuilds().stream()
                .map(g -> "**" + g.getName() + "** (ID:" + g.getId() + ") ~ " + g.getMembers().size() + " Members")
                .forEach(pbuilder::addItems);
        ColorUtil colorUtil = new ColorUtil();
        Paginator p = pbuilder.setColor(colorUtil.getRandomColor())
                .setText(" Guilds that **" + ctx.getEvent().getJDA().getSelfUser().getName() + "** is connected to"
                        + "(Shard ID " + ctx.getEvent().getJDA().getShardInfo().getShardId() + "):")
                .setUsers(ctx.getEvent().getAuthor())
                .build();
        p.paginate(ctx.getEvent().getChannel(), page);
    }

    @Override
    public String getName() {
        return COMMAND_NAME;
    }

    @Override
    public String getDescription() {
        return "Displays all guilds with pagination";
    }

    @Override
    public CommandCategory getCategory() {
        return CommandCategory.OWNER;
    }

    @Override
    public boolean guildOnly() {
        return false;
    }

    @Override
    public List<String> getAliases() {
        List<String> aliases = new ArrayList<>();
        aliases.add("serverlist");
        aliases.add("sl");
        aliases.add("gl");
        return aliases;
    }

    @Nullable
    @Override
    public EnumSet<Permission> getBotPermissions() {
        return EnumSet.of(Permission.MESSAGE_ADD_REACTION);
    }
}
