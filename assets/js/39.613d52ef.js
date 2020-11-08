(window.webpackJsonp=window.webpackJsonp||[]).push([[39],{405:function(a,t,s){"use strict";s.r(t);var e=s(43),r=Object(e.a)({},(function(){var a=this,t=a.$createElement,s=a._self._c||t;return s("ContentSlotsDistributor",{attrs:{"slot-key":a.$parent.slotKey}},[s("h2",{attrs:{id:"mathjax-latex"}},[s("a",{staticClass:"header-anchor",attrs:{href:"#mathjax-latex"}},[a._v("#")]),a._v(" MathJAX - $\\LaTeX$")]),a._v(" "),s("h3",{attrs:{id:"links"}},[s("a",{staticClass:"header-anchor",attrs:{href:"#links"}},[a._v("#")]),a._v(" Links")]),a._v(" "),s("p",[s("a",{attrs:{href:"https://vuepress.github.io/en/plugins/mathjax/",target:"_blank",rel:"noopener noreferrer"}},[a._v("Docs"),s("OutboundLink")],1)]),a._v(" "),s("p",[s("a",{attrs:{href:"https://www.mathelounge.de/509545/mathjax-latex-basic-tutorial-und-referenz-deutsch",target:"_blank",rel:"noopener noreferrer"}},[a._v("MathJax: LaTeX Basic Tutorial und Referenz (German)"),s("OutboundLink")],1)]),a._v(" "),s("p",[s("a",{attrs:{href:"https://quantumcomputing.meta.stackexchange.com/questions/49/tutorial-how-to-use-tex-mathjax/76#76",target:"_blank",rel:"noopener noreferrer"}},[a._v("Basics of typesetting in MathJax"),s("OutboundLink")],1)]),a._v(" "),s("h3",{attrs:{id:"inline-rendering"}},[s("a",{staticClass:"header-anchor",attrs:{href:"#inline-rendering"}},[a._v("#")]),a._v(" Inline Rendering")]),a._v(" "),s("div",{staticClass:"language- extra-class"},[s("pre",{pre:!0,attrs:{class:"language-text"}},[s("code",[a._v("Surround your LaTeX with a single `$` on each side for inline rendering.\n")])])]),s("p",[a._v("Input:")]),a._v(" "),s("blockquote",[s("p",[a._v("Euler's identity "),s("code",[a._v("$e^{i\\pi}+1=0$")]),a._v(" is a beautiful formula in "),s("code",[a._v("$\\R^2$")])])]),a._v(" "),s("p",[a._v("Output:")]),a._v(" "),s("blockquote",[s("p",[a._v("$Euler's identity \\$e^{i\\pi}+1=0\\$ is a beautiful formula in \\$\\R^2$")])]),a._v(" "),s("h3",{attrs:{id:"block-rendering"}},[s("a",{staticClass:"header-anchor",attrs:{href:"#block-rendering"}},[a._v("#")]),a._v(" Block Rendering")]),a._v(" "),s("div",{staticClass:"language- extra-class"},[s("pre",{pre:!0,attrs:{class:"language-text"}},[s("code",[a._v("Use two `$$` for block rendering. This mode uses bigger symbols and centers the result.\n")])])]),s("p",[a._v("Input:")]),a._v(" "),s("blockquote",[s("p",[s("code",[a._v("$$\\frac {\\partial^r} {\\partial \\omega^r} \\left(\\frac {y^{\\omega}} {\\omega}\\right) = \\left(\\frac {y^{\\omega}} {\\omega}\\right) \\left\\{(\\log y)^r + \\sum_{i=1}^r \\frac {(-1)^i r \\cdots (r-i+1) (\\log y)^{r-i}} {\\omega^i} \\right\\}$$")])])]),a._v(" "),s("p",[a._v("Output:")]),a._v(" "),s("blockquote",[s("p",[a._v("$$\n= \\left(\\frac {y^{\\omega}} {\\omega}\\right) \\left{(\\log y)^r + \\sum_{i=1}^r \\frac {(-1)^i r \\cdots (r-i+1) (\\log y)^{r-i}} {\\omega^i} \\right}$$\n$$")])]),a._v(" "),s("h3",{attrs:{id:"using-macros"}},[s("a",{staticClass:"header-anchor",attrs:{href:"#using-macros"}},[a._v("#")]),a._v(" Using Macros")]),a._v(" "),s("p",[a._v("This is part of "),s("code",[a._v("config.js")]),a._v(" of this project:")]),a._v(" "),s("div",{staticClass:"language-js extra-class"},[s("pre",{pre:!0,attrs:{class:"language-js"}},[s("code",[a._v("module"),s("span",{pre:!0,attrs:{class:"token punctuation"}},[a._v(".")]),a._v("exports "),s("span",{pre:!0,attrs:{class:"token operator"}},[a._v("=")]),a._v(" "),s("span",{pre:!0,attrs:{class:"token punctuation"}},[a._v("{")]),a._v("\n  plugins"),s("span",{pre:!0,attrs:{class:"token operator"}},[a._v(":")]),a._v(" "),s("span",{pre:!0,attrs:{class:"token punctuation"}},[a._v("{")]),a._v("\n    mathjax"),s("span",{pre:!0,attrs:{class:"token operator"}},[a._v(":")]),a._v(" "),s("span",{pre:!0,attrs:{class:"token punctuation"}},[a._v("{")]),a._v("\n      macros"),s("span",{pre:!0,attrs:{class:"token operator"}},[a._v(":")]),a._v(" "),s("span",{pre:!0,attrs:{class:"token punctuation"}},[a._v("{")]),a._v("\n        "),s("span",{pre:!0,attrs:{class:"token string"}},[a._v('"\\\\Z"')]),s("span",{pre:!0,attrs:{class:"token operator"}},[a._v(":")]),a._v(" "),s("span",{pre:!0,attrs:{class:"token string"}},[a._v('"\\\\mathbb{Z}"')]),a._v("\n      "),s("span",{pre:!0,attrs:{class:"token punctuation"}},[a._v("}")]),a._v("\n    "),s("span",{pre:!0,attrs:{class:"token punctuation"}},[a._v("}")]),a._v("\n  "),s("span",{pre:!0,attrs:{class:"token punctuation"}},[a._v("}")]),a._v("\n"),s("span",{pre:!0,attrs:{class:"token punctuation"}},[a._v("}")]),s("span",{pre:!0,attrs:{class:"token punctuation"}},[a._v(";")]),a._v("\n")])])]),s("p",[a._v("now we can write "),s("code",[a._v("$\\Z$")]),a._v(" instead of "),s("code",[a._v("$\\mathbb{Z}$")]),a._v(" to print $\\Z$")]),a._v(" "),s("h3",{attrs:{id:"greek-letters"}},[s("a",{staticClass:"header-anchor",attrs:{href:"#greek-letters"}},[a._v("#")]),a._v(" Greek letters")]),a._v(" "),s("p",[s("code",[a._v("$\\alpha$")]),a._v(" - $\\alpha$\n"),s("code",[a._v("$\\beta$")]),a._v(" - $\\beta$\n"),s("code",[a._v("$\\omega$")]),a._v(" - $\\omega$")]),a._v(" "),s("p",[s("code",[a._v("$\\Gamma")]),a._v(" - $\\Gamma$\n"),s("code",[a._v("$\\Delta")]),a._v(" - $Delta$\n"),s("code",[a._v("$\\Omega$")]),a._v(" - $\\Omega$")]),a._v(" "),s("h3",{attrs:{id:"superscript-and-subscript"}},[s("a",{staticClass:"header-anchor",attrs:{href:"#superscript-and-subscript"}},[a._v("#")]),a._v(" Superscript and Subscript")]),a._v(" "),s("p",[a._v("use "),s("code",[a._v("^")]),a._v(" for "),s("em",[a._v("superscript")]),a._v(" and "),s("code",[a._v("_")]),a._v(" for subscript")]),a._v(" "),s("blockquote",[s("p",[s("code",[a._v("x_i^2")]),a._v(" $x_i^2$"),s("br"),a._v(" "),s("code",[a._v("\\log_2 x")]),a._v(" $\\log_2 x$")])]),a._v(" "),s("h3",{attrs:{id:"groups"}},[s("a",{staticClass:"header-anchor",attrs:{href:"#groups"}},[a._v("#")]),a._v(" Groups")]),a._v(" "),s("blockquote",[s("p",[a._v("Superscripts, subscripts, and other operations apply only to the next “group”.\nA “group” is either a single symbol, or any formula surrounded by curly braces {…}.\nIf you do 10^10, you will get a surprise: $10^10$. But 10^{10} gives what you probably wanted: $10^{10}$.\nUse curly braces to delimit a formula to which a superscript or subscript applies: x^5^6 is an error;\n{x^y}^z is ${x^y}^z$, and x^{y^z} is $x^{y^z}$. Observe the difference between x"),s("em",[a._v("i^2 $x_i^2$ and x")]),a._v("{i^2} $x_{i^2}$.")])]),a._v(" "),s("h3",{attrs:{id:"parentheses"}},[s("a",{staticClass:"header-anchor",attrs:{href:"#parentheses"}},[a._v("#")]),a._v(" Parentheses")]),a._v(" "),s("p",[a._v("Ordinary symbols "),s("code",[a._v("()[]")]),a._v(" make parentheses and brackets $(2+3)[4+4]$. Use "),s("code",[a._v("\\{")]),a._v(" and "),s("code",[a._v("\\}")]),a._v(" for curly braces ${}$")]),a._v(" "),s("h3",{attrs:{id:"sums-and-integrals"}},[s("a",{staticClass:"header-anchor",attrs:{href:"#sums-and-integrals"}},[a._v("#")]),a._v(" Sums and integrals")]),a._v(" "),s("h3",{attrs:{id:"fractions"}},[s("a",{staticClass:"header-anchor",attrs:{href:"#fractions"}},[a._v("#")]),a._v(" Fractions")]),a._v(" "),s("h3",{attrs:{id:"fonts"}},[s("a",{staticClass:"header-anchor",attrs:{href:"#fonts"}},[a._v("#")]),a._v(" Fonts")]),a._v(" "),s("h3",{attrs:{id:"radical-signs"}},[s("a",{staticClass:"header-anchor",attrs:{href:"#radical-signs"}},[a._v("#")]),a._v(" Radical signs")]),a._v(" "),s("h3",{attrs:{id:"special-functions"}},[s("a",{staticClass:"header-anchor",attrs:{href:"#special-functions"}},[a._v("#")]),a._v(" special functions")]),a._v(" "),s("h3",{attrs:{id:"special-symbols-and-notations"}},[s("a",{staticClass:"header-anchor",attrs:{href:"#special-symbols-and-notations"}},[a._v("#")]),a._v(" special symbols and notations")]),a._v(" "),s("h3",{attrs:{id:"spaces"}},[s("a",{staticClass:"header-anchor",attrs:{href:"#spaces"}},[a._v("#")]),a._v(" Spaces")]),a._v(" "),s("h3",{attrs:{id:"accents-and-diacritical-marks"}},[s("a",{staticClass:"header-anchor",attrs:{href:"#accents-and-diacritical-marks"}},[a._v("#")]),a._v(" Accents and diacritical marks")])])}),[],!1,null,null,null);t.default=r.exports}}]);