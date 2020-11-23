CREATE TABLE "user"
(
    "id" bigserial NOT NULL,
    "user_name" varchar(50),
    "last_name" varchar(50),
    "phone_number" char(12),
    "email_address" varchar(100) NOT NULL UNIQUE,
    "password" varchar(400) NOT NULL,
    "create_date" TIMESTAMP DEFAULT CURRENT_TIMESTAMP(2),
    "code" varchar(8) UNIQUE,
    "id_trainer" bigint,
    "id_admin" bigint,
    "active" boolean DEFAULT true,
    "email_confirmed" boolean DEFAULT false,
    "birthday_date" date,
    "gender" varchar(10),
    CONSTRAINT "user_pk" PRIMARY KEY ("id")
) WITH (
      OIDS=FALSE
    );

CREATE TYPE roles AS ENUM ('ROLE_USER', 'ROLE_TRAINER', 'ROLE_ADMIN', 'ROLE_BASIC', 'ROLE_SUPERADMIN', 'ROLE_MODERATOR');

CREATE TABLE "role"
(
    "id" serial NOT NULL,
    "name" varchar(20) NOT NULL ,
    CONSTRAINT "role_pk" PRIMARY KEY ("id")
) WITH (
      OIDS=FALSE
    );

CREATE TABLE "role_users"
(
    "id" bigserial NOT NULL,
    "id_role" bigint NOT NULL,
    "id_user" bigint NOT NULL,
    CONSTRAINT "user_role_pk" PRIMARY KEY ("id")
) WITH (
      OIDS=FALSE
    );

CREATE TABLE "ticket"
(
    "id" smallserial NOT NULL,
    "name_ticket" varchar(20) NOT NULL,
    "price" decimal(12,2) NOT NULL,
    "validity_days" int NOT NULL,
    CONSTRAINT "ticket_pk" PRIMARY KEY ("id")
) WITH (
      OIDS=FALSE
    );

CREATE TABLE "user_ticket"
(
    "id" bigserial NOT NULL,
    "id_ticket" int NOT NULL,
    "id_user" bigint NOT NULL,
    "id_coupon" bigint NOT NULL,
    "purchase_date" TIMESTAMP DEFAULT CURRENT_TIMESTAMP(2),
    "start_date" date not null ,
    CONSTRAINT "ticket_ticket_pk" PRIMARY KEY ("id")
) WITH (
      OIDS=FALSE
    );

CREATE TABLE "schedule"
(
    "id" serial NOT NULL,
    "id_user" bigint NOT NULL,
    "start_date" TIMESTAMP[7] NOT NULL,
    "end_date" TIMESTAMP[7] NOT NULL,
    CONSTRAINT "schedule_pk" PRIMARY KEY ("id")
) WITH (
      OIDS=FALSE
    );

CREATE TABLE "coupon"
(
    "id" serial NOT NULL,
    "discount" int,
    "discount_percent" int,
    "name" varchar(20) NOT NULL,
    "code" varchar(6) NOT NULL,
    CONSTRAINT "coupon_pk" PRIMARY KEY ("id")
) WITH (
      OIDS=FALSE
    );


CREATE TABLE "exercise" (
                            "id" serial NOT NULL,
                            "name" varchar(20) NOT NULL,
                            "description" TEXT NOT NULL,
                            CONSTRAINT "exercise_pk" PRIMARY KEY ("id")
) WITH (
      OIDS=FALSE
    );



CREATE TABLE "training_plan" (
                                 "id" serial NOT NULL,
                                 "id_user" bigint NOT NULL,
                                 "id_trainer" bigint NOT NULL,
                                 "day_of_week" varchar(10) NOT NULL,
                                 "propose_rest_exercise" TIME[] NOT NULL,
                                 CONSTRAINT "training_plan_pk" PRIMARY KEY ("id")
) WITH (
      OIDS=FALSE
    );



CREATE TABLE "training_plan_exercise"
(
    "id" serial NOT NULL,
    "id_training_plan" bigint NOT NULL,
    "id_exercise" bigint NOT NULL,
    "description" TEXT NOT NULL,
    "propose_series" smallint[] NOT NULL,
    "propose_weight" DECIMAL[] NOT NULL,
    "propose_repeat" smallint[] NOT NULL,
    "propose_rest" TIME[] NOT NULL,
    CONSTRAINT "training_plan_exercise_pk" PRIMARY KEY ("id")
) WITH (
      OIDS=FALSE
    );



CREATE TABLE "complete_exercise" (
                                     "id" serial NOT NULL,
                                     "id_training_plan_exercise" bigint NOT NULL,
                                     "duration" TIME NOT NULL,
                                     "weight" DECIMAL[] NOT NULL,
                                     "series" smallint[] NOT NULL,
                                     "note" TEXT NOT NULL,
                                     "rest" TIME[] NOT NULL,
                                     CONSTRAINT "complete_exercise_pk" PRIMARY KEY ("id")
) WITH (
      OIDS=FALSE
    );



CREATE TABLE "complete_training_plan"
(
    "id" serial NOT NULL,
    "id_training_plan" bigint NOT NULL,
    "start_date" TIMESTAMP NOT NULL,
    "end_date" TIMESTAMP NOT NULL,
    "rest_exercise" TIME[] NOT NULL,
    CONSTRAINT "complete_training_plan_pk" PRIMARY KEY ("id")
) WITH (
      OIDS=FALSE
    );






