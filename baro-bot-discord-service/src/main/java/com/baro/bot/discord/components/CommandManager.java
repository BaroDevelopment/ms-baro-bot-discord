package com.baro.bot.discord.components;

import com.baro.bot.discord.commands.ACommand;
import com.baro.bot.discord.commands.CommandCategory;
import com.baro.bot.discord.commands.CommandContext;
import com.baro.bot.discord.commands.ICommand;
import com.baro.bot.discord.commands.admin.*;
import com.baro.bot.discord.commands.information.EmoteCmd;
import com.baro.bot.discord.commands.information.HelpCmd;
import com.baro.bot.discord.commands.information.InviteCmd;
import com.baro.bot.discord.commands.misc.PollCmd;
import com.baro.bot.discord.commands.moderation.BlacklistCmd;
import com.baro.bot.discord.commands.moderation.LockCmd;
import com.baro.bot.discord.commands.music.*;
import com.baro.bot.discord.commands.music.dj.*;
import com.baro.bot.discord.commands.owner.*;
import com.baro.bot.discord.commands.search.DictionaryCmd;
import com.baro.bot.discord.commands.search.GoogleCmd;
import com.baro.bot.discord.commands.search.UrbanCmd;
import com.baro.bot.discord.config.BotConfig;
import com.baro.bot.discord.config.FlagsConfig;
import com.baro.bot.discord.model.entities.CommandDisabledEntity;
import com.baro.bot.discord.model.entities.CommandDisabledEntityId;
import com.baro.bot.discord.model.entities.GuildEntity;
import com.baro.bot.discord.model.entities.MusicEntity;
import com.baro.bot.discord.repository.CommandDisabledRepository;
import com.baro.bot.discord.repository.GuildRepository;
import com.baro.bot.discord.repository.MusicRepository;
import com.baro.bot.discord.service.BaroBot;
import com.sun.istack.NotNull;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.util.*;
import java.util.regex.Pattern;

@Component
public class CommandManager extends ACommand {

    private final static Logger LOGGER = LoggerFactory.getLogger(CommandManager.class);
    private final Map<String, ICommand> commands;
    private final BaroBot bot;
    private final BotConfig botConfig;
    private final FlagsConfig flagsConfig;
    private final GuildRepository guildRepository;
    private final MusicRepository musicRepository;
    private final CommandDisabledRepository commandDisabledRepository;

    public CommandManager(BaroBot bot, BotConfig botConfig, FlagsConfig flagsConfig, GuildRepository guildRepository,
                          MusicRepository musicRepository, CommandDisabledRepository commandDisabledRepository) {
        this.bot = bot;
        this.botConfig = botConfig;
        this.flagsConfig = flagsConfig;
        this.guildRepository = guildRepository;
        this.musicRepository = musicRepository;
        this.commandDisabledRepository = commandDisabledRepository;
        this.commands = new HashMap();

        //ADMIN
        commands.put(PrefixCmd.COMMAND_NAME, new PrefixCmd(guildRepository, botConfig));
        commands.put(MusicDjRoleIdCmd.COMMAND_NAME, new MusicDjRoleIdCmd(musicRepository));
        commands.put(MusicTextChannelIdCmd.COMMAND_NAME, new MusicTextChannelIdCmd(musicRepository));
        commands.put(MusicVoiceChannelIdCmd.COMMAND_NAME, new MusicVoiceChannelIdCmd(musicRepository));
        commands.put(TicketCmd.COMMAND_NAME, new TicketCmd(flagsConfig));

        // INFORMATION
        commands.put(EmoteCmd.COMMAND_NAME, new EmoteCmd());
        commands.put(HelpCmd.COMMAND_NAME, new HelpCmd());
        commands.put(InviteCmd.COMMAND_NAME, new InviteCmd());

        // MUSIC
        commands.put(PlayCmd.COMMAND_NAME, new PlayCmd());
        commands.put(NowplayingCmd.COMMAND_NAME, new NowplayingCmd());
        commands.put(ShuffleCmd.COMMAND_NAME, new ShuffleCmd());
        commands.put(SkipCmd.COMMAND_NAME, new SkipCmd());
        commands.put(RemoveCmd.COMMAND_NAME, new RemoveCmd());
        commands.put(QueueCmd.COMMAND_NAME, new QueueCmd(bot, musicRepository));
        commands.put(PlayPlaylistCmd.COMMAND_NAME, new PlayPlaylistCmd());
        commands.put(PlaylistsCmd.COMMAND_NAME, new PlaylistsCmd());
        commands.put(SearchCmd.COMMAND_NAME, new SearchCmd(bot));
        commands.put(LyricsCmd.COMMAND_NAME, new LyricsCmd());

        // MUSIC with DJ PERMISSIONS
        commands.put(StopCmd.COMMAND_NAME, new StopCmd());
        commands.put(ForceRemoveCmd.COMMAND_NAME, new ForceRemoveCmd());
        commands.put(MoveTrackCmd.COMMAND_NAME, new MoveTrackCmd());
        commands.put(ForceskipCmd.COMMAND_NAME, new ForceskipCmd());
        commands.put(PauseCmd.COMMAND_NAME, new PauseCmd());
        commands.put(PlaynextCmd.COMMAND_NAME, new PlaynextCmd());
        commands.put(PlaylistRepeatCmd.COMMAND_NAME, new PlaylistRepeatCmd(musicRepository));
        commands.put(RepeatCmd.COMMAND_NAME, new RepeatCmd(musicRepository));
        commands.put(SeekCmd.COMMAND_NAME, new SeekCmd());
        commands.put(SkiptoCmd.COMMAND_NAME, new SkiptoCmd());
        commands.put(VolumeCmd.COMMAND_NAME, new VolumeCmd());

        // OWNER
        commands.put(EvalCmd.COMMAND_NAME, new EvalCmd());
        commands.put(DebugCmd.COMMAND_NAME, new DebugCmd(botConfig));
        commands.put(GuildListCmd.COMMAND_NAME, new GuildListCmd(bot));
        commands.put(PlaylistCmd.COMMAND_NAME, new PlaylistCmd());
        commands.put(ShutdownCmd.COMMAND_NAME, new ShutdownCmd());
        commands.put(SetstatusCmd.COMMAND_NAME, new SetstatusCmd());
        commands.put(SetnameCmd.COMMAND_NAME, new SetnameCmd());
        commands.put(SetgameCmd.COMMAND_NAME, new SetgameCmd());
        commands.put(SetavatarCmd.COMMAND_NAME, new SetavatarCmd());
        commands.put(DownloadEmoteCmd.COMMAND_NAME, new DownloadEmoteCmd());
        commands.put(LeaveServerCmd.COMMAND_NAME, new LeaveServerCmd());

        // MISC
        commands.put(PollCmd.COMMAND_NAME, new PollCmd());

        // MODERATION
        commands.put(LockCmd.COMMAND_NAME, new LockCmd());
        commands.put(BlacklistCmd.COMMAND_NAME, new BlacklistCmd(bot.getEventWaiter(), commandDisabledRepository));

        // search
        commands.put(GoogleCmd.COMMAND_NAME, new GoogleCmd(botConfig));
        commands.put(UrbanCmd.COMMAND_NAME, new UrbanCmd(bot));
        commands.put(DictionaryCmd.COMMAND_NAME, new DictionaryCmd(botConfig, bot));
    }

