package com.baro.bot.discord.features;

import com.baro.bot.discord.components.redis.RedisDiscordMessageHandler;
import com.baro.bot.discord.model.redis.MessageModel;
import com.baro.bot.discord.service.BaroBot;
import com.baro.bot.discord.util.ColorUtil;
import com.baro.bot.discord.util.EmoteUtil;
import com.baro.bot.discord.util.FormatUtil;
import com.baro.bot.discord.util.StatusUtil;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.channel.text.TextChannelCreateEvent;
import net.dv8tion.jda.api.events.channel.text.TextChannelDeleteEvent;
import net.dv8tion.jda.api.events.channel.text.update.*;
import net.dv8tion.jda.api.events.channel.voice.VoiceChannelCreateEvent;
import net.dv8tion.jda.api.events.channel.voice.VoiceChannelDeleteEvent;
import net.dv8tion.jda.api.events.channel.voice.update.VoiceChannelUpdateBitrateEvent;
import net.dv8tion.jda.api.events.channel.voice.update.VoiceChannelUpdateNameEvent;
import net.dv8tion.jda.api.events.channel.voice.update.VoiceChannelUpdatePositionEvent;
import net.dv8tion.jda.api.events.channel.voice.update.VoiceChannelUpdateUserLimitEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceJoinEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceLeaveEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageDeleteEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageUpdateEvent;
import net.dv8tion.jda.api.events.user.UserActivityStartEvent;
import net.dv8tion.jda.api.events.user.UserTypingEvent;
import net.dv8tion.jda.api.events.user.update.UserUpdateAvatarEvent;
import net.dv8tion.jda.api.events.user.update.UserUpdateDiscriminatorEvent;
import net.dv8tion.jda.api.events.user.update.UserUpdateNameEvent;
import net.dv8tion.jda.api.events.user.update.UserUpdateOnlineStatusEvent;

import javax.annotation.Nonnull;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;

public class Logging {

    // TODO: Add to channelflags.properties

    private final String BAROBOT_NAME_LOG = "BAROBOT_NAME_LOG";
    private final String BAROBOT_DISCRIMINATOR_LOG = "BAROBOT_DISCRIMINATOR_LOG";
    private final String BAROBOT_AVATAR_LOG = "BAROBOT_AVATAR_LOG";
    private final String BAROBOT_STATUS_LOG = "BAROBOT_STATUS_LOG";
    private final String BAROBOT_TYPING_LOG = "BAROBOT_TYPING_LOG";
    private final String BAROBOT_ACTIVITY_LOG = "BAROBOT_ACTIVITY_LOG";
    private final String BAROBOT_MESSAGE_DELETE_LOG = "BAROBOT_MESSAGE_DELETE_LOG";
    private final String BAROBOT_MESSAGE_EDIT_LOG = "BAROBOT_MESSAGE_EDIT_LOG";
    private final String BAROBOT_TCHANNEL_LOG = "BAROBOT_TCHANNEL_LOG";
    private final String BAROBOT_VCHANNEL_LOG = "BAROBOT_VCHANNEL_LOG";
    private final String BAROBOT_CHANNEL_TOPIC_LOG = "BAROBOT_CHANNEL_TOPIC_LOG";
    private final String BAROBOT_SERVER_JOIN = "BAROBOT_SERVER_JOIN";
    private final String BAROBOT_VOICE_JOIN_LOG = "BAROBOT_VOICE_JOIN_LOG";
    private final String BAROBOT_VOICE_LEAVE_LOG = "BAROBOT_VOICE_LEAVE_LOG";

    private void postLog(EmbedBuilder eb, Guild guild, String flag, Message msg) {
        for (TextChannel tc : guild.getTextChannels()) {
            if (tc.getTopic() != null && tc.getTopic().toUpperCase().contains(flag)) {
                tc.sendMessage(eb.build()).queue();
                if (msg != null && msg.getEmbeds().size() > 0)
                    tc.sendMessage(msg.getEmbeds().get(0)).queue();
            }
        }
    }

