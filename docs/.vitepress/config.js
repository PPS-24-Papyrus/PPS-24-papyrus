import { defineConfig } from 'vitepress'
import { withMermaid } from 'vitepress-plugin-mermaid'

// Base path della repo GitHub (da aggiornare se cambia nome)
const basePath = '/papyrus/'

// Configurazione VitePress con supporto Mermaid e grafica personalizzata
export default withMermaid(
    defineConfig({
        base: basePath,
        title: 'Papyrus',
        description: 'Relazione tecnica del progetto Papyrus',
        themeConfig: {
            nav: [
                { text: 'Home', link: '/' }
                // Aggiungi link extra qui se necessario
            ],
            sidebar: [
                {
                    text: 'Relazione',
                    items: [
                        { text: 'Indice', link: '/' }
                        // Puoi aggiungere altre sezioni quando crei altri file
                    ]
                }
            ],
            socialLinks: [
                {
                    icon: 'github',
                    link: 'https://github.com/dev-pps/papyrus'
                }
            ]
        }
    })
)
