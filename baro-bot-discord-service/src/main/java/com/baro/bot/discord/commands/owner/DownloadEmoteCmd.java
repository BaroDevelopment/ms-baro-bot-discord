package com.baro.bot.discord.commands.owner;

import com.baro.bot.discord.commands.ACommand;
import com.baro.bot.discord.commands.CommandCategory;
import com.baro.bot.discord.commands.CommandContext;
import com.baro.bot.discord.commands.ICommand;
import com.baro.bot.discord.util.ColorUtil;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Emote;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

public class DownloadEmoteCmd extends ACommand implements ICommand {

    public static final String COMMAND_NAME = "downloademotes";
    // make sure  this directory is in your gitignore
    public static final String DIRECTORY = "EMOJI";
    public static final Logger LOGGER = LoggerFactory.getLogger(DownloadEmoteCmd.class);

    @Override
    public void execute(CommandContext ctx) {
        int counter = 0;
        EmbedBuilder eb = new EmbedBuilder().setColor(new ColorUtil().getRandomColor());
        eb.setDescription("Processing ...");
        ctx.getEvent().getChannel().sendMessage(eb.build()).queue();
        for (Emote emote : ctx.getBot().getJda().getEmotes()) {
            String fileExtension = emote.isAnimated() ? "gif" : "png";
            boolean successfulDownload = downloadEmote(emote.getImageUrl(), emote.getName(), fileExtension);
            if (successfulDownload) {
                counter++;
            }
        }
        eb.setDescription("Successfully downloaded " + counter + " emotes");
        ctx.getEvent().getChannel().sendMessage(eb.build()).queue();
    }

    private boolean downloadEmote(String url, String name, String fileExtension) {
        try {
            FileUtils.copyURLToFile(new URL(url), new File("Emojis" + "/" + name + "." + fileExtension));
            LOGGER.debug("Sucessfully downloaded emote: " + name);
            return true;
        } catch (IOException e) {
            LOGGER.debug("Failed to download emote: " + e.getMessage());
            return false;
        }
    }

    @Override
    public String getName() {
        return COMMAND_NAME;
    }

    @Override
    public String getDescription() {
        return "Download all emotes from all servers";
    }

    @Override
    public CommandCategory getCategory() {
        return CommandCategory.OWNER;
    }


    @Override
    public boolean getArgs() {
        return false;
    }

    @Override
    public boolean guildOnly() {
        return false;
    }

    @Override
    public boolean isNsfw() {
        return false;
    }

    @Override
    public EmbedBuilder getUsage() {
        return null;
    }

    @Override
    public List<String> getExamples() {
        return null;
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("de", "downloademote");
    }

    @Override
    public long getCooldown() {
        return 0;
    }

    @Override
    public String getDocumentationUrl() {
        return null;
    }
}