    public void onUserUpdateName(@Nonnull UserUpdateNameEvent event) {
        EmbedBuilder eb = new EmbedBuilder().setColor(new ColorUtil().getRandomColor());
        User user = event.getUser();
        eb.setDescription("USER NAME CHANGE");
        eb.setAuthor(user.getName() + "#" + user.getDiscriminator(), user.getEffectiveAvatarUrl(), user.getEffectiveAvatarUrl());
        eb.addField("Old Name", event.getOldValue(), true);
        eb.addField("New Name", event.getNewValue(), true);
        eb.addField("User ID", user.getId(), true);
        eb.setTimestamp(OffsetDateTime.now());
        for (Guild guild : event.getJDA().getGuilds()) {
            if (guild.getMemberById(user.getIdLong()) != null) {
                postLog(eb, guild, BAROBOT_NAME_LOG, null);
            }
        }
    }

    public void onUserUpdateDiscriminator(@Nonnull UserUpdateDiscriminatorEvent event) {
        EmbedBuilder eb = new EmbedBuilder().setColor(new ColorUtil().getRandomColor());
        User user = event.getUser();
        eb.setDescription("USER DISCRIMINATOR CHANGE");
        eb.setAuthor(user.getName() + "#" + user.getDiscriminator(), user.getEffectiveAvatarUrl(), user.getEffectiveAvatarUrl());
        eb.addField("Old Discriminator", event.getOldValue(), true);
        eb.addField("New Discriminator", event.getNewValue(), true);
        eb.addField("User ID", user.getId(), true);
        eb.setTimestamp(OffsetDateTime.now());
        for (Guild guild : event.getJDA().getGuilds()) {
            if (guild.getMemberById(user.getIdLong()) != null) {
                postLog(eb, guild, BAROBOT_DISCRIMINATOR_LOG, null);
            }
        }
    }

    public void onUserUpdateAvatar(@Nonnull UserUpdateAvatarEvent event) {
        EmbedBuilder eb = new EmbedBuilder().setColor(new ColorUtil().getRandomColor());
        User user = event.getUser();
        eb.setDescription("USER AVATAR CHANGE");
        eb.setAuthor(user.getName() + "#" + user.getDiscriminator(), user.getEffectiveAvatarUrl(), user.getEffectiveAvatarUrl());
        eb.addField("Old Avatar", "[See the Thumbnail](" + event.getOldAvatarUrl() + ")", true);
        eb.addField("New Avatar", "[See this embed Image](" + event.getNewAvatarUrl() + ")", true);
        eb.addField("User ID", user.getId(), true);
        eb.setThumbnail(event.getOldAvatarUrl());
        eb.setImage(event.getNewAvatarUrl());
        eb.setTimestamp(OffsetDateTime.now());
        for (Guild guild : event.getJDA().getGuilds()) {
            if (guild.getMemberById(user.getIdLong()) != null) {
                postLog(eb, guild, BAROBOT_AVATAR_LOG, null);
            }
        }
    }

    public void onUserUpdateOnlineStatus(@Nonnull UserUpdateOnlineStatusEvent event, BaroBot bot) {
        EmbedBuilder eb = new EmbedBuilder().setColor(new ColorUtil().getRandomColor());
        User user = event.getUser();
        StatusUtil su = new StatusUtil(bot);
        eb.setDescription("USER STATUS CHANGE");
        eb.setAuthor(user.getName() + "#" + user.getDiscriminator(), user.getEffectiveAvatarUrl(), user.getEffectiveAvatarUrl());
        eb.addField("Old Status", su.getStatusEmote(event.getOldOnlineStatus()).getAsMention(), true);
        eb.addField("New Status", su.getStatusEmote(event.getNewOnlineStatus()).getAsMention(), true);
        eb.addField("User ID", user.getId(), true);
        eb.setTimestamp(OffsetDateTime.now());
        if (event.getGuild().getMemberById(user.getIdLong()) != null)
            postLog(eb, event.getGuild(), BAROBOT_STATUS_LOG, null);
    }

