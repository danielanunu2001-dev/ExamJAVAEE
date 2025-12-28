import React, { useState } from 'react';
import AdminDestinations from './AdminDestinations';
import AdminUsers from './AdminUsers';

type Props = { email: string; password: string; onClose: () => void };

export default function AdminPanel({ email, password, onClose }: Props) {
  const [tab, setTab] = useState<'dest' | 'users'>('dest');

  return (
    <div className="fixed inset-0 bg-black/60 flex items-start justify-center p-6 z-50">
      <div className="bg-[#0b1220] rounded-lg shadow-xl w-full max-w-4xl text-white overflow-auto">
        <div className="flex items-center justify-between p-4 border-b border-white/6">
          <h3 className="text-lg font-semibold">Admin Panel</h3>
          <div className="flex gap-2">
            <button className="px-3 py-1 bg-white/6 rounded" onClick={() => setTab('dest')}>Destinations</button>
            <button className="px-3 py-1 bg-white/6 rounded" onClick={() => setTab('users')}>Utilisateurs</button>
            <button className="px-3 py-1 bg-red-600 rounded" onClick={onClose}>Fermer</button>
          </div>
        </div>
        <div className="p-4">
          {tab === 'dest' ? <AdminDestinations email={email} password={password} /> : <AdminUsers email={email} password={password} />}
        </div>
      </div>
    </div>
  );
}
