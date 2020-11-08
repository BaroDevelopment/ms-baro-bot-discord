<template>
  <div class="ml-5 mt-5 mr-5">
    <v-text-field
        ref="input"
        v-model="query"
        :placeholder="placeholder"
        :value="query"
        aria-label="Search"
        autocomplete="off"
        class="mt-1"
        color="secondary"
        dense
        outlined
        single-line
        spellcheck="false"
        @blur="focused = false"
        @focus="focused = true"
        @keyup.enter="go(focusIndex)"
        @keyup.up="onUp"
        @keyup.down="onDown"
    >
      <template v-slot:append>
        <v-icon color="primary">mdi-magnify</v-icon>
      </template>
    </v-text-field>
    <ul
        v-if="showSuggestions"
        :class="{ 'align-right': alignRight }"
        class="suggestions"
        @mouseleave="unfocus"
    >
      <li
          v-for="(s, i) in suggestions"
          :key="i"
          :class="{ focused: i === focusIndex }"
          class="suggestion"
          @mousedown="go(i)"
          @mouseenter="focus(i)"
      >
        <a :href="s.path" @click.prevent>
          <span class="page-title">{{ s.title || s.path }}</span>
          <span v-if="s.header" class="header">&gt; {{ s.header.title }}</span>
        </a>
      </li>
    </ul>
  </div>
</template>

<script>
/* global SEARCH_MAX_SUGGESTIONS, SEARCH_PATHS, SEARCH_HOTKEYS */
export default {
  name: "SearchBox",

  data() {
    return {
      query: "",
      focused: false,
      focusIndex: 0,
      placeholder: undefined
    };
  },

  computed: {
    showSuggestions() {
      return this.focused && this.suggestions && this.suggestions.length;
    },

    suggestions() {
      const query = this.query.trim().toLowerCase();
      if (!query) {
        return;
      }

      const {pages} = this.$site;
      const max =
          this.$site.themeConfig.searchMaxSuggestions || SEARCH_MAX_SUGGESTIONS;
      const localePath = this.$localePath;
      const matches = item =>
          item && item.title && item.title.toLowerCase().indexOf(query) > -1;
      const res = [];
      for (let i = 0; i < pages.length; i++) {
        if (res.length >= max) break;
        const p = pages[i];
        // filter out results that do not match current locale
        if (this.getPageLocalePath(p) !== localePath) {
          continue;
        }

        // filter out results that do not match searchable paths
        if (!this.isSearchable(p)) {
          continue;
        }

        if (matches(p)) {
          res.push(p);
        } else if (p.headers) {
          for (let j = 0; j < p.headers.length; j++) {
            if (res.length >= max) break;
            const h = p.headers[j];
            if (matches(h)) {
              res.push(
                  Object.assign({}, p, {
                    path: p.path + "#" + h.slug,
                    header: h
                  })
              );
            }
          }
        }
      }
      return res;
    },

    // make suggestions align right when there are not enough items
    alignRight() {
      const navCount = (this.$site.themeConfig.nav || []).length;
      const repo = this.$site.repo ? 1 : 0;
      return navCount + repo <= 2;
    }
  },

  mounted() {
    this.placeholder = this.$site.themeConfig.searchPlaceholder || "";
    document.addEventListener("keydown", this.onHotkey);
  },

  beforeDestroy() {
    document.removeEventListener("keydown", this.onHotkey);
  },

  methods: {
    getPageLocalePath(page) {
      for (const localePath in this.$site.locales || {}) {
        if (localePath !== "/" && page.path.indexOf(localePath) === 0) {
          return localePath;
        }
      }
      return "/";
    },

    isSearchable(page) {
      let searchPaths = SEARCH_PATHS;

      // all paths searchables
      if (searchPaths === null) {
        return true;
      }

      searchPaths = Array.isArray(searchPaths)
          ? searchPaths
          : new Array(searchPaths);

      return (
          searchPaths.filter(path => {
            return page.path.match(path);
          }).length > 0
      );
    },

    onHotkey(event) {
      if (
          event.srcElement === document.body &&
          SEARCH_HOTKEYS.includes(event.key)
      ) {
        this.$refs.input.focus();
        event.preventDefault();
      }
    },

    onUp() {
      if (this.showSuggestions) {
        if (this.focusIndex > 0) {
          this.focusIndex--;
        } else {
          this.focusIndex = this.suggestions.length - 1;
        }
      }
    },

    onDown() {
      if (this.showSuggestions) {
        if (this.focusIndex < this.suggestions.length - 1) {
          this.focusIndex++;
        } else {
          this.focusIndex = 0;
        }
      }
    },

    go(i) {
      if (!this.showSuggestions) {
        return;
      }
      this.$router.push(this.suggestions[i].path);
      this.query = "";
      this.focusIndex = 0;
    },

    focus(i) {
      this.focusIndex = i;
    },

    unfocus() {
      this.focusIndex = -1;
    }
  }
};
</script>

<style lang="stylus" scoped>

.suggestions
  background-color: var(--v-background-base)
  border-color: var(--v-secondary-base) !important
  z-index: 1
  width 20rem
  position absolute
  top 3rem
  border 1px solid darken($borderColor, 10%)
  border-radius 6px
  padding 0.4rem
  list-style-type none

  &.align-right
    right 0

.suggestion
  line-height 1.4
  padding 0.4rem 0.6rem
  border-radius 4px
  cursor pointer

  a
    white-space normal
    color var(--v-accent-base)

    .page-title
      font-weight 600

    .header
      font-size 0.9em
      margin-left 0.25em

  &.focused
    background-color: var(--v-primary-base)

    a
      color: white
</style>
