import { LayoutShell } from "@/components/ui/LayoutShell";

export default function ProfilePage() {
  return (
    <LayoutShell>
      <section className="screen-header">
        <h1 className="screen-title">Coach profile</h1>
        <p className="screen-subtitle">
          Basic info and quick stats styled similar to the Figma profile screen.
        </p>
      </section>

      <section className="card">
        <div className="screen-title" style={{ fontSize: 18, marginBottom: 4 }}>
          Coach Name
        </div>
        <p className="screen-subtitle">Football · PSC Academy</p>
        <div className="chip-row">
          <span className="chip chip-primary">12 active players</span>
          <span className="chip">3 batches</span>
        </div>
      </section>
    </LayoutShell>
  );
}


