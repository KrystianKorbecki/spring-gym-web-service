ALTER TABLE "role_users" ADD CONSTRAINT "user_role_fk0" FOREIGN KEY ("id_role") REFERENCES "role"("id");
ALTER TABLE "role_users" ADD CONSTRAINT "user_role_fk1" FOREIGN KEY ("id_user") REFERENCES "user"("id");
ALTER TABLE "user_ticket" ADD CONSTRAINT "user_ticket_fk0" FOREIGN KEY ("id_ticket") REFERENCES "ticket"("id");
ALTER TABLE "user_ticket" ADD CONSTRAINT "user_ticket_fk1" FOREIGN KEY ("id_user") REFERENCES "user"("id");
ALTER TABLE "user_ticket" ADD CONSTRAINT "user_ticket_fk2" FOREIGN KEY ("id_coupon") REFERENCES "coupon"("id");
ALTER TABLE "schedule" ADD CONSTRAINT "user_schedule_fk0" FOREIGN KEY ("id_user") REFERENCES "user"("id");
ALTER TABLE "training_plan" ADD CONSTRAINT "training_plan_fk0" FOREIGN KEY ("id_user") REFERENCES "user"("id");

ALTER TABLE "training_plan_exercise" ADD CONSTRAINT "training_plan_exercise_fk0" FOREIGN KEY ("id_training_plan") REFERENCES "training_plan"("id");
ALTER TABLE "training_plan_exercise" ADD CONSTRAINT "training_plan_exercise_fk1" FOREIGN KEY ("id_exercise") REFERENCES "exercise"("id");

ALTER TABLE "complete_exercise" ADD CONSTRAINT "complete_exercise_fk0" FOREIGN KEY ("id_training_plan_exercise") REFERENCES "training_plan_exercise"("id");

ALTER TABLE "complete_training_plan" ADD CONSTRAINT "complete_training_plan_fk0" FOREIGN KEY ("id_training_plan") REFERENCES "training_plan"("id");

ALTER TABLE "message" ADD CONSTRAINT "message_fk0" FOREIGN KEY ("from_user_id") REFERENCES "user"("id");
ALTER TABLE "message" ADD CONSTRAINT "message_fk1" FOREIGN KEY ("to_user_id") REFERENCES "user"("id");
ALTER TABLE "user" ADD CONSTRAINT "user_fk0" FOREIGN KEY ("id_profile") REFERENCES "profile"("id");
