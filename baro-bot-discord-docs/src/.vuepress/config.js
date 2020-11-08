const sidebar = require("./sidebar.js");

const config = {
    title: "BaroBot Documentation",
    description: "A Multipurpose Premium Discord Bot with unique features.",
    markdown: {
        lineNumbers: false
    },
    base: "/ms-baro-bot-discord/",
    // dest: "dist",
    head: [
        // ["link", { rel: "icon", href: "/favicon.png" }],
        [
            "link",
            {
                rel: "stylesheet",
                href:
                    "https://cdn.jsdelivr.net/npm/@mdi/font@4.x/css/materialdesignicons.min.css"
            }
        ],
        [
            "link",
            {
                rel: "stylesheet",
                href: "https://use.fontawesome.com/releases/v5.0.13/css/all.css"
            }
        ],
        [
            "meta",
            {
                name: "viewport",
                content:
                    "width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no, minimal-ui"
            }
        ]
        // ['script', {src: 'https://cdn.jsdelivr.net/npm/@widgetbot/crate@3', async: true, defer: true}, `
        // 	const button = new Crate({
        // 	location: ['bottom', 'left'],
        // 	server: '391946504509587476',
        // 	channel: '501484507585708038',
        // 	shard: 'https://disweb.deploys.io'
        // 	})
        // 	button.notify({
        // 	content: 'Need a hand? Leave a message!',
        // 	timeout: 3000,
        // 	})
        // `],
    ],
    plugins: [
        [
            "@vuepress/active-header-links",
            {
                sidebarLinkSelector: ".sidebar-link",
                headerAnchorSelector: ".header-anchor"
            }
        ],
        ["container", {type: "tip"}],
        ["container", {type: "caution"}],
        ["container", {type: "danger"}],
        ["container", {type: "information"}],
        [
            "container",
            {
                type: "vue",
                before: '<pre class="vue-container"><code>',
                after: "</code></pre>"
            }
        ]
    ],
    themeConfig: {
        repo: "BaroDevelopment/BaroBot",
        docsDir: "src",
        search: true, // https://vuepress.vuejs.org/theme/default-theme-config.html#built-in-search
        searchMaxSuggestions: 15,
        editLinks: true,
        sidebarDepth: 3,
        lastUpdated: true,
        displayAllHeaders: false,
        activeHeaderLinks: true,
        // default value is true. Set it to false to hide next page links on all pages
        nextLinks: true,
        // default value is true. Set it to false to hide prev page links on all pages
        prevLinks: true,
        logo:
            "https://cdn.discordapp.com/attachments/396964573007052800/492135654919241739/PaladinMainAvatar.png",
        nav: [
            {
                text: "Home",
                link: "/"
            },
            {
                text: "Docs",
                link: "/commands/"
            },
            {
                text: "Logging",
                link: "/logging"
            },
            {
                text: "VuePress",
                ariaLabel: "VuePress",
                items: [
                    {
                        text: "VuePress",
                        items: [
                            {
                                text: "Getting Started",
                                link: "/usage/vuepress"
                            },
                            {
                                text: "Markdown",
                                link: "/usage/markdown"
                            },
                            {
                                text: "Container",
                                link: "/usage/container"
                            }
                        ]
                    },
                    {
                        text: "JavaScript",
                        items: [
                            {
                                text: "Discord.js",
                                link: "/usage/discordjs/"
                            }
                        ]
                    },
                    {
                        text: "Tools",
                        items: [
                            {
                                text: "Netdata",
                                link: "/usage/monitoring/"
                            }
                        ]
                    }
                ]
            }
        ],
        sidebar
    },
    configureWebpack: {
        module: {
            rules: [
                {
                    test: /\.s(c|a)ss$/,
                    use: [
                        "vue-style-loader",
                        "css-loader",
                        {
                            loader: "sass-loader",
                            // Requires sass-loader@^8.0.0
                            options: {
                                implementation: require("sass"),
                                sassOptions: {
                                    fiber: require("fibers"),
                                    indentedSyntax: true // optional
                                }
                            }
                        }
                    ]
                }
            ]
        }
    }
};

// if (process.env.NODE_ENV === "production") {
config.themeConfig.algolia = {
    apiKey: "7fa0446a3a8d4974fee6c47c59668ede",
    indexName: "barobot"
};
// config.plugins.push(["@vuepress/google-analytics", { ga: "UA-1a18as217-1" }]);
// }

module.exports = config;
