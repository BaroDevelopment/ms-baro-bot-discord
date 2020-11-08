(window.webpackJsonp=window.webpackJsonp||[]).push([[16],{317:function(e,s){e.exports={help:{name:"Help",category:"information",description:"Get all available commands",args:!1,dm:!0,nsfw:!1,cooldown:5,aliases:!1,botPermissions:!1,memberPermissions:!1,usages:[{command:"-help",description:"displays all available commands"},{command:"-help <command_name>",description:"displays informations about provided command"}]},avatar:{name:"Avatar",category:"information",description:"Display a users avatar and link",args:!1,dm:!0,nsfw:!1,cooldown:5,aliases:!1,botPermissions:!1,memberPermissions:!1,usages:[{command:"-avatar",description:"displays your avatar"},{command:"-avatar <@user | user_id | user_name>",description:"displays avatar of provided user by mention, id or name"}]},userInfo:{name:"UserInfo",category:"information",description:"Get all informations from a user",args:!1,dm:!1,nsfw:!1,cooldown:5,aliases:["uinfo","whois"],botPermissions:!1,memberPermissions:!1,usages:[{command:"-userinfo",description:"displays your user discord informations"},{command:"-userinfo <@user | user_id | user_name>",description:"displays informations about provided user by mention, id or name"}]},serverInfo:{name:"ServerInfo",category:"information",description:"Display full information about a discord server",args:!1,dm:!1,nsfw:!1,cooldown:5,aliases:["sinfo"],botPermissions:!1,memberPermissions:!1,usages:[{command:"-serverinfo",description:"Display full information about current discord server"},{command:"-serverinfo <server_id | server_name>",description:"Display full information about a discord server using id or name"}]},channelInfo:{name:"ChannelInfo",category:"information",description:"Get an overview about a channel",args:!1,dm:!0,nsfw:!1,cooldown:5,aliases:["cinfo"],botPermissions:!1,memberPermissions:!1,usages:[{command:"-cinfo",description:"info about current channel"},{command:"-cinfo <#channel | channel_id | channel_name>",description:""}]},roleInfo:{name:"RoleInfo",category:"information",description:"Get an overview about a role",args:!0,dm:!1,nsfw:!1,cooldown:5,aliases:["rinfo"],botPermissions:!1,memberPermissions:!1,usages:[{command:"-roleinfo <@role | server_id | server_name>",description:""}]},emoteInfo:{name:"EmoteInfo",category:"information",description:"Get an overview about a specific emoji",args:!0,dm:!0,nsfw:!1,cooldown:5,aliases:["einfo"],botPermissions:!1,memberPermissions:!1,usages:[{command:"-emoteinfo <:emote: | emote_id | emote_name>",description:""}]},botInfo:{name:"BotInfo",category:"information",description:"Get an overview about BaroBot Stats",args:!0,dm:!0,nsfw:!1,cooldown:60,aliases:["binfo","info"],botPermissions:!1,memberPermissions:!1,usages:[{command:"-emoteinfo <:emote: | emote_id | emote_name>",description:""}]},shardInfo:{name:"ShardInfo",category:"information",description:"Get an overview about the shards",args:!1,dm:!0,nsfw:!1,cooldown:60,aliases:!1,botPermissions:!1,memberPermissions:!1,usages:!1},afk:{name:"Afk",category:"information",description:"Set your afk message",args:!1,dm:!0,nsfw:!1,cooldown:10,aliases:!1,botPermissions:!1,memberPermissions:!1,usages:[{command:"-afk <afk_message>",description:""}]},ping:{name:"Ping",category:"information",description:"Get Discord websocket and rest pings",args:!1,dm:!0,nsfw:!1,cooldown:60,aliases:!1,botPermissions:!1,memberPermissions:!1,usages:[{command:"-ping",description:""}]},uptime:{name:"Uptime",category:"information",description:"Displays the time the bot has been up.",args:!1,dm:!0,nsfw:!1,cooldown:60,aliases:!1,botPermissions:!1,memberPermissions:!1,usages:[{command:"-uptime",description:""}]},speedtest:{name:"Speedtest",category:"information",description:"Used to test the speed of the Bot/Host",args:!1,dm:!0,nsfw:!1,cooldown:60,aliases:!1,botPermissions:!1,memberPermissions:!1,usages:[{command:"-speedtest",description:""}]},mute:{name:"Mute",category:"moderation",description:"Prevents a member from sending any messages in your discord server",args:!0,dm:!1,nsfw:!1,cooldown:5,aliases:!1,botPermissions:["MANAGE_ROLES","MANAGE_CHANNELS"],memberPermissions:["MANAGE_ROLES"],usages:[{command:"-mute <@user | user_id | user_name>",description:"this is a permanent mute and requires manual unmute"},{command:"-mute <@user | user_id | user_name> -t <n>",description:"n is the mute time in minutes"}]},unmute:{name:"Unmute",category:"moderation",description:"Unmute a muted user",args:!0,dm:!1,nsfw:!1,cooldown:5,aliases:!1,botPermissions:["MANAGE_ROLES","MANAGE_CHANNELS"],memberPermissions:["MANAGE_ROLES"],usages:[{command:"-unmute <@user | user_id | user_name>",description:""}]},ban:{name:"Ban",category:"moderation",description:"Ban a member from the server.",args:!0,dm:!1,nsfw:!1,cooldown:5,aliases:!1,botPermissions:["BAN_MEMBERS"],memberPermissions:["BAN_MEMBERS"],usages:[{command:"-ban <@user | user_id | user_name> <reason>",description:"Reason is optional"},{command:"-ban <@user | user_id | user_name> <reason> -d <n>",description:"n is number of days of messages to delete. Default is 0 and max is 7"}]},kick:{name:"Kick",category:"moderation",description:"Kick a member from the server.",args:!0,dm:!1,nsfw:!1,cooldown:5,aliases:!1,botPermissions:["KICK_MEMBERS"],memberPermissions:["KICK_MEMBERS"],usages:[{command:"-kick <@user | user_id | user_name> <reason>",description:"Reason is optional"}]},channelTopic:{name:"ChannelTopic",category:"moderation",description:"Change the topic of a channel",args:!1,dm:!1,nsfw:!1,cooldown:5,aliases:!1,botPermissions:["MANAGE_CHANNELS"],memberPermissions:["MANAGE_CHANNELS"],usages:[{command:"-channeltopic <topic> -r <reason>",description:"Reason is optional and will be visible in the audit logs"}]},emote:{name:"Emote",category:"moderation",description:"Add emotes to your server",args:!0,dm:!1,nsfw:!1,cooldown:5,aliases:!1,botPermissions:["MANAGE_EMOJIS"],memberPermissions:["MANAGE_EMOJIS"],usages:[{command:"-emote <name> <url>",description:""}]},purge:{name:"Purge",category:"moderation",description:"Bulk-delete messages from a channel",args:!0,dm:!1,nsfw:!1,cooldown:20,aliases:["prune","delete","wipe"],botPermissions:["MANAGE_MESSAGES"],memberPermissions:["MANAGE_MESSAGES"],usages:[{command:"-purge <n>",description:"Bulk delete n messages"}]},google:{name:"Google",category:"search",description:"Google something",args:!0,dm:!0,nsfw:!1,cooldown:20,aliases:["g"],botPermissions:!1,memberPermissions:!1,usages:[{command:"-google <search_term>",description:"displays first google result in discord"}]},lmgify:{name:"LMGIFY",category:"search",description:"Act like a smartass and provide a googleitfor.me link with your search query.",args:!0,dm:!0,nsfw:!1,cooldown:20,aliases:["lmgtfy"],botPermissions:!1,memberPermissions:!1,usages:[{command:"-lmgify <search_term>",description:""}]},urban:{name:"Urban",category:"search",description:"Query the Urban Dictionary API",args:!0,dm:!0,nsfw:!0,cooldown:10,aliases:!1,botPermissions:!1,memberPermissions:!1,usages:[{command:"-urban <search_term>",description:""}]},dictionary:{name:"Dictionary",category:"search",description:"A oxford dictionary lookup to get the definition of a word",args:!0,dm:!0,nsfw:!1,cooldown:10,aliases:["dic","define"],botPermissions:!1,memberPermissions:!1,usages:[{command:"-define <search_term>",description:""}]},belike:{name:"Belike",category:"media",description:"Show others your coolness and send a Be-Like-Bill Meme",args:!1,dm:!0,nsfw:!1,cooldown:5,aliases:["bl"],botPermissions:!1,memberPermissions:!1,usages:[{command:"-belike <name>",description:"The name to show. If not given your name will be choosen"},{command:"-belike <name> -s <f | m>",description:"you can set the gender using -s f or -s m"}]},nsfw:{name:"Nsfw",category:"media",description:"Show nsfw image",args:!1,dm:!0,nsfw:!0,cooldown:5,aliases:["porngif"],botPermissions:!1,memberPermissions:!1,usages:[{command:"-nsfw",description:""},{command:"-porngif",description:""}]},embed:{name:"Embed",category:"misc",description:"Generate a beautiful and easy to create embed powered by [Embed-Visualizer](https://leovoel.github.io/embed-visualizer/)",args:!0,dm:!0,nsfw:!1,cooldown:60,aliases:["embedcode"],botPermissions:!1,memberPermissions:!1,usages:[{command:"-embed <text>",description:"send a normal text as embed"},{command:"-embed <embed_json>",description:"design your own embed"}]},tag:{name:"Tag",category:"misc",description:"Add/Remove/Display custom commands",args:!0,dm:!1,nsfw:!1,cooldown:5,aliases:["tags"],botPermissions:!1,memberPermissions:!1,usages:[{command:"-tags",description:"Displays all custom commands"},{command:"-tag add <name> <value>",description:"Add new custom command"},{command:"-tag remove <name>",description:"Remove a custom command"},{command:"-tag embed <name> -c <channel_id> -m <message_id>",description:"Add any embed message as custom command."}]},json:{name:"Json",category:"misc",description:"Get the json code of a MessageEmbed. Can be used for the Embed Visualizer/Generator",args:!0,dm:!0,nsfw:!1,cooldown:60,aliases:!1,botPermissions:!1,memberPermissions:!1,usages:[{command:"-json <message-id>",description:"Bot will search the message in the channel where the command got executed"},{command:"-json <channel_id> <message_id>",description:"Get json code of an embed that is in an other channel"}]},quote:{name:"Quote",category:"misc",description:"Quote a message",args:!0,dm:!1,nsfw:!1,cooldown:10,aliases:["q"],botPermissions:!1,memberPermissions:!1,usages:[{command:"-quote <message-id>",description:"Bot will search the message in the channel where the command got executed"},{command:"-quote <channel_id> <message_id>",description:"Get the message to quote by channel_id and message_id and post anywhere"}]},eightball:{name:"EightBall",category:"misc",description:"Ask the magic 8ball a special question!",args:!0,dm:!0,nsfw:!1,cooldown:10,aliases:["8ball"],botPermissions:!1,memberPermissions:!1,usages:[{command:"-8ball are bots scary?",description:""}]},randomReaction:{name:"RandomReaction",category:"misc",description:"Picks a random reacted user from a message",args:!0,dm:!1,nsfw:!1,cooldown:5,aliases:["rr"],botPermissions:!1,memberPermissions:!1,usages:[{command:"-randomreaction <msg_id>",description:"Get 1 random user from first message reaction"},{command:"-randomreaction <msg_id> -w 5",description:"Get 5 random users from first message reaction"}]},downloadEmotes:{name:"DownloadEmotes",category:"owner",description:"Download all emotes from all servers",args:!1,dm:!0,nsfw:!1,cooldown:0,aliases:["de","downloademote"],botPermissions:!1,memberPermissions:["BOT_OWNER"],usages:!1},eval:{name:"Eval",category:"owner",description:"Evaluate and execute Javascript code through Discord",args:!0,dm:!0,nsfw:!1,cooldown:0,aliases:!1,botPermissions:!1,memberPermissions:["BOT_OWNER"],usages:[{command:"-quote <javascript_code>",description:""}]},exec:{name:"Exec",category:"owner",description:"Evaluate and execute Javascript code through Discord",args:!0,dm:!0,nsfw:!1,cooldown:0,aliases:!1,botPermissions:!1,memberPermissions:["BOT_OWNER"],usages:[{command:"-exec <script>",description:""}]},leaveServer:{name:"LeaveServer",category:"owner",description:"Let the bot leave a Server",args:!0,dm:!0,nsfw:!1,cooldown:0,aliases:!1,botPermissions:!1,memberPermissions:["BOT_OWNER"],usages:[{command:"-leaveserver <guild_id>",description:""}]},setActivity:{name:"SetActivity",category:"owner",description:"Change the discord activity of the bot",args:!0,dm:!0,nsfw:!1,cooldown:0,aliases:!1,botPermissions:!1,memberPermissions:["BOT_OWNER"],usages:[{command:"-setActivity <activity> -t <PLAYING | STREAMING | LISTENING | WATCHING> -u <STREAM_URL>",description:"url and type are optional!"}]},setAvatar:{name:"SetAvatar",category:"owner",description:"Change the bot's avatar",args:!0,dm:!0,nsfw:!1,cooldown:0,aliases:!1,botPermissions:!1,memberPermissions:["BOT_OWNER"],usages:[{command:"-setAvatar <avatar_url>",description:""}]},setNickname:{name:"SetNickname",category:"owner",description:"Set bots nickanme",args:!0,dm:!0,nsfw:!1,cooldown:0,aliases:!1,botPermissions:!1,memberPermissions:["BOT_OWNER"],usages:[{command:"-setNickname <nickname> -r <reason>",description:"reason is optional"}]},setUsername:{name:"SetUsername",category:"owner",description:"Set bots username",args:!0,dm:!0,nsfw:!1,cooldown:0,aliases:!1,botPermissions:!1,memberPermissions:["BOT_OWNER"],usages:[{command:"-setUsername <username>",description:""}]},ssh:{name:"Ssh",category:"owner",description:"Execute command line commands on connected device using SSH. Mainly used to execute scripts like start, restart and update",args:!0,dm:!0,nsfw:!1,cooldown:0,aliases:!1,botPermissions:!1,memberPermissions:["BOT_OWNER"],usages:[{command:"-ssh <script>",description:""}]}}},383:function(e,s,o){"use strict";o.r(s);var n=o(317),i=o.n(n),a={name:"CommandTable",data:function(){return{headers:[{text:"Args",value:"args",align:"center",sortable:!1},{text:"DM",value:"dm",align:"center",sortable:!1},{text:"NSFW",value:"nsfw",align:"center",sortable:!1},{text:"Cooldown",value:"cooldown",align:"center",sortable:!1},{text:"Aliases",value:"aliases",align:"center",sortable:!1},{text:"BotPerms",value:"botPermissions",align:"center",sortable:!1},{text:"MemberPerms",value:"memberPermissions",align:"center",sortable:!1}],items:i.a}},props:["command"]},r=o(43),m=Object(r.a)(a,(function(){var e=this,s=e.$createElement,o=e._self._c||s;return o("div",[e.items[e.command].description?o("h4",[e._v("\n    "+e._s(e.items[e.command].description)+"\n  ")]):e._e(),e._v(" "),o("v-data-table",{staticClass:"elevation-4 mb-4 mt-5",attrs:{headers:e.headers,items:[e.items[e.command]],"hide-default-footer":""},scopedSlots:e._u([{key:"item.args",fn:function(s){var n=s.item;return[o("v-icon",{attrs:{color:n.args?"success":"error"}},[e._v(e._s(n.args?"mdi-check":"mdi-close")+"\n      ")])]}},{key:"item.cooldown",fn:function(s){var n=s.item;return[o("v-chip",{staticClass:"ma-2",attrs:{color:"warning",label:"",outlined:"","text-color":"accent"}},[e._v("\n        "+e._s(n.cooldown)+" seconds\n      ")])]}},{key:"item.dm",fn:function(s){var n=s.item;return[o("v-icon",{attrs:{color:n.dm?"success":"error"}},[e._v(e._s(n.dm?"mdi-check":"mdi-close")+"\n      ")])]}},{key:"item.nsfw",fn:function(s){var n=s.item;return[o("v-icon",{attrs:{color:n.nsfw?"success":"error"}},[e._v(e._s(n.nsfw?"mdi-check":"mdi-close")+"\n      ")])]}},{key:"item.aliases",fn:function(s){var n=s.item;return[!1===n.aliases?o("v-icon",{attrs:{color:"error"}},[e._v("\n        mdi-close\n      ")]):e._l(n.aliases,(function(s){return o("v-chip",{staticClass:"ma-1",attrs:{color:"warning",label:"",outlined:"","text-color":"accent"},domProps:{textContent:e._s(s)}})}))]}},{key:"item.botPermissions",fn:function(s){var n=s.item;return[!1===n.botPermissions?o("v-icon",{attrs:{color:"error"}},[e._v("\n        mdi-close\n      ")]):e._l(n.botPermissions,(function(s){return o("v-chip",{staticClass:"ma-1",attrs:{color:"warning",label:"",outlined:"","text-color":"accent"},domProps:{textContent:e._s(s)}})}))]}},{key:"item.memberPermissions",fn:function(s){var n=s.item;return[!1===n.memberPermissions?o("v-icon",{attrs:{color:"error"}},[e._v("\n        mdi-close\n      ")]):e._l(n.memberPermissions,(function(s){return o("v-chip",{staticClass:"ma-1",attrs:{color:"warning",label:"",outlined:"","text-color":"accent"},domProps:{textContent:e._s(s)}})}))]}}])}),e._v(" "),e._l(e.items[e.command].usages,(function(s){return e.items[e.command].usages?o("div",[s.command?o("v-chip",{staticClass:"ma-2 secondary",attrs:{label:"","text-color":"white"}},[e._v("\n      "+e._s(s.command)+"\n    ")]):e._e(),e._v("\n    "+e._s(s.description)+"\n  ")],1):e._e()}))],2)}),[],!1,null,"1eae0e39",null);s.default=m.exports}}]);