import React from 'react';

type Props = {
  id: number;
  name: string;
  description?: string;
  country?: string;
  onBook: (id: number) => void;
};

export default function DestinationCard({ id, name, description, country, onBook }: Props) {
  return (
    <article className="vc-glass vc-float p-4 rounded-xl border border-white/5 hover:scale-105 transition-transform duration-300">
      <div className="flex items-start justify-between">
        <div>
          <h3 className="text-lg font-semibold text-white">{name}</h3>
          {country && <div className="text-xs text-cyan-200/80 mt-1">{country}</div>}
        </div>
        <div className="ml-4">
          <div className="w-12 h-12 rounded-lg bg-gradient-to-br from-indigo-500 to-cyan-400 flex items-center justify-center vc-neon">
            <svg width="20" height="20" viewBox="0 0 24 24" fill="none" className="text-white">
              <path d="M12 2l3 7h7l-5.5 4 2 7L12 16l-6.5 4 2-7L2 9h7l3-7z" fill="white" opacity="0.12"></path>
            </svg>
          </div>
        </div>
      </div>
      {description && <p className="mt-3 text-sm text-neutral-300">{description}</p>}
      <div className="mt-4 flex items-center justify-between">
        <div className="text-xs text-neutral-400">Disponibilité: Illimitée</div>
        <button onClick={() => onBook(id)} className="px-3 py-1 bg-gradient-to-r from-indigo-500 to-cyan-400 text-white rounded-md shadow-md hover:brightness-105">
          Réserver
        </button>
      </div>
    </article>
  );
}
