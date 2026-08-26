# Retailer Ordering Platform + CRM Integration (India)

Role-based B2B ordering platform for retailers, with GST-compliant order
processing and bidirectional CRM sync.

## Architecture

- **Frontend:** Angular (web), role-gated views for Retailer / Franchise / Admin
- **Backend:** Spring Boot, REST APIs, JWT-secured
- **Database:** PostgreSQL (Render) — system of record for app data + CRM mirror
- **Async processing:** Kafka-pattern outbound sync (simulated via `@Async` for
  this demo; production swaps in a real Kafka cluster without touching the
  service layer)
- **CRM integration:** outbound REST/Composite upserts with external-ID
  idempotency; inbound CDC-style webhook for catalog/pricing sync

## Order lifecycle


Each transition:
1. Validates against the allowed state machine
2. Notifies the retailer in real time (in-app / push)
3. Queues a delta-only outbound sync to CRM

## GST handling

- Retailer and franchise each carry a GSTIN and home state
- Same state → CGST + SGST split (9% + 9% for an 18% rate)
- Different state → IGST (full 18%)
- Tax rate resolved per line item from the product's HSN code, not hardcoded

## Local setup

```bash
# Backend
cd backend
mvn spring-boot:run          # http://localhost:8080

# Frontend
cd frontend
ng serve                     # http://localhost:4200
```

## Deployment

- Backend: Render (auto-detects `pom.xml`, set env vars below)
- Frontend: Vercel (`ng build`, output directory `dist/retailer-ordering-ui`)

## Environment variables (Render)

| Key | Value |
|---|---|
| `DATABASE_URL` | Render Postgres connection string |
| `DATABASE_USERNAME` | from Render Postgres dashboard |
| `DATABASE_PASSWORD` | from Render Postgres dashboard |
| `JWT_SECRET` | random 32+ char string |

## Roles

| Role | Access |
|---|---|
| Retailer | Own catalog view, place orders, track own order status |
| Franchise | Incoming order queue for mapped region, update order status |
| Admin | Full metrics dashboard, all orders, all franchises |

## Tech notes for reviewers

- Kafka is simulated with `@Async` + a scheduled retry loop in
  `CrmOutboundSyncService` — same contract shape as the real design, easy
  swap-in point later
- CRM Pub/Sub CDC subscription is simulated as a webhook endpoint
  (`/api/crm/inbound/catalog-change`) with the same field-ownership rules
  the real integration would enforce
