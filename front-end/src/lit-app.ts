import './base/app-router';
import AppLocalStorage from './base/app-localstorage';
import { loadLang } from './xconfig/strings';
import { css, CtLit, customElement, html } from '@conectate/ct-lit';
import { property } from 'lit/decorators.js';
import { injectTheme, Theme } from './base/styles/default-theme';

@customElement('lit-app')
export class LitApp extends CtLit {
  static styles = [
    css`
      :host,
      main {
        display: flex;
        flex-direction: column;
        color: var(--color-on-background);
        height: 100%;
        overflow: hidden;
        box-sizing: border-box;
      }

      app-router {
        flex: 1;
        overflow: auto; /* Allow the app-router to scroll when content overflows */
      }

      header {
        display: flex;
        align-items: center;
        justify-content: space-between;
        font-size: 1.5em;
        font-weight: bold;
        padding: 0px 16px;
        height: 56px;
        color: var(--color-primary);
        background: var(--color-surface);
        box-shadow: rgba(0, 0, 0, 0.26) 0px 4px 11px 0px;
        z-index: 90;
        position: relative;
      }

      /* Responsive styles */
      @media (max-width: 768px) {
        header {
          font-size: 1.3em; /* Reduce header size on smaller screens */
          padding: 0px 12px; /* Reduce padding */
        }

        main {
          padding: 1rem; /* Add some padding to main content */
        }

        app-router {
          padding: 1rem; /* Add padding around app-router */
        }
      }

      @media (max-width: 480px) {
        header {
          font-size: 1.1em; /* Further reduce header size */
          padding: 0px 8px; /* Further reduce padding */
        }

        main {
          padding: 0.5rem; /* Reduce padding on very small screens */
        }

        app-router {
          padding: 0.5rem; /* Reduce padding for app-router */
        }
      }
    `,
  ];

  @property({ type: Number }) foo = 1;

  async connectedCallback() {
    await loadLang();
    injectTheme();
    Theme.setTheme(AppLocalStorage.theme || 'light');
    super.connectedCallback();
  }

  render() {
    return html`
      <main>
        <!-- <header>GrabIt</header> -->
        <app-router></app-router>
      </main>
    `;
  }

  firstUpdated() {}
}
