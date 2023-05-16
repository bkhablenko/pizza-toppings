pizza-toppings
==============

[![CircleCI](https://circleci.com/gh/bkhablenko/pizza-toppings.svg?style=shield)](https://circleci.com/gh/bkhablenko/pizza-toppings)

## How to run

```bash
./gradlew clean build -x test && docker compose up --build
```

API documentation will be available at http://localhost:8090.

## Sample API requests

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
