import React from 'react';
import { Link } from 'react-router-dom';

const HomePage = () => {
  return (
    <div className="flex flex-col items-center justify-center min-h-screen bg-gray-50">
      <div className="text-center">
        <h1 className="text-4xl font-bold text-gray-800 mb-4">Bienvenue sur VoyageConnect</h1>
        <p className="text-lg text-gray-600 mb-8">
          Votre aventure commence ici. Connectez-vous pour explorer les meilleures destinations.
        </p>
        <Link
          to="/login"
          className="px-6 py-3 bg-blue-600 text-white font-semibold rounded-lg shadow-md hover:bg-blue-700 transition-colors"
        >
          Se connecter
        </Link>
      </div>
    </div>
  );
};

export default HomePage;
