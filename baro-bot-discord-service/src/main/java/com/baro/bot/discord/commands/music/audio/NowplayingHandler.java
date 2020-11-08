
package com.baro.bot.discord.commands.music.audio;

import com.baro.bot.discord.service.BaroBot;
import com.baro.bot.discord.util.Pair;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


public class NowplayingHandler {
    private final BaroBot bot;
    private final HashMap<Long, Pair<Long, Long>> lastNP = new HashMap<>();
    // guild -> channel,message
    private final boolean isNpimages = true;
    private final boolean isTrackInStatus = true;

    public NowplayingHandler(BaroBot bot) {
        this.bot = bot;
    }

    public void init() {
        if (isNpimages)
            Executors.newSingleThreadScheduledExecutor().scheduleWithFixedDelay(() -> updateAll(), 0, 30, TimeUnit.SECONDS);
    }

    public void setLastNPMessage(Message m) {
        lastNP.put(m.getGuild().getIdLong(), new Pair<>(m.getTextChannel().getIdLong(), m.getIdLong()));
    }

    public void clearLastNPMessage(Guild guild) {
        lastNP.remove(guild.getIdLong());
    }

    private void updateAll() {
        Set<Long> toRemove = new HashSet<>();
        for (long guildId : lastNP.keySet()) {
            Guild guild = bot.getJda().getGuildById(guildId);
            if (guild == null) {
                toRemove.add(guildId);
                continue;
            }
            Pair<Long, Long> pair = lastNP.get(guildId);
            TextChannel tc = guild.getTextChannelById(pair.getKey());
            if (tc == null) {
                toRemove.add(guildId);
                continue;
            }
            AudioHandler handler = (AudioHandler) guild.getAudioManager().getSendingHandler();
            Message msg = handler.getNowPlaying(bot.getJda(), bot);
            if (msg == null) {
                msg = handler.getNoMusicPlaying(bot.getJda());
                toRemove.add(guildId);
            }
            try {
                tc.editMessageById(pair.getValue(), msg).queue(m -> {
                }, t -> lastNP.remove(guildId));
            } catch (Exception e) {
                toRemove.add(guildId);
            }
        }
        toRemove.forEach(lastNP::remove);
    }

    // "event"-based methods
    public void onTrackUpdate(long guildId, AudioTrack track, AudioHandler handler) {
        // update bot status if applicable
        if (isTrackInStatus) {
            if (track != null && bot.getJda().getGuilds().stream().filter(g -> g.getSelfMember().getVoiceState().inVoiceChannel()).count() <= 1)
                bot.getJda().getPresence().setActivity(Activity.listening(track.getInfo().title));
            else
                bot.resetActivity();
        }
    }

    public void onMessageDelete(Guild guild, long messageId) {
        Pair<Long, Long> pair = lastNP.get(guild.getIdLong());
        if (pair == null)
            return;
        if (pair.getValue() == messageId)
            lastNP.remove(guild.getIdLong());
    }
}
