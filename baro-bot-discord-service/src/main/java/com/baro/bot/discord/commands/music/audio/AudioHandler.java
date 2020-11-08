
package com.baro.bot.discord.commands.music.audio;

import com.baro.bot.discord.commands.music.queue.FairQueue;
import com.baro.bot.discord.model.entities.MusicEntity;
import com.baro.bot.discord.repository.MusicRepository;
import com.baro.bot.discord.service.BaroBot;
import com.baro.bot.discord.util.*;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;
import com.sedmelluq.discord.lavaplayer.track.playback.MutableAudioFrame;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.audio.AudioSendHandler;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.*;

import static com.baro.bot.discord.util.Statics.*;

public class AudioHandler extends AudioEventAdapter implements AudioSendHandler {

    private final FairQueue<QueuedTrack> queue = new FairQueue<>();
    private final List<AudioTrack> defaultQueue = new LinkedList<>();
    private final Set<String> votes = new HashSet<>();
    private final PlayerManager manager;
    private final AudioPlayer audioPlayer;
    private final long guildId;
    private final ByteBuffer buffer;
    private final MutableAudioFrame frame;
    private final boolean isAutoLeave = true;

    private final MusicRepository musicRepository;


    protected AudioHandler(PlayerManager manager, Guild guild, AudioPlayer player, MusicRepository musicRepository) {
        this.manager = manager;
        this.buffer = ByteBuffer.allocate(1024);
        this.frame = new MutableAudioFrame();
        this.frame.setBuffer(buffer);
        this.audioPlayer = player;
        this.guildId = guild.getIdLong();
        this.musicRepository = musicRepository;
    }

    public int addTrackToFront(QueuedTrack qtrack) {
        if (audioPlayer.getPlayingTrack() == null) {
            audioPlayer.playTrack(qtrack.getTrack());
            return -1;
        } else {
            queue.addAt(0, qtrack);
            return 0;
        }
    }

    public int addTrack(QueuedTrack qtrack) {
        if (audioPlayer.getPlayingTrack() == null) {
            audioPlayer.playTrack(qtrack.getTrack());
            return -1;
        } else
            return queue.add(qtrack);
    }

    public FairQueue<QueuedTrack> getQueue() {
        return queue;
    }

    public void stopAndClear() {
        queue.clear();
        defaultQueue.clear();
        audioPlayer.stopTrack();
        //current = null;
    }

    public boolean isMusicPlaying(JDA jda) {
        return guild(jda).getSelfMember().getVoiceState().inVoiceChannel() && audioPlayer.getPlayingTrack() != null;
    }

    public Set<String> getVotes() {
        return votes;
    }

    public AudioPlayer getPlayer() {
        return audioPlayer;
    }

    public long getRequester() {
        if (audioPlayer.getPlayingTrack() == null || audioPlayer.getPlayingTrack().getUserData(Long.class) == null)
            return 0;
        return audioPlayer.getPlayingTrack().getUserData(Long.class);
    }

    // Audio Events
    @Override
    public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
        // if the track ended normally, and we're in repeat mode, re-add it to the queue
        Optional<MusicEntity> musicSettingsEntity = musicRepository.findById(guildId);
        boolean prepeat = musicSettingsEntity.isPresent() && musicSettingsEntity.get().isPlaylistRepeat();
        if (endReason == AudioTrackEndReason.FINISHED && prepeat) {
            queue.add(new QueuedTrack(track.makeClone(), track.getUserData(Long.class) == null ? 0L : track.getUserData(Long.class)));
        }

        boolean repeat = musicSettingsEntity.isPresent() && musicSettingsEntity.get().isTrackRepeat();
        if (endReason == AudioTrackEndReason.FINISHED && repeat) {
            queue.addAt(0, new QueuedTrack(track.makeClone(), track.getUserData(Long.class) == null ? 0L : track.getUserData(Long.class)));
        }