    public void handle(MessageReceivedEvent event) {

        String prefix = event.getChannelType() == ChannelType.PRIVATE ? botConfig.getPrefix() : getPrefix(event.getGuild().getIdLong());

        if (!event.getMessage().getContentRaw().startsWith(prefix)) {
            return;
        }

        String[] args = event.getMessage().getContentRaw()
                .replaceFirst("(?i)" + Pattern.quote(prefix), "")
                .split("\\s+");

        String invoke = args[0].toLowerCase();
        ICommand cmd = getCommand(invoke);

        if (cmd == null) return;

        CommandContext ctx = new CommandContext(bot, prefix, args, event, invoke, this);
        if (!argsProvided(ctx, cmd)) {
            event.getChannel().sendMessage("Please provide arguments").queue();
            return;
        }
        if (isGuildOnlyViolation(ctx, cmd)) {
            event.getChannel().sendMessage("This command is not available in private channels").queue();
            return;
        }
        if (isNsfwViolation(ctx, cmd)) {
            event.getChannel().sendMessage("This is a nsfw command. Please execute it in a nsfw channel.").queue();
            return;
        }
        if (!hasAllMemberPermissions(ctx, cmd)) {
            event.getChannel().sendMessage("You don't have needed permissions to run this command!" +
                    "\nPermissions needed:\n" + cmd.getMemberPermissions().toString()).queue();
            return;
        }
        if (!hasAllBotPermissions(ctx, cmd)) {
            event.getChannel().sendMessage("I don't have needed permissions to run this command!" +
                    "\nI need the following permissions:\n" + cmd.getBotPermissions().toString()).queue();
            return;
        }

        if (!hasOwnerPermissions(ctx, cmd)) return;

        // TODO - Cooldown

        try {
            isBlacklistViolation(ctx, cmd);
        } catch (IllegalArgumentException e) {
            return;
        }

        cmd.execute(ctx);
    }

    @Nullable
    public ICommand getCommand(String search) {
        String searchLower = search.toLowerCase();
        for (ICommand cmd : commands.values()) {
            if (cmd.getName().equalsIgnoreCase(searchLower) || cmd.getAliases().contains(searchLower)) {
                return cmd;
            }
        }
        return null;
    }

    private boolean hasOwnerPermissions(CommandContext ctx, ICommand cmd) {
        if (cmd.getCategory().equals(CommandCategory.OWNER) &&
                !botConfig.getBotOwnerIds().contains(ctx.getEvent().getAuthor().getId())) {
            ctx.getEvent().getChannel().sendMessage("Only a bot owner can execute this command!").queue();
            return false;
        }
        return true;
    }

