import React, { useState } from 'react';

const API_BASE = (process.env.REACT_APP_API_URL as string) ?? '';

export default function Register({ onShowLogin }: { onShowLogin: () => void }) {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [firstName, setFirstName] = useState('');
  const [lastName, setLastName] = useState('');
  const [msg, setMsg] = useState<string | null>(null);

  async function submit(e: React.FormEvent) {
    e.preventDefault();
    setMsg(null);
    try {
      const res = await fetch(`${API_BASE}/api/auth/register`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ email, password, firstName, lastName }),
      });
      if (res.ok) {
        setMsg('Inscription réussie ! Vous pouvez maintenant vous connecter.');
      } else {
        const errorText = await res.text();
        setMsg(`Erreur lors de l'inscription : ${errorText}`);
      }
    } catch (e) {
      setMsg('Erreur réseau lors de l\'inscription.');
    }
  }

  return (
    <div className="vc-glass p-8 rounded-lg max-w-md w-full">
      <h1 className="text-3xl font-bold mb-6 text-center">Créer un compte</h1>
      <form onSubmit={submit} className="space-y-4">
        <input placeholder="Prénom" value={firstName} onChange={e => setFirstName(e.target.value)} className="w-full p-2 rounded bg-transparent border border-white/6" />
        <input placeholder="Nom" value={lastName} onChange={e => setLastName(e.target.value)} className="w-full p-2 rounded bg-transparent border border-white/6" />
        <input placeholder="Email" type="email" value={email} onChange={e => setEmail(e.target.value)} className="w-full p-2 rounded bg-transparent border border-white/6" />
        <input placeholder="Mot de passe" type="password" value={password} onChange={e => setPassword(e.target.value)} className="w-full p-2 rounded bg-transparent border border-white/6" />
        <button className="w-full px-3 py-2 bg-gradient-to-r from-indigo-500 to-cyan-400 rounded" type="submit">S'inscrire</button>
      </form>
      {msg && <div className="mt-4 text-sm text-center text-amber-200">{msg}</div>}
      <div className="mt-4 text-center text-sm">
        <button onClick={onShowLogin} className="text-cyan-400 hover:underline">Déjà un compte ? Se connecter</button>
      </div>
    </div>
  );
}