    public void onUserTyping(@Nonnull UserTypingEvent event, BaroBot bot) {
        if (event.getChannel().getType().equals(ChannelType.PRIVATE)) return;
        EmbedBuilder eb = new EmbedBuilder().setColor(new ColorUtil().getRandomColor());
        User user = event.getUser();
        StatusUtil su = new StatusUtil(bot);
        eb.setDescription("USER TYPING EVENT");
        eb.setAuthor(user.getName() + "#" + user.getDiscriminator(), user.getEffectiveAvatarUrl(), user.getEffectiveAvatarUrl());

        if (event.getTextChannel() != null)
            eb.addField("Channel Name", event.getTextChannel().getAsMention(), true);
        else
            eb.addField("Channel Name", event.getChannel().getName(), true);

        eb.addField("Channel ID", event.getChannel().getId(), true);
        eb.addField("User ID", user.getId(), true);
        eb.setTimestamp(OffsetDateTime.now());
        if (event.getGuild().getMemberById(user.getIdLong()) != null)
            postLog(eb, event.getGuild(), BAROBOT_TYPING_LOG, null);
    }

    public void onUserActivityStart(@Nonnull UserActivityStartEvent event, BaroBot bot) {
        EmbedBuilder eb = new EmbedBuilder().setColor(new ColorUtil().getRandomColor());
        User user = event.getUser();
        Activity ac = event.getNewActivity();
        StatusUtil su = new StatusUtil(bot);
        EmoteUtil eu = new EmoteUtil(bot);
        String activityName = event.getNewActivity().getName().contains("Spotify")
                ? eu.getEmojiAsString("spotify") + event.getNewActivity().getName()
                : event.getNewActivity().getName();
        eb.setDescription("USER ACTIVITY START");
        eb.setAuthor(user.getName() + "#" + user.getDiscriminator(), user.getEffectiveAvatarUrl(), user.getEffectiveAvatarUrl());
        eb.addField("Activity", activityName, false);
        if (ac.getUrl() != null)
            eb.addField("URL", ac.getUrl(), true);
        if (ac.getEmoji() != null)
            eb.addField("Emoji", ac.getEmoji().getAsMention(), true);
        eb.addField("Type", ac.getType().name(), true);
        eb.addField("User ID", user.getId(), true);
        eb.setTimestamp(OffsetDateTime.now());

        if (ac.isRich()) {
            String image = ac.asRichPresence().getLargeImage() != null
                    ? ac.asRichPresence().getLargeImage().getUrl()
                    : ac.asRichPresence().getSmallImage() != null
                    ? ac.asRichPresence().getSmallImage().getUrl()
                    : null;
            eb.setThumbnail(image);
            String richTitle = ac.asRichPresence().getName();
            String richState = ac.asRichPresence().getState() != null ? ac.asRichPresence().getState() : "";
            String richDetails = ac.asRichPresence().getDetails() != null ? ac.asRichPresence().getDetails() : "";
            String richUrl = ac.asRichPresence().getUrl() == null || ac.asRichPresence().getUrl().isEmpty()
                    ? "" : "[URL](" + ac.asRichPresence().getUrl() + ")";
            eb.addField("RichPresence", richUrl + "```http\n" + richTitle + "\n" + richState + "\n" + richDetails + "\n```", false);
        }
        if (event.getGuild().getMemberById(user.getIdLong()) != null)
            postLog(eb, event.getGuild(), BAROBOT_ACTIVITY_LOG, null);
    }


