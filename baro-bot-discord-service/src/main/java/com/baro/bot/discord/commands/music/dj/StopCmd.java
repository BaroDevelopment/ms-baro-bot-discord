package com.baro.bot.discord.commands.music.dj;

import com.baro.bot.discord.commands.CommandCategory;
import com.baro.bot.discord.commands.CommandContext;
import com.baro.bot.discord.commands.ICommand;
import com.baro.bot.discord.commands.MusicCommand;
import com.baro.bot.discord.commands.music.audio.AudioHandler;

public class StopCmd extends MusicCommand implements ICommand {

    public static final String COMMAND_NAME = "stop";

    @Override
    public void execute(CommandContext ctx) {
        if (!init(ctx)) return;
        AudioHandler handler = (AudioHandler) ctx.getEvent().getGuild().getAudioManager().getSendingHandler();
        handler.stopAndClear();
        ctx.getEvent().getGuild().getAudioManager().closeAudioConnection();
        ctx.getEvent().getChannel().sendMessage("The player has stopped and the queue has been cleared.").queue();
    }

    @Override
    public String getName() {
        return COMMAND_NAME;
    }

    @Override
    public String getDescription() {
        return "Clears the queue, ends the current song, and leaves the voice channel";
    }

    @Override
    public CommandCategory getCategory() {
        return CommandCategory.DJ;
    }

    @Override
    public boolean guildOnly() {
        return true;
    }
}
