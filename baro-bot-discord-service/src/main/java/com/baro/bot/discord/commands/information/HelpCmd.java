package com.baro.bot.discord.commands.information;

import com.baro.bot.discord.commands.ACommand;
import com.baro.bot.discord.commands.CommandCategory;
import com.baro.bot.discord.commands.CommandContext;
import com.baro.bot.discord.commands.ICommand;
import com.baro.bot.discord.util.ColorUtil;
import com.baro.bot.discord.util.EmoteUtil;
import com.baro.bot.discord.util.Statics;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HelpCmd extends ACommand implements ICommand {

    public static final String COMMAND_NAME = "help";

    @Override
    public void execute(CommandContext ctx) {
        switch (ctx.getArgs()) {
            case "":
                handleHelpCommand(ctx);
                break;
            default:
                handleCommandDescription(ctx);
        }
    }

    private void handleCommandDescription(CommandContext ctx) {
        ICommand cmd = ctx.getCommandManager().getCommand(ctx.getArgs());
        if (cmd == null) {
            replayDM(ctx, "command not found");
            return;
        }
        User user = ctx.getEvent().getAuthor();
        ColorUtil colorUtil = new ColorUtil();
        EmoteUtil eu = new EmoteUtil(ctx.getBot());
        String enabled = eu.getEmojiAsString("on");
        String disabled = eu.getEmojiAsString("off");
        String category = cmd.getCategory().equals(CommandCategory.DJ)
                ? CommandCategory.MUSIC.toString()
                : cmd.getCategory().toString();
        EmbedBuilder eb = new EmbedBuilder()
                .setImage(Statics.LOADING_RAINBOW)
                .setColor(colorUtil.getRandomColor())
                .setAuthor(cmd.getName().toUpperCase() + " COMMAND", null, user.getEffectiveAvatarUrl())
                .setDescription(cmd.getDescription())
                .addField("args", cmd.getArgs() ? enabled : disabled, true)
                .addField("DM", !cmd.guildOnly() ? enabled : disabled, true)
                .addField("Nsfw", cmd.isNsfw() ? enabled : disabled, true)
                .addField("Cooldown", cmd.getCooldown() + " seconds", true)
                .addField("Category", category, true)
                .addBlankField(true);
        if (cmd.getBotPermissions() != null && !cmd.getBotPermissions().isEmpty()) {
            eb.addField(eu.getEmojiAsString("botTag") + " Needed BOT Permissions",
                    StringUtils.join(cmd.getBotPermissions().toArray(), "\n"), true);
        }
        if (cmd.getMemberPermissions() != null && !cmd.getMemberPermissions().isEmpty()) {
            eb.addField(eu.getEmojiAsString("member") + " Needed Member Permissions",
                    StringUtils.join(cmd.getMemberPermissions().toArray(), "\n"), true);
        }
        if (!cmd.getAliases().isEmpty()) {
            eb.addField("Aliases", StringUtils.join(cmd.getAliases(), "\n"), true);
        }
        if (!cmd.getExamples().isEmpty()) {
            eb.addField("Examples", StringUtils.join(cmd.getExamples(), "\n"), false);
        }
        replayDM(ctx, eb);
    }

    private void handleHelpCommand(CommandContext ctx) {
        ColorUtil colorUtil = new ColorUtil();
        EmbedBuilder eb = new EmbedBuilder().setColor(colorUtil.getRandomColor())
                .setAuthor("Help Command", null, ctx.getEvent().getAuthor().getEffectiveAvatarUrl())
                .setDescription("List of all Paladin commands");
        Map<String, ICommand> commands = ctx.getCommandManager().getCommands();
        List<ICommand> infoCmds = commands.values().stream()
                .filter(cmd -> cmd.getCategory().equals(CommandCategory.INFORMATION))
                .collect(Collectors.toList());

        List<ICommand> musicCmds = commands.values().stream()
                .filter(cmd -> cmd.getCategory().equals(CommandCategory.MUSIC))
                .collect(Collectors.toList());

        List<ICommand> djCmds = commands.values().stream()
                .filter(cmd -> cmd.getCategory().equals(CommandCategory.DJ))
                .collect(Collectors.toList());

        List<ICommand> ownerCmds = commands.values().stream()
                .filter(cmd -> cmd.getCategory().equals(CommandCategory.OWNER))
                .collect(Collectors.toList());

        List<ICommand> adminCmds = commands.values().stream()
                .filter(cmd -> cmd.getCategory().equals(CommandCategory.ADMIN))
                .collect(Collectors.toList());

        List<ICommand> searchCmds = commands.values().stream()
                .filter(cmd -> cmd.getCategory().equals(CommandCategory.SEARCH))
                .collect(Collectors.toList());

        String ownerValue = "```css\n";
        String musicValue = "Everyone can use those\n```css\n";
        String djValue = "Admin or DJ role needed\n```css\n";
        String infoValue = "```css\n";
        String adminValue = "```css\n";
        String searchValue = "```css\n";

        for (int i = 0; i < ownerCmds.size(); i++) {
            if (i % 3 == 2) {
                ownerValue += make25Chars(ctx.getPrefix() + ownerCmds.get(i).getName()).toLowerCase() + "\n";
            } else {
                ownerValue += make25Chars(ctx.getPrefix() + ownerCmds.get(i).getName()).toLowerCase();
            }
        }
        for (int i = 0; i < musicCmds.size(); i++) {
            if (i % 3 == 2) {
                musicValue += make25Chars(ctx.getPrefix() + musicCmds.get(i).getName()).toLowerCase() + "\n";
            } else {
                musicValue += make25Chars(ctx.getPrefix() + musicCmds.get(i).getName()).toLowerCase();
            }
        }
        for (int i = 0; i < djCmds.size(); i++) {
            if (i % 3 == 2) {
                djValue += make25Chars(ctx.getPrefix() + djCmds.get(i).getName()).toLowerCase() + "\n";
            } else {
                djValue += make25Chars(ctx.getPrefix() + djCmds.get(i).getName()).toLowerCase();
            }
        }
        for (int i = 0; i < infoCmds.size(); i++) {
            if (i % 3 == 2) {
                infoValue += make25Chars(ctx.getPrefix() + infoCmds.get(i).getName()).toLowerCase() + "\n";
            } else {
                infoValue += make25Chars(ctx.getPrefix() + infoCmds.get(i).getName()).toLowerCase();
            }
        }
        for (int i = 0; i < adminCmds.size(); i++) {
            if (i % 3 == 2) {
                adminValue += make25Chars(ctx.getPrefix() + adminCmds.get(i).getName()).toLowerCase() + "\n";
            } else {
                adminValue += make25Chars(ctx.getPrefix() + adminCmds.get(i).getName()).toLowerCase();
            }
        }
        for (int i = 0; i < searchCmds.size(); i++) {
            if (i % 3 == 2) {
                searchValue += make25Chars(ctx.getPrefix() + searchCmds.get(i).getName()).toLowerCase() + "\n";
            } else {
                searchValue += make25Chars(ctx.getPrefix() + searchCmds.get(i).getName()).toLowerCase();
            }
        }
        ownerValue += "\n```";
        musicValue += "\n```";
        djValue += "\n```";
        infoValue += "\n```";
        adminValue += "\n```";
        searchValue += "\n```";

        eb.addField("BOT OWNER COMMANDS", ownerValue, false)
                .addField("ADMIN COMMANDS", adminValue, false)
                .addField("MUSIC COMMANDS", musicValue + djValue, false)
                .addField("INFO COMMANDS", infoValue, false)
                .addField("SEARCH COMMANDS", searchValue, false)
                .addBlankField(false)
                .addField("How to get the description of all those commands ?", ctx.getPrefix() + "help <command>", false)
                .addField("EXAMPLE", ctx.getPrefix() + "help play", false)
                .addBlankField(false);
        replayDM(ctx, eb);
    }

    private String make25Chars(String s) {
        String resul = s;
        while (resul.length() < 15) {
            resul += " ";
        }
        return resul;
    }

    @Override
    public String getName() {
        return COMMAND_NAME;
    }

    @Override
    public String getDescription() {
        return "Gives an overview about all commands";
    }

    @Override
    public CommandCategory getCategory() {
        return CommandCategory.INFORMATION;
    }

    @Override
    public boolean guildOnly() {
        return false;
    }
}
