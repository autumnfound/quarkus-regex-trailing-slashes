# Reproducer for Quarkus Reactive REST package issue

When attempting to navigate to endpoints that use regex within the path expression (i.e. `@Path("{somePath:[a-z]{2}}")`), trailing slashes do not properly route to the resource and instead return an error that the path cannot be found.

This seems to be a regression/missed aspect for the fix associated with https://github.com/quarkusio/quarkus/issues/26016.

## Reproduction steps:

1. With server started, open the following URLs in browser to confirm that endpoints do properly exist + route:
- http://localhost:8080/hello/working/123
- http://localhost:8080/hello/non-working/123
- http://localhost:8080/hello/non-working/123/second-test
1. In browser, open the following links to observe behaviour with trailing slashes
- http://localhost:8080/hello/working/123/
- http://localhost:8080/hello/non-working/123/
- http://localhost:8080/hello/non-working/123/second-test/  

All 3 URLs in step 2 should pass, but only the first link does

### Expected

In cases where there are regex expressions within the `@Path` value, adding trailing slashes to the endpoint should route to the same endpoint.

### Actual

When a trailing slash is present in URL for endpoints that contain a regex in the path, the endpoint doesn't properly route. 