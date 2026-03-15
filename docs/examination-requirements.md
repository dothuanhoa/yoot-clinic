# Examination Creation User Stories and Requirements

## 1. Objective

Define product requirements for creating a new examination so clinic staff can register a patient visit, capture examination details.

## 2. Actors

- Receptionist: create examination records during patient check-in.

## 3. User Stories

### US-01: Create New Examination

As a receptionist, I want to create a new examination so that the patient visit is recorded and charges are generated.

Acceptance criteria:

1. Given I am receptionist, when I submit a valid create request, then the system returns `201 Created`.
2. The request must include `patientId`, `examinationDate` (or defaults to current date), and at least one service item.
3. The response includes a unique `examinationRef` and calculated financial totals.
4. The system stores examination header and service details in one successful transaction.

### US-02: Validate Required Fields and Business Rules

As a cashier, I want strict validation so that invalid examination data is rejected before persistence.

Acceptance criteria:

1. Missing `patientId` returns `400 Bad Request` with `ERR-REQ-001`.
2. Empty service list returns `400 Bad Request` with `ERR-REQ-001`.
3. Negative discount or negative line-item amount returns `400 Bad Request` with `ERR-REQ-001`.
4. Invalid `serviceId` or `patientId` returns `404 Not Found` with domain-specific error code.

### US-03: Enforce Atomic Save Behavior

As an admin, I want the create flow to be atomic so that no partial examination data is stored.

Acceptance criteria:

1. If any service lookup or persistence step fails, the entire transaction is rolled back.
2. No partial examination header/detail records remain after a failed request.
3. Failure response contains a stable error code and diagnosable message.

## 4. Functional Requirements

### FR-01 Create Examination API Contract

1. Provide `POST /api/examinations`.
2. Request body supports:
- `patientId` (required)
- `referringPhysician` (optional)
- `examinationDate` (optional; defaults to current date)
- `notes` (optional)
- `discount` (optional; default `0`)
- `serviceDetails[]` (required; at least one item)
3. `serviceDetails[]` item supports:
- `serviceId` (required)
- `amount` (required, >= 0)
4. Response returns created resource fields including `examinationRef`, `patientId`, `examinationDate`, service details, and totals.

### FR-03 Validation and Domain Checks

1. Validate request schema and numeric constraints at API boundary.
2. Confirm patient exists before creation.
3. Confirm every referenced service exists before commit.
4. Apply default values for optional fields consistently.

### FR-04 Financial Calculation Rules

1. `totalAmount` equals sum of all service detail amounts.
2. `payableAmount` equals `totalAmount - discount`.

### FR-05 Error Handling Contract

1. `ERR-REQ-001` for request validation errors.
2. Not-found patient/service uses stable domain not-found error codes.
3. Error payload follows global API standard format.

### FR-06 Transaction and Consistency

1. Examination header and details are saved in one transaction.
2. Any failure during create must rollback all writes.
3. Foreign keys and indexes enforce referential integrity and query performance.

## 5. Non-Functional Requirements

1. Create endpoint target response time: <= 500 ms under normal clinic load.
2. Validation and transaction behavior must be covered by automated tests.
3. API contract remains backward compatible unless versioned.
4. Logging must be actionable without leaking sensitive patient data.

## 6. API Contract Summary

1. `POST /api/examinations`

## 7. Definition of Done

1. Create examination API is implemented.
2. Validation, defaults, and financial calculations are verified.
3. Transaction rollback behavior is tested for failure paths.
4. Audit logging is implemented and verified.
5. API documentation is published and reviewed by product/engineering.