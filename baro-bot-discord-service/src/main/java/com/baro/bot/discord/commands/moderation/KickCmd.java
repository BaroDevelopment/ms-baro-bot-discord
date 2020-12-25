package com.baro.bot.discord.commands.moderation;

import com.baro.bot.discord.commands.ACommand;
import com.baro.bot.discord.commands.CommandCategory;
import com.baro.bot.discord.commands.CommandContext;
import com.baro.bot.discord.commands.ICommand;
import com.baro.bot.discord.config.ImageConfig;
import com.baro.bot.discord.util.ColorUtil;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import org.jsoup.internal.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.util.OptionalInt;

public class KickCmd extends ACommand implements ICommand {

    public static final String COMMAND_NAME = "kick";
    public static final Logger LOGGER = LoggerFactory.getLogger(KickCmd.class);
    private final ImageConfig imageConfig;

    public KickCmd(ImageConfig imageConfig) {
        this.imageConfig = imageConfig;
    }

    @Override
    public void execute(CommandContext ctx) {
        if (ctx.getEvent().getMessage().getMentionedMembers().isEmpty()) {
            sendError(ctx, "No member mentioned");
        }
        List<Member> members = ctx.getEvent().getMessage().getMentionedMembers();
        KickParams kickParams = new KickParams();
        JCommander.newBuilder()
                .addObject(kickParams)
                .build()
                .parse(ctx.getArgs().split(" "));
        kick(ctx, members, StringUtil.join(kickParams.reason, " "), kickParams.fake);
    }

    private int kick(CommandContext ctx, List<Member> targets, String reason, boolean fake) {
        int kickedMemberCount = 0;
        for (Member target : targets) {
            int highestTargetRolePosition = findHighestMemberRole(target);
            int highestAuthorRolePosition = findHighestMemberRole(ctx.getEvent().getMember());
            Member botAsMember = ctx.getEvent().getGuild().getMemberById(ctx.getBot().getJda().getSelfUser().getId());
            int highestBotRolePosition = findHighestMemberRole(botAsMember);

            if (highestAuthorRolePosition <= highestTargetRolePosition) {
                sendError(ctx, target.getAsMention() + " has a role equal or higher than your role!");
                kickedMemberCount++;
                continue;
            }
            if (highestTargetRolePosition >= highestBotRolePosition) {
                sendError(ctx, target.getAsMention() + " has a higher or equal role. I can not kick him.");
                kickedMemberCount++;
                continue;
            }

            // apply kick
            EmbedBuilder eb = new EmbedBuilder();
            String authorUrl = ctx.getEvent().getAuthor().getEffectiveAvatarUrl();
            eb.setColor(new ColorUtil().getRandomColor());
            eb.setTitle("Server Member got kicked");
            eb.addField("Kicked Member", target.getAsMention(), true);
            eb.setThumbnail(target.getUser().getEffectiveAvatarUrl());
            eb.setAuthor(ctx.getEvent().getAuthor().getName(), authorUrl, authorUrl);
            eb.addField("Reason", reason, true);
            eb.setImage(imageConfig.getKickImage());
            target.getUser().openPrivateChannel().queue(privateChannel -> {
                privateChannel.sendMessage(eb.build()).queue();
                eb.addField("Notified", "YES", true);
                ctx.getEvent().getChannel().sendMessage(eb.build()).queue();
            }, throwable -> {
                eb.addField("Notified", "NO", true);
                ctx.getEvent().getChannel().sendMessage(eb.build()).queue();
            });
            if (!fake)
                target.kick(reason).queue();
        }
        return kickedMemberCount;
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
        return "Kick a member from the server";
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

    @Override
    public boolean isNsfw() {
        return false;
    }

    @Nullable
    @Override
    public EnumSet<Permission> getMemberPermissions() {
        return EnumSet.of(Permission.KICK_MEMBERS);
    }

    @Nullable
    @Override
    public EnumSet<Permission> getBotPermissions() {
        return EnumSet.of(Permission.KICK_MEMBERS);
    }

    @Override
    public long getCooldown() {
        return 5;
    }

    private class KickParams {
        @Parameter
        private List<String> main;
        @Parameter(names = {"-r", "-reason"})
        private List<String> reason = Arrays.asList("Not specified");
        @Parameter(names = {"-f", "-fake"})
        private boolean fake = false;
    }
}
