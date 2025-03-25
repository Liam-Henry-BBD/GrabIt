import { html, css, LitElement } from 'lit';
import { customElement, state } from 'lit/decorators.js';

@customElement('redirect-app')
export class RedirectComponent extends LitElement {

    connectedCallback() {
        super.connectedCallback();
        const urlParams = new URLSearchParams(window.location.search);
        const token = urlParams.get('token');
        if (token) {
            localStorage.setItem('token', token);
                window.location.href = '/';
        }
    }

    render() {
        return html`
            <h1>Wait... We will redirect...</h1>
        `;
    }
}