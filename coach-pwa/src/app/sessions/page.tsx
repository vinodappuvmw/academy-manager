import { LayoutShell } from "@/components/ui/LayoutShell";

const MOCK_SESSIONS = [
  {
    id: "s1",
    title: "U16 Tactical Drill",
    time: "6:00 PM",
    location: "Court 2",
    batch: "Batch A",
  },
  {
    id: "s2",
    title: "U14 Fitness",
    time: "7:15 PM",
    location: "Ground 1",
    batch: "Batch B",
  },
];

export default function SessionsPage() {
  return (
    <LayoutShell>
      <section className="screen-header">
        <h1 className="screen-title">Sessions</h1>
        <p className="screen-subtitle">Upcoming and recent training blocks.</p>
      </section>

      {MOCK_SESSIONS.map((s) => (
        <article key={s.id} className="card">
          <div className="pill-stat">
            <span className="pill-stat-dot" />
            {s.time} · {s.batch}
          </div>
          <div className="chip-row">
            <span className="chip chip-primary">{s.title}</span>
            <span className="chip">{s.location}</span>
          </div>
        </article>
      ))}
    </LayoutShell>
  );
}


