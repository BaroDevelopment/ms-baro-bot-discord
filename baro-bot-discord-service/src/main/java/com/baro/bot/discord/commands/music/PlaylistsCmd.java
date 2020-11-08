package com.baro.bot.discord.commands.music;

import com.baro.bot.discord.commands.CommandCategory;
import com.baro.bot.discord.commands.CommandContext;
import com.baro.bot.discord.commands.ICommand;
import com.baro.bot.discord.commands.MusicCommand;

import java.util.List;

public class PlaylistsCmd extends MusicCommand implements ICommand {

    public static final String COMMAND_NAME = "playlists";

    @Override
    public void execute(CommandContext ctx) {
        if (!init(ctx)) return;

        if (!ctx.getBot().getPlaylistLoader().folderExists())
            ctx.getBot().getPlaylistLoader().createFolder();
        if (!ctx.getBot().getPlaylistLoader().folderExists()) {
            ctx.getEvent().getChannel().sendMessage(" Playlists folder does not exist and could not be created!").queue();
            return;
        }
        List<String> list = ctx.getBot().getPlaylistLoader().getPlaylistNames();
        if (list == null) {
            ctx.getEvent().getChannel().sendMessage("Failed to load available playlists!").queue();
        } else if (list.isEmpty()) {
            ctx.getEvent().getChannel().sendMessage("There are no playlists in the Playlists folder!").queue();
        } else {
            StringBuilder builder = new StringBuilder("Available playlists:\n");
            list.forEach(str -> builder.append("`").append(str).append("` "));
            builder.append("\nType `").append(ctx.getPrefix()).append("playlist <name>` to play a playlist");
            ctx.getEvent().getChannel().sendMessage(builder.toString()).queue();
        }
    }

    @Override
    public String getName() {
        return COMMAND_NAME;
    }

    @Override
    public String getDescription() {
        return "Shows available playlists. These playlists must be inside the Playlists folder.";
    }

    @Override
    public CommandCategory getCategory() {
        return CommandCategory.MUSIC;
    }
}
