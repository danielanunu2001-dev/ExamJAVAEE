import React, { useState } from 'react';

const API_BASE = (process.env.REACT_APP_API_URL as string) ?? '';

export default function Login({ onLogin }: { onLogin: (email: string) => void }) {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [msg, setMsg] = useState<string | null>(null);

  async function submit(e: React.FormEvent) {
    e.preventDefault();
    setMsg(null);
    try {
      const res = await fetch(`${API_BASE}/api/auth/login`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        credentials: 'include',
        body: JSON.stringify({ email, password }),
      });
      if (res.ok) { onLogin(email); setMsg('Connexion réussie'); }
      else { setMsg('Échec de la connexion'); }
    } catch (e) { setMsg('Erreur réseau'); }
  }

  return (
    <form onSubmit={submit} className="vc-glass p-4 rounded mb-4">
      <div className="grid grid-cols-1 sm:grid-cols-3 gap-2">
        <input placeholder="Email" value={email} onChange={e => setEmail(e.target.value)} className="p-2 rounded bg-transparent border border-white/6" />
        <input placeholder="Mot de passe" type="password" value={password} onChange={e => setPassword(e.target.value)} className="p-2 rounded bg-transparent border border-white/6" />
        <div className="flex gap-2">
          <button className="px-3 py-2 bg-gradient-to-r from-indigo-500 to-cyan-400 rounded" type="submit">Se connecter</button>
        </div>
      </div>
      {msg && <div className="mt-2 text-sm text-amber-200">{msg}</div>}
    </form>
  );
}
