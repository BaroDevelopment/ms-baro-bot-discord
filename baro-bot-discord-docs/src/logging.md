---
sidebar: auto
---

# BaroBot Logging
The bot can log several information in your desired channels.
>You have to write specific words in your channel topic to enable the logging.

![Setup](https://cdn.discordapp.com/attachments/688858069022932996/688858134965518355/unknown.png)

## User Logs
`BAROBOT_NAME_LOG` - logs user name changes\
`BAROBOT_DISCRIMINATOR_LOG` - logs discriminator changes\
`BAROBOT_AVATAR_LOG` - logs avatar changes\
`BAROBOT_STATUS_LOG` - logs status changes\
`BAROBOT_TYPING_LOG` - logs typing events\
`BAROBOT_ACTIVITY_LOG` - logs activity events (playing games, listening to spotify, watching movies and custom RPC)

## Server Message Logs
`BAROBOT_MESSAGE_DELETE_LOG` - will log deleted messages\
`BAROBOT_MESSAGE_EDIT_LOG` - will log message edits in your channel 

## Server Voice Logs
`BAROBOT_VOICE_JOIN_LOG` - logs voice join activities\
`BAROBOT_VOICE_LEAVE_LOG` - logs voice leave activities

## Text Channel Logs
`BAROBOT_TCHANNEL_LOG` - logs channel creations, deletes and updates (name, position, nsfw updates)\
`BAROBOT_CHANNEL_TOPIC_LOG` - logs channel topic changes

## Voice Channel Logs
`BAROBOT_VCHANNEL_LOG` - logs channel creations, deletes and updates (name, position, bitrate, user limit updates)

## Category Logs
> COMING SOON

## Server Logs
> COMING SOON

## Member Logs
`BAROBOT_SERVER_JOIN` - logs information about newly joined members \
`BAROBOT_SERVER_LEAVE` - logs information about newly left members

## Server Voice Logs
> COMING SOON

## Role logs
> COMING SOON

## Emote Logs
> COMING SOON

## Bot Logs

::: information
This section is only important for the bot owner
:::

### Self Events
TODO: 
```java
    //Self Events. Fires only in relation to the currently logged in account.
    public void onSelfUpdateAvatar(@Nonnull SelfUpdateAvatarEvent event) {}
    public void onSelfUpdateEmail(@Nonnull SelfUpdateEmailEvent event) {}
    public void onSelfUpdateMFA(@Nonnull SelfUpdateMFAEvent event) {}
    public void onSelfUpdateName(@Nonnull SelfUpdateNameEvent event) {}
    public void onSelfUpdateVerified(@Nonnull SelfUpdateVerifiedEvent event) {}

    //PrivateChannel Events
    public void onPrivateChannelCreate(@Nonnull PrivateChannelCreateEvent event) {}
    public void onPrivateChannelDelete(@Nonnull PrivateChannelDeleteEvent event) {}

    //Guild Events
    public void onGuildReady(@Nonnull GuildReadyEvent event) {}
    public void onGuildJoin(@Nonnull GuildJoinEvent event) {}
    public void onGuildLeave(@Nonnull GuildLeaveEvent event) {}
    public void onGuildAvailable(@Nonnull GuildAvailableEvent event) {}
    public void onGuildUnavailable(@Nonnull GuildUnavailableEvent event) {}
    public void onUnavailableGuildJoined(@Nonnull UnavailableGuildJoinedEvent event) {}
    public void onUnavailableGuildLeave(@Nonnull UnavailableGuildLeavText Channel LogseEvent event) {}
    public void onGuildBan(@Nonnull GuildBanEvent event) {}
    public void onGuildUnban(@Nonnull GuildUnbanEvent event) {}
```

> COMING SOON
