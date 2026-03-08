# Service Categories User Stories and Requirements

## 1. Objective

Define product requirements for viewing, filtering, and correctly classifying service categories for API usage and daily report generation.

## 2. Actors

- Receptionist: browse and select medical services when preparing examinations.

## 3. User Stories

### US-01: View Full Service Catalog

As a receptionist, I want to view all medical services so that I can choose the correct service for billing.

Acceptance criteria:

1. Given I am authenticated as `USER`, when I call `GET /api/medical-services`, then the system returns `200 OK` with all services.
2. Results are sorted by `code` ascending.
3. Each item includes `id`, `code`, `name`, `price`, `category`, and `isActive`.

### US-02: Filter Services by Category

As a doctor, I want to filter services by category so that I can quickly find the relevant service group.

Acceptance criteria:

1. Given I am authenticated, when I call `GET /api/medical-services?category=DONG_Y|TAY_Y|KHAC`, then the system returns only matching services.
2. Filtered results are sorted by `code` ascending.
3. Response status is `200 OK`.

### US-03: Find Service by Code

As a receptionist, I want to find a service by code so that I can validate I selected the correct service.

Acceptance criteria:

1. Given a valid code, when I call `GET /api/medical-services?code={code}`, then the system returns `200 OK` and the matching service.
2. Given a non-existing code, the system returns `404 Not Found` with error code `ERR-MED-001`.
3. If `code` exceeds 50 characters, the system returns `400 Bad Request` with `ERR-REQ-001`.

## 4. Functional Requirements

### FR-01 Service Catalog Retrieval

1. Provide `GET /api/medical-services` for full catalog retrieval.
2. Provide `GET /api/medical-services?code={code}` for single-code lookup.
3. Provide `GET /api/medical-services?category={category}` for category lookup.
4. Reject requests that include both `code` and `category`.

### FR-02 Error Handling Contract

1. `ERR-MED-001` for not found service.
2. `ERR-MED-002` for invalid service request combinations.
3. `ERR-REQ-001` for request validation failures.
4. Error payload shape must follow global API standard.

### FR-03 Seed Data Consistency

1. Default services and categories are seeded by Flyway (`V1__seed_medical_services.sql`).
2. Seed operation is idempotent via `ON CONFLICT (code) DO UPDATE`.

## 5. Non-Functional Requirements

1. Category mapping should remain O(1) lookup for `(code, category)` in reporting flow.
2. Category mapping and lookup behavior must be covered by automated unit tests.
3. API contract fields must remain backward compatible unless versioned.
4. Logging for unmapped services should be low-noise (`debug`) and diagnosable.

## 6. API Contract Summary

1. `GET /api/medical-services`
2. `GET /api/medical-services?code={code}`
3. `GET /api/medical-services?category={DONG_Y|TAY_Y|KHAC}`

## 7. Definition of Done

1. Service-category documentation is published.
2. User stories and acceptance criteria are reviewed by product/engineering.
3. Existing automated tests for category mapping and service-code lookup pass.
4. API consumers can implement against the documented contracts without clarifications.