        if (queue.isEmpty()) {
            manager.getBot().getNowplayingHandler().onTrackUpdate(guildId, null, this);
            if (isAutoLeave)
                manager.getBot().closeAudioConnection(guildId);
            // unpause, in the case when the player was paused and the track has been skipped.
            // this is to prevent the player being paused next time it's being used.
            player.setPaused(false);
        } else {
            QueuedTrack qt = queue.pull();
            player.playTrack(qt.getTrack());
        }
    }

    @Override
    public void onTrackStart(AudioPlayer player, AudioTrack track) {
        votes.clear();
        manager.getBot().getNowplayingHandler().onTrackUpdate(guildId, track, this);
    }


    // Formatting
    public Message getNowPlaying(JDA jda, BaroBot bot) {
        if (isMusicPlaying(jda)) {
            Guild guild = guild(jda);
            AudioTrack track = audioPlayer.getPlayingTrack();
            MessageBuilder mb = new MessageBuilder();
//            mb.append(FormatUtil.filter("\uD83C\uDFB6\" **Now Playing in " + guild.getSelfMember().getVoiceState().getChannel().getName() + "...**"));
            EmbedBuilder eb = new EmbedBuilder().setColor(guild.getSelfMember().getColor());
            MusicUtils mu = new MusicUtils(bot);
            EmoteUtil eu = new EmoteUtil(bot);
            String platform = mu.getPlatform(track);
            String platform_icon = mu.getPlatformIcon(platform);
            String thubmnail = mu.getThumbnail(platform, track);
            if (getRequester() != 0) {
                User u = guild.getJDA().getUserById(getRequester());
                if (u == null)
                    eb.setAuthor("Unknown (ID:" + getRequester() + ")", null, null);
                else
                    eb.setAuthor("[DJ] " + u.getName() + "#" + u.getDiscriminator(), null, u.getEffectiveAvatarUrl());
            }

//            if (track instanceof YoutubeAudioTrack && BotSettingsManager.getInstance().getSettings().isNpimages()) {
//                eb.setThumbnail("https://img.youtube.com/vi/" + track.getIdentifier() + "/mqdefault.jpg");
//            }

            eb.setThumbnail(thubmnail);
            eb.addField(platform_icon + " Channel", track.getInfo().author, true);

            eb.setFooter("Volume:  " + "  " + audioPlayer.getVolume(), Statics.IMAGE_SPEAKERS_VOLUME);

            try {
                eb.setTitle(track.getInfo().title, track.getInfo().uri);
            } catch (Exception e) {
                eb.setTitle(platform_icon + "  " + track.getInfo().title);
            }


            try {
                eb.addField(eu.getEmote("spotify").getAsMention() + " Spotify", "[Click here](" + mu.getSpotifyLink(audioPlayer) + ")", true);
            } catch (Exception ex) {
                // No Spotify link found
                eb.addField(eu.getEmote("spotify").getAsMention() + " Spotify", "Not found.", true);
            }

            try {
                Pair<Boolean, String> lyrics = mu.handleLyrics(track.getInfo().title);
                if (lyrics.getKey()) {
                    eb.addField(eu.getEmote("lyrics").getAsMention() + " Lyrics", "[Click here](" + lyrics.getValue() + ")", true);
                } else
                    eb.addField(eu.getEmote("lyrics").getAsMention() + " Lyrics", "Not found.", true);
            } catch (IOException ioexception) {
            }

            eb.addField(eu.getEmojiAsString("google") + " Queue", queue.size() + " tracks ( " + mu.calcTotalQueueTime(queue) + ")", true);

            if (queue.size() >= 1) {
                QueuedTrack nextTrack = queue.get(0);
                eb.addField(eu.getEmojiAsString("nextTrack") + " Next Track", "[" + (mu.titleShortner(nextTrack.getTrack().getInfo().title)) + "]" + "(" + nextTrack.getTrack().getInfo().uri + ")", false);
            }

            double progress = (double) audioPlayer.getPlayingTrack().getPosition() / track.getDuration();
            eb.setDescription((audioPlayer.isPaused() ? PAUSE_EMOJI : PLAY_EMOJI)
                    + " " + FormatUtil.progressBar(progress)
                    + " `[" + FormatUtil.formatTime(track.getPosition()) + "/" + FormatUtil.formatTime(track.getDuration()) + "]` "
                    + FormatUtil.volumeIcon(audioPlayer.getVolume()));

            return mb.setEmbed(eb.build()).build();
        } else return null;
    }

    public Message getNoMusicPlaying(JDA jda) {
        Guild guild = guild(jda);
        return new MessageBuilder()
                .setContent(FormatUtil.filter("\uD83C\uDFB6 **Now Playing...**"))
                .setEmbed(new EmbedBuilder()
                        .setTitle("No music playing")
                        .setDescription(STOP_EMOJI + " " + FormatUtil.progressBar(-1) + " " + FormatUtil.volumeIcon(audioPlayer.getVolume()))
                        .setColor(guild.getSelfMember().getColor())
                        .build()).build();
    }

    public String getTopicFormat(JDA jda) {
        if (isMusicPlaying(jda)) {
            long userid = getRequester();
            AudioTrack track = audioPlayer.getPlayingTrack();
            String title = track.getInfo().title;
            if (title == null || title.equals("Unknown Title"))
                title = track.getInfo().uri;
            return "**" + title + "** [" + (userid == 0 ? "autoplay" : "<@" + userid + ">") + "]"
                    + "\n" + (audioPlayer.isPaused() ? PAUSE_EMOJI : PLAY_EMOJI) + " "
                    + "[" + FormatUtil.formatTime(track.getDuration()) + "] "
                    + FormatUtil.volumeIcon(audioPlayer.getVolume());
        } else return "No music playing " + STOP_EMOJI + " " + FormatUtil.volumeIcon(audioPlayer.getVolume());
    }

    // Audio Send Handler methods
    @Override
    public boolean canProvide() {
        // returns true if audio was provided
        return audioPlayer.provide(frame);
    }

    @Override
    public ByteBuffer provide20MsAudio() {
        // flip to make it a read buffer
        buffer.flip();
        return buffer;
    }

    @Override
    public boolean isOpus() {
        return true;
    }


    // Private methods
    private Guild guild(JDA jda) {
        return jda.getGuildById(guildId);
    }
}
