<template>
  <form id="search-form" class="algolia-search-wrapper">
    <v-text-field
        id="algolia-search-input"
        :placeholder="placeholder"
        class="search-query mt-5 mr-5"
        color="secondary"
        dense
        outlined
        single-line
    >
      <template v-slot:append>
        <v-icon color="primary">mdi-magnify</v-icon>
      </template>
    </v-text-field>
  </form>
</template>

<script>
export default {
  name: "AlgoliaSearchBox",

  props: ["options"],

  data() {
    return {
      placeholder: undefined
    };
  },

  watch: {
    $lang(newValue) {
      this.update(this.options, newValue);
    },

    options(newValue) {
      this.update(newValue, this.$lang);
    }
  },

  mounted() {
    this.initialize(this.options, this.$lang);
    this.placeholder = this.$site.themeConfig.searchPlaceholder || "";
  },

  methods: {
    initialize(userOptions, lang) {
      Promise.all([
        import(
            /* webpackChunkName: "docsearch" */ "docsearch.js/dist/cdn/docsearch.min.js"
            ),
        import(
            /* webpackChunkName: "docsearch" */ "docsearch.js/dist/cdn/docsearch.min.css"
            )
      ]).then(([docsearch]) => {
        docsearch = docsearch.default;
        const {algoliaOptions = {}} = userOptions;
        docsearch(
            Object.assign({}, userOptions, {
              inputSelector: "#algolia-search-input",
              // #697 Make docsearch work well at i18n mode.
              algoliaOptions: Object.assign(
                  {
                    facetFilters: [`lang:${lang}`].concat(
                        algoliaOptions.facetFilters || []
                    )
                  },
                  algoliaOptions
              ),
              handleSelected: (input, event, suggestion) => {
                const {pathname, hash} = new URL(suggestion.url);
                const routepath = pathname.replace(this.$site.base, "/");
                this.$router.push(`${routepath}${hash}`);
              }
            })
        );
      });
    },

    update(options, lang) {
      this.$el.innerHTML =
          '<input id="algolia-search-input" class="search-query">';
      this.initialize(options, lang);
    }
  }
};
</script>

<style lang="stylus">

/* Description description (eg. Bootstrap currently works...) */
.algolia-autocomplete .algolia-docsearch-suggestion--text
font-size:

0.8
rem
color:

var
(
--v-accent-base

)
!important

.algolia-search-wrapper .algolia-autocomplete
.ds-dropdown-menu
[class^="ds-dataset-"]
background-color:

var
(
--v-background-base

)

.ds-cursor .algolia-docsearch-suggestion--content
background-color:

var
(
--v-background-darken2

)
!important

&
::before
background-color:

var
(
--v-primary-base

)

.algolia-docsearch-suggestion
.algolia-docsearch-suggestion--highlight
color:

var
(
--v-primary-base

)
.algolia-docsearch-suggestion--subcategory-column-text
color:

var
(
--v-accent-base

)

.algolia-search-wrapper

&
> span
vertical-align middle
.algolia-autocomplete
line-height normal
.ds-dropdown-menu
background-color

var
(
--v-background-base

)
border-radius

1
px
font-size

16
px
margin

6
px

0
0
padding

4
px
text-align left

            & :before
border-color

var
(
--v-secondary-base

)
background-color

var
(
--v-primary-base

)

[class*=ds-dataset-]
border none
padding

0

.ds-suggestions
margin-top

0

.ds-suggestion
border-bottom

1
px solid

var
(
--v-secondary-base

)

.algolia-docsearch-suggestion--content

&
:before
background-color:

var
(
--v-secondary-base

)

.algolia-docsearch-suggestion--highlight
color

var
(
--v-primary-base

)

.algolia-docsearch-suggestion
padding

0

.algolia-docsearch-suggestion--category-header
padding

5
px

10
px
margin-top

0
background

var
(
--v-primary-darken2

)
color #ececec
font-weight

600

.algolia-docsearch-suggestion--highlight
background

var
(
--v-background-base

)

.algolia-docsearch-suggestion--wrapper
padding

0

.algolia-docsearch-suggestion--title
font-weight

600
margin-bottom

0
color

var
(
--v-accent-base

)

.algolia-docsearch-suggestion--subcategory-column
vertical-align top
padding

5
px

7
px

5
px

5
px
background

var
(
--v-background-base

)

&
:after
display none
.algolia-docsearch-suggestion--subcategory-column-text
background

var
(
--v-background-base

)

.algolia-docsearch-footer
background-color

var
(
--v-background-base

)

.algolia-docsearch-suggestion--content
background-color

var
(
--v-background-base

)

@media (min-width: $ MQMobile)
  .algolia-search-wrapper
  .algolia-autocomplete
  .algolia-docsearch-suggestion
  .algolia-docsearch-suggestion--subcategory-column
  float none
  width

150px
   min-width

150px
   display table-cell
   .algolia-docsearch-suggestion--content
   float none
   display table-cell
   width

100%
  vertical-align top
  .ds-dropdown-menu
  min-width

515px

!important

  @media (max-width: $ MQMobile)
    .algolia-search-wrapper
    .ds-dropdown-menu
    min-width

  calc(100vw

  - 4rem

  ) !important
    max-width

  calc(100vw

  - 4rem

  ) !important

    .algolia-docsearch-suggestion--wrapper
    padding

  5px

  7px

  5px

  5px

  !important

    .algolia-docsearch-suggestion--subcategory-column
    padding

  0 !important

    .algolia-docsearch-suggestion--subcategory-column-text:after
    content

  " > "
    font-size

  10px
    line-height

  14.4px
      display inline-block
      width

  5px
   margin

  -3px

  3px

  0
    vertical-align middle
</style>
