<template>
  <div>
    <v-divider/>
    <v-row v-if="prev || next" class="page-nav">
      <v-col v-if="prev" cols="12" lg="6" md="6" xl="6">
        <a
            v-if="prev.type === 'external'"
            :href="prev.path"
            rel="noopener noreferrer"
            target="_blank"
        >
          <v-btn block class="accentColor" outlined x-large>
            {{ prev.title || prev.path }}
            <OutboundLink/>
          </v-btn>
        </a>
        <RouterLink v-else :to="prev.path">
          <v-btn block class="accentColor" outlined x-large>
            <v-icon right x-large>mdi-chevron-left</v-icon>
            <v-subheader class="justify-end subtitle-2">Previous</v-subheader>
            <v-spacer/>
            {{ prev.title || prev.path }}
          </v-btn>
        </RouterLink>
      </v-col>
      <v-col v-if="next" cols="12" lg="6" md="6" xl="6">
        <a
            v-if="next.type === 'external'"
            :href="next.path"
            rel="noopener noreferrer"
            target="_blank"
        >
          <v-btn block class="accentColor" outlined x-large>
            {{ next.title || next.path }}
            <OutboundLink/>
          </v-btn>
        </a>
        <RouterLink v-else :to="next.path">
          <v-btn block class="accentColor" outlined x-large>
            {{ next.title || next.path }}
            <v-spacer/>
            <v-subheader class="justify-end subtitle-2">Next</v-subheader>
            <v-icon large right>mdi-chevron-right</v-icon>
          </v-btn>
        </RouterLink>
      </v-col>
    </v-row>
  </div>
</template>

<script>
import {resolvePage} from "../util";
import isString from "lodash/isString";
import isNil from "lodash/isNil";

export default {
  name: "PageNav",

  props: ["sidebarItems"],

  computed: {
    prev() {
      return resolvePageLink(LINK_TYPES.PREV, this);
    },

    next() {
      return resolvePageLink(LINK_TYPES.NEXT, this);
    }
  }
};

function resolvePrev(page, items) {
  return find(page, items, -1);
}

function resolveNext(page, items) {
  return find(page, items, 1);
}

const LINK_TYPES = {
  NEXT: {
    resolveLink: resolveNext,
    getThemeLinkConfig: ({nextLinks}) => nextLinks,
    getPageLinkConfig: ({frontmatter}) => frontmatter.next
  },
  PREV: {
    resolveLink: resolvePrev,
    getThemeLinkConfig: ({prevLinks}) => prevLinks,
    getPageLinkConfig: ({frontmatter}) => frontmatter.prev
  }
};

function resolvePageLink(
    linkType,
    {$themeConfig, $page, $route, $site, sidebarItems}
) {
  const {resolveLink, getThemeLinkConfig, getPageLinkConfig} = linkType;

  // Get link config from theme
  const themeLinkConfig = getThemeLinkConfig($themeConfig);

  // Get link config from current page
  const pageLinkConfig = getPageLinkConfig($page);

  // Page link config will overwrite global theme link config if defined
  const link = isNil(pageLinkConfig) ? themeLinkConfig : pageLinkConfig;

  if (link === false) {

  } else if (isString(link)) {
    return resolvePage($site.pages, link, $route.path);
  } else {
    return resolveLink($page, sidebarItems);
  }
}

function find(page, items, offset) {
  const res = [];
  flatten(items, res);
  for (let i = 0; i < res.length; i++) {
    const cur = res[i];
    if (cur.type === "page" && cur.path === decodeURIComponent(page.path)) {
      return res[i + offset];
    }
  }
}

function flatten(items, res) {
  for (let i = 0, l = items.length; i < l; i++) {
    if (items[i].type === "group") {
      flatten(items[i].children || [], res);
    } else {
      res.push(items[i]);
    }
  }
}
</script>

<style lang="stylus" scoped>
.accentColor
  border-color var(--v-primary-base)
</style>
