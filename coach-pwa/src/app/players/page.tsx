import { LayoutShell } from "@/components/ui/LayoutShell";

const MOCK_PLAYERS = [
  { id: "p1", name: "Aarav", age: 15, level: "Advanced" },
  { id: "p2", name: "Diya", age: 14, level: "Intermediate" },
];

export default function PlayersPage() {
  return (
    <LayoutShell>
      <section className="screen-header">
        <h1 className="screen-title">Players</h1>
        <p className="screen-subtitle">Filter by age and level as in Figma.</p>
      </section>

      {MOCK_PLAYERS.map((p) => (
        <article key={p.id} className="card">
          <div className="screen-title" style={{ fontSize: 16 }}>
            {p.name}
          </div>
          <div className="chip-row">
            <span className="chip">{p.age} yrs</span>
            <span className="chip chip-primary">{p.level}</span>
          </div>
        </article>
      ))}
    </LayoutShell>
  );
}


