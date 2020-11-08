<script>
export default {
  functional: true,
  props: {
    type: {
      type: String,
      default: "tip"
    },
    text: String,
    vertical: {
      type: String,
      default: "top"
    }
  },
  render(h, {props, slots}) {
    return h(
        "span",
        {
          class: ["badge", props.type],
          style: {
            verticalAlign: props.vertical
          }
        },
        props.text || slots().default
    );
  }
};
</script>

<style lang="stylus" scoped>
.badge
display inline-block
font-size

14
px
height

18
px
line-height

18
px
border-radius

3
px
padding

0
6
px
color white
background-color #42b983

&
.tip,

&
.green
background-color $ badgeTipColor
  & .error
background-color $ badgeErrorColor
  & .warning,

&
.warn,

&
.yellow
background-color $ badgeWarningColor
  & + &
margin-left

5
px
</style>
