import { LayoutShell } from "@/components/ui/LayoutShell";

export default function Home() {
  return (
    <LayoutShell>
      <section className="screen-header">
        <h1 className="screen-title">Hi, Player</h1>
        <p className="screen-subtitle">
          Overview of your next session, ratings and workouts.
        </p>
      </section>

      <section className="card">
        <div className="pill-stat">
          <span className="pill-stat-dot" />
          Next session · Today 6:00 PM
        </div>
        <div className="chip-row">
          <span className="chip chip-primary">U16 Batch A</span>
          <span className="chip">Coach name</span>
        </div>
      </section>

      <section className="card" style={{ marginTop: 16 }}>
        <h2 className="screen-subtitle">Quick links</h2>
        <div className="chip-row">
          <span className="chip chip-primary">My ratings</span>
          <span className="chip">Workouts</span>
          <span className="chip">Payments</span>
        </div>
      </section>
    </LayoutShell>
  );
}