    private boolean hasAllMemberPermissions(CommandContext ctx, ICommand cmd) {
        if (!ctx.getEvent().getChannel().getType().equals(ChannelType.TEXT)) {
            return true;
        }
        EnumSet<Permission> neededPerms = cmd.getMemberPermissions();
        EnumSet<Permission> memberPermissions = ctx.getEvent().getMember().getPermissions();

        if (neededPerms == null) return true;

        return memberPermissions.contains(Permission.ADMINISTRATOR) ||
                memberPermissions.containsAll(neededPerms) ||
                botConfig.getBotOwnerIds().contains(ctx.getEvent().getAuthor().getId());
    }

    private boolean hasAllBotPermissions(CommandContext ctx, ICommand cmd) {
        if (!ctx.getEvent().getChannel().getType().equals(ChannelType.TEXT)) {
            return true;
        }
        EnumSet<Permission> neededPermissions = cmd.getBotPermissions();
        EnumSet<Permission> botPermissions = ctx.getEvent().getGuild()
                .getMemberById(ctx.getEvent().getJDA().getSelfUser().getId()).getPermissions();

        if (neededPermissions == null) {
            return true;
        }

        return botPermissions.containsAll(neededPermissions);
    }

    private boolean argsProvided(CommandContext ctx, ICommand cmd) {
        return !ctx.getArgs().isEmpty() || !cmd.getArgs();
    }

    private boolean isGuildOnlyViolation(CommandContext ctx, ICommand cmd) {
        return !ctx.getEvent().getChannel().getType().equals(ChannelType.TEXT) && cmd.guildOnly();
    }

    private boolean isNsfwViolation(CommandContext ctx, ICommand cmd) {
        boolean notTextChannel = !ctx.getEvent().getChannel().getType().equals(ChannelType.TEXT);
        if (notTextChannel) return false;
        return !ctx.getEvent().getTextChannel().isNSFW() && cmd.isNsfw();
    }

    private void isBlacklistViolation(CommandContext ctx, ICommand cmd) {
        if (!ctx.getEvent().getChannelType().isGuild()) return;
        if (ctx.getEvent().getMember().getPermissions().contains(Permission.ADMINISTRATOR)) return;
        List<CommandDisabledEntity> cdes = commandDisabledRepository.findByCommandDisabledEntityIdServerIdAndCommandDisabledEntityIdName(ctx.getEvent().getGuild().getId(), cmd.getName());

        for (CommandDisabledEntity cde : cdes) {
            CommandDisabledEntityId cdei = cde.getCommandDisabledEntityId();
            if (cdei.getType().equals("server")) {
                String errorMsg = "This command is disabled in this server";
                sendError(ctx, errorMsg);
                throw new IllegalArgumentException(errorMsg);
            } else if (cdei.getType().equals("role")) {
                // check if role exists
                Role role = ctx.getEvent().getGuild().getRoleById(cdei.getId());
                if (role == null) {
                    commandDisabledRepository.delete(cde);
                } else {// check if user has role
                    for (Role r : ctx.getEvent().getMember().getRoles()) {
                        if (r.getId().equals(cdei.getId())) {
                            String errorMsg = "This command is disabled for this role";
                            sendError(ctx, errorMsg);
                            throw new IllegalArgumentException(errorMsg);
                        }
                    }
                }
            } else if (cdei.getType().equals("channel") && ctx.getEvent().getChannel().getId().equals(cdei.getId())) {
                String errorMsg = "This command is disabled for this channel";
                sendError(ctx, errorMsg);
                throw new IllegalArgumentException(errorMsg);
            }
        }
    }

    @NotNull
    public String getPrefix(Long serverId) {
        Optional<GuildEntity> settings = guildRepository.findById(serverId);
        if (settings.isPresent()) {
            return settings.get().getPrefix();
        }
        return botConfig.getPrefix();
    }

    @NotNull
    public String getMusicTextChannelId(Long serverId) {
        Optional<MusicEntity> settings = musicRepository.findById(serverId);
        return settings.map(MusicEntity::getTextChannelId).orElse(null);
    }

    @NotNull
    public String getMusicVoiceChannelId(Long serverId) {
        Optional<MusicEntity> settings = musicRepository.findById(serverId);
        return settings.map(MusicEntity::getVoiceChannelId).orElse(null);
    }

    @NotNull
    public String getDjRoleId(Long serverId) {
        Optional<MusicEntity> settings = musicRepository.findById(serverId);
        return settings.map(MusicEntity::getDjRoleId).orElse(null);
    }

    public Map<String, ICommand> getCommands() {
        return commands;
    }

    public BotConfig getBotConfig() {
        return botConfig;
    }

    public GuildRepository getGuildSettingsReository() {
        return guildRepository;
    }

    public MusicRepository getMusicSettingsRepository() {
        return musicRepository;
    }

    public CommandDisabledRepository getCommandDisabledRepository() {
        return commandDisabledRepository;
    }
}
