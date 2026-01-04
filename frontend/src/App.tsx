import React, { useEffect, useState } from 'react';
import DestinationCard from './components/DestinationCard';
import AdminPanel from './pages/AdminPanel';
import Login from './pages/Login';
import Register from './pages/Register';

type Destination = { id: number; name: string; description?: string; country?: string };

function App() {
  const [email, setEmail] = useState(localStorage.getItem('vc_email') || '');
  const [password, setPassword] = useState(localStorage.getItem('vc_pass') || '');
  const [message, setMessage] = useState<string | null>(null);
  const [destinations, setDestinations] = useState<Destination[]>([]);
  const [query, setQuery] = useState('');
  const [showAdmin, setShowAdmin] = useState(false);
  const [showRegister, setShowRegister] = useState(false);
  const [authEmail, setAuthEmail] = useState(localStorage.getItem('vc_auth_email') || '');
  const API_BASE = (process.env.REACT_APP_API_URL as string) ?? '';

  useEffect(() => {
    if (authEmail) fetchDestinations();
  }, [authEmail]);

  async function fetchDestinations() {
    setMessage(null);
    try {
      const res = await fetch(`${API_BASE}/api/destinations`, { credentials: 'include' });
      if (res.status === 401) {
        setMessage('Veuillez vous connecter pour voir les destinations.');
        setDestinations([]);
        return;
      }
      const data = await res.json();
      setDestinations(data || []);
    } catch (e) {
      setMessage('Erreur lors de la récupération des destinations.');
    }
  }

  async function book(destId: number) {
    setMessage(null);
    try {
      const res = await fetch(`${API_BASE}/api/bookings`, {
        method: 'POST',
        credentials: 'include',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ destinationId: destId, bookingDate: new Date().toISOString().slice(0, 10) }),
      });
      if (res.ok) {
        const data = await res.json();
        setMessage(`Réservation créée (id ${data.id}).`);
      } else if (res.status === 401) {
        setMessage('Authentification requise pour effectuer une réservation.');
      } else {
        const txt = await res.text();
        setMessage(`Erreur réservation: ${txt}`);
      }
    } catch (e) {
      setMessage('Erreur lors de la création de la réservation.');
    }
  }

  const filtered = destinations.filter(d => d.name.toLowerCase().includes(query.toLowerCase()) || (d.country || '').toLowerCase().includes(query.toLowerCase()));

  return (
    <div className="vc-bg min-h-screen text-white">
      <div className="max-w-6xl mx-auto p-6">
        <header className="flex items-center justify-between mb-8">
          <div>
            <h1 className="text-4xl font-extrabold tracking-tight">VoyageConnect</h1>
            <div className="vc-hero-line" />
          </div>
            <div className="flex items-center gap-3">
            <input value={query} onChange={e => setQuery(e.target.value)} placeholder="Rechercher..." className="px-3 py-2 rounded-md bg-white/6 placeholder:text-white/60" />
            <div className="text-sm text-white/70">{destinations.length} destinations</div>
            {authEmail && (
              <button className="ml-3 px-3 py-2 bg-amber-600 rounded" onClick={() => setShowAdmin(true)}>Admin</button>
            )}
          </div>
        </header>

        <section className="mb-6">
          {showRegister ? (
            <Register onShowLogin={() => setShowRegister(false)} />
          ) : (
            <Login onLogin={(em) => { setAuthEmail(em); localStorage.setItem('vc_auth_email', em); fetchDestinations(); }} onShowRegister={() => setShowRegister(true)} />
          )}
        </section>

        <section>
          <h2 className="text-xl mb-4">Destinations</h2>
          {filtered.length === 0 ? (
            <p className="text-white/60">Aucune destination trouvée. Essayez une autre recherche ou connectez-vous.</p>
          ) : (
            <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
              {filtered.map(d => (
                <DestinationCard key={d.id} id={d.id} name={d.name} description={d.description} country={d.country} onBook={book} />
              ))}
            </div>
          )}
        </section>
      </div>
      {showAdmin && <AdminPanel email={authEmail} password={''} onClose={() => setShowAdmin(false)} />}
    </div>
  );
}

export default App;
