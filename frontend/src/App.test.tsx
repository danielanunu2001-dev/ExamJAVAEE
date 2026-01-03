import React from 'react';
import { render, screen } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import App from './App';

test('renders home page on default route', () => {
  render(
    <MemoryRouter initialEntries={['/']}>
      <App />
    </MemoryRouter>
  );

  const welcomeMessage = screen.getByText(/Bienvenue sur VoyageConnect/i);
  expect(welcomeMessage).toBeInTheDocument();
});

test('renders login page on /login route', () => {
  render(
    <MemoryRouter initialEntries={['/login']}>
      <App />
    </MemoryRouter>
  );

  const loginTitle = screen.getByText(/Connexion/i);
  expect(loginTitle).toBeInTheDocument();
});
