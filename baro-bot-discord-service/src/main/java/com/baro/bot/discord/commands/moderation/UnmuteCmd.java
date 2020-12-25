package com.baro.bot.discord.commands.moderation;

import com.baro.bot.discord.commands.ACommand;
import com.baro.bot.discord.commands.CommandCategory;
import com.baro.bot.discord.commands.CommandContext;
import com.baro.bot.discord.commands.ICommand;
import com.baro.bot.discord.util.ColorUtil;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

public class UnmuteCmd extends ACommand implements ICommand {

    public static final String COMMAND_NAME = "unmute";
    public static final Logger LOGGER = LoggerFactory.getLogger(UnmuteCmd.class);

    @Override
    public void execute(CommandContext ctx) {
        if (!findMuteRole(ctx.getEvent().getGuild()).isPresent()) {
            sendError(ctx, "Mute role not found. Please create a role and name it **Mute** or **Muted**");
            return;
        }

        if (ctx.getEvent().getMessage().getMentionedMembers().isEmpty()) {
            sendError(ctx, "No members mentioned. Please mention the member to unmute.");
            return;
        }
        List<Member> targets = ctx.getEvent().getMessage().getMentionedMembers();

        unmute(ctx, targets);
    }

    private void unmute(CommandContext ctx, List<Member> targets) {
        int unmutedMembers = 0;
        for (Member target : targets) {
            int highestTargetRolePosition = findHighestMemberRole(target);
            int highestAuthorRolePosition = findHighestMemberRole(ctx.getEvent().getMember());
            Member botAsMember = ctx.getEvent().getGuild().getMemberById(ctx.getBot().getJda().getSelfUser().getId());
            int highestBotRolePosition = findHighestMemberRole(botAsMember);

            if (highestAuthorRolePosition <= highestTargetRolePosition) {
                sendError(ctx, target.getAsMention() + " has a role equal or higher than your role!");
                unmutedMembers++;
                continue;
            }
            if (highestTargetRolePosition >= highestBotRolePosition) {
                sendError(ctx, target.getAsMention() + " has a higher or equal role. I can not unmute him.");
                unmutedMembers++;
                continue;
            }

            // apply mute
            Role muteRole = findMuteRole(ctx.getEvent().getGuild()).get();
            EmbedBuilder eb = new EmbedBuilder().setColor(new ColorUtil().getRandomColor());
            String authorUrl = ctx.getEvent().getAuthor().getEffectiveAvatarUrl();
            eb.setThumbnail(target.getUser().getEffectiveAvatarUrl());
            eb.setTitle("Server Member got unmuted");
            eb.setAuthor(ctx.getEvent().getAuthor().getName(), authorUrl, authorUrl);
            eb.addField("Unmuted Member", target.getAsMention(), true);

            ctx.getEvent().getGuild().removeRoleFromMember(target, muteRole).queue();
            ctx.getEvent().getTextChannel().sendMessage(eb.build()).queue();
        }
    }

    private Optional<Role> findMuteRole(Guild guild) {
        return guild.getRoles().stream()
                .filter(role -> role.getName().toLowerCase().contains("mute"))
                .findFirst();
    }

    private int findHighestMemberRole(Member member) {
        if (member.getRoles().isEmpty())
            return 0;
        OptionalInt position = member.getRoles()
                .stream()
                .mapToInt(Role::getPosition)
                .max();
        return position.isPresent() ? position.getAsInt() : 0;
    }

    @Override
    public String getName() {
        return COMMAND_NAME;
    }

    @Override
    public String getDescription() {
        return "Unmute a muted user";
    }

    @Override
    public CommandCategory getCategory() {
        return CommandCategory.MODERATION;
    }

    @Override
    public boolean getArgs() {
        return true;
    }

    @Override
    public boolean guildOnly() {
        return true;
    }

    @Nullable
    @Override
    public EnumSet<Permission> getMemberPermissions() {
        return EnumSet.of(Permission.MANAGE_ROLES);
    }

    @Nullable
    @Override
    public EnumSet<Permission> getBotPermissions() {
        return EnumSet.of(Permission.MANAGE_ROLES);
    }

    @Override
    public long getCooldown() {
        return 5;
    }

}
