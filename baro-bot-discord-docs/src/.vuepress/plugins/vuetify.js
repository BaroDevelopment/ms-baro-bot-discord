import Vue from "vue";
import Vuetify from "vuetify";
import "vuetify/dist/vuetify.min.css";
import colors from "vuetify/lib/util/colors";

Vue.use(Vuetify);

export default new Vuetify({
  icons: {
    iconfont: "fa"
  },
  theme: {
    dark: true,
    options: {
      customProperties: true
    },
    themes: {
      light: {
        primary: colors.purple.accent2,
        secondary: colors.blue.base,
        accent: colors.grey.darken4,
        error: colors.red.accent3,
        anchor: colors.shades.black,
        info: "#2196F3",
        success: "#4CAF50",
        warning: "#FFC107",
        background: colors.grey.lighten4
      },
      dark: {
        primary: colors.purple.accent2,
        secondary: colors.blue.base,
        accent: colors.grey.lighten2,
        error: colors.red.accent3,
        anchor: colors.grey.lighten5,
        info: "#2196F3",
        success: "#4CAF50",
        warning: "#FFC107",
        background: colors.grey.darken4
      }
    }
  }
});
