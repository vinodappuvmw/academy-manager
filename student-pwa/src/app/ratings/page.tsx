import { LayoutShell } from "@/components/ui/LayoutShell";

export default function RatingsPage() {
  return (
    <LayoutShell>
      <section className="screen-header">
        <h1 className="screen-title">Ratings</h1>
        <p className="screen-subtitle">
          Attribute-wise breakdown similar to the progress view in Figma.
        </p>
      </section>

      <section className="card">
        <div className="screen-subtitle" style={{ marginBottom: 8 }}>
          Overall
        </div>
        <div className="pill-stat">
          <span className="pill-stat-dot" />
          4.2 / 5
        </div>
      </section>

      <section className="card" style={{ marginTop: 16 }}>
        <div className="screen-subtitle" style={{ marginBottom: 8 }}>
          Attributes
        </div>
        <div className="chip-row">
          <span className="chip chip-primary">Passing · 4.5</span>
          <span className="chip chip-primary">Shooting · 4.0</span>
          <span className="chip">Header · 3.8</span>
        </div>
      </section>
    </LayoutShell>
  );
}


