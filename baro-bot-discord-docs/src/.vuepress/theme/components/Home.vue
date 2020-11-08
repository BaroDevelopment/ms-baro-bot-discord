<template>
  <main aria-labelledby="main-title" class="home">
    <header class="hero">
      <img
          v-if="data.heroImage"
          :alt="data.heroAlt || 'hero'"
          :src="$withBase(data.heroImage)"
      />

      <h1 v-if="data.heroText !== null" id="main-title">
        {{ data.heroText || $title || "Hello" }}
      </h1>

      <p v-if="data.tagline !== null" class="description">
        {{ data.tagline || $description || "Welcome to your VuePress site" }}
      </p>

      <p v-if="data.actionText && data.actionLink" class="action">
        <NavLink :item="actionLink" class="action-button"/>
      </p>
    </header>

    <div v-if="data.features && data.features.length" class="features">
      <div
          v-for="(feature, index) in data.features"
          :key="index"
          class="feature"
      >
        <h2>{{ feature.title }}</h2>
        <p>{{ feature.details }}</p>
      </div>
    </div>

    <Content class="theme-default-content custom"/>

    <div v-if="data.footer" class="footer">
      {{ data.footer }}
    </div>
  </main>
</template>

<script>
import NavLink from "@theme/components/NavLink.vue";

export default {
  name: "Home",

  components: {NavLink},

  computed: {
    data() {
      return this.$page.frontmatter;
    },

    actionLink() {
      return {
        link: this.data.actionLink,
        text: this.data.actionText
      };
    }
  }
};
</script>

<style lang="stylus">
.home
padding $ navbarHeight

2
rem

0
max-width $ homePageWidth
margin

0
px auto
display block
.hero
text-align center
img
max-width:

100
%
max-height

280
px
display block
margin

3
rem auto

1.5
rem
h1
font-size

3
rem
h1, .description, .action
margin

1.8
rem auto
.description
max-width

35
rem
font-size

1.6
rem
line-height

1.3
color

var
(
--v-accent-base

)
.action-button
a
color #fff
display inline-block
font-size

1.2
rem
background-color

var
(
--v-primary-base

)
padding

0.8
rem

1.6
rem
border-radius

4
px
transition background-color

.1
s ease
box-sizing border-box
      & :hover
background-color

var
(
--v-secondary-base

)
.features
border-top

1
px solid

var
(
--v-secondary-base

)
padding

1.2
rem

0
margin-top

2.5
rem
display flex
flex-wrap wrap
align-items flex-start
align-content stretch
justify-content space-between
.feature
flex-grow

1
flex-basis

30
%
max-width

30
%
h2
font-size

1.4
rem
font-weight

500
border-bottom none
padding-bottom

0
color

var
(
--v-accent-base

)
p
color

var
(
--v-accent-base

)
.footer
padding

2.5
rem
border-top

1
px solid

var
(
--v-secondary-base

)
text-align center
color

var
(
--v-accent-base

)

@media (max-width: $ MQMobile)
  .home
  .features
  flex-direction column
  .feature
  max-width

100%
  padding

0 2.5rem

  @media (max-width: $ MQMobileNarrow)
    .home
    padding-left

  1.5rem
     padding-right

  1.5rem
     .hero
     img
     max-height

  210px
     margin

  2rem auto

  1.2rem
     h1
     font-size

  2rem
   h1, .description, .action
  margin

  1.2rem auto
     .description
     font-size

  1.2rem
     .action-button
     font-size

  1rem
   padding

  0.6rem

  1.2rem
     .feature
     h2
     font-size

  1.25rem
</style>
