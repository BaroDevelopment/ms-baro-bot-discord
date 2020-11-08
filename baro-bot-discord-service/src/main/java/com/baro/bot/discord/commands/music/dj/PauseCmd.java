package com.baro.bot.discord.commands.music.dj;

import com.baro.bot.discord.commands.CommandCategory;
import com.baro.bot.discord.commands.CommandContext;
import com.baro.bot.discord.commands.ICommand;
import com.baro.bot.discord.commands.MusicCommand;
import com.baro.bot.discord.commands.music.audio.AudioHandler;

public class PauseCmd extends MusicCommand implements ICommand {

    public static final String COMMAND_NAME = "pause";

    @Override
    public void execute(CommandContext ctx) {
        if (!init(ctx)) return;

        AudioHandler handler = (AudioHandler) ctx.getEvent().getGuild().getAudioManager().getSendingHandler();
        if (handler.getPlayer().isPaused()) {
            ctx.getEvent().getChannel().sendMessage("The player is already paused! Use `" + ctx.getPrefix()
                    + "play` to unpause!").queue();
            return;
        }
        handler.getPlayer().setPaused(true);
        ctx.getEvent().getChannel().sendMessage("Paused **" + handler.getPlayer().getPlayingTrack().getInfo().title
                + "**. Type `" + ctx.getPrefix() + "play` to unpause!").queue();
    }

    @Override
    public String getName() {
        return COMMAND_NAME;
    }

    @Override
    public String getDescription() {
        return "Pauses the player. The player remains paused until a DJ or Admin uses the **play** command";
    }

    @Override
    public CommandCategory getCategory() {
        return CommandCategory.DJ;
    }
}
