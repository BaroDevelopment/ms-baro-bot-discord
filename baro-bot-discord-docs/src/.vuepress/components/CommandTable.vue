<template>
  <div>
    <h4 v-if="items[command].description">
      {{ items[command].description }}
    </h4>
    <v-data-table
        :headers="headers"
        :items="[items[command]]"
        class="elevation-4 mb-4 mt-5"
        hide-default-footer
    >
      <template v-slot:item.args="{ item }">
        <v-icon :color="item.args ? 'success' : 'error'">{{
            item.args ? "mdi-check" : "mdi-close"
          }}
        </v-icon>
      </template>
      <template v-slot:item.cooldown="{ item }">
        <v-chip class="ma-2" color="warning" label outlined text-color="accent">
          {{ item.cooldown }} seconds
        </v-chip>
      </template>
      <template v-slot:item.dm="{ item }">
        <v-icon :color="item.dm ? 'success' : 'error'">{{
            item.dm ? "mdi-check" : "mdi-close"
          }}
        </v-icon>
      </template>
      <template v-slot:item.nsfw="{ item }">
        <v-icon :color="item.nsfw ? 'success' : 'error'">{{
            item.nsfw ? "mdi-check" : "mdi-close"
          }}
        </v-icon>
      </template>
      <template v-slot:item.aliases="{ item }">
        <v-icon v-if="item.aliases === false" color="error">
          mdi-close
        </v-icon>
        <v-chip
            v-for="alias in item.aliases"
            v-else
            class="ma-1"
            color="warning"
            label
            outlined
            text-color="accent"
            v-text="alias"
        />
      </template>
      <template v-slot:item.botPermissions="{ item }">
        <v-icon v-if="item.botPermissions === false" color="error">
          mdi-close
        </v-icon>
        <v-chip
            v-for="perm in item.botPermissions"
            v-else
            class="ma-1"
            color="warning"
            label
            outlined
            text-color="accent"
            v-text="perm"
        />
      </template>
      <template v-slot:item.memberPermissions="{ item }">
        <v-icon v-if="item.memberPermissions === false" color="error">
          mdi-close
        </v-icon>
        <v-chip
            v-for="perm in item.memberPermissions"
            v-else
            class="ma-1"
            color="warning"
            label
            outlined
            text-color="accent"
            v-text="perm"
        />
      </template>
    </v-data-table>
    <div v-for="usage in items[command].usages" v-if="items[command].usages">
      <v-chip
          v-if="usage.command"
          class="ma-2 secondary"
          label
          text-color="white"
      >
        {{ usage.command }}
      </v-chip>
      {{ usage.description }}
    </div>
  </div>
</template>

<script>
import commands from "../../commands";

export default {
  name: "CommandTable",
  data() {
    return {
      headers: [
        {text: "Args", value: "args", align: "center", sortable: false},
        {text: "DM", value: "dm", align: "center", sortable: false},
        {text: "NSFW", value: "nsfw", align: "center", sortable: false},
        {
          text: "Cooldown",
          value: "cooldown",
          align: "center",
          sortable: false
        },
        {text: "Aliases", value: "aliases", align: "center", sortable: false},
        {
          text: "BotPerms",
          value: "botPermissions",
          align: "center",
          sortable: false
        },
        {
          text: "MemberPerms",
          value: "memberPermissions",
          align: "center",
          sortable: false
        }
      ],
      items: commands
    };
  },
  props: ["command"]
};
</script>

<style lang="stylus" scoped>

</style>
