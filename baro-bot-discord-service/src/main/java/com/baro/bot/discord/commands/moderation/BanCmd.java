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
import org.jetbrains.annotations.Nullable;
import org.jsoup.internal.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.util.OptionalInt;

public class BanCmd extends ACommand implements ICommand {

    public static final String COMMAND_NAME = "ban";
    public static final Logger LOGGER = LoggerFactory.getLogger(BanCmd.class);
    private final ImageConfig imageConfig;


    public BanCmd(ImageConfig imageConfig) {
        this.imageConfig = imageConfig;
    }

    @Override
    public void execute(CommandContext ctx) {
        if (ctx.getEvent().getMessage().getMentionedMembers().isEmpty()) {
            sendError(ctx, "No member mentioned");
        }
        List<Member> members = ctx.getEvent().getMessage().getMentionedMembers();
        BanParams banParams = new BanParams();
        JCommander.newBuilder()
                .addObject(banParams)
                .build()
                .parse(ctx.getArgs().split(" "));
        ban(ctx, members, banParams.days, StringUtil.join(banParams.reason, " "), banParams.fake);
    }

    private int ban(CommandContext ctx, List<Member> targets, int days, String reason, boolean fake) {
        int bannedMemberCount = 0;
        for (Member target : targets) {
            int highestTargetRolePosition = findHighestMemberRole(target);
            int highestAuthorRolePosition = findHighestMemberRole(ctx.getEvent().getMember());
            Member botAsMember = ctx.getEvent().getGuild().getMemberById(ctx.getBot().getJda().getSelfUser().getId());
            int highestBotRolePosition = findHighestMemberRole(botAsMember);

            if (highestAuthorRolePosition <= highestTargetRolePosition) {
                sendError(ctx, target.getAsMention() + " has a role equal or higher than your role!");
                bannedMemberCount++;
                continue;
            }
            if (highestTargetRolePosition >= highestBotRolePosition) {
                sendError(ctx, target.getAsMention() + " has a higher or equal role. I can not ban him.");
                bannedMemberCount++;
                continue;
            }

            // apply ban
            EmbedBuilder eb = new EmbedBuilder();
            String authorUrl = ctx.getEvent().getAuthor().getEffectiveAvatarUrl();
            eb.setColor(new ColorUtil().getRandomColor());
            eb.setTitle("Server Member got banned");
            eb.addField("Banned Member", target.getAsMention(), true);
            eb.addField("Banned by", ctx.getEvent().getAuthor().getAsMention(), true);
            eb.setThumbnail(authorUrl);
            eb.setAuthor(ctx.getEvent().getAuthor().getName(), authorUrl, authorUrl);
            eb.addField("Reason", reason, true);
            eb.setImage(imageConfig.getBanImage());
            target.getUser().openPrivateChannel().queue(privateChannel -> {
                privateChannel.sendMessage(eb.build()).queue();
                eb.addField("Notified", "YES", true);
                ctx.getEvent().getChannel().sendMessage(eb.build()).queue();
            }, throwable -> {
                eb.addField("Notified", "NO", true);
                ctx.getEvent().getChannel().sendMessage(eb.build()).queue();
            });
            if (!fake)
                target.ban(days, reason).queue();
        }
        return bannedMemberCount;
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
        return "Ban a member from the server";
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
        return EnumSet.of(Permission.BAN_MEMBERS);
    }

    @Nullable
    @Override
    public EnumSet<Permission> getBotPermissions() {
        return EnumSet.of(Permission.BAN_MEMBERS);
    }

    @Override
    public long getCooldown() {
        return 5;
    }

    private class BanParams {
        @Parameter
        private List<String> main;
        @Parameter(names = {"-d", "-days"})
        private int days = 7;
        @Parameter(names = {"-r", "-reason"})
        private List<String> reason = Arrays.asList("Not specified");
        @Parameter(names = {"-f", "-fake"})
        private boolean fake = false;
    }
}
