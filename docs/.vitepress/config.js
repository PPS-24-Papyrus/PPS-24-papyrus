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
                        {text: 'Introduzione', link: `${reportPath}/1-getStarted`},
                        {text: 'Processo di Sviluppo', link: `${reportPath}/2-processoDiSviluppo`},
                        {text: 'Requisiti', link: `${reportPath}/3-requisiti`},
                        {text: 'Design Architetturale', link: `${reportPath}/4-designArchitetturale`},
                        {text: 'Design di Dettaglio', link: `${reportPath}/5-designDiDettaglio`},
                        {text: 'Implementazione',
                            items: [
                                {text: 'Cantagallo Luca', link: `${reportPath}/7-CantagalloLuca`},
                                {text: 'Capannini Daniel', link: `${reportPath}/8-CapanniniDaniel`},
                            ]
                        },
                        {text: 'Testing', link: `${reportPath}/9-testing`},
                        {text: 'Retrospettiva', link: `${reportPath}/10-retrospettiva`},
                    ]
                },
            ],

            socialLinks: [
                {icon: 'github', link: 'https://github.com/PPS-24-Papyrus/PPS-24-Papyrus'}
            ]
        }
    })
)
