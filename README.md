pizza-toppings
==============

[![CircleCI](https://circleci.com/gh/bkhablenko/pizza-toppings.svg?style=shield)](https://circleci.com/gh/bkhablenko/pizza-toppings)

## Problem statement

The company aims to diversify its business by venturing into the food industry
and opening a pizzeria. During the process of collaborating with various food
supplier services in the area, it has become apparent that determining customer
preferences for toppings is crucial for making informed decisions about ordering
supplies.

## Requirements

1. Develop an endpoint that enables customers to submit their email addresses
   along with a list of toppings they prefer.

2. Implement an endpoint that allows the front-end team to access the list of
   currently submitted toppings, including the number of unique customers who
   have requested each topping.

3. Ensure that only the most recent topping submission is recorded.

4. Build the application serving these endpoints using Kotlin and Spring Boot.

Bonus points:

- Implement data persistence between application runs.
- Demonstrate creativity by incorporating additional features.
- Create a dedicated endpoint for displaying your personal topping choice.

## How to run

```bash
./gradlew clean build -x test && docker compose up --build
```

API documentation will be available at http://localhost:8090.

## Sample API requests

**NOTE:** You can authenticate with _any_ email address using an empty password.

### Update preferences

```bash
# john.smith@gmail.com
curl -i -X PUT 'localhost:8080/api/v1/user/preferences' \
  -H 'Authorization: Basic am9obi5zbWl0aEBnbWFpbC5jb206' \
  -H 'Content-Type: application/json' \
  --data '{"toppings":["bacon","mozzarella","pepperoni"]}'
```

```bash
# jane.doe@gmail.com
curl -i -X PUT 'localhost:8080/api/v1/user/preferences' \
  -H 'Authorization: Basic amFuZS5kb2VAZ21haWwuY29tOg==' \
  -H 'Content-Type: application/json' \
  --data '{"toppings":["bacon","pepperoni"]}'
```

### Get preferences

```bash
# john.smith@gmail.com
curl -i 'localhost:8080/api/v1/user/preferences' \
  -H 'Authorization: Basic am9obi5zbWl0aEBnbWFpbC5jb206'
```

```bash
# jane.doe@gmail.com
curl -i 'localhost:8080/api/v1/user/preferences' \
  -H 'Authorization: Basic amFuZS5kb2VAZ21haWwuY29tOg=='
```

### Get popular toppings

```bash
curl -i 'localhost:8080/api/v1/toppings/popular'
```

## Additional features

- App data is persisted between runs in PostgreSQL
- `Basic` authentication (for demonstration purposes)

### Observability

- **Logs** are written as JSON and ready for collection
- Prometheus **metrics** are exposed at http://localhost:8080/metrics
- OpenTelemetry **traces** are pushed directly to Jaeger, see http://localhost:16686

## Missing features

- [ ] Authorize endpoint access with OAuth 2
- [ ] Include trace ID as a response header
- [ ] Build the app and run tests with Docker Compose
- [ ] Implement an end-to-end test suite

## License

This project is licensed under the terms of the MIT license. See the [LICENSE](LICENSE) file for details.
