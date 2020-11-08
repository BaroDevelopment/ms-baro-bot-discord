<template>
  <div>
    <RouterLink
        v-if="isInternal"
        :exact="exact"
        :to="link"
        class="nav-link"
        @focusout.native="focusoutAction"
    >
      {{ item.text }}
    </RouterLink>
    <a
        v-else
        :href="link"
        :rel="rel"
        :target="target"
        class="nav-link external"
        @focusout="focusoutAction"
    >
      {{ item.text }}
      <OutboundLink v-if="isBlankTarget"/>
    </a>
  </div>
</template>

<script>
import {ensureExt, isExternal, isMailto, isTel} from "../util";

export default {
  name: "NavLink",

  props: {
    item: {
      required: true
    }
  },

  computed: {
    link() {
      return ensureExt(this.item.link);
    },

    exact() {
      if (this.$site.locales) {
        return Object.keys(this.$site.locales).some(
            rootLink => rootLink === this.link
        );
      }
      return this.link === "/";
    },

    isNonHttpURI() {
      return isMailto(this.link) || isTel(this.link);
    },

    isBlankTarget() {
      return this.target === "_blank";
    },

    isInternal() {
      return !isExternal(this.link) && !this.isBlankTarget;
    },

    target() {
      if (this.isNonHttpURI) {
        return null;
      }
      if (this.item.target) {
        return this.item.target;
      }
      return isExternal(this.link) ? "_blank" : "";
    },

    rel() {
      if (this.isNonHttpURI) {
        return null;
      }
      if (this.item.rel) {
        return this.item.rel;
      }
      return this.isBlankTarget ? "noopener noreferrer" : "";
    }
  },

  methods: {
    focusoutAction() {
      this.$emit("focusout");
    }
  }
};
</script>
