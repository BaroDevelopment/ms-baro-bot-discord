module.exports = {
    "/usage/": [
        {
            title: "Home",
            collapsable: false,
            children: ["/"]
        },
        {
            title: "Vuepress",
            icon: "fab fa-vuejs",
            collapsable: false,
            children: ["/usage/vuepress", "/usage/markdown", "/usage/container"]
        },
        {
            title: "Javascript",
            icon: "fab fa-discord",
            collapsable: false,
            children: ["/usage/discordjs/"]
        },
        {
            title: "Monitoring",
            collapsable: false,
            icon: "fas fa-desktop",
            children: ["/usage/monitoring"]
        }
    ],
    "/": [
        {
            title: "Home",
            collapsable: false, // optional, defaults to true
            children: ["/", "/commands/"],
            sidebarDepth: 1 // optional, defaults to 1
        },
        {
            title: "Commands",
            collapsable: false,
            children: [
                "commands/overview",
                "commands/information",
                "commands/media",
                "commands/misc",
                "commands/moderation",
                "commands/search",
                "commands/music",
                "commands/owner"
            ]
        }
    ]
};
