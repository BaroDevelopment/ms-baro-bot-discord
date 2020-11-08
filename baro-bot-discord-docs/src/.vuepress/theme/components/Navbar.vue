<template>
  <div>
    <v-navigation-drawer
        v-if="$frontmatter.sidebar !== false && !$frontmatter.home"
        v-model="drawer"
        app
        clipped
        max-width="300"
    >
      <v-row align-content="end" class="fill-height" no-gutters>
        <v-col cols="12">
          <Sidebar :items="sidebarItems" style="width: 100%">
            <template #top>
              <slot name="sidebar-top"/>
            </template>
            <template #bottom>
              <slot name="sidebar-bottom"/>
            </template>
          </Sidebar>
        </v-col>
        <v-col>
          <v-btn block color="background" @click="themeChanger = !themeChanger">
            Theme
            <v-icon right small>fas fa-cog</v-icon>
          </v-btn>
        </v-col>
      </v-row>
    </v-navigation-drawer>
    <v-navigation-drawer
        v-model="themeChanger"
        app
        clipped
        max-width="300"
        right
    >
      <v-card class="mx-auto fill-height">
        <v-card-title class="justify-center">
          Theme Configurator
        </v-card-title>
        <v-row justify="center" no-gutters>
          <v-switch v-model="$vuetify.theme.dark" label="Dark Mode"/>
          <v-expansion-panels class="ma-2">
            <v-expansion-panel>
              <v-expansion-panel-header>
                Primary
                <template v-slot:actions>
                  <v-icon color="primary" small>$expand</v-icon>
                </template>
              </v-expansion-panel-header>
              <v-expansion-panel-content>
                <v-color-picker
                    v-if="$vuetify.theme.dark"
                    v-model="$vuetify.theme.themes.dark.primary"
                    class="ma-2"
                    show-swatches
                ></v-color-picker>
                <v-color-picker
                    v-else
                    v-model="$vuetify.theme.themes.light.primary"
                    class="ma-2"
                    show-swatches
                ></v-color-picker>
              </v-expansion-panel-content>
            </v-expansion-panel>
            <v-expansion-panel>
              <v-expansion-panel-header>
                Secondary
                <template v-slot:actions>
                  <v-icon color="primary" small>$expand</v-icon>
                </template>
              </v-expansion-panel-header>
              <v-expansion-panel-content>
                <v-color-picker
                    v-if="$vuetify.theme.dark"
                    v-model="$vuetify.theme.themes.dark.secondary"
                    class="ma-2"
                    show-swatches
                ></v-color-picker>
                <v-color-picker
                    v-else
                    v-model="$vuetify.theme.themes.light.secondary"
                    class="ma-2"
                    show-swatches
                ></v-color-picker>
              </v-expansion-panel-content>
            </v-expansion-panel>
            <v-expansion-panel>
              <v-expansion-panel-header>
                Text Color
                <template v-slot:actions>
                  <v-icon color="primary" small>$expand</v-icon>
                </template>
              </v-expansion-panel-header>
              <v-expansion-panel-content>
                <v-color-picker
                    v-if="$vuetify.theme.dark"
                    v-model="$vuetify.theme.themes.dark.accent"
                    class="ma-2"
                    show-swatches
                ></v-color-picker>
                <v-color-picker
                    v-else
                    v-model="$vuetify.theme.themes.light.accent"
                    class="ma-2"
                    show-swatches
                ></v-color-picker>
              </v-expansion-panel-content>
            </v-expansion-panel>
            <v-expansion-panel>
              <v-expansion-panel-header>
                Anchor
                <template v-slot:actions>
                  <v-icon color="primary" small>$expand</v-icon>
                </template>
              </v-expansion-panel-header>
              <v-expansion-panel-content>
                <v-color-picker
                    v-if="$vuetify.theme.dark"
                    v-model="$vuetify.theme.themes.dark.anchor"
                    class="ma-2"
                    show-swatches
                ></v-color-picker>
                <v-color-picker
                    v-else
                    v-model="$vuetify.theme.themes.light.anchor"
                    class="ma-2"
                    show-swatches
                ></v-color-picker>
              </v-expansion-panel-content>
            </v-expansion-panel>
          </v-expansion-panels>
        </v-row>
      </v-card>
    </v-navigation-drawer>
    <v-app-bar app clipped-left clipped-right>
      <v-avatar
          v-if="$frontmatter.sidebar !== false && !$frontmatter.home"
          size="27"
          @click.stop="drawer = !drawer"
      >
        <v-icon>
          mdi-menu
        </v-icon>
      </v-avatar>
      <v-avatar v-if="$site.themeConfig.logo" class="mr-3 ml-4" size="35">
        <v-img :src="$withBase($site.themeConfig.logo)"/>
      </v-avatar>
      <RouterLink :to="$localePath" style="text-decoration: none">
        <v-toolbar-title v-if="$siteTitle" class="hidden-xs-only docsTitle">
          {{ $siteTitle }}
        </v-toolbar-title>
      </RouterLink>
      <v-spacer/>
      <AlgoliaSearchBox v-if="isAlgoliaSearch" :options="algolia"/>
      <SearchBox
          v-else-if="
          $site.themeConfig.search !== false &&
            $page.frontmatter.search !== false
        "
      />
      <NavLinks class="can-hide"/>
    </v-app-bar>
  </div>
</template>

<script>
import AlgoliaSearchBox from "@AlgoliaSearchBox";
import SearchBox from "@theme/components/SearchBox";
import NavLinks from "@theme/components/NavLinks.vue";
import Sidebar from "./Sidebar";

export default {
  name: "Navbar",

  components: {
    Sidebar,
    NavLinks,
    SearchBox,
    AlgoliaSearchBox
  },
  props: ["sidebarItems"],

  data() {
    return {
      drawer: true,
      themeChanger: false,
      linksWrapMaxWidth: null
    };
  },

  computed: {
    algolia() {
      return (
          this.$themeLocaleConfig.algolia || this.$site.themeConfig.algolia || {}
      );
    },

    isAlgoliaSearch() {
      return this.algolia && this.algolia.apiKey && this.algolia.indexName;
    }
  },

  mounted() {
    const MOBILE_DESKTOP_BREAKPOINT = 719; // refer to config.styl
    const NAVBAR_VERTICAL_PADDING =
        parseInt(css(this.$el, "paddingLeft")) +
        parseInt(css(this.$el, "paddingRight"));
    const handleLinksWrapWidth = () => {
      if (document.documentElement.clientWidth < MOBILE_DESKTOP_BREAKPOINT) {
        this.linksWrapMaxWidth = null;
      } else {
        this.linksWrapMaxWidth =
            this.$el.offsetWidth -
            NAVBAR_VERTICAL_PADDING -
            ((this.$refs.siteName && this.$refs.siteName.offsetWidth) || 0);
      }
    };
    handleLinksWrapWidth();
    window.addEventListener("resize", handleLinksWrapWidth, false);
  }
};

function css(el, property) {
  // NOTE: Known bug, will return 'auto' if style value is 'auto'
  const win = el.ownerDocument.defaultView;
  // null means not to return pseudo styles
  return win.getComputedStyle(el, null)[property];
}
</script>

<style lang="stylus" scoped>
.docsTitle
  color var(--v-accent-base)

@media (max-width: $MQMobile)
  .can-hide
    display none
</style>
