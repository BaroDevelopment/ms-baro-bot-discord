## Using Markdown

::: details Click me to view the code
```js
console.log('Hello, VuePress!')
```
:::

::: tip
Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore
:::

::: caution
Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore
:::

::: danger
Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore
:::

::: tip YINGYANG - Lead Developer & Maintainer
![avatar](https://cdn.discordapp.com/attachments/396964573007052800/534165823645024296/YingYang.gif)

Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore
magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd
gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.

<em>All inquires about Lorem can be sent to <a href="mailto:zach@mtgjson.com">test@example.com</a></em>
:::

## Using Vue Component

<Hint
    type='info'
    img='https://cdn.discordapp.com/attachments/396964573007052800/492135655233683458/PaladinHypeMain.gif'
    title='Info Block'
    text='Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.'
/>

<Hint
    type='warning'
    img='https://cdn.discordapp.com/attachments/396964573007052800/492135655233683458/PaladinHypeMain.gif'
    title='Info Block'
    text='Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.'
/>

<Hint
    type='danger'
    img='https://cdn.discordapp.com/attachments/396964573007052800/492135655233683458/PaladinHypeMain.gif'
    title='Info Block'
    text='Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.'
/>

<Hint
    type='tip'
    img='https://cdn.discordapp.com/attachments/396964573007052800/492135655233683458/PaladinHypeMain.gif'
    title='Info Block'
    text='Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.'
/>

::: vue Directory Structure
.
├── docs
│ ├── .vuepress _(**Optional**)_
│ │ ├── `components` _(**Optional**)_
│ │ ├── `theme` _(**Optional**)_
│ │ │ └── Layout.vue
│ │ ├── `public` _(**Optional**)_
│ │ ├── `styles` _(**Optional**)_
│ │ │ ├── index.styl
│ │ │ └── palette.styl
│ │ ├── `templates` _(**Optional, Danger Zone**)_
│ │ │ ├── dev.html
│ │ │ └── ssr.html
│ │ ├── `config.js` _(**Optional**)_
│ │ └── `enhanceApp.js` _(**Optional**)_
│ │
│ ├── README.md
│ ├── guide
│ │ └── README.md
│ └── config.md
│
└── package.json
:::

<br/>

<Admonition text="Note Admonition Content" type="note" title="Note Admonition Title" :expand="1"/>
<br/>
<Admonition text="Abstract Admonition Content" type="abstract" title="Abstract Admonition Title" :expand="1"/>
<br/>
<Admonition text="Information Admonition Content" type="information" title="Information Admonition Title"/>
<br/>
<Admonition text="Tip Admonition Content" type="tip" title="Tip Admonition Title"/>
<br/>
<Admonition text="Check Admonition Content" type="check" title="Check Admonition Title"/>
<br/>
<Admonition text="Question Admonition Content" type="question" title="Question Admonition Title"/>
<br/>
<Admonition text="Warn Admonition Content" type="warn" title="Warn Admonition Title"/>
<br/>
<Admonition text="Fail Admonition Content" type="fail" title="Fail Admonition Title"/>
<br/>
<Admonition text="Error Admonition Content" type="err" title="Error Admonition Title"/>
<br/>
<Admonition text="Bug Admonition Content" type="bug" title="Bug Admonition Title"/>
<br/>
<Admonition text="Example Admonition Content" type="example" title="Example Admonition Title"/>
<br/>
<Admonition text="Quote Admonition Content" type="quote" title="Quote Admonition Title"/>
