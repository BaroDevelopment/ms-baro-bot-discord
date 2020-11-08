package com.baro.bot.discord.commands.music.dj;

import com.baro.bot.discord.commands.CommandCategory;
import com.baro.bot.discord.commands.CommandContext;
import com.baro.bot.discord.commands.ICommand;
import com.baro.bot.discord.commands.MusicCommand;
import com.baro.bot.discord.commands.music.audio.AudioHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SkiptoCmd extends MusicCommand implements ICommand {

    public static final String COMMAND_NAME = "skipto";

    // TODO: if playlist repeat is enabled then add all tracks back to queue
    // TODO: is track playlist is enabled then add it back to queue
    @Override
    public void execute(CommandContext ctx) {
        if (!init(ctx)) return;

        int index = 0;
        try {
            index = Integer.parseInt(ctx.getArgs());
        } catch (NumberFormatException e) {
            ctx.getEvent().getChannel().sendMessage("`" + ctx.getArgs() + "` is not a valid integer!").queue();
            return;
        }
        AudioHandler handler = (AudioHandler) ctx.getEvent().getGuild().getAudioManager().getSendingHandler();
        if (index < 1 || index > handler.getQueue().size()) {
            ctx.getEvent().getChannel().sendMessage("Position must be a valid integer between 1 and " + handler.getQueue().size() + "!").queue();
            return;
        }
        handler.getQueue().skip(index - 1);
        ctx.getEvent().getChannel().sendMessage("Skipped to **" + handler.getQueue().get(0).getTrack().getInfo().title + "**").queue();
        handler.getPlayer().stopTrack();
    }

    @Override
    public String getName() {
        return COMMAND_NAME;
    }

    @Override
    public String getDescription() {
        return "Skips to the specified song";
    }

    @Override
    public CommandCategory getCategory() {
        return CommandCategory.DJ;
    }

    @Override
    public List<String> getAliases() {
        return Collections.singletonList("st");
    }

    @Override
    public boolean getArgs() {
        return true;
    }

    @Override
    public List<String> getExamples() {
        List<String> samples = new ArrayList<>();
        samples.add("`skipto <position>` - Skips forward in the queue to the provided song number, " +
                "playing that song and removing any songs before that from the queue");
        return samples;
    }
}
