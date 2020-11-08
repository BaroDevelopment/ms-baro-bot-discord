<template>
  <v-card>
    <v-card-title>
      <v-text-field
          v-model="search"
          append-icon="mdi-magnify"
          hide-details
          label="Search"
          single-line
      ></v-text-field>
    </v-card-title>
    <v-data-table
        :headers="headers"
        :items="Object.values(items)"
        :items-per-page="itemsPerPage"
        :page.sync="page"
        :search="search"
        class="elevation-4 mb-4 mt-5"
        hide-default-footer
        @page-count="pageCount = $event"
        @click:row="goToCommand"
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
    <div class="text-center pt-2">
      <v-pagination
          v-model="page"
          :length="pageCount"
          color="primary"
      ></v-pagination>
    </div>
  </v-card>
</template>

<script>
import commands from "../../commands";

export default {
  name: "AllCommands",
  data() {
    return {
      page: 1,
      pageCount: 0,
      itemsPerPage: 10,
      search: "",
      headers: [
        {text: "Name", value: "name", align: "center", sortable: false},
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
  methods: {
    goToCommand(r) {
      this.$router.push(
          r.category + ".html#" + r.name.toLowerCase() + "-command"
      );
    }
  }
};
</script>

<style lang="stylus" scoped>
th, td
border

1
px solid red

!important
</style>
