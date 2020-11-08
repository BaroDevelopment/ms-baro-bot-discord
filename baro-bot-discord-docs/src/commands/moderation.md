# Moderation Commands

## Ban Command

<CommandTable command="ban"/>

::: danger
If you provide `user_name` as parameter and multiple users have the same name
then bot will target the first match and you might end up banning wrong user!
:::

::: tip
Multiple mentions are not working.
:::

::: caution
If DM is enabled then bot will send the banned user the same notification via DM
:::

## Kick Command

<CommandTable command="kick"/>

::: danger
If you provide `user_name` as parameter and multiple users have the same name
then bot will target the first match and you might end up kicking wrong user!
:::

::: tip
Multiple mentions are not working.
:::

::: caution
If DM is enabled then bot will send the kicked user the same notification via DM
:::

## Channeltopic Command

<CommandTable command="channelTopic"/>

> You can also use emotes and mention users, roles and channels in the topic.
>
> If no topic is given then the channel topic will be cleared
>
> ![Channel Topic Preview](https://cdn.discordapp.com/attachments/396964573007052800/494429935813787659/ChannelTopic.gif)

## Emote Command

<CommandTable command="emote"/>

::: caution Image Size
Max Size: is 256kb

Your emote should have at least 128x128 pixels and will be resized to 32x32 pixels once uploaded.
:::

## Purge Command

<CommandTable command="purge"/>

::: danger Discord restrictions
You can not delete more than 100 messages and messages posted 2 weeks ago or more.
This is not a restriction by the bot!
:::

#### Advanced Usage

```bash
-t [n]      => purge messages that got pasted n minutes ago
-b          => purge only messages from bots (if user mentioned this will be ignored)
-p          => delete also pinned messages
-n          => the ammount of messages you wanna delete
@user       => delete messages only from mentioned user
```

> Example
>
> purge from last 40 messages (including pinned ones) where
> the author is a bot, and the message is not older than 30 min
>
> **Those are all equivalent to each other**
>
> `-purge 100 -bpt 30`
>
> `-purge -n 100 -bpt 30`
>
> `-purge -n 100 -b -p -t 30`
>
> `-purge --amount=100 --bots --pinned --time=30`

> Purge only messages from Stalista#7777
> `-purge 100 @Stalista#7777`

## Mute Command

<CommandTable command="mute"/>

::: danger
If you provide `user_name` as parameter and multiple users have the same name
then bot will target the first match and you might end up muting wrong user!
:::

::: tip
Multiple mentions are not working.
:::

## Unmute Command

<CommandTable command="unmute"/>

::: danger
If you provide `user_name` as parameter and multiple users have the same name
then bot will target the first match and you might end up unmuting wrong user!
:::

::: tip
Multiple mentions are not working.
:::
