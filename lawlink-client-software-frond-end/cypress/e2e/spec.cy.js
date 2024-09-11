describe('Login Page', () => {
  beforeEach(() => {
    cy.visit('http://localhost:5173/login'); 
  });

  it('should load the login page', () => {
    cy.get('form.login-form').should('exist');
  });

  it('should login with valid credentials and click the login button', () => {
    cy.get('input[name="username"]').type(Cypress.env('username'));
    cy.get('input[name="password"]').type(Cypress.env('password'));

    cy.get('button.login-button').should('exist').click();

    cy.url().should('include', '/');
  });

  it('should display an error for invalid credentials', () => {
    cy.get('input[name="username"]').type('invalid-username');
    cy.get('input[name="password"]').type('invalid-password');

    cy.get('button.login-button').should('exist').click();

    cy.get('.error-message').should('contain', 'Invalid username or password'); 
  });

});








