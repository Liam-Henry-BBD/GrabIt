import { LitElement, html, css } from 'lit';
import { customElement, state } from 'lit/decorators.js';
import "../../../../auth/activities/auth-router";

@customElement('projects-app')
class Projects extends LitElement {
  @state() isAuthenticated = false;


  static styles = css`
    :host {
      display: block;
    }
  `;


  render() {
    return html`
    <auth-router>
    <h1>Liam Router</h1>
    </auth-router>
        `;

  }
}


