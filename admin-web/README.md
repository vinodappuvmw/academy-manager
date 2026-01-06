## Admin Web (Next.js + TypeScript)

Purpose: Desktop-first console for CRUD (students/coaches/locations/batches/sessions), payments/reminders, attendance/payment summaries, attribute config, matches/tournaments, dashboards.

Tech: Next.js (App Router) + TS, React Query, RHF + Zod, UI library (MUI/Chakra/Tailwind), shared OpenAPI client. Not PWA-focused.

Next steps:
1) Add Prettier and stricter ESLint config.
2) Wire shared API client and layout shell.
3) Add auth guard and initial CRUD pages.

## Getting Started

Run the dev server:

```bash
npm run dev
```

Open http://localhost:3000 to view. Edit `app/page.tsx` and the page auto-updates.

