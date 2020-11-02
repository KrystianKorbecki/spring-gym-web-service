CREATE TABLE "user"
(
    "id" bigserial NOT NULL,
    "user_name" varchar(50),
    "last_name" varchar(50),
    "phone_number" char(12),
    "email_address" varchar(100) NOT NULL UNIQUE,
    "password" varchar(400) NOT NULL,
    "create_date" DATE,
    "code" varchar(8) UNIQUE,
    "id_trainer" bigint,
    "id_admin" bigint,
    "active" boolean DEFAULT true,
    CONSTRAINT "user_pk" PRIMARY KEY ("id")
) WITH (
      OIDS=FALSE
    );

CREATE TABLE "role"
(
    "id" serial NOT NULL,
    "name" varchar(20) NOT NULL,
    CONSTRAINT "role_pk" PRIMARY KEY ("id")
) WITH (
      OIDS=FALSE
    );


CREATE TABLE "role_users"
(
    "id_user_role" bigserial NOT NULL,
    "id_role" bigint NOT NULL,
    "id_user" bigint NOT NULL,
    CONSTRAINT "user_role_pk" PRIMARY KEY ("id_user_role")
) WITH (
      OIDS=FALSE
    );

CREATE TABLE "ticket"
(
    "id_ticket" serial NOT NULL,
    "name_ticket" varchar(20) NOT NULL,
    "price" decimal NOT NULL,
    "validity" char NOT NULL,
    CONSTRAINT "ticket_pk" PRIMARY KEY ("id_ticket")
) WITH (
      OIDS=FALSE
    );

CREATE TABLE "user_ticket"
(
    "id_user_ticket" bigserial NOT NULL,
    "id_ticket" bigint NOT NULL,
    "id_user" bigint NOT NULL,
    "id_coupon" bigint NOT NULL,
    "purchase_date" DATE NOT NULL
) WITH (
      OIDS=FALSE
    );

CREATE TABLE "user_schedule"
(
    "id_user_schedule" serial NOT NULL,
    "id_user" bigint NOT NULL,
    "start_date" DATE NOT NULL,
    "end_date" DATE NOT NULL,
    CONSTRAINT "user_schedule_pk" PRIMARY KEY ("id_user_schedule")
) WITH (
      OIDS=FALSE
    );



CREATE TABLE "coupon"
(
    "id_coupon" serial NOT NULL,
    "discount" int NOT NULL,
    CONSTRAINT "coupon_pk" PRIMARY KEY ("id_coupon")
) WITH (
      OIDS=FALSE
    );