    // Embed deleted are not logged
    public void onGuildMessageDelete(GuildMessageDeleteEvent event, BaroBot bot, RedisDiscordMessageHandler redis) {
        bot.getNowplayingHandler().onMessageDelete(event.getGuild(), event.getMessageIdLong());
        MessageModel msg = redis.findOne(event.getMessageId());

        if (msg == null) return;

        TextChannel channel = event.getGuild().getTextChannelById(msg.getChannelId());
        Member member = event.getGuild().getMemberById(msg.getUserId());

        if (member == null) return;

        EmbedBuilder eb = new EmbedBuilder().setColor(new ColorUtil().getRandomColor());
        eb.setAuthor(member.getUser().getName() + "#" + member.getUser().getDiscriminator(),
                member.getUser().getEffectiveAvatarUrl(), member.getUser().getEffectiveAvatarUrl());
        if (channel != null)
            eb.addField("Channel", channel.getAsMention(), true);
        eb.addField("MessageID", msg.getMessageId(), true);
        eb.addField("Author ID", msg.getUserId(), true);
        eb.setDescription("MESSAGE DELETE");
        eb.setTimestamp(OffsetDateTime.parse(msg.getCreationTime()));
        eb.setFooter("Creation time: ");

        if (!msg.getAttachment().isEmpty()) {
            eb.setImage(msg.getAttachment());
        }

        if (!msg.getContent().isEmpty())
            eb.addField("Content", "```css\n" + msg.getContent() + "\n```", false);

        postLog(eb, event.getGuild(), BAROBOT_MESSAGE_DELETE_LOG, null);
    }

    public void onGuildMessageUpdate(GuildMessageUpdateEvent event, HashMap<Long, Message> messages) {

        // Log message updates
        try {
            Message msg = messages.get(event.getMessageIdLong());
            if (msg == null) return;
            EmbedBuilder eb = new EmbedBuilder().setColor(new ColorUtil().getRandomColor());
            eb.setAuthor(msg.getAuthor().getName() + "#" + msg.getAuthor().getDiscriminator(), msg.getAuthor().getEffectiveAvatarUrl(), msg.getAuthor().getEffectiveAvatarUrl());
            eb.addField("Channel", msg.getTextChannel().getAsMention(), true);
            eb.addField("MessageID", msg.getId(), true);
            eb.addField("Author ID", msg.getAuthor().getId(), true);
            if (msg.getEmbeds().size() == 0) {
                eb.addField("Before", "```css\n" + msg.getContentDisplay() + "\n```", false);
                eb.addField("After", "```css\n" + event.getMessage().getContentDisplay() + "\n```", false);
            } else
                eb.addField("", "```css\nEmbed edited\n```", false);
            eb.setDescription("MESSAGE EDIT");
            eb.setTimestamp(msg.getTimeCreated());
            eb.setFooter("Creation time: ");
            postLog(eb, event.getGuild(), BAROBOT_MESSAGE_EDIT_LOG, msg);

        } catch (Exception ex) {
            //ignore
        }
    }

    public void onTextChannelDelete(@Nonnull TextChannelDeleteEvent event) {
        EmbedBuilder eb = new EmbedBuilder().setColor(new ColorUtil().getRandomColor());
        eb.setDescription("TEXTCHANNEL DELETED");
        eb.addField("Channel Name", event.getChannel().getName(), true);
        eb.addField("Channel ID", event.getChannel().getId(), true);
        eb.setTimestamp(OffsetDateTime.now());
        postLog(eb, event.getGuild(), BAROBOT_TCHANNEL_LOG, null);
    }

    public void onTextChannelUpdateName(@Nonnull TextChannelUpdateNameEvent event) {
        EmbedBuilder eb = new EmbedBuilder().setColor(new ColorUtil().getRandomColor());
        eb.setDescription("TEXTCHANNEL NAME CHANGE");
        eb.addField("Channel", event.getChannel().getAsMention(), true);
        eb.addField("Channel ID", event.getChannel().getId(), true);
        eb.addField("Old name", event.getOldName(), true);
        eb.addField("New name", event.getNewName(), true);
        eb.setTimestamp(OffsetDateTime.now());
        postLog(eb, event.getGuild(), BAROBOT_TCHANNEL_LOG, null);
    }

