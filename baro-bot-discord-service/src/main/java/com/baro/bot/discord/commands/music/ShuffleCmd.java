package com.baro.bot.discord.commands.music;

import com.baro.bot.discord.commands.CommandCategory;
import com.baro.bot.discord.commands.CommandContext;
import com.baro.bot.discord.commands.ICommand;
import com.baro.bot.discord.commands.MusicCommand;
import com.baro.bot.discord.commands.music.audio.AudioHandler;

public class ShuffleCmd extends MusicCommand implements ICommand {

    public static final String COMMAND_NAME = "shuffle";

    @Override
    public void execute(CommandContext ctx) {
        if (!init(ctx)) return;

        AudioHandler handler = (AudioHandler) ctx.getEvent().getGuild().getAudioManager().getSendingHandler();
        int s = handler.getQueue().shuffle(ctx.getEvent().getAuthor().getIdLong());
        switch (s) {
            case 0:
                String msg0 = "You don't have any music in the queue to shuffle!";
                ctx.getEvent().getChannel().sendMessage(msg0).queue();
                break;
            case 1:
                String msg1 = "You only have one song in the queue!";
                ctx.getEvent().getChannel().sendMessage(msg1).queue();
                break;
            default:
                String msg3 = "You successfully shuffled your " + s + " entries.";
                ctx.getEvent().getChannel().sendMessage(msg3).queue();
                break;
        }
    }

    @Override
    public String getName() {
        return COMMAND_NAME;
    }

    @Override
    public String getDescription() {
        return "Shuffles (changes the order, randomly) of songs that you have added to the queue";
    }

    @Override
    public CommandCategory getCategory() {
        return CommandCategory.MUSIC;
    }
}
