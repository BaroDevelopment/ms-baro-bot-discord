
package com.baro.bot.discord.commands.information;

import com.baro.bot.discord.commands.ACommand;
import com.baro.bot.discord.commands.CommandCategory;
import com.baro.bot.discord.commands.CommandContext;
import com.baro.bot.discord.commands.ICommand;
import com.baro.bot.discord.util.EmoteUtil;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ServerInfoCmd extends ACommand implements ICommand {

    public static final String COMMAND_NAME = "serverinfo";
    public static final Logger LOGGER = LoggerFactory.getLogger(ServerInfoCmd.class);

    @Override
    public void execute(CommandContext ctx) {

        Guild target = findGuild(ctx);
        if (target == null) {
            sendError(ctx, "Server not found");
            return;
        }
        CompletableFuture<Message> waitMessage = sendWaitMessage(ctx, "Please wait - fetching data ...");
        target.retrieveOwner().queue(owner -> target.loadMembers().onSuccess(members -> {
            EmbedBuilder eb = new EmbedBuilder();
            String afk = target.getAfkChannel() == null ? "not set" : target.getAfkChannel().getName();
            String membersAsString = getMembersAsString(ctx, members);
            eb.setThumbnail(target.getIconUrl())
                    .setDescription("***___SERVER STATS___***")
                    .addField("Server Name:", target.getName(), true)
                    .addField("Server ID:", target.getId(), true)
//                    .addField("Owner", owner.getUser().getAsMention(), true)
                    .addField("Server Region:", target.getRegion().getEmoji() + " " + target.getRegion().getName(), true)
                    .addField("AFK Channel", afk, true)
                    .addField("Creation Date", target.getTimeCreated().format(DateTimeFormatter.ISO_LOCAL_DATE), true)
                    .addField("Verification Level", target.getVerificationLevel().toString(), true)
                    .addField("Emotes Count", getEmotesCountAsString(ctx.getEvent().getGuild()), true)
                    .addField("Channels", "**Text Channels:**  " + target.getTextChannels().size() +
                            "\n**Voice Channels:**  " + target.getVoiceChannels().size() + "\n**Categories** " +
                            target.getCategories().size(), true)
                    // TODO: ADD PING
                    .addField("Members:", membersAsString, false);
            waitMessage.thenAccept(message -> message.editMessage(eb.build()).queue());
        }));
    }

    private String getEmotesCountAsString(Guild guild) {
        long staticEmotes = guild.getEmotes().stream().filter(emote -> !emote.isAnimated()).count();
        long totalEmotes = guild.getEmotes().size();
        long animatedEmotes = totalEmotes - staticEmotes;

        return "Static: " + staticEmotes + "\nAnimated: " + animatedEmotes + "\n**Total:** " + totalEmotes;
    }

    private String getMembersAsString(CommandContext ctx, List<Member> members) {
        long all = members.size();
        long users = members.stream().filter(m -> !m.getUser().isBot()).count();
        long onlineUsers = members.stream().filter(m -> !m.getUser().isBot() && m.getOnlineStatus().equals(OnlineStatus.ONLINE)).count();
        long offlineUsers = members.stream().filter(m -> !m.getUser().isBot() && m.getOnlineStatus().equals(OnlineStatus.OFFLINE)).count();
        long dndUsers = members.stream().filter(m -> !m.getUser().isBot() && m.getOnlineStatus().equals(OnlineStatus.DO_NOT_DISTURB)).count();
        long idleUsers = members.stream().filter(m -> !m.getUser().isBot() && m.getOnlineStatus().equals(OnlineStatus.IDLE)).count();
        long streamingUsers = members.stream().filter(m -> !m.getUser().isBot() && !m.getActivities().isEmpty() && m.getActivities().stream().anyMatch(activity -> activity.getType() == Activity.ActivityType.STREAMING)).count();
        long bots = members.stream().filter(m -> m.getUser().isBot()).count();
        long onlineBots = members.stream().filter(m -> m.getUser().isBot() && !m.getOnlineStatus().equals(OnlineStatus.OFFLINE)).count();

        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        EmoteUtil eu = new EmoteUtil(ctx.getBot());
        return "**All Clients:**   " + decimalFormat.format(all) + "\n" +
                "**Members:**   " + decimalFormat.format(users) + "\n" +
                eu.getEmojiAsString("online") + decimalFormat.format(onlineUsers) + ", " +
                eu.getEmojiAsString("idle") + decimalFormat.format(idleUsers) + ", " +
                eu.getEmojiAsString("dnd") + decimalFormat.format(dndUsers) + ", " +
                eu.getEmojiAsString("offline") + decimalFormat.format(offlineUsers) + ", " +
                eu.getEmojiAsString("streaming") + decimalFormat.format(streamingUsers) + "\n" +
                eu.getEmojiAsString("botTag") + " **Bots:**   " + decimalFormat.format(bots) + "   (Online:  " + decimalFormat.format(onlineBots) + ")";
    }

    @Override
    public String getName() {
        return COMMAND_NAME;
    }

    @Override
    public String getDescription() {
        return "Display full information about a discord server";
    }

    @Override
    public CommandCategory getCategory() {
        return CommandCategory.INFORMATION;
    }

    @Override
    public boolean getArgs() {
        return false;
    }

    @Override
    public boolean guildOnly() {
        return false;
    }

    @Override
    public boolean isNsfw() {
        return false;
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("sinfo");
    }

    private Guild findGuild(CommandContext ctx) {

        if (ctx.getArgs().isEmpty())
            return ctx.getEvent().getGuild();

        //find guildByID
        long mayGuildID = 0;
        try {
            mayGuildID = Long.parseLong(ctx.getArgs());
            return ctx.getBot().getJda().getGuildById(mayGuildID);
        } catch (Exception ex) {
            // find by name
            for (Guild g : ctx.getBot().getJda().getGuilds()) {
                if (g.getName().toLowerCase().contains(ctx.getArgs().toLowerCase())) {
                    return g;
                }
            }
        }
        return null;
    }

    long getMembsInRole(Role r) {
        return r.getGuild().getMembers().stream().filter(m -> m.getRoles().contains(r)).count();
    }
}
