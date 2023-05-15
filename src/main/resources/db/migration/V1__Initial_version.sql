CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS "user_preferences" (
    "id"         uuid PRIMARY KEY DEFAULT uuid_generate_v4(),
    "email"      text NOT NULL UNIQUE,
    "toppings"   text[] NOT NULL DEFAULT '{}',
    "created_at" timestamp without time zone NOT NULL DEFAULT now(),
    "updated_at" timestamp without time zone NOT NULL DEFAULT now()
);
