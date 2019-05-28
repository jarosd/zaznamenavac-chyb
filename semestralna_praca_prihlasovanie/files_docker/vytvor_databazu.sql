CREATE USER docker WITH ENCRYPTED PASSWORD 'docker';

-- nepoužívať pomlčky, vytvorenie tabulky
CREATE DATABASE vpa_jarosd;
GRANT ALL PRIVILEGES ON DATABASE vpa_jarosd TO docker;