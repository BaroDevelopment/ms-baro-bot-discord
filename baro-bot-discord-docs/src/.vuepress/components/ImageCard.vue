<template>
  <v-card
      :height="height"
      :hoverColor="hoverColor"
      :hoverText="hoverText"
      :img="img"
      :style="cardStyle"
      :title="title"
      :width="width"
      class="ma-3"
      @mouseover.native="hover = true"
      @mouseleave.native="hover = false"
  >
    <v-card-text>
      <span v-if="hover && hoverText.length > 0" class="hoverTitle">{{
          hoverText
        }}</span>
      <div class="title">{{ title }}</div>
    </v-card-text>
  </v-card>
</template>

<script>
export default {
  name: "ImageCard",
  data() {
    return {
      hover: false
    };
  },
  props: {
    width: {
      type: Number,
      default: 200
    },
    height: {
      type: Number,
      default: 200
    },
    img: {
      type: String,
      required: true
    },
    title: {
      type: String
    },
    hoverColor: {
      type: String,
      default: "#000000"
    },
    hoverText: {
      type: String,
      default: ""
    }
  },
  computed: {
    cardStyle() {
      return {
        boxShadow: this.hover ? `0px 0px 14px 4px ${this.hoverColor}` : ""
      };
    }
  }
};
</script>

<style lang="stylus">

.title
width:

100
%
;
font-size:

14
px

!important
;
display: flex

;
align-items: center

;
justify-content: center

;
background-color:

rgba
(
0
,
0
,
0
,
0.6
)
;
height:

30
px

;
color: white

;
position: absolute

;
bottom:

0
;
left:

0
;

.hoverTitle
width:

100
%
;
font-size:

14
px

!important
;
display: flex

;
align-items: center

;
justify-content: center

;
background-color:

rgba
(
0
,
0
,
0
,
0.6
)
;
height:

30
px

;
color: white

;
position: absolute

;
left:

0
;
top:

0
;
</style>
