import React, { useEffect, useState } from 'react';

type Dest = { id: number; name: string; description?: string; country?: string; price?: number | null; active?: boolean };

const API_BASE = (process.env.REACT_APP_API_URL as string) ?? '';

// use cookie-based auth: include credentials on requests

export default function AdminDestinations({ email, password }: { email: string; password: string }) {
  const [list, setList] = useState<Dest[]>([]);
  const [loading, setLoading] = useState(false);
  const [msg, setMsg] = useState<string | null>(null);
  const [form, setForm] = useState({ name: '', country: '', description: '', price: '' });

  async function load() {
    setLoading(true);
    try {
      const res = await fetch(`${API_BASE}/api/admin/destinations`, { credentials: 'include' });
      const data = await res.json();
      setList(data || []);
    } catch (e) {
      // noop
    } finally { setLoading(false); }
  }

  useEffect(() => { load(); }, []); // eslint-disable-line

  async function create() {
    try {
      const payload = { name: form.name, country: form.country, description: form.description, price: form.price ? Number(form.price) : null, active: true };
      const res = await fetch(`${API_BASE}/api/admin/destinations`, { method: 'POST', credentials: 'include', headers: { 'Content-Type': 'application/json' }, body: JSON.stringify(payload) });
      if (res.ok) { setForm({ name: '', country: '', description: '', price: '' }); setMsg('Destination ajoutée.'); load(); }
      else { const t = await res.text(); setMsg('Erreur ajout: ' + t); }
    } catch (e) {}
  }

  async function toggle(id: number, suspend: boolean) {
    try {
      const r = await fetch(`${API_BASE}/api/admin/destinations/${id}/suspend?suspend=${suspend}`, { method: 'PATCH', credentials: 'include' });
      if (r.ok) setMsg('Mise à jour effectuée.'); else setMsg('Erreur mise à jour');
    } catch (e) { setMsg('Erreur réseau'); }
    load();
  }

  async function remove(id: number) {
    try {
      const r = await fetch(`${API_BASE}/api/admin/destinations/${id}`, { method: 'DELETE', credentials: 'include' });
      if (r.ok) setMsg('Destination supprimée.'); else setMsg('Erreur suppression');
    } catch (e) { setMsg('Erreur réseau'); }
    load();
  }

  return (
    <div>
      <h4 className="text-lg mb-3">Gérer les destinations</h4>
      <div className="mb-4 grid grid-cols-1 sm:grid-cols-4 gap-2">
        <input placeholder="Nom" value={form.name} onChange={e => setForm(f => ({ ...f, name: e.target.value }))} className="p-2 rounded bg-white/6" />
        <input placeholder="Pays" value={form.country} onChange={e => setForm(f => ({ ...f, country: e.target.value }))} className="p-2 rounded bg-white/6" />
        <input placeholder="Prix" value={form.price} onChange={e => setForm(f => ({ ...f, price: e.target.value }))} className="p-2 rounded bg-white/6" />
        <button className="px-3 py-2 bg-green-600 rounded" onClick={create}>Ajouter</button>
      </div>

      {loading ? <div>Chargement...</div> : (
        <table className="w-full text-sm">
          <thead className="text-left text-white/70"><tr><th>Nom</th><th>Pays</th><th>Prix</th><th>Actif</th><th>Actions</th></tr></thead>
          <tbody>
            {list.map(d => (
              <tr key={d.id} className="border-t border-white/6">
                <td className="py-2">{d.name}</td>
                <td>{d.country}</td>
                <td>{d.price ?? '-'}</td>
                <td>{d.active ? 'oui' : 'non'}</td>
                <td className="py-2">
                  <button className="mr-2 px-2 py-1 bg-yellow-600 rounded" onClick={() => toggle(d.id, !d.active)}>Suspendre</button>
                  <button className="px-2 py-1 bg-red-600 rounded" onClick={() => remove(d.id)}>Supprimer</button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
      {msg && <div className="mt-3 text-sm text-amber-200">{msg}</div>}
    </div>
  );
}
