// Basic install/activate
self.addEventListener("install", (event) => {
  self.skipWaiting();
});

self.addEventListener("activate", (event) => {
  event.waitUntil(self.clients.claim());
});

// Simple caching: cache-first for static assets; network-first for GET /api/*
const STATIC_CACHE = "static-v1";
const API_CACHE = "api-v1";

self.addEventListener("fetch", (event) => {
  const req = event.request;
  if (req.method !== "GET") return;

  const url = new URL(req.url);
  const isSameOrigin = url.origin === self.location.origin;
  const isStaticAsset =
    isSameOrigin &&
    (url.pathname.endsWith(".js") ||
      url.pathname.endsWith(".css") ||
      url.pathname.endsWith(".png") ||
      url.pathname.endsWith(".jpg") ||
      url.pathname.endsWith(".jpeg") ||
      url.pathname.endsWith(".svg") ||
      url.pathname.endsWith(".woff2"));

  const isApi = url.pathname.startsWith("/api/");

  if (isStaticAsset) {
    event.respondWith(
      caches.open(STATIC_CACHE).then(async (cache) => {
        const cached = await cache.match(req);
        if (cached) return cached;
        const resp = await fetch(req);
        cache.put(req, resp.clone());
        return resp;
      })
    );
    return;
  }

  if (isApi && isSameOrigin) {
    event.respondWith(
      caches.open(API_CACHE).then(async (cache) => {
        try {
          const resp = await fetch(req);
          cache.put(req, resp.clone());
          return resp;
        } catch (e) {
          const cached = await cache.match(req);
          if (cached) return cached;
          throw e;
        }
      })
    );
  }
});

