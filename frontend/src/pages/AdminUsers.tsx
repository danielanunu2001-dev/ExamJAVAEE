import React, { useEffect, useState } from 'react';

type User = { id: number; email: string; firstName?: string; lastName?: string; role?: string };
const API_BASE = (process.env.REACT_APP_API_URL as string) ?? '';

// Use credentials cookie: send requests with credentials: 'include'

export default function AdminUsers({ email, password }: { email: string; password: string }) {
  const [users, setUsers] = useState<User[]>([]);
  const [msg, setMsg] = useState<string | null>(null);

  async function load() {
    try {
      const res = await fetch(`${API_BASE}/api/admin/users`, { credentials: 'include' });
      if (res.ok) {
        const data = await res.json();
        setUsers(data || []);
      } else {
        setMsg('Erreur récupération utilisateurs');
      }
    } catch (e) { setMsg('Erreur réseau'); }
  }

  useEffect(() => { load(); }, []); // eslint-disable-line

  async function setRole(id: number, role: string) {
    try {
      const r = await fetch(`${API_BASE}/api/admin/users/${id}/role?role=${encodeURIComponent(role)}`, { method: 'PUT', credentials: 'include' });
      if (r.ok) setMsg('Rôle modifié.'); else setMsg('Erreur modification rôle');
    } catch (e) { setMsg('Erreur réseau'); }
    load();
  }

  async function suspend(id: number, suspendFlag: boolean) {
    try {
      const r = await fetch(`${API_BASE}/api/admin/users/${id}/suspend?suspend=${suspendFlag}`, { method: 'PATCH', credentials: 'include' });
      if (r.ok) setMsg(suspendFlag ? 'Utilisateur suspendu.' : 'Utilisateur réactivé.'); else setMsg('Erreur suspension');
    } catch (e) { setMsg('Erreur réseau'); }
    load();
  }

  return (
    <div>
      <h4 className="text-lg mb-3">Gérer les utilisateurs</h4>
      <table className="w-full text-sm">
        <thead className="text-left text-white/70"><tr><th>Email</th><th>Role</th><th>Actions</th></tr></thead>
        <tbody>
          {users.map(u => (
            <tr key={u.id} className="border-t border-white/6">
              <td className="py-2">{u.email}</td>
              <td>{u.role}</td>
              <td>
                <select defaultValue={u.role} onChange={e => setRole(u.id, e.target.value)} className="mr-2 bg-white/6 p-1 rounded">
                  <option value="USER">USER</option>
                  <option value="ADMIN">ADMIN</option>
                </select>
                <button className="mr-2 px-2 py-1 bg-yellow-600 rounded" onClick={() => suspend(u.id, true)}>Suspendre</button>
                <button className="px-2 py-1 bg-green-600 rounded" onClick={() => suspend(u.id, false)}>Réactiver</button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
      {msg && <div className="mt-3 text-sm text-amber-200">{msg}</div>}
    </div>
  );
}
