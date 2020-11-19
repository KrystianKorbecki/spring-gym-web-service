ALTER TABLE "role_users" ADD CONSTRAINT "user_role_fk0" FOREIGN KEY ("id_role") REFERENCES "role"("id");
ALTER TABLE "role_users" ADD CONSTRAINT "user_role_fk1" FOREIGN KEY ("id_user") REFERENCES "user"("id");
ALTER TABLE "user_ticket" ADD CONSTRAINT "user_ticket_fk0" FOREIGN KEY ("id_ticket") REFERENCES "ticket"("id");
ALTER TABLE "user_ticket" ADD CONSTRAINT "user_ticket_fk1" FOREIGN KEY ("id_user") REFERENCES "user"("id");
ALTER TABLE "user_ticket" ADD CONSTRAINT "user_ticket_fk2" FOREIGN KEY ("id_coupon") REFERENCES "coupon"("id");
ALTER TABLE "schedule" ADD CONSTRAINT "user_schedule_fk0" FOREIGN KEY ("id_user") REFERENCES "user"("id");
ALTER TABLE "training_plan" ADD CONSTRAINT "training_plan_fk0" FOREIGN KEY ("id_user") REFERENCES "user"("id");


ALTER TABLE "propose_exercise_training_plan" ADD CONSTRAINT "propose_exercise_training_plan_fk0" FOREIGN KEY ("id_training_plan") REFERENCES "training_plan"("id");
ALTER TABLE "propose_exercise_training_plan" ADD CONSTRAINT "propose_exercise_training_plan_fk1" FOREIGN KEY ("id_exercise") REFERENCES "exercise"("id");

ALTER TABLE "exercise_training_plan" ADD CONSTRAINT "exercise_training_plan_fk0" FOREIGN KEY ("id_training_plan") REFERENCES "training_plan"("id");
ALTER TABLE "exercise_training_plan" ADD CONSTRAINT "exercise_training_plan_fk1" FOREIGN KEY ("id_exercise") REFERENCES "exercise"("id");
