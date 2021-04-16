CREATE TABLE "user"
(
    "id" bigserial NOT NULL,
    "user_name" varchar(50),
    "last_name" varchar(50),
    "phone_number" char(12),
    "email_address" varchar(100) NOT NULL UNIQUE,
    "password" varchar(400) NOT NULL,
    "create_date" TIMESTAMP DEFAULT CURRENT_TIMESTAMP(2),
    "confirmation_code" varchar(32) UNIQUE,
    "chat_code" varchar(32) UNIQUE,
    "email_confirm" bool default false,
    "id_trainer" bigint,
    "id_admin" bigint,
    "active" boolean DEFAULT true,
    "email_confirmed" boolean DEFAULT false,
    "profile_name" varchar(50) UNIQUE  NOT NULL,
    "birthday_date" date,
    "gender" varchar(10),
    "photo_name" varchar(70),
    "id_profile" bigint,
    "id_address" bigint,
    "id_gym_address" bigint,
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
    "start_date" TIMESTAMP NOT NULL,
    "end_date" TIMESTAMP NOT NULL,
    CONSTRAINT "schedule_pk" PRIMARY KEY ("id")
) WITH (
      OIDS=FALSE
    );

CREATE TABLE "coupon"
(
    "id" serial NOT NULL,
    "discount" decimal(5, 2),
    "discount_percent" int,
    "name" varchar(20) NOT NULL,
    "code" varchar(6) NOT NULL,
    CONSTRAINT "coupon_pk" PRIMARY KEY ("id")
) WITH (
      OIDS=FALSE
    );


CREATE TABLE "exercise"
(
    "id" serial NOT NULL,
    "name" varchar(20) NOT NULL,
    "description" TEXT NOT NULL,
    CONSTRAINT "exercise_pk" PRIMARY KEY ("id")
) WITH (
      OIDS=FALSE
    );



CREATE TABLE "training_plan"
(
    "id" serial NOT NULL,
    "id_user" bigint NOT NULL,
    "id_trainer" bigint NOT NULL,
    "day_of_week" varchar(10) NOT NULL,
    "propose_rest_exercise" INTEGER[] NOT NULL,
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
    "propose_weight" DECIMAL[] NOT NULL,
    "propose_repeat" INTEGER[] NOT NULL,
    "propose_rest" INTEGER[] NOT NULL,
    CONSTRAINT "training_plan_exercise_pk" PRIMARY KEY ("id")
) WITH (
      OIDS=FALSE
    );



CREATE TABLE "complete_exercise"
(
                                     "id" serial NOT NULL,
                                     "id_training_plan_exercise" bigint NOT NULL,
                                     "duration" TIME NOT NULL,
                                     "weight" DECIMAL[] NOT NULL,
                                     "repeat" INTEGER[] NOT NULL,
                                     "rest" INTEGER[] NOT NULL,
                                     "note" TEXT NOT NULL,
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
    "rest_exercise" INTEGER[] NOT NULL,
    CONSTRAINT "complete_training_plan_pk" PRIMARY KEY ("id")
) WITH (
      OIDS=FALSE
    );

CREATE TABLE "message"
(
    "id" serial NOT NULL,
    "from_user_id" bigint NOT NULL,
    "to_user_id" bigint NOT NULL,
    "message" TEXT NOT NULL,
    "date" TIMESTAMP,
    "status" varchar(30),
    CONSTRAINT "message_pk" PRIMARY KEY ("id")
) WITH (
      OIDS=FALSE
    );

CREATE TABLE "reports"
(
    "id" serial NOT NULL,
    "create_date" DATE NOT NULL,
    "path" varchar(70) NOT NULL,
    "name" varchar(70) NOT NULL,
    CONSTRAINT "reports_pk" PRIMARY KEY ("id")
) WITH (
      OIDS=FALSE
    );



CREATE TABLE "user_reports"
(
    "id" serial NOT NULL,
    "id_user" bigint NOT NULL,
    "id_reports" bigint NOT NULL,
    CONSTRAINT "user_reports_pk" PRIMARY KEY ("id")
) WITH (
      OIDS=FALSE
    );


CREATE TABLE "address"
(
    "id" serial NOT NULL,
    "city" varchar(70) NOT NULL,
    "street" varchar(70),
    "house_number" varchar(70) NOT NULL,
    "apartment_number" varchar(70),
    "voivodeship" varchar(20) NOT NULL,
    "postal_code" varchar(6) NOT NULL,
    CONSTRAINT "address_pk" PRIMARY KEY ("id")
) WITH (
      OIDS=FALSE
    );



CREATE TABLE "gym_address"
(
    "id" serial NOT NULL,
    "id_address" bigint NOT NULL,
    "place_name" varchar(70),
    CONSTRAINT "gym_address_pk" PRIMARY KEY ("id")
) WITH (
      OIDS=FALSE
    );



CREATE TABLE "profile"
(
    "id" serial NOT NULL,
    "description" TEXT,
    "public" BOOLEAN NOT NULL DEFAULT 'true',
    CONSTRAINT "profile_pk" PRIMARY KEY ("id")
) WITH (
      OIDS=FALSE
    );



CREATE TABLE "photo"
(
    "id" serial NOT NULL,
    "id_profile" bigint,
    "path" varchar(70) NOT NULL,
    "name" varchar(70) NOT NULL,
    "description" TEXT,
    "like" bigint NOT NULL,
    "unlike" bigint NOT NULL,
    CONSTRAINT "photo_pk" PRIMARY KEY ("id")
) WITH (
      OIDS=FALSE
    );



CREATE TABLE "comment"
(
    "id" serial NOT NULL,
    "id_photo" bigint,
    "id_comment" bigint NOT NULL,
    "id_user" bigint NOT NULL,
    "date" DATE NOT NULL,
    "description" TEXT NOT NULL,
    "like" bigint NOT NULL,
    "unlike" bigint NOT NULL,
    CONSTRAINT "comment_pk" PRIMARY KEY ("id")
) WITH (
      OIDS=FALSE
    );




