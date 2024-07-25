# Reproducer for Quarkus #25496

This error causes generic object fetches of getEntity calls to fail, while typed calls will still function. This shows the body is present but not reachable. This project is a reproducer which uses a resource that calls itself through a rest client to demonstrate issue.

## Reproduction steps:

1. Start Quarkus server w/ `quarkus dev` or `mvn compile quarkus:dev`.
2. Check following URLs:
   - http://localhost:8080/hello/proxy
   - http://localhost:8080/hello/proxy-with-stream
   - http://localhost:8080/hello/proxy-with-casting
3. Additional samples provided for list support:
   - http://localhost:8080/hello/list/proxy
   - http://localhost:8080/hello/list/proxy-with-stream
   - http://localhost:8080/hello/list/proxy-with-casting

### Expected

All URLs should return a simple record JSON to the browser.

### Actual

Only with stream and exact casting does the object return.