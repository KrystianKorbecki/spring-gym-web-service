ALTER TABLE "role_users" ADD CONSTRAINT "user_role_fk0" FOREIGN KEY ("id_role") REFERENCES "role"("id_role");
ALTER TABLE "role_users" ADD CONSTRAINT "user_role_fk1" FOREIGN KEY ("id_user") REFERENCES "user"("id_user");
