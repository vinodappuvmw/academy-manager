import { LayoutShell } from "@/components/ui/LayoutShell";

export default function ProfilePage() {
  return (
    <LayoutShell>
      <section className="screen-header">
        <h1 className="screen-title">Profile</h1>
        <p className="screen-subtitle">
          Player info and history, styled like the Naadanz profile screen.
        </p>
      </section>

      <section className="card">
        <div className="screen-title" style={{ fontSize: 18, marginBottom: 4 }}>
          Player Name
        </div>
        <p className="screen-subtitle">U16 · Right winger</p>
        <div className="chip-row">
          <span className="chip chip-primary">Attendance 92%</span>
          <span className="chip">12 matches</span>
        </div>
      </section>
    </LayoutShell>
  );
}