    public void onTextChannelUpdateTopic(@Nonnull TextChannelUpdateTopicEvent event) {
        EmbedBuilder eb = new EmbedBuilder().setColor(new ColorUtil().getRandomColor());
        eb.setDescription("TEXTCHANNEL TOPIC CHANGE");
        eb.addField("Channel", event.getChannel().getAsMention(), true);
        eb.addField("Channel ID", event.getChannel().getId(), true);
        eb.addField("Old topic", event.getOldTopic() == null ? "`none`" : event.getOldTopic(), true);
        eb.addField("New topic", event.getNewTopic() != null ? event.getNewTopic() : "`none`", true);
        eb.setTimestamp(OffsetDateTime.now());
        postLog(eb, event.getGuild(), BAROBOT_CHANNEL_TOPIC_LOG, null);
    }

    public void onTextChannelUpdatePosition(@Nonnull TextChannelUpdatePositionEvent event) {
        EmbedBuilder eb = new EmbedBuilder().setColor(new ColorUtil().getRandomColor());
        eb.setDescription("TEXTCHANNEL POSITION CHANGE");
        eb.addField("Channel", event.getChannel().getAsMention(), true);
        eb.addField("Channel ID", event.getChannel().getId(), true);
        eb.addField("Old position", event.getOldPosition() + "", true);
        eb.addField("New position", event.getNewPosition() + "", true);
        eb.setTimestamp(OffsetDateTime.now());
        postLog(eb, event.getGuild(), BAROBOT_TCHANNEL_LOG, null);
    }

    public void onTextChannelUpdateNSFW(@Nonnull TextChannelUpdateNSFWEvent event, BaroBot bot) {
        EmbedBuilder eb = new EmbedBuilder().setColor(new ColorUtil().getRandomColor());
        EmoteUtil eu = new EmoteUtil(bot);
        eb.setDescription("TEXTCHANNEL NSFW CHANGE");
        eb.addField("Channel", event.getChannel().getAsMention(), true);
        eb.addField("Channel ID", event.getChannel().getId(), true);
        eb.addBlankField(true);
        eb.addField("Before", eu.getBooleanEmote(event.getOldNSFW()).getAsMention(), true);
        eb.addField("Now", eu.getBooleanEmote(event.getNewValue()).getAsMention(), true);
        eb.setTimestamp(OffsetDateTime.now());
        postLog(eb, event.getGuild(), BAROBOT_TCHANNEL_LOG, null);
    }

    public void onTextChannelUpdateSlowmode(@Nonnull TextChannelUpdateSlowmodeEvent event, BaroBot bot) {
        EmbedBuilder eb = new EmbedBuilder().setColor(new ColorUtil().getRandomColor());
        EmoteUtil eu = new EmoteUtil(bot);
        eb.setDescription("TEXTCHANNEL SLOWMODE CHANGE");
        eb.addField("Channel", event.getChannel().getAsMention(), true);
        eb.addField("Channel ID", event.getChannel().getId(), true);
        eb.addBlankField(true);
        eb.addField("Before", event.getOldSlowmode() + " seconds", true);
        eb.addField("Now", event.getNewSlowmode() + " seconds", true);
        eb.setTimestamp(OffsetDateTime.now());
        postLog(eb, event.getGuild(), BAROBOT_TCHANNEL_LOG, null);
    }

