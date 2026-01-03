import React from 'react';
import { Link } from 'react-router-dom';

const LoginPage = () => {
  return (
    <div className="flex items-center justify-center min-h-screen bg-gray-100">
      <div className="w-full max-w-md p-8 space-y-6 bg-white rounded-xl shadow-lg">
        <h2 className="text-3xl font-bold text-center text-gray-800">Connexion</h2>
        <form className="space-y-6">
          <div>
            <label
              htmlFor="email"
              className="text-sm font-semibold text-gray-600 block"
            >
              Adresse e-mail
            </label>
            <input
              id="email"
              type="email"
              className="w-full px-4 py-2 mt-2 text-gray-700 bg-gray-200 border border-gray-300 rounded-lg focus:outline-none focus:bg-white focus:border-blue-500"
              placeholder="votre@email.com"
              required
            />
          </div>
          <div>
            <label
              htmlFor="password"
              className="text-sm font-semibold text-gray-600 block"
            >
              Mot de passe
            </label>
            <input
              id="password"
              type="password"
              className="w-full px-4 py-2 mt-2 text-gray-700 bg-gray-200 border border-gray-300 rounded-lg focus:outline-none focus:bg-white focus:border-blue-500"
              placeholder="********"
              required
            />
          </div>
          <div>
            <button
              type="submit"
              className="w-full px-4 py-2 font-bold text-white bg-blue-600 rounded-lg hover:bg-blue-700 focus:outline-none focus:bg-blue-700"
            >
              Se connecter
            </button>
          </div>
        </form>
        <div className="text-center">
          <p className="text-sm text-gray-600">
            Pas encore de compte ?{' '}
            <Link to="/signup" className="font-semibold text-blue-600 hover:underline">
              Inscrivez-vous
            </Link>
          </p>
        </div>
      </div>
    </div>
  );
};

export default LoginPage;
