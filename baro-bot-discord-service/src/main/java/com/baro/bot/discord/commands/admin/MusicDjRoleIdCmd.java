package com.baro.bot.discord.commands.admin;

import com.baro.bot.discord.commands.ACommand;
import com.baro.bot.discord.commands.CommandCategory;
import com.baro.bot.discord.commands.CommandContext;
import com.baro.bot.discord.commands.ICommand;
import com.baro.bot.discord.repository.MusicRepository;
import com.baro.bot.discord.util.FormatUtil;
import com.jagrosh.jdautilities.commons.utils.FinderUtil;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Role;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public class MusicDjRoleIdCmd extends ACommand implements ICommand {

    public static final String COMMAND_NAME = "setdj";
    private final MusicRepository musicRepository;

    public MusicDjRoleIdCmd(MusicRepository musicRepository) {
        this.musicRepository = musicRepository;
    }

    @Override
    public void execute(CommandContext ctx) {
        Long guildId = ctx.getEvent().getGuild().getIdLong();
        if (ctx.getArgs().isEmpty()) {
            musicRepository.setDjRoleId("", guildId);
            sendSuccess(ctx, "DJ role cleared; Only Admins can use the DJ commands.");

            return;
        }
        List<Role> list = FinderUtil.findRoles(ctx.getArgs(), ctx.getEvent().getGuild());
        if (list.isEmpty()) {
            sendError(ctx, " No Roles found matching \"" + ctx.getArgs() + "\"");
        } else if (list.size() > 1) {
            sendWarning(ctx, FormatUtil.listOfRoles(list, ctx.getArgs()));
        } else {
            musicRepository.setDjRoleId(list.get(0).getId(), guildId);
            sendSuccess(ctx, " DJ commands can now be used by users with the **" + list.get(0).getName() + "** role.");
        }
    }

    @Override
    public String getName() {
        return COMMAND_NAME;
    }

    @Override
    public String getDescription() {
        return "Sets the DJ role for certain music commands";
    }

    @Override
    public boolean getArgs() {
        return false;
    }

    @Override
    public boolean guildOnly() {
        return true;
    }

    @Override
    public boolean isNsfw() {
        return false;
    }

    @Nullable
    @Override
    public EnumSet<Permission> getMemberPermissions() {
        return EnumSet.of(Permission.ADMINISTRATOR);
    }

    @Override
    public CommandCategory getCategory() {
        return CommandCategory.ADMIN;
    }

    @Override
    public List<String> getExamples() {
        List<String> samples = new ArrayList<>();
        samples.add("`setdj` - Clears the DJ role. Only Admins will be able to use the DJ commands.");
        samples.add("`setdj <role_id | role_name>` - Sets the DJ role. Users with this role will be able to use DJ commands.");
        samples.add("");
        samples.add("**HINT:** role mentions are not supported!");
        return samples;
    }
}
