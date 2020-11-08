package com.baro.bot.discord.commands.admin;

import com.baro.bot.discord.commands.ACommand;
import com.baro.bot.discord.commands.CommandCategory;
import com.baro.bot.discord.commands.CommandContext;
import com.baro.bot.discord.commands.ICommand;
import com.baro.bot.discord.config.FlagsConfig;
import com.baro.bot.discord.service.BaroBot;
import com.baro.bot.discord.util.ColorUtil;
import com.baro.bot.discord.util.EmoteUtil;
import com.baro.bot.discord.util.Flags;
import com.jagrosh.jdautilities.commons.utils.FinderUtil;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class TicketCmd extends ACommand implements ICommand {

    public static final String COMMAND_NAME = "ticket";
    private final FlagsConfig flagsConfig;

    public TicketCmd(FlagsConfig flagsConfig) {
        this.flagsConfig = flagsConfig;
    }

    public static void handleTickets(BaroBot bot, GuildMessageReactionAddEvent event) {
        if (!event.getChannel().getType().equals(ChannelType.TEXT)) return;
        // handleTicket
        if (event.getChannel().getTopic() != null
                && event.getChannel().getTopic().toUpperCase().contains(Flags.BAROBOT_TICKET_CHANNEL.toString())
                && event.getReactionEmote().getName().equals("paladin")
                && !event.getUser().isBot()
                && FinderUtil.findTextChannels("ticket-" + event.getUser().getName(), event.getGuild()).isEmpty()) {
            try {
                event.getChannel().getParent()
                        .createTextChannel("ticket-" + event.getUser().getName()).queue(channel -> {
                    channel.getManager()
                            .putPermissionOverride(event.getMember(), Collections.singleton(Permission.VIEW_CHANNEL), null)
                            .putPermissionOverride(event.getGuild().getPublicRole(), null, Collections.singleton(Permission.VIEW_CHANNEL)).queue();
                    EmbedBuilder eb = new EmbedBuilder().setColor(new ColorUtil().getRandomColor());
                    eb.setDescription("Ticket opened by " + event.getUser().getAsMention());
                    channel.sendMessage(eb.build()).queue(message -> {
                        // Delete channel if creator does not chat within 1 minute
                        bot.getEventWaiter().waitForEvent(
                                GuildMessageReceivedEvent.class,
                                (e) -> e.getChannel().getIdLong() == channel.getIdLong() && e.getAuthor().getIdLong() == event.getUser().getIdLong(),
                                (e) -> {
                                    // don't delete the ticket channel
                                },
                                5, TimeUnit.MINUTES,
                                () -> {
                                    channel.delete().queue();
                                }
                        );
                    });
                    // remove reaction
                    event.getChannel().retrieveMessageById(event.getMessageIdLong()).queue(message -> message.removeReaction(event.getReactionEmote().getEmote(), event.getUser()).queue());
                });
            } catch (Exception ex) {
                event.getChannel().sendMessage("Failed to create a ticket channel for you.\nMake sure this channel is inside the Tickets Category").queue();
            }
        }
    }

    private void createTicket(CommandContext ctx) {
        ctx.getEvent().getGuild().createCategory("Tickets").queue(
                category -> category.createTextChannel(COMMAND_NAME).queue(
                        channel -> {
                            sendSuccess(ctx, "Your new ticket channel is " + channel.getAsMention());
                            channel.getManager().setTopic(flagsConfig.getTicket()).setSlowmode(channel.MAX_SLOWMODE).queue();
                            Role everyone = ctx.getEvent().getGuild().getPublicRole();
                            List<Permission> deny = new ArrayList<>();
                            deny.add(Permission.MESSAGE_ADD_REACTION);
                            deny.add(Permission.MESSAGE_WRITE);
                            channel.getManager().putPermissionOverride(everyone, null, deny).queue();
                            channel.sendMessage(getTicketEmbed(ctx.getBot().getJda().getSelfUser().getName(),
                                    ctx.getBot().getJda().getSelfUser().getEffectiveAvatarUrl()).build()).queue(
                                    message -> message.addReaction(new EmoteUtil(ctx.getBot()).getEmote("paladin")).queue());
                        }));
    }

    private void deleteTicket(CommandContext ctx) {
        if (ctx.getEvent().getChannel().getName().toLowerCase().contains("ticket-")) {
            try {
                ctx.getEvent().getTextChannel().delete().queue();
            } catch (Exception ex) {
                // channel already deleted - ignore
            }
        } else {
            sendError(ctx, "This is not a ticket channel");
        }
    }

    private EmbedBuilder getTicketEmbed(String name, String avatar) {
        ColorUtil colorUtil = new ColorUtil();
        return new EmbedBuilder().setColor(colorUtil.getRandomColor())
                .setDescription("```http\nReact to open a ticket!\n```")
                .setImage("https://cdn.discordapp.com/attachments/396964573007052800/547342790904774671/Loading.gif")
                .setAuthor(name + " Ticket System", avatar, avatar);
    }

    @Override
    public void execute(CommandContext ctx) {
        switch (ctx.getArgs().toLowerCase()) {
            case "delete":
            case "remove":
            case "close":
                deleteTicket(ctx);
                break;
            case "create":
            case "add":
            case "make":
                createTicket(ctx);
                break;
            default:
                sendError(ctx, "First argument must be **create** or **close**");
        }
    }

    @Override
    public String getName() {
        return COMMAND_NAME;
    }

    @Override
    public String getDescription() {
        return "Enable or disable ticket system in your server";
    }

    @Override
    public boolean getArgs() {
        return true;
    }

    @Override
    public boolean guildOnly() {
        return true;
    }

    @Override
    public boolean isNsfw() {
        return false;
    }

    @Nullable
    @Override
    public EnumSet<Permission> getMemberPermissions() {
        return EnumSet.of(Permission.ADMINISTRATOR);
    }

    @Nullable
    @Override
    public EnumSet<Permission> getBotPermissions() {
        return EnumSet.of(Permission.MANAGE_CHANNEL);
    }

    @Override
    public CommandCategory getCategory() {
        return CommandCategory.ADMIN;
    }

    @Override
    public List<String> getExamples() {
        List<String> samples = new ArrayList<>();
        samples.add("`ticket create` - creates ticket channel");
        samples.add("`ticket close` - closes an existing user ticket/channel");
        return samples;
    }
}
