<template>
  <section
      :class="[{ collapsable, 'is-sub-group': depth !== 0 }, `depth-${depth}`]"
      class="sidebar-group"
  >
    <RouterLink
        v-if="item.path"
        :class="{ open, active: isActive($route, item.path) }"
        :to="item.path"
        class="sidebar-heading clickable"
        @click.native="$emit('toggle')"
    >
      <span>{{ item.title }}</span>
      <v-icon v-if="collapsable" small>
        {{ open ? "mdi-chevron-right" : "mdi-chevron-down" }}
      </v-icon>
    </RouterLink>

    <p
        v-else
        :class="{ open }"
        class="sidebar-heading"
        @click="$emit('toggle')"
    >
      <span>{{ item.title }}</span>
      <v-icon v-if="collapsable" small>
        {{ open ? "mdi-chevron-right" : "mdi-chevron-down" }}
      </v-icon>
    </p>

    <DropdownTransition>
      <SidebarLinks
          v-if="open || !collapsable"
          :depth="depth + 1"
          :items="item.children"
          :sidebar-depth="item.sidebarDepth"
          class="sidebar-group-items"
      />
    </DropdownTransition>
  </section>
</template>

<script>
import {isActive} from "../util";
import DropdownTransition from "@theme/components/DropdownTransition.vue";

export default {
  name: "SidebarGroup",

  components: {
    DropdownTransition
  },

  props: ["item", "open", "collapsable", "depth"],

  // ref: https://vuejs.org/v2/guide/components-edge-cases.html#Circular-References-Between-Components
  beforeCreate() {
    this.$options.components.SidebarLinks = require("@theme/components/SidebarLinks.vue").default;
  },

  methods: {isActive}
};
</script>

<style lang="stylus">
.sidebar-group
.sidebar-group
padding-left

0.5
em

    & :not(.collapsable)
.sidebar-heading:not(.clickable)
cursor auto
color inherit

/
/
refine styles of nested sidebar groups

    & .is-sub-group
padding-left

0

&
> .sidebar-heading
font-size

0.95
em
line-height

1.4
font-weight normal
padding-left

2
rem

            & :not(.clickable)
opacity

0.5

&
> .sidebar-group-items
padding-left

1
rem

            & > li > .sidebar-link
font-size:

0.95
em

;
border-left none

    & .depth-2

&
> .sidebar-heading
border-left none
.sidebar-heading
color

var
(
--v-accent

)
!important
cursor pointer
font-size

1
em
font-weight bold

/
/
text-transform uppercase
padding

0.35
rem

1.5
rem

0.35
rem

1.25
rem
width

100
%
box-sizing border-box
margin

0
border-left

0.25
rem solid transparent
display flex
align-items center
justify-content flex-start
box-icon
max-width

20
px
margin-bottom

-
4
px
margin-left

4
px

        & .open
box-icon
transform

rotate
(
90
deg

)

.arrow
position relative
top

-
0.12
em
left

0.5
em

        & .clickable

&
.active
font-weight

600
color $ accentColor
border-left-color $ accentColor

            & :hover
color $ accentColor
.sidebar-group-items
transition height

.1
s ease-out
font-size

0.95
em
overflow hidden
</style>
