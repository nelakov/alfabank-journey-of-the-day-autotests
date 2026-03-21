# Automation Summary

### Planned vs Done

| Metric | Value |
|:-------|:------|
| Planned scenarios | 26 |
| Implemented scenarios | 26 |
| Completion | 100% |

Everything from the [Automation Plan](Plan.md) was implemented.

### Risks that materialized

- **Dual-database complexity** — configuring and running the SUT against MySQL and PostgreSQL required extra setup effort and careful switching between JDBC URLs.
- **No technical specification** — absence of documentation made it difficult to distinguish between expected behavior and bugs, especially around form validation rules and notification timing.

### Time spent

| Metric | Hours |
|:-------|------:|
| Planned | 70–75 |
| Actual | ~65 |

### Artifacts

| Document | Link |
|:---------|:-----|
| Automation Plan | [Plan.md](Plan.md) |
| Test Report | [Report.md](Report.md) |
| Allure Report | Generated via `./gradlew allureServe` |
| Architecture Diagram | [architecture.png](architecture.png) |
