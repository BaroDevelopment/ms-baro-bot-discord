package com.baro.bot.discord.commands.owner;

import com.baro.bot.discord.commands.ACommand;
import com.baro.bot.discord.commands.CommandCategory;
import com.baro.bot.discord.commands.CommandContext;
import com.baro.bot.discord.commands.ICommand;
import com.baro.bot.discord.commands.music.playlist.PlaylistLoader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class PlaylistCmd extends ACommand implements ICommand {

    public static final String COMMAND_NAME = "playlist";

    // args:    <append|delete|make|setdefault>
    @Override
    public void execute(CommandContext ctx) {
        String args = ctx.getArgs().toLowerCase();

        switch (args.split(" ")[0]) {
            case "":
                printUsage(ctx);
                break;
            case "all":
            case "list":
            case "l":
                handleAll(ctx);
                break;
            case "append":
                handleAppend(ctx);
                break;
            case "delete":
            case "remove":
            case "wipe":
                handleDelete(ctx);
                break;
            case "make":
                handleMake(ctx);
                break;
            case "setdefault":
                handleSetDefault(ctx);
                break;
            default:

        }
    }

    private void handleSetDefault(CommandContext ctx) {
        if (ctx.getArgs().isEmpty()) {
            ctx.getEvent().getChannel().sendMessage("Please include a playlist name or NONE").queue();
            return;
        }
        if (ctx.getArgs().equalsIgnoreCase("none")) {
            // TODO: FIXME
//            settings.setDefaultPlaylist(null);
//            ctx.getEvent().getChannel().sendMessage("Cleared the default playlist for **" + ctx.getEvent().getGuild().getName() + "**").queue();
            return;
        }
        String pname = ctx.getArgs().split(" ", 2)[1].replaceAll("\\s+", "_");
        if (ctx.getBot().getPlaylistLoader().getPlaylist(pname) == null) {
            ctx.getEvent().getChannel().sendMessage("Could not find `" + pname + ".txt`!").queue();
        } else {
            // TODO: FIXME
//            settings.setDefaultPlaylist(pname);
            ctx.getEvent().getChannel().sendMessage("The default playlist for **" + ctx.getEvent().getGuild().getName() + "** is now `" + pname + "`").queue();
        }
    }

    private void printUsage(CommandContext ctx) {
        StringBuilder builder = new StringBuilder("Playlist Management Commands:\n");
        builder.append("`" + ctx.getPrefix() + "playlist all  - lists all available playlists" + "`\n");
        builder.append("`" + ctx.getPrefix() + "playlist append <name> <URL> | <URL> | ... - appends songs to an existing playlist" + "`\n");
        builder.append("`" + ctx.getPrefix() + "playlist delete <name> - deletes an existing playlist" + "`\n");
        builder.append("`" + ctx.getPrefix() + "playlist make <name> - makes a new playlist" + "`\n");
        builder.append("`" + ctx.getPrefix() + "playlist setdefault <playlistname|NONE> - sets the default playlist for the server" + "`\n");
        ctx.getEvent().getChannel().sendMessage(builder.toString()).queue();
    }

    private void handleAppend(CommandContext ctx) {
        String[] parts = ctx.getArgs().split(" ", 2)[1].split("\\s+", 2);
        if (parts.length < 2) {
            ctx.getEvent().getChannel().sendMessage("Please include a playlist name and URLs to add!").queue();
            return;
        }
        String pname = parts[0];
        PlaylistLoader.Playlist playlist = ctx.getBot().getPlaylistLoader().getPlaylist(pname);
        if (playlist == null)
            ctx.getEvent().getChannel().sendMessage("Playlist `" + pname + "` doesn't exist!").queue();
        else {
            StringBuilder builder = new StringBuilder();
            playlist.getItems().forEach(item -> builder.append("\r\n").append(item));
            String[] urls = parts[1].split("\\|");
            for (String url : urls) {
                String u = url.trim();
                if (u.startsWith("<") && u.endsWith(">"))
                    u = u.substring(1, u.length() - 1);
                builder.append("\r\n").append(u);
            }
            try {
                ctx.getBot().getPlaylistLoader().writePlaylist(pname, builder.toString());
                ctx.getEvent().getChannel().sendMessage("Successfully added " + urls.length + " items to playlist `" + pname + "`!").queue();
            } catch (IOException e) {
                ctx.getEvent().getChannel().sendMessage("I was unable to append to the playlist: " + e.getLocalizedMessage()).queue();
            }
        }
    }

    private void handleAll(CommandContext ctx) {
        if (!ctx.getBot().getPlaylistLoader().folderExists())
            ctx.getBot().getPlaylistLoader().createFolder();
        if (!ctx.getBot().getPlaylistLoader().folderExists()) {
            ctx.getEvent().getChannel().sendMessage("Playlists folder does not exist and could not be created!").queue();
            return;
        }
        List<String> list = ctx.getBot().getPlaylistLoader().getPlaylistNames();
        if (list == null)
            ctx.getEvent().getChannel().sendMessage("Failed to load available playlists!").queue();
        else if (list.isEmpty())
            ctx.getEvent().getChannel().sendMessage("There are no playlists in the Playlists folder!").queue();
        else {
            StringBuilder builder = new StringBuilder("Available playlists:\n");
            list.forEach(str -> builder.append("`").append(str).append("` "));
            ctx.getEvent().getChannel().sendMessage(builder.toString()).queue();
        }
    }

    private void handleMake(CommandContext ctx) {
        String pname = ctx.getArgs().split(" ", 2)[1].replaceAll("\\s+", "_");
        if (ctx.getBot().getPlaylistLoader().getPlaylist(pname) == null) {
            try {
                ctx.getBot().getPlaylistLoader().createPlaylist(pname);
                ctx.getEvent().getChannel().sendMessage("Successfully created playlist `" + pname + "`!").queue();
            } catch (IOException e) {
                ctx.getEvent().getChannel().sendMessage("I was unable to create the playlist: " + e.getLocalizedMessage()).queue();
            }
        } else
            ctx.getEvent().getChannel().sendMessage("Playlist `" + pname + "` already exists!").queue();
    }

    private void handleDelete(CommandContext ctx) {
        String pname = ctx.getArgs().split(" ", 2)[1].replaceAll("\\s+", "_");
        if (ctx.getBot().getPlaylistLoader().getPlaylist(pname) == null)
            ctx.getEvent().getChannel().sendMessage("Playlist `" + pname + "` doesn't exist!").queue();
        else {
            try {
                ctx.getBot().getPlaylistLoader().deletePlaylist(pname);
                ctx.getEvent().getChannel().sendMessage("Successfully deleted playlist `" + pname + "`!").queue();
            } catch (IOException e) {
                ctx.getEvent().getChannel().sendMessage("I was unable to delete the playlist: " + e.getLocalizedMessage()).queue();
            }
        }
    }

    @Override
    public String getName() {
        return COMMAND_NAME;
    }

    @Override
    public String getDescription() {
        return "Playlist management";
    }

    @Override
    public CommandCategory getCategory() {
        return CommandCategory.OWNER;
    }

    @Override
    public boolean guildOnly() {
        return true;
    }

    @Override
    public List<String> getExamples() {
        List<String> samples = new ArrayList<>();
        samples.add("`playlist all` - lists all available playlists");
        samples.add("`playlist append <name> <URL> | <URL> | ...` - appends songs to an existing playlist");
        samples.add("`playlist delete <name>` - deletes an existing playlist");
        samples.add("`playlist make <name>` - makes a new playlist");
        samples.add("`playlist setdefault <playlistname|NONE>` - sets the default playlist for the server");
        return samples;
    }
}
