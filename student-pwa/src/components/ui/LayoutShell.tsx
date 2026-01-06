"use client";

import Link from "next/link";
import { usePathname } from "next/navigation";
import type { ReactNode } from "react";

const NAV_ITEMS = [
  { href: "/", label: "Home" },
  { href: "/ratings", label: "Ratings" },
  { href: "/workouts", label: "Workouts" },
  { href: "/profile", label: "Profile" },
];

export function LayoutShell({ children }: { children: ReactNode }) {
  const pathname = usePathname();

  return (
    <div className="app-root">
      <header className="app-bar">
        <div className="app-bar-title">Player</div>
      </header>
      <main className="app-main">{children}</main>
      <nav className="bottom-nav">
        {NAV_ITEMS.map((item) => {
          const active = pathname === item.href;
          return (
            <Link
              key={item.href}
              href={item.href}
              className={active ? "bottom-nav-item active" : "bottom-nav-item"}
            >
              <span className="bottom-nav-label">{item.label}</span>
            </Link>
          );
        })}
      </nav>
    </div>
  );
}


