# Owner Commands

## DownloadEmotes Command

<CommandTable command="downloadEmotes"/>

## Eval Command

<CommandTable command="eval"/>

#### Examples

> Calculate

```js
-eval 5 + 6
 -eval Math.pow(5, 8)
```

> Display all servers the bot is in (only from 1 shard)

```js
-eval message.client.guilds.map(g=>g.name).join('\n')
```

> Leave server with id: 285532310466461697

```js
-eval message.client.shard.broadcastEval('if(this.guilds.get(\'285532310466461697\'))this.guilds.get(\'285532310466461697\').leave()')
```

## Exec Command

<CommandTable command="exec"/>

::: information
The starting point/ base of the command is where the `index.js` file is.
:::

> This command works like the SSH COMMAND.
> It is executing a given command on the cli the device is running on.

## LeaveServer Command

<CommandTable command="leaveServer"/>

## SetActivity Command

<CommandTable command="setActivity"/>

Available types:

- PLAYING **(default)**
- STREAMING
- LISTENING
- WATCHING

#### Example

`-setActivity i am borred -t WATCHING`

`-setActivity check me out -t STREAMING -u https://www.twitch.tv/misselion`

## SetAvatar Command

<CommandTable command="setAvatar"/>

::: caution
Make sure you pass a valid image url as an argument!
:::

## SetNickname Command

<CommandTable command="setNickname"/>

## SetUsername Command

<CommandTable command="setUsername"/>

## SSH Command

<CommandTable command="ssh"/>

::: tip
The starting point/ base of the command is `/root/`.
:::

> This command works like the EXEC COMMAND.
> It is executing a given command on the cli the device is running on.

#### Examples

```bash
-ssh sudo apt-get update                            => Update Linux host
-ssh ls                                             => check content in /root
-ssh cd BaroBot && ls                               => check content in BaroBot folder
-ssh cd /root/BaroBot/BaroBotTS/ && sh start.sh     => start BaroBot
-ssh cd /root/BaroBot/BaroBotTS/ && sh restart.sh   => restart BaroBot
-ssh cd /root/BaroBot/BaroBotTS/ && sh update.sh    => Update BaroBot
```

::: tip How to stop the bot

```bash
-ssh tmux kill-session -t BaroBotJava         => Shutdown BaroBotJava
```

:::

For more informations about PM2 click [here](https://pm2.keymetrics.io/)
