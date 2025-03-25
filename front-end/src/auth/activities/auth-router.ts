import { LitElement, html, css } from 'lit';
import { customElement, property, state } from 'lit/decorators.js';
import sendRequest from '../../services/requests';


@customElement('auth-router')
class AuthRouter extends LitElement {
  @state() isAuthenticated = false;

  static styles = css`
    :host {
      display: block;
    }
  `;

  constructor() {
    super();
    this.checkAuth();
  }

  async checkAuth() {
      try {
       const response = await sendRequest('/user/validate'); 
      } catch (error) {
        console.error('Error validating token:', error);
        this.isAuthenticated = false;
      }
    }


  render() {
    return html`<slot ></slot> `;
  }
}


