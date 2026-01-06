import { LayoutShell } from "@/components/ui/LayoutShell";

export default function WorkoutsPage() {
  return (
    <LayoutShell>
      <section className="screen-header">
        <h1 className="screen-title">Workouts</h1>
        <p className="screen-subtitle">Home workout cards inspired by Figma.</p>
      </section>

      <section className="card">
        <div className="screen-title" style={{ fontSize: 16, marginBottom: 4 }}>
          Lower body strength
        </div>
        <p className="screen-subtitle">4 drills · 20 min</p>
        <div className="chip-row">
          <span className="chip chip-primary">Today</span>
          <span className="chip">Legs</span>
        </div>
      </section>
    </LayoutShell>
  );
}


