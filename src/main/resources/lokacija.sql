-- H2 2.1.214;
;             
CREATE USER IF NOT EXISTS "STUDENT" SALT 'ff763707533514ef' HASH 'de2d50d1fa63680c735150704feb5a9da02fb09c17c59fa06a5c8936a9637f0d' ADMIN;    
CREATE CACHED TABLE "PUBLIC"."LOKACIJA"(
    "ID" BIGINT GENERATED ALWAYS AS IDENTITY(START WITH 1 RESTART WITH 33) NOT NULL,
    "NAZIV" CHARACTER VARYING(50) NOT NULL,
    "TIP" CHARACTER VARYING(50) NOT NULL,
    "X_COORD" NUMERIC(10, 8) NOT NULL,
    "Y_COORD" NUMERIC(10, 8) NOT NULL
);     
ALTER TABLE "PUBLIC"."LOKACIJA" ADD CONSTRAINT "PUBLIC"."CONSTRAINT_A" PRIMARY KEY("ID");     
-- 6 +/- SELECT COUNT(*) FROM PUBLIC.LOKACIJA;
INSERT INTO "PUBLIC"."LOKACIJA"("ID", "NAZIV", "TIP", "X_COORD", "Y_COORD") OVERRIDING SYSTEM VALUE VALUES(1, 'Ucka', 'PP', 45.24401300, 14.21229500);        
INSERT INTO "PUBLIC"."LOKACIJA"("ID", "NAZIV", "TIP", "X_COORD", "Y_COORD") OVERRIDING SYSTEM VALUE VALUES(2, 'Mljet', 'NP', 42.74675980, 17.39564970);       
INSERT INTO "PUBLIC"."LOKACIJA"("ID", "NAZIV", "TIP", "X_COORD", "Y_COORD") OVERRIDING SYSTEM VALUE VALUES(3, 'Palud', 'ostalo', 45.03230090, 13.68814510);   
INSERT INTO "PUBLIC"."LOKACIJA"("ID", "NAZIV", "TIP", "X_COORD", "Y_COORD") OVERRIDING SYSTEM VALUE VALUES(4, 'Brijuni', 'NP', 44.23301300, 14.21245600);     
INSERT INTO "PUBLIC"."LOKACIJA"("ID", "NAZIV", "TIP", "X_COORD", "Y_COORD") OVERRIDING SYSTEM VALUE VALUES(5, 'Savica', 'ostalo', 43.74675980, 17.39564970);  
INSERT INTO "PUBLIC"."LOKACIJA"("ID", "NAZIV", "TIP", "X_COORD", "Y_COORD") OVERRIDING SYSTEM VALUE VALUES(6, 'Papuk', 'PP', 44.11130090, 12.68456510);       