<template>
  <aside class="sidebar">
    <NavLinks/>
    <slot name="top"/>
    <SidebarLinks :depth="0" :items="items"/>
    <slot name="bottom"/>
  </aside>
</template>

<script>
import SidebarLinks from "@theme/components/SidebarLinks.vue";
import NavLinks from "@theme/components/NavLinks.vue";

export default {
  name: "Sidebar",
  components: {SidebarLinks, NavLinks},
  props: ["items"]
};
</script>

<style lang="stylus">

.theme--dark
.sidebar
font-size

16
px
background-color #2e2e2e
aside& .sidebar
font-size

16
px
width $ sidebarWidth
position fixed
margin

0
top

0
left

0
bottom

0
box-sizing border-box

/
/
border-right

1
px solid $ borderColor
overflow-y auto
.sidebar
ul
padding

0
margin

0
list-style-type none
a
display inline-block
.nav-links
display none
border-bottom

1
px solid

darken
(
$
borderColor,

70
%
)
padding

0.5
rem

0
0.75
rem

0
a
font-weight

600
.nav-item, .repo-link
display block
line-height

1.25
rem
font-size

1.1
em
padding

0.5
rem

0
0.5
rem

1.5
rem
    & > .sidebar-links
padding

1.5
rem

0
&
> li > a.sidebar-link
font-size

1.1
em
line-height

1.7
font-weight bold
        & > li:not(:first-child)
margin-top

.75
rem

@media (max-width: $ MQMobile)
  .sidebar
  .nav-links
  display block
  .dropdown-wrapper .nav-dropdown .dropdown-item a.router-link-active::after
  top

calc(1rem

- 2px

)
& > .sidebar-links
  padding

1rem

0
</style>
