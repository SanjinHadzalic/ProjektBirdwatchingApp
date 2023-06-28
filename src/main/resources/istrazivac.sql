-- H2 2.1.214;
;             
CREATE USER IF NOT EXISTS "STUDENT" SALT 'ff763707533514ef' HASH 'de2d50d1fa63680c735150704feb5a9da02fb09c17c59fa06a5c8936a9637f0d' ADMIN;    
CREATE CACHED TABLE "PUBLIC"."ISTRAZIVAC"(
    "ID" BIGINT GENERATED ALWAYS AS IDENTITY(START WITH 1 RESTART WITH 33) NOT NULL,
    "IME" CHARACTER VARYING(50) NOT NULL,
    "PREZIME" CHARACTER VARYING(50) NOT NULL,
    "DATUM_RODJENJA" DATE NOT NULL,
    "INSTITUCIJA" CHARACTER VARYING(50) NOT NULL,
    "ADRESA" CHARACTER VARYING(50) NOT NULL,
    "TELEFON" INTEGER,
    "EMAIL" CHARACTER VARYING(50) NOT NULL
);      
ALTER TABLE "PUBLIC"."ISTRAZIVAC" ADD CONSTRAINT "PUBLIC"."CONSTRAINT_3" PRIMARY KEY("ID");   
-- 5 +/- SELECT COUNT(*) FROM PUBLIC.ISTRAZIVAC;              
INSERT INTO "PUBLIC"."ISTRAZIVAC"("ID", "IME", "PREZIME", "DATUM_RODJENJA", "INSTITUCIJA", "ADRESA", "TELEFON", "EMAIL") OVERRIDING SYSTEM VALUE VALUES(1, 'Luka', 'Lovrinovic', DATE '1979-05-25', 'HVI', 'Bosutska 27a', 987894561, 'llovrinovic12@gmail.com');             
INSERT INTO "PUBLIC"."ISTRAZIVAC"("ID", "IME", "PREZIME", "DATUM_RODJENJA", "INSTITUCIJA", "ADRESA", "TELEFON", "EMAIL") OVERRIDING SYSTEM VALUE VALUES(2, 'Ljiljana', 'Becmenica', DATE '1991-06-26', 'Udruga BIOM', 'Vrapcanska 15', 92457812, 'ljbecmenica2@gmail.com');   
INSERT INTO "PUBLIC"."ISTRAZIVAC"("ID", "IME", "PREZIME", "DATUM_RODJENJA", "INSTITUCIJA", "ADRESA", "TELEFON", "EMAIL") OVERRIDING SYSTEM VALUE VALUES(3, 'Ana', 'Medic', DATE '1992-05-25', 'Geo', 'Teleskopska 12', 98123123, 'amedic22@gmail.com');       
INSERT INTO "PUBLIC"."ISTRAZIVAC"("ID", "IME", "PREZIME", "DATUM_RODJENJA", "INSTITUCIJA", "ADRESA", "TELEFON", "EMAIL") OVERRIDING SYSTEM VALUE VALUES(4, 'Sanda', 'Katic', DATE '1993-04-27', 'Spark', 'Ferdinandska 17', 92123453, 'skatic88@gmail.com');  
INSERT INTO "PUBLIC"."ISTRAZIVAC"("ID", "IME", "PREZIME", "DATUM_RODJENJA", "INSTITUCIJA", "ADRESA", "TELEFON", "EMAIL") OVERRIDING SYSTEM VALUE VALUES(5, 'Borna', 'Lasrek', DATE '1989-05-03', 'ZelenO', 'Samoborska 12', 98123123, 'blasrek@zeleno.com');  
