package com.baro.bot.discord.commands.owner;

import com.baro.bot.discord.commands.ACommand;
import com.baro.bot.discord.commands.CommandCategory;
import com.baro.bot.discord.commands.CommandContext;
import com.baro.bot.discord.commands.ICommand;
import com.baro.bot.discord.config.BotConfig;
import com.jagrosh.jdautilities.commons.JDAUtilitiesInfo;
import com.sedmelluq.discord.lavaplayer.tools.PlayerLibrary;
import net.dv8tion.jda.api.JDAInfo;

public class DebugCmd extends ACommand implements ICommand {

    public static final String COMMAND_NAME = "debug";
    private final static String[] PROPERTIES = {"java.version", "java.vm.name", "java.vm.specification.version",
            "java.runtime.name", "java.runtime.version", "java.specification.version", "os.arch", "os.name"};
    private final BotConfig botConfig;

    public DebugCmd(BotConfig botConfig) {
        this.botConfig = botConfig;
    }

    @Override
    public void execute(CommandContext ctx) {
        StringBuilder sb = new StringBuilder();
        sb.append("```\nSystem Properties:");
        for (String key : PROPERTIES)
            sb.append("\n  ").append(key).append(" = ").append(System.getProperty(key));
        sb.append("\n\nJMusicBot Information:")
                .append("\n  Owner ID = ").append(String.join("\n", botConfig.getBotOwnerIds()))
                .append("\n  Prefix = ").append(botConfig.getPrefix());
        // TODO: FIX ME

//                .append("\n  NPImages = ").append(BotSettingsManager.getInstance().getSettings().isNpimages())
//                .append("\n  SongInStatus = ").append(BotSettingsManager.getInstance().getSettings().isTrackInStatus())
//                .append("\n  StayInChannel = ").append(BotSettingsManager.getInstance().getSettings().isAutoLeave());
        sb.append("\n\nDependency Information:")
                .append("\n  JDA Version = ").append(JDAInfo.VERSION)
                .append("\n  JDA-Utilities Version = ").append(JDAUtilitiesInfo.VERSION)
                .append("\n  Lavaplayer Version = ").append(PlayerLibrary.VERSION);
        long total = Runtime.getRuntime().totalMemory() / 1024 / 1024;
        long used = total - (Runtime.getRuntime().freeMemory() / 1024 / 1024);
        sb.append("\n\nRuntime Information:")
                .append("\n  Total Memory = ").append(total)
                .append("\n  Used Memory = ").append(used);
        sb.append("\n\nDiscord Information:")
                .append("\n  ID = ").append(ctx.getEvent().getJDA().getSelfUser().getId())
                .append("\n  Guilds = ").append(ctx.getEvent().getJDA().getGuildCache().size())
                .append("\n  Users = ").append(ctx.getEvent().getJDA().getUserCache().size());
        sb.append("\n```");

//        if (ctx.getEvent().isFromType(ChannelType.PRIVATE)
//                || ctx.getEvent().getGuild().getSelfMember().hasPermission(ctx.getEvent().getTextChannel(), Permission.MESSAGE_ATTACH_FILES))
        ctx.getEvent().getChannel().sendFile(sb.toString().getBytes(), "debug_information.txt").queue();
//        else
        ctx.getEvent().getChannel().sendMessage("Debug Information: " + sb.toString()).queue();
    }

    @Override
    public String getName() {
        return COMMAND_NAME;
    }

    @Override
    public String getDescription() {
        return "Generates a file or msg  containing debug information";
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
}
