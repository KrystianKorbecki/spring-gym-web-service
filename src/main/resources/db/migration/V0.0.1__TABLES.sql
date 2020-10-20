CREATE TABLE "user"
(
    "id_user" bigserial NOT NULL,
    "name" varchar(50),
    "last_name" varchar(50),
    "phone_number" char(12),
    "email_address" varchar(100) NOT NULL UNIQUE,
    "password" varchar(300) NOT NULL,
    "create_date" DATE NOT NULL,
    "code" varchar(8) UNIQUE,
    "id_trainer" bigint,
    "id_admin" bigint,
    CONSTRAINT "user_pk" PRIMARY KEY ("id_user")
) WITH (
      OIDS=FALSE
    );



CREATE TABLE "role"
(
    "id_role" serial NOT NULL,
    "role" varchar(20) NOT NULL,
    CONSTRAINT "role_pk" PRIMARY KEY ("id_role")
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

