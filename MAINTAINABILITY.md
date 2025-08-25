Project: HotelReservation

This document summarizes quick wins and a roadmap to improve maintainability, readability, and robustness.

Quick wins implemented in this commit
- Clarified transactional boundaries in ReservationService:
  - Read operations use @Transactional(readOnly = true) to optimize and document intent.
  - Update and delete operations are explicitly @Transactional.
- Made calculateNights a static helper for clarity of side-effect-free computation.
- Removed misleading dateFormat usage on Instant fields in ReservationMapper (MapStruct dateFormat does not apply to Instant-to-Instant mapping), reducing confusion for future maintainers.

Recommended next steps (low-to-medium effort)
1. Validation and DTOs
   - Add Bean Validation annotations to ReservationInfo: e.g., @NotBlank on firstName/lastName/nationality/roomName, @Positive on nights, consider @FutureOrPresent on checkInDate. Keep service-level cross-field validation (check-out after check-in) as is.
   - Consider separating input DTO (command) from output DTO (view), so internal IDs and computed fields (totalPrice) are controlled explicitly.
2. Business rules and conflicts
   - Prevent double-booking: before saving, check overlapping reservations for the same room, ideally using a repository query with a unique constraint or pessimistic/optimistic locking strategy.
   - Move price calculation rules into a dedicated PricingService (future-proofing for seasonal pricing, taxes, discounts).
3. Error handling
   - Enrich Error enum with codes to be stable for clients; return both code and message.
   - Add logging (WARN for validation, ERROR for unexpected) in RestExceptionHandler.
4. Mapping
   - Consider using MapStruct for all DTOs, and centralize mapping logic; add unit tests for important mappings.
5. Persistence and entities
   - Add @EqualsAndHashCode(onlyExplicitlyIncluded = true) or business-key equality if entities are compared in memory.
   - Add indexes/constraints at the DB level (e.g., index on ROOM_ID and date range if you implement availability checks).
6. API design
   - Define explicit response models for POST/PUT (return created resource or a location header).
   - Improve OpenAPI annotations to include schemas/examples; consider springdoc-openapi for live docs.
7. Testing
   - Add service-layer unit tests covering: validation rules, pricing, client find-or-create, and mapper conversions.
   - Add repository tests for availability/overlap checks when implemented.
   - Add web layer tests for controller endpoints (MockMvc).
8. Observability & ops
   - Add request/response logging (mask PII), correlation IDs, and basic metrics (request counts, error rates).
9. Project hygiene
   - Add Checkstyle/Spotless and Error Prone; enable nullity annotations where appropriate.
   - Add a CONTRIBUTING.md and expand README with environment, run instructions, and API examples.

Notes
- The current domain model is clean and easy to extend. The above steps focus on making behaviors explicit, improving error surfaces, and preparing for future complexity (pricing, availability).