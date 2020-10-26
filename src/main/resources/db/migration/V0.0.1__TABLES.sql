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

