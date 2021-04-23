package com.baro.bot.discord.service;

import com.baro.bot.discord.commands.music.audio.AudioHandler;
import com.baro.bot.discord.commands.music.audio.NowplayingHandler;
import com.baro.bot.discord.commands.music.audio.PlayerManager;
import com.baro.bot.discord.commands.music.playlist.PlaylistLoader;
import com.baro.bot.discord.components.Listener;
import com.baro.bot.discord.config.BotConfig;
import com.baro.bot.discord.util.ConsoleColors;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import com.sedmelluq.discord.lavaplayer.jdaudp.NativeAudioSendFactory;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.security.auth.login.LoginException;
import java.util.concurrent.Executors;

@Service
@Profile("!test")
public class BaroBot {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaroBot.class);
    private final BotConfig botConfig;
    private final PlayerManager playerManager;
    private final NowplayingHandler nowplayingHandler;
    private final PlaylistLoader playlistLoader;
    private final EventWaiter eventWaiter;
    private final Listener listener;
    private JDA jda;
    private boolean shuttingDown = false;

    public BaroBot(BotConfig botConfig, Listener listener) {
        this.botConfig = botConfig;
        this.eventWaiter = new EventWaiter();
        this.listener = listener;
        this.playerManager = new PlayerManager(this);
        this.playerManager.init();
        this.nowplayingHandler = new NowplayingHandler(this);
        this.nowplayingHandler.init();
        this.playlistLoader = new PlaylistLoader();
    }

    @Bean
    private void createJda() throws LoginException {

        JDABuilder builder = JDABuilder.createDefault(botConfig.getToken())
                .setBulkDeleteSplittingEnabled(true)
                .setAudioSendFactory(new NativeAudioSendFactory())
                .enableIntents(
                        GatewayIntent.DIRECT_MESSAGE_REACTIONS,
                        GatewayIntent.DIRECT_MESSAGES,
                        GatewayIntent.GUILD_BANS,
                        GatewayIntent.GUILD_MEMBERS,
                        GatewayIntent.GUILD_INVITES,
                        GatewayIntent.GUILD_PRESENCES,
                        GatewayIntent.GUILD_VOICE_STATES,
                        GatewayIntent.DIRECT_MESSAGE_TYPING,
                        GatewayIntent.GUILD_EMOJIS,
                        GatewayIntent.GUILD_MESSAGE_REACTIONS,
                        GatewayIntent.GUILD_MESSAGE_TYPING,
                        GatewayIntent.GUILD_MESSAGES
                )
                .addEventListeners(eventWaiter, listener);
        if (!botConfig.getActivity().isEmpty()) {
            builder.setActivity(Activity.watching(botConfig.getActivity()));
        }
        jda = builder.build();
        LOGGER.info(ConsoleColors.PURPLE_BOLD_BRIGHT + "---------------------------------");
        LOGGER.info("Logged in as: " + jda.getSelfUser().getName() + "#" + jda.getSelfUser().getDiscriminator());
        LOGGER.info("UserID: " + jda.getSelfUser().getId());
        LOGGER.info("---------------------------------" + ConsoleColors.RESET);
    }

    public void resetActivity() {
        jda.getPresence().setActivity(null);
    }

    public void shutdown() {
        if (shuttingDown)
            return;
        shuttingDown = true;
        if (jda.getStatus() != JDA.Status.SHUTTING_DOWN) {
            jda.getGuilds().stream().forEach(g -> {
                g.getAudioManager().closeAudioConnection();
                AudioHandler ah = (AudioHandler) g.getAudioManager().getSendingHandler();
                if (ah != null) {
                    ah.stopAndClear();
                    ah.getPlayer().destroy();
                }
            });
            jda.shutdown();
        }
        System.exit(0);
    }

    public void closeAudioConnection(long guildId) {
        Guild guild = jda.getGuildById(guildId);
        if (guild != null)
            Executors.newSingleThreadScheduledExecutor().submit(() -> guild.getAudioManager().closeAudioConnection());
    }

    public JDA getJda() {
        return jda;
    }

    public EventWaiter getEventWaiter() {
        return eventWaiter;
    }

    public PlayerManager getPlayerManager() {
        return playerManager;
    }

    public NowplayingHandler getNowplayingHandler() {
        return nowplayingHandler;
    }

    public PlaylistLoader getPlaylistLoader() {
        return playlistLoader;
    }

    public Listener getListener() {
        return listener;
    }

    public BotConfig getBotConfig() {
        return botConfig;
    }
}
