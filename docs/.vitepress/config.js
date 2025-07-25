import { defineConfig } from 'vitepress'
import { withMermaid } from "vitepress-plugin-mermaid";

let reportPath = '/report'
let processPath = '/process'

export default withMermaid(
  defineConfig({
    base: '/PPS-24-papyrus/',
    title: "Papyrus",
    description: "Project of PPS - Papyrus",
    themeConfig: {
      nav: [
        { text: 'Home', link: '/' },
      ],

      sidebar: [
        {
          text: 'Report',
          items: [
            {
              text: 'Generali',
              items: [
                { text: 'Introduzione', link: `${reportPath}/1-getStarted` },
                { text: 'Esempio di Utilizzo', link: `${reportPath}/6-example` },
                { text: 'Get Started', link: `${reportPath}/12-getStarted` },
              ]
            },
            { text: 'Processo di Sviluppo', link: `${reportPath}/2-processoDiSviluppo` },
            { text: 'Requisiti', link: `${reportPath}/3-requisiti` },
            {
              text: 'Architettura',
              items: [
                { text: 'Design Architetturale', link: `${reportPath}/4-designArchitetturale` },
                { text: 'Struttura', link: `${reportPath}/13-struttura` },
                { text: 'Grammatica', link: `${reportPath}/11-grammatica` },
                { text: 'Glossario', link: `${reportPath}/14-glossario` },
              ]
            },
            { text: 'Design di Dettaglio', link: `${reportPath}/5-designDiDettaglio` },
            {
              text: 'Implementazione',
              items: [
                { text: 'Librerie', link: `${reportPath}/15-librerie` },
                { text: 'Cantagallo Luca', link: `${reportPath}/7-CantagalloLuca` },
                { text: 'Capannini Daniel', link: `${reportPath}/8-CapanniniDaniel` },
              ]
            },
            { text: 'Testing', link: `${reportPath}/9-testing` },
            { text: 'Retrospettiva', link: `${reportPath}/10-retrospettiva` },
          ]
        },
        {
          text: 'Artefatti del processo di sviluppo',
          items: [
            { text: 'Sprint 1', link: `${processPath}/sprint-1` },
            { text: 'Sprint 2', link: `${processPath}/sprint-2` },
            { text: 'Sprint 3', link: `${processPath}/sprint-3` },
            { text: 'Sprint 4', link: `${processPath}/sprint-4` },
            { text: 'Sprint 5', link: `${processPath}/sprint-5` },
            { text: 'Sprint 6', link: `${processPath}/sprint-6` },
          ]
        }
      ],

      socialLinks: [
        { icon: 'github', link: 'https://github.com/PPS-24-Papyrus/PPS-24-Papyrus' }
      ]
    }
  })
)
