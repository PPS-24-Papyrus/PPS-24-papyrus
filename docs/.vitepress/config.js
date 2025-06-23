import {defineConfig} from 'vitepress'
import { withMermaid } from "vitepress-plugin-mermaid";

let reportPath = '/report'
let processPath = '/process'
// https://vitepress.dev/reference/site-config

export default withMermaid(
    defineConfig({
        base: '/PPS-24-papyrus/',
        title: "Papyrus",
        description: "Project of PPS - Papyrus",
        themeConfig: {
            // https://vitepress.dev/reference/default-theme-config
            nav: [
                {text: 'Home', link: '/'},
            ],

            sidebar: [
                {text: 'Report',
                    items: [
                        {text: 'Process scoping meeting', link: `${reportPath}/1-prova`},
                        //{text: 'Valutazione proposte del committente', link: `${reportPath}/2-valutazione-proposte-committente`},
                        //{text: 'Project Overview Statement', link: `${reportPath}/3-POS`},
                        //{text: 'Analisi dei rischi', link: `${reportPath}/4-POS-AnalisiRischi`},
                        //{text: 'Requirements Breakdown Structure', link: `${reportPath}/5-RBS`},
                        //{text: 'PMLC Model', link: `${reportPath}/6-PMLC-model`},
                        //{text: 'Traduzione', link: `${reportPath}/7-traduzione`},
                    ]
                },
            ],

            socialLinks: [
                {icon: 'github', link: 'https://github.com/PPS-24-Papyrus/PPS-24-Papyrus'}
            ]
        }
    })
)
