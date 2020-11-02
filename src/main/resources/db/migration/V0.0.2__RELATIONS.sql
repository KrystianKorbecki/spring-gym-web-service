ALTER TABLE "role_users" ADD CONSTRAINT "user_role_fk0" FOREIGN KEY ("id_role") REFERENCES "role"("id");
ALTER TABLE "role_users" ADD CONSTRAINT "user_role_fk1" FOREIGN KEY ("id_user") REFERENCES "user"("id");
ALTER TABLE "user_ticket" ADD CONSTRAINT "user_ticket_fk0" FOREIGN KEY ("id_ticket") REFERENCES "ticket"("id_ticket");
ALTER TABLE "user_ticket" ADD CONSTRAINT "user_ticket_fk1" FOREIGN KEY ("id_user") REFERENCES "user"("id");
ALTER TABLE "user_ticket" ADD CONSTRAINT "user_ticket_fk2" FOREIGN KEY ("id_coupon") REFERENCES "coupon"("id_coupon");
ALTER TABLE "user_schedule" ADD CONSTRAINT "user_schedule_fk0" FOREIGN KEY ("id_user") REFERENCES "user"("id");