    public void onTextChannelCreate(@Nonnull TextChannelCreateEvent event) {
        EmbedBuilder eb = new EmbedBuilder().setColor(new ColorUtil().getRandomColor());
        eb.setDescription("TEXTCHANNEL CREATED");
        eb.addField("Channel", event.getChannel().getAsMention(), true);
        eb.addField("Channel ID", event.getChannel().getId(), true);
        eb.setTimestamp(OffsetDateTime.now());
        postLog(eb, event.getGuild(), BAROBOT_TCHANNEL_LOG, null);
    }

    public void onVoiceChannelDelete(@Nonnull VoiceChannelDeleteEvent event) {
        EmbedBuilder eb = new EmbedBuilder().setColor(new ColorUtil().getRandomColor());
        eb.setDescription("VOICECHANNEL DELETED");
        eb.addField("Channel Name", event.getChannel().getName(), true);
        eb.addField("Channel ID", event.getChannel().getId(), true);
        eb.setTimestamp(OffsetDateTime.now());
        postLog(eb, event.getGuild(), BAROBOT_VCHANNEL_LOG, null);
    }

    public void onVoiceChannelUpdateName(@Nonnull VoiceChannelUpdateNameEvent event) {
        EmbedBuilder eb = new EmbedBuilder().setColor(new ColorUtil().getRandomColor());
        eb.setDescription("VOICECHANNEL NAME CHANGE");
        eb.addField("Channel Name", event.getChannel().getName(), true);
        eb.addField("Channel ID", event.getChannel().getId(), true);
        eb.addField("Old name", event.getOldName(), true);
        eb.addField("New name", event.getNewName(), true);
        eb.setTimestamp(OffsetDateTime.now());
        postLog(eb, event.getGuild(), BAROBOT_VCHANNEL_LOG, null);
    }

    public void onVoiceChannelUpdatePosition(@Nonnull VoiceChannelUpdatePositionEvent event) {
        EmbedBuilder eb = new EmbedBuilder().setColor(new ColorUtil().getRandomColor());
        eb.setDescription("VOICECHANNEL POSITION CHANGE");
        eb.addField("Channel Name", event.getChannel().getName(), true);
        eb.addField("Channel ID", event.getChannel().getId(), true);
        eb.addField("Old position", event.getOldPosition() + "", true);
        eb.addField("New position", event.getNewPosition() + "", true);
        eb.setTimestamp(OffsetDateTime.now());
        postLog(eb, event.getGuild(), BAROBOT_VCHANNEL_LOG, null);
    }

    public void onVoiceChannelUpdateUserLimit(@Nonnull VoiceChannelUpdateUserLimitEvent event) {
        EmbedBuilder eb = new EmbedBuilder().setColor(new ColorUtil().getRandomColor());
        eb.setDescription("VOICECHANNEL USER LIMIT CHANGE");
        eb.addField("Channel Name", event.getChannel().getName(), true);
        eb.addField("Channel ID", event.getChannel().getId(), true);
        eb.addField("Old position", event.getOldUserLimit() + "", true);
        eb.addField("New position", event.getNewUserLimit() + "", true);
        eb.setTimestamp(OffsetDateTime.now());
        postLog(eb, event.getGuild(), BAROBOT_VCHANNEL_LOG, null);
    }

    public void onVoiceChannelUpdateBitrate(@Nonnull VoiceChannelUpdateBitrateEvent event) {
        EmbedBuilder eb = new EmbedBuilder().setColor(new ColorUtil().getRandomColor());
        eb.setDescription("VOICECHANNEL BITRATE CHANGE");
        eb.addField("Channel Name", event.getChannel().getName(), true);
        eb.addField("Channel ID", event.getChannel().getId(), true);
        eb.addField("Old position", event.getOldBitrate() + "", true);
        eb.addField("New position", event.getNewBitrate() + "", true);
        eb.setTimestamp(OffsetDateTime.now());
        postLog(eb, event.getGuild(), BAROBOT_VCHANNEL_LOG, null);
    }

