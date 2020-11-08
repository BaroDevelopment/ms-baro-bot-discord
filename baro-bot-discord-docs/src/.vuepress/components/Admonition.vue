<template>
  <v-expansion-panels :value="expand">
    <v-expansion-panel :class="items[type]['type']">
      <v-expansion-panel-header disable-icon-rotate>
        <span class="subtitle-1"
        ><strong>{{ title }}</strong></span
        >
        <template v-slot:actions>
          <v-icon>{{ items[type]["icon"] }}</v-icon>
        </template>
      </v-expansion-panel-header>
      <v-expansion-panel-content>
        {{ text }}
      </v-expansion-panel-content>
    </v-expansion-panel>
  </v-expansion-panels>
</template>

<script>
export default {
  name: "Admonition",
  data() {
    return {
      items: {
        note: {type: "note", icon: "mdi-note"},
        abstract: {type: "abstract", icon: "mdi-pencil"},
        information: {type: "information", icon: "mdi-information"},
        tip: {type: "tip", icon: "mdi-code-tags"},
        check: {type: "check", icon: "mdi-check"},
        question: {type: "question", icon: "mdi-help"},
        warn: {type: "warn", icon: "mdi-alert-octagon"},
        fail: {type: "fail", icon: "mdi-close"},
        err: {type: "err", icon: "mdi-flash"},
        bug: {type: "bug", icon: "mdi-bug"},
        example: {type: "example", icon: "mdi-bookmark"},
        quote: {type: "quote", icon: "mdi-comment-text-outline"}
      }
    };
  },
  props: {
    type: {
      default: "note",
      type: String
    },
    text: {
      type: String,
      required: true
    },
    expand: {
      type: Number,
      default: 0
    },
    title: {
      type: String,
      required: true
    }
  },
  computed: {},
  methods: {
    hexToRgb(hex) {
      const result = /^#?([a-f\d]{2})([a-f\d]{2})([a-f\d]{2})$/i.exec(hex);
      return result
          ? `${parseInt(result[1], 16)}, ${parseInt(result[2], 16)}, ${parseInt(
              result[3],
              16
          )}`
          : "0, 0, 0";
    }
  }
};
</script>

<style lang="stylus" scoped>

$
noteBlue

=
#0050ff

;
$
abstractYellow

=
#c5d845

;
$
infoBlue

=
#19d8f5

;
$
tipGreen

=
#00bfa5

;
$
checkGreen

=
#00c853

;
$
questionGreen

=
#64dd17

;
$
warnOrange

=
#ff9100

;
$
failRed

=
#ff5252

$
errorRed

=
#c2185b

$
bugPurple

=
#e040fb

$
examplePurple

=
#651fff

$
quoteGrey

=
#9e9e9e
.v-expansion-panel& .note
background-color

alpha
(
$
noteBlue,

0.20
)
color:

lighten
(
$
noteBlue,

50
%
)
border-left

3
px solid $ noteBlue
.v-expansion-panel& .abstract
background-color

alpha
(
$
abstractYellow,

0.20
)
color:

lighten
(
$
abstractYellow,

50
%
)
border-left

3
px solid $ abstractYellow
.v-expansion-panel& .information
background-color

alpha
(
$
infoBlue,

0.20
)
color:

lighten
(
$
infoBlue,

50
%
)
border-left

3
px solid $ infoBlue
.v-expansion-panel& .tip
background-color

alpha
(
$
tipGreen,

0.20
)
color:

lighten
(
$
tipGreen,

50
%
)
border-left

3
px solid $ tipGreen
.v-expansion-panel& .check
background-color

alpha
(
$
checkGreen,

0.20
)
color:

lighten
(
$
checkGreen,

50
%
)
border-left

3
px solid $ checkGreen
.v-expansion-panel& .question
background-color

alpha
(
$
questionGreen,

0.20
)
color:

lighten
(
$
questionGreen,

50
%
)
border-left

3
px solid $ questionGreen
.v-expansion-panel& .warn
background-color

alpha
(
$
warnOrange,

0.20
)
color:

lighten
(
$
warnOrange,

50
%
)
border-left

3
px solid $ warnOrange
.v-expansion-panel& .fail
background-color

alpha
(
$
failRed,

0.20
)
color:

lighten
(
$
failRed,

50
%
)
border-left

3
px solid $ failRed
.v-expansion-panel& .err
background-color

alpha
(
$
errorRed,

0.20
)
color:

lighten
(
$
errorRed,

50
%
)
border-left

3
px solid $ errorRed
.v-expansion-panel& .bug
background-color

alpha
(
$
bugPurple,

0.20
)
color:

lighten
(
$
bugPurple,

50
%
)
border-left

3
px solid $ bugPurple
.v-expansion-panel& .example
background-color

alpha
(
$
examplePurple,

0.20
)
color:

lighten
(
$
examplePurple,

50
%
)
border-left

3
px solid $ examplePurple
.v-expansion-panel& .quote
background-color

alpha
(
$
quoteGrey,

0.20
)
color:

lighten
(
$
quoteGrey,

50
%
)
border-left

3
px solid $ quoteGrey
</style>
