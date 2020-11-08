module.exports = {
    help: {
        name: "Help",
        category: "information",
        description: "Get all available commands",
        args: false,
        dm: true,
        nsfw: false,
        cooldown: 5,
        aliases: false,
        botPermissions: false,
        memberPermissions: false,
        usages: [
            {
                command: "-help",
                description: "displays all available commands"
            },
            {
                command: "-help <command_name>",
                description: "displays informations about provided command"
            }
        ]
    },
    avatar: {
        name: "Avatar",
        category: "information",
        description: "Display a users avatar and link",
        args: false,
        dm: true,
        nsfw: false,
        cooldown: 5,
        aliases: false,
        botPermissions: false,
        memberPermissions: false,
        usages: [
            {
                command: "-avatar",
                description: "displays your avatar"
            },
            {
                command: "-avatar <@user | user_id | user_name>",
                description: "displays avatar of provided user by mention, id or name"
            }
        ]
    },
    userInfo: {
        name: "UserInfo",
        category: "information",
        description: "Get all informations from a user",
        args: false,
        dm: false,
        nsfw: false,
        cooldown: 5,
        aliases: ["uinfo", "whois"],
        botPermissions: false,
        memberPermissions: false,
        usages: [
            {
                command: "-userinfo",
                description: "displays your user discord informations"
            },
            {
                command: "-userinfo <@user | user_id | user_name>",
                description:
                    "displays informations about provided user by mention, id or name"
            }
        ]
    },
    serverInfo: {
        name: "ServerInfo",
        category: "information",
        description: "Display full information about a discord server",
        args: false,
        dm: false,
        nsfw: false,
        cooldown: 5,
        aliases: ["sinfo"],
        botPermissions: false,
        memberPermissions: false,
        usages: [
            {
                command: "-serverinfo",
                description: "Display full information about current discord server"
            },
            {
                command: "-serverinfo <server_id | server_name>",
                description:
                    "Display full information about a discord server using id or name"
            }
        ]
    },
    channelInfo: {
        name: "ChannelInfo",
        category: "information",
        description: "Get an overview about a channel",
        args: false,
        dm: true,
        nsfw: false,
        cooldown: 5,
        aliases: ["cinfo"],
        botPermissions: false,
        memberPermissions: false,
        usages: [
            {
                command: "-cinfo",
                description: "info about current channel"
            },
            {
                command: "-cinfo <#channel | channel_id | channel_name>",
                description: ""
            }
        ]
    },
    roleInfo: {
        name: "RoleInfo",
        category: "information",
        description: "Get an overview about a role",
        args: true,
        dm: false,
        nsfw: false,
        cooldown: 5,
        aliases: ["rinfo"],
        botPermissions: false,
        memberPermissions: false,
        usages: [
            {
                command: "-roleinfo <@role | server_id | server_name>",
                description: ""
            }
        ]
    },
    emoteInfo: {
        name: "EmoteInfo",
        category: "information",
        description: "Get an overview about a specific emoji",
        args: true,
        dm: true,
        nsfw: false,
        cooldown: 5,
        aliases: ["einfo"],
        botPermissions: false,
        memberPermissions: false,
        usages: [
            {
                command: "-emoteinfo <:emote: | emote_id | emote_name>",
                description: ""
            }
        ]
    },
    botInfo: {
        name: "BotInfo",
        category: "information",
        description: "Get an overview about BaroBot Stats",
        args: true,
        dm: true,
        nsfw: false,
        cooldown: 60,
        aliases: ["binfo", "info"],
        botPermissions: false,
        memberPermissions: false,
        usages: [
            {
                command: "-emoteinfo <:emote: | emote_id | emote_name>",
                description: ""
            }
        ]
    },
    shardInfo: {
        name: "ShardInfo",
        category: "information",
        description: "Get an overview about the shards",
        args: false,
        dm: true,
        nsfw: false,
        cooldown: 60,
        aliases: false,
        botPermissions: false,
        memberPermissions: false,
        usages: false
    },
    afk: {
        name: "Afk",
        category: "information",
        description: "Set your afk message",
        args: false,
        dm: true,
        nsfw: false,
        cooldown: 10,
        aliases: false,
        botPermissions: false,
        memberPermissions: false,
        usages: [
            {
                command: "-afk <afk_message>",
                description: ""
            }
        ]
    },
    ping: {
        name: "Ping",
        category: "information",
        description: "Get Discord websocket and rest pings",
        args: false,
        dm: true,
        nsfw: false,
        cooldown: 60,
        aliases: false,
        botPermissions: false,
        memberPermissions: false,
        usages: [
            {
                command: "-ping",
                description: ""
            }
        ]
    },
    uptime: {
        name: "Uptime",
        category: "information",
        description: "Displays the time the bot has been up.",
        args: false,
        dm: true,
        nsfw: false,
        cooldown: 60,
        aliases: false,
        botPermissions: false,
        memberPermissions: false,
        usages: [
            {
                command: "-uptime",
                description: ""
            }
        ]
    },
    speedtest: {
        name: "Speedtest",
        category: "information",
        description: "Used to test the speed of the Bot/Host",
        args: false,
        dm: true,
        nsfw: false,
        cooldown: 60,
        aliases: false,
        botPermissions: false,
        memberPermissions: false,
        usages: [
            {
                command: "-speedtest",
                description: ""
            }
        ]
    },
    mute: {
        name: "Mute",
        category: "moderation",
        description:
            "Prevents a member from sending any messages in your discord server",
        args: true,
        dm: false,
        nsfw: false,
        cooldown: 5,
        aliases: false,
        botPermissions: ["MANAGE_ROLES", "MANAGE_CHANNELS"],
        memberPermissions: ["MANAGE_ROLES"],
        usages: [
            {
                command: "-mute <@user | user_id | user_name>",
                description: "this is a permanent mute and requires manual unmute"
            },
            {
                command: "-mute <@user | user_id | user_name> -t <n>",
                description: "n is the mute time in minutes"
            }
        ]
    },
    unmute: {
        name: "Unmute",
        category: "moderation",
        description: "Unmute a muted user",
        args: true,
        dm: false,
        nsfw: false,
        cooldown: 5,
        aliases: false,
        botPermissions: ["MANAGE_ROLES", "MANAGE_CHANNELS"],
        memberPermissions: ["MANAGE_ROLES"],
        usages: [
            {
                command: "-unmute <@user | user_id | user_name>",
                description: ""
            }
        ]
    },
    ban: {
        name: "Ban",
        category: "moderation",
        description: "Ban a member from the server.",
        args: true,
        dm: false,
        nsfw: false,
        cooldown: 5,
        aliases: false,
        botPermissions: ["BAN_MEMBERS"],
        memberPermissions: ["BAN_MEMBERS"],
        usages: [
            {
                command: "-ban <@user | user_id | user_name> <reason>",
                description: "Reason is optional"
            },
            {
                command: "-ban <@user | user_id | user_name> <reason> -d <n>",
                description:
                    "n is number of days of messages to delete. Default is 0 and max is 7"
            }
        ]
    },
    kick: {
        name: "Kick",
        category: "moderation",
        description: "Kick a member from the server.",
        args: true,
        dm: false,
        nsfw: false,
        cooldown: 5,
        aliases: false,
        botPermissions: ["KICK_MEMBERS"],
        memberPermissions: ["KICK_MEMBERS"],
        usages: [
            {
                command: "-kick <@user | user_id | user_name> <reason>",
                description: "Reason is optional"
            }
        ]
    },
    channelTopic: {
        name: "ChannelTopic",
        category: "moderation",
        description: "Change the topic of a channel",
        args: false,
        dm: false,
        nsfw: false,
        cooldown: 5,
        aliases: false,
        botPermissions: ["MANAGE_CHANNELS"],
        memberPermissions: ["MANAGE_CHANNELS"],
        usages: [
            {
                command: "-channeltopic <topic> -r <reason>",
                description: "Reason is optional and will be visible in the audit logs"
            }
        ]
    },
    emote: {
        name: "Emote",
        category: "moderation",
        description: "Add emotes to your server",
        args: true,
        dm: false,
        nsfw: false,
        cooldown: 5,
        aliases: false,
        botPermissions: ["MANAGE_EMOJIS"],
        memberPermissions: ["MANAGE_EMOJIS"],
        usages: [
            {
                command: "-emote <name> <url>",
                description: ""
            }
        ]
    },
    purge: {
        name: "Purge",
        category: "moderation",
        description: "Bulk-delete messages from a channel",
        args: true,
        dm: false,
        nsfw: false,
        cooldown: 20,
        aliases: ["prune", "delete", "wipe"],
        botPermissions: ["MANAGE_MESSAGES"],
        memberPermissions: ["MANAGE_MESSAGES"],
        usages: [
            {
                command: "-purge <n>",
                description: "Bulk delete n messages"
            }
        ]
    },
    google: {
        name: "Google",
        category: "search",
        description: "Google something",
        args: true,
        dm: true,
        nsfw: false,
        cooldown: 20,
        aliases: ["g"],
        botPermissions: false,
        memberPermissions: false,
        usages: [
            {
                command: "-google <search_term>",
                description: "displays first google result in discord"
            }
        ]
    },
    lmgify: {
        name: "LMGIFY",
        category: "search",
        description:
            "Act like a smartass and provide a googleitfor.me link with your search query.",
        args: true,
        dm: true,
        nsfw: false,
        cooldown: 20,
        aliases: ["lmgtfy"],
        botPermissions: false,
        memberPermissions: false,
        usages: [
            {
                command: "-lmgify <search_term>",
                description: ""
            }
        ]
    },
    urban: {
        name: "Urban",
        category: "search",
        description: "Query the Urban Dictionary API",
        args: true,
        dm: true,
        nsfw: true,
        cooldown: 10,
        aliases: false,
        botPermissions: false,
        memberPermissions: false,
        usages: [
            {
                command: "-urban <search_term>",
                description: ""
            }
        ]
    },
    dictionary: {
        name: "Dictionary",
        category: "search",
        description: "A oxford dictionary lookup to get the definition of a word",
        args: true,
        dm: true,
        nsfw: false,
        cooldown: 10,
        aliases: ["dic", "define"],
        botPermissions: false,
        memberPermissions: false,
        usages: [
            {
                command: "-define <search_term>",
                description: ""
            }
        ]
    },
    belike: {
        name: "Belike",
        category: "media",
        description: "Show others your coolness and send a Be-Like-Bill Meme",
        args: false,
        dm: true,
        nsfw: false,
        cooldown: 5,
        aliases: ["bl"],
        botPermissions: false,
        memberPermissions: false,
        usages: [
            {
                command: "-belike <name>",
                description: "The name to show. If not given your name will be choosen"
            },
            {
                command: "-belike <name> -s <f | m>",
                description: "you can set the gender using -s f or -s m"
            }
        ]
    },
    nsfw: {
        name: "Nsfw",
        category: "media",
        description: "Show nsfw image",
        args: false,
        dm: true,
        nsfw: true,
        cooldown: 5,
        aliases: ["porngif"],
        botPermissions: false,
        memberPermissions: false,
        usages: [
            {
                command: "-nsfw",
                description: ""
            },
            {
                command: "-porngif",
                description: ""
            }
        ]
    },
    embed: {
        name: "Embed",
        category: "misc",
        description:
            "Generate a beautiful and easy to create embed powered by [Embed-Visualizer](https://leovoel.github.io/embed-visualizer/)",
        args: true,
        dm: true,
        nsfw: false,
        cooldown: 60,
        aliases: ["embedcode"],
        botPermissions: false,
        memberPermissions: false,
        usages: [
            {
                command: "-embed <text>",
                description: "send a normal text as embed"
            },
            {
                command: "-embed <embed_json>",
                description: "design your own embed"
            }
        ]
    },
    tag: {
        name: "Tag",
        category: "misc",
        description:
            "Add/Remove/Display custom commands",
        args: true,
        dm: false,
        nsfw: false,
        cooldown: 5,
        aliases: ["tags"],
        botPermissions: false,
        memberPermissions: false,
        usages: [
            {
                command: "-tags",
                description: "Displays all custom commands"
            },
            {
                command: "-tag add <name> <value>",
                description: "Add new custom command"
            },
            {
                command: "-tag remove <name>",
                description: "Remove a custom command"
            },
            {
                command: "-tag embed <name> -c <channel_id> -m <message_id>",
                description: "Add any embed message as custom command."
            }
        ]
    },
    json: {
        name: "Json",
        category: "misc",
        description:
            "Get the json code of a MessageEmbed. Can be used for the Embed Visualizer/Generator",
        args: true,
        dm: true,
        nsfw: false,
        cooldown: 60,
        aliases: false,
        botPermissions: false,
        memberPermissions: false,
        usages: [
            {
                command: "-json <message-id>",
                description:
                    "Bot will search the message in the channel where the command got executed"
            },
            {
                command: "-json <channel_id> <message_id>",
                description: "Get json code of an embed that is in an other channel"
            }
        ]
    },
    quote: {
        name: "Quote",
        category: "misc",
        description: "Quote a message",
        args: true,
        dm: false,
        nsfw: false,
        cooldown: 10,
        aliases: ["q"],
        botPermissions: false,
        memberPermissions: false,
        usages: [
            {
                command: "-quote <message-id>",
                description:
                    "Bot will search the message in the channel where the command got executed"
            },
            {
                command: "-quote <channel_id> <message_id>",
                description:
                    "Get the message to quote by channel_id and message_id and post anywhere"
            }
        ]
    },
    eightball: {
        name: "EightBall",
        category: "misc",
        description: "Ask the magic 8ball a special question!",
        args: true,
        dm: true,
        nsfw: false,
        cooldown: 10,
        aliases: ['8ball'],
        botPermissions: false,
        memberPermissions: false,
        usages: [
            {
                command: "-8ball are bots scary?",
                description: ""
            }
        ]
    },
    randomReaction: {
        name: "RandomReaction",
        category: "misc",
        description: "Picks a random reacted user from a message",
        args: true,
        dm: false,
        nsfw: false,
        cooldown: 5,
        aliases: ['rr'],
        botPermissions: false,
        memberPermissions: false,
        usages: [
            {
                command: "-randomreaction <msg_id>",
                description: "Get 1 random user from first message reaction"
            },
            {
                command: "-randomreaction <msg_id> -w 5",
                description: "Get 5 random users from first message reaction"
            }
        ]
    },
    downloadEmotes: {
        name: "DownloadEmotes",
        category: "owner",
        description: "Download all emotes from all servers",
        args: false,
        dm: true,
        nsfw: false,
        cooldown: 0,
        aliases: ["de", "downloademote"],
        botPermissions: false,
        memberPermissions: ["BOT_OWNER"],
        usages: false
    },
    eval: {
        name: "Eval",
        category: "owner",
        description: "Evaluate and execute Javascript code through Discord",
        args: true,
        dm: true,
        nsfw: false,
        cooldown: 0,
        aliases: false,
        botPermissions: false,
        memberPermissions: ["BOT_OWNER"],
        usages: [
            {
                command: "-quote <javascript_code>",
                description: ""
            }
        ]
    },
    exec: {
        name: "Exec",
        category: "owner",
        description: "Evaluate and execute Javascript code through Discord",
        args: true,
        dm: true,
        nsfw: false,
        cooldown: 0,
        aliases: false,
        botPermissions: false,
        memberPermissions: ["BOT_OWNER"],
        usages: [
            {
                command: "-exec <script>",
                description: ""
            }
        ]
    },
    leaveServer: {
        name: "LeaveServer",
        category: "owner",
        description: "Let the bot leave a Server",
        args: true,
        dm: true,
        nsfw: false,
        cooldown: 0,
        aliases: false,
        botPermissions: false,
        memberPermissions: ["BOT_OWNER"],
        usages: [
            {
                command: "-leaveserver <guild_id>",
                description: ""
            }
        ]
    },
    setActivity: {
        name: "SetActivity",
        category: "owner",
        description: "Change the discord activity of the bot",
        args: true,
        dm: true,
        nsfw: false,
        cooldown: 0,
        aliases: false,
        botPermissions: false,
        memberPermissions: ["BOT_OWNER"],
        usages: [
            {
                command:
                    "-setActivity <activity> -t <PLAYING | STREAMING | LISTENING | WATCHING> -u <STREAM_URL>",
                description: "url and type are optional!"
            }
        ]
    },
    setAvatar: {
        name: "SetAvatar",
        category: "owner",
        description: "Change the bot's avatar",
        args: true,
        dm: true,
        nsfw: false,
        cooldown: 0,
        aliases: false,
        botPermissions: false,
        memberPermissions: ["BOT_OWNER"],
        usages: [
            {
                command: "-setAvatar <avatar_url>",
                description: ""
            }
        ]
    },
    setNickname: {
        name: "SetNickname",
        category: "owner",
        description: "Set bots nickanme",
        args: true,
        dm: true,
        nsfw: false,
        cooldown: 0,
        aliases: false,
        botPermissions: false,
        memberPermissions: ["BOT_OWNER"],
        usages: [
            {
                command: "-setNickname <nickname> -r <reason>",
                description: "reason is optional"
            }
        ]
    },
    setUsername: {
        name: "SetUsername",
        category: "owner",
        description: "Set bots username",
        args: true,
        dm: true,
        nsfw: false,
        cooldown: 0,
        aliases: false,
        botPermissions: false,
        memberPermissions: ["BOT_OWNER"],
        usages: [
            {
                command: "-setUsername <username>",
                description: ""
            }
        ]
    },
    ssh: {
        name: "Ssh",
        category: "owner",
        description:
            "Execute command line commands on connected device using SSH. Mainly used to execute scripts like start, restart and update",
        args: true,
        dm: true,
        nsfw: false,
        cooldown: 0,
        aliases: false,
        botPermissions: false,
        memberPermissions: ["BOT_OWNER"],
        usages: [
            {
                command: "-ssh <script>",
                description: ""
            }
        ]
    }
};
