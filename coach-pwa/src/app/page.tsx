import { LayoutShell } from "@/components/ui/LayoutShell";

export default function Home() {
  return (
    <LayoutShell>
      <section className="screen-header">
        <h1 className="screen-title">Today&apos;s sessions</h1>
        <p className="screen-subtitle">
          Quick view of your upcoming coaching schedule.
        </p>
      </section>

      <section className="card">
        <div className="pill-stat">
          <span className="pill-stat-dot" />
          3 sessions · 24 players
        </div>
        <div className="chip-row">
          <span className="chip chip-primary">Next · 6:00 PM · U16 Batch A</span>
          <span className="chip">Indoor · Court 2</span>
        </div>
      </section>

      <section className="card" style={{ marginTop: 16 }}>
        <h2 className="screen-subtitle">Shortcuts</h2>
        <div className="chip-row">
          <span className="chip chip-primary">Mark attendance</span>
          <span className="chip">Rate players</span>
          <span className="chip">Upload session plan</span>
        </div>
      </section>
    </LayoutShell>
  );
}