    public void onVoiceChannelCreate(@Nonnull VoiceChannelCreateEvent event) {
        EmbedBuilder eb = new EmbedBuilder().setColor(new ColorUtil().getRandomColor());
        eb.setDescription("VOICECHANNEL CREATED");
        eb.addField("Channel Name", event.getChannel().getName(), true);
        eb.addField("Channel ID", event.getChannel().getId(), true);
        eb.setTimestamp(OffsetDateTime.now());
        postLog(eb, event.getGuild(), BAROBOT_VCHANNEL_LOG, null);
    }

    public void onGuildMemberJoin(@Nonnull GuildMemberJoinEvent event) {
        EmbedBuilder eb = new EmbedBuilder().setColor(new ColorUtil().getRandomColor());
        User user = event.getUser();
        eb.setDescription("MEMBER JOINED");
        eb.setThumbnail(user.getEffectiveAvatarUrl());
        eb.addField("Username", user.getName() + "#" + user.getDiscriminator(), true);
        eb.addField("User ID", user.getId(), true);
        eb.addField("Account creation", FormatUtil.formatTime(event.getMember().getTimeCreated()), false);
        eb.addField("Join Date", FormatUtil.formatTime(event.getMember().getTimeJoined()), true);
        eb.setTimestamp(OffsetDateTime.now());
        postLog(eb, event.getGuild(), BAROBOT_SERVER_JOIN, null);
    }

    public void onGuildMemberLeave(@Nonnull GuildMemberRemoveEvent event) {
        EmbedBuilder eb = new EmbedBuilder().setColor(new ColorUtil().getRandomColor());
        User user = event.getUser();
        eb.setDescription("MEMBER LEFT");
        eb.setThumbnail(user.getEffectiveAvatarUrl());
        eb.addField("Username", user.getName() + "#" + user.getDiscriminator(), true);
        eb.addField("User ID", user.getId(), true);
        eb.addBlankField(true);
        // TODO: Joined Date
        eb.addField("Account Creation", FormatUtil.formatTime(event.getUser().getTimeCreated()), true);
        eb.addField("Join Date", FormatUtil.formatTime(event.getMember().getTimeJoined()), true);
        eb.addField("Been in server for", Instant.ofEpochSecond(event.getMember().getTimeJoined().toEpochSecond()).until(Instant.now(), ChronoUnit.DAYS) + " days", true);
        eb.setTimestamp(OffsetDateTime.now());
        postLog(eb, event.getGuild(), "BAROBOT_SERVER_LEAVE", null);
    }

    public void onGuildVoiceJoin(GuildVoiceJoinEvent event) {
        EmbedBuilder eb = new EmbedBuilder().setColor(new ColorUtil().getRandomColor());
        User user = event.getEntity().getUser();
        eb.setDescription("VOICE JOIN");
        eb.setAuthor(user.getName() + "#" + user.getDiscriminator(), user.getEffectiveAvatarUrl(), user.getEffectiveAvatarUrl());
        eb.addField("Voice Channel", event.getChannelJoined().getName(), true);
        eb.addField("Author ID", user.getId(), true);
        eb.setTimestamp(OffsetDateTime.now());
        postLog(eb, event.getGuild(), BAROBOT_VOICE_JOIN_LOG, null);
    }

    public void onGuildVoiceLeave(GuildVoiceLeaveEvent event) {
        EmbedBuilder eb = new EmbedBuilder().setColor(new ColorUtil().getRandomColor());
        User user = event.getEntity().getUser();
        eb.setDescription("VOICE LEAVE");
        eb.setAuthor(user.getName() + "#" + user.getDiscriminator(), user.getEffectiveAvatarUrl(), user.getEffectiveAvatarUrl());
        eb.addField("Voice Channel", event.getChannelLeft().getName(), true);
        eb.addField("Author ID", user.getId(), true);
        eb.setTimestamp(OffsetDateTime.now());
        postLog(eb, event.getGuild(), BAROBOT_VOICE_LEAVE_LOG, null);
    }
}