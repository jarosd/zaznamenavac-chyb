--
-- PostgreSQL database dump
--

-- Dumped from database version 11.3 (Debian 11.3-1.pgdg90+1)
-- Dumped by pg_dump version 11.2

-- Started on 2019-05-28 16:35:41

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

DROP DATABASE vpa_jarosd;
--
-- TOC entry 2898 (class 1262 OID 16385)
-- Name: vpa_jarosd; Type: DATABASE; Schema: -; Owner: postgres
--

CREATE DATABASE vpa_jarosd WITH TEMPLATE = template0 ENCODING = 'UTF8' LC_COLLATE = 'en_US.utf8' LC_CTYPE = 'en_US.utf8';


ALTER DATABASE vpa_jarosd OWNER TO postgres;

\connect vpa_jarosd

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 200 (class 1259 OID 40962)
-- Name: autorizacne_kluce; Type: TABLE; Schema: public; Owner: docker
--

CREATE TABLE public.autorizacne_kluce (
    kluc character varying(50) NOT NULL,
    vytvoreny_dna date DEFAULT now() NOT NULL,
    blokovany boolean DEFAULT false NOT NULL,
    poznamka text
);


ALTER TABLE public.autorizacne_kluce OWNER TO docker;

--
-- TOC entry 197 (class 1259 OID 24580)
-- Name: pouzivatelia; Type: TABLE; Schema: public; Owner: docker
--

CREATE TABLE public.pouzivatelia (
    id_pouzivatela integer NOT NULL,
    meno character varying(30) COLLATE pg_catalog."C.UTF-8" NOT NULL,
    priezvisko character varying(30) COLLATE pg_catalog."C.UTF-8" NOT NULL,
    nick character varying(30) COLLATE pg_catalog."C.UTF-8" NOT NULL,
    heslo character varying(120) COLLATE pg_catalog."C.UTF-8" NOT NULL,
    email character varying(100) COLLATE pg_catalog."C.UTF-8" NOT NULL,
    typ_pouzivatela integer DEFAULT 1 NOT NULL
);


ALTER TABLE public.pouzivatelia OWNER TO docker;

--
-- TOC entry 196 (class 1259 OID 24578)
-- Name: pouzivatelia_id_pouzivatelia_seq; Type: SEQUENCE; Schema: public; Owner: docker
--

CREATE SEQUENCE public.pouzivatelia_id_pouzivatelia_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.pouzivatelia_id_pouzivatelia_seq OWNER TO docker;

--
-- TOC entry 2900 (class 0 OID 0)
-- Dependencies: 196
-- Name: pouzivatelia_id_pouzivatelia_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: docker
--

ALTER SEQUENCE public.pouzivatelia_id_pouzivatelia_seq OWNED BY public.pouzivatelia.id_pouzivatela;


--
-- TOC entry 199 (class 1259 OID 32772)
-- Name: typ_pouzivatela; Type: TABLE; Schema: public; Owner: docker
--

CREATE TABLE public.typ_pouzivatela (
    id_typu integer NOT NULL,
    typ character varying(30) NOT NULL,
    popis text
);


ALTER TABLE public.typ_pouzivatela OWNER TO docker;

--
-- TOC entry 198 (class 1259 OID 32770)
-- Name: typ_pouzivatela_id_typu_seq; Type: SEQUENCE; Schema: public; Owner: docker
--

CREATE SEQUENCE public.typ_pouzivatela_id_typu_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.typ_pouzivatela_id_typu_seq OWNER TO docker;

--
-- TOC entry 2901 (class 0 OID 0)
-- Dependencies: 198
-- Name: typ_pouzivatela_id_typu_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: docker
--

ALTER SEQUENCE public.typ_pouzivatela_id_typu_seq OWNED BY public.typ_pouzivatela.id_typu;


--
-- TOC entry 2752 (class 2604 OID 24583)
-- Name: pouzivatelia id_pouzivatela; Type: DEFAULT; Schema: public; Owner: docker
--

ALTER TABLE ONLY public.pouzivatelia ALTER COLUMN id_pouzivatela SET DEFAULT nextval('public.pouzivatelia_id_pouzivatelia_seq'::regclass);


--
-- TOC entry 2754 (class 2604 OID 32775)
-- Name: typ_pouzivatela id_typu; Type: DEFAULT; Schema: public; Owner: docker
--

ALTER TABLE ONLY public.typ_pouzivatela ALTER COLUMN id_typu SET DEFAULT nextval('public.typ_pouzivatela_id_typu_seq'::regclass);


--
-- TOC entry 2892 (class 0 OID 40962)
-- Dependencies: 200
-- Data for Name: autorizacne_kluce; Type: TABLE DATA; Schema: public; Owner: docker
--

INSERT INTO public.autorizacne_kluce (kluc, vytvoreny_dna, blokovany, poznamka) VALUES ('c02ed0fb-cc5a-4698-b035-1d6161557463', '2019-05-23', false, 'kľúč pre aplikáciu v Spring Boot');
INSERT INTO public.autorizacne_kluce (kluc, vytvoreny_dna, blokovany, poznamka) VALUES ('restapi', '2019-05-23', false, 'kľúč pre REST API');


--
-- TOC entry 2889 (class 0 OID 24580)
-- Dependencies: 197
-- Data for Name: pouzivatelia; Type: TABLE DATA; Schema: public; Owner: docker
--

INSERT INTO public.pouzivatelia (id_pouzivatela, meno, priezvisko, nick, heslo, email, typ_pouzivatela) VALUES (41, 'Test', 'Test', 'test', '$2a$12$qGOjzPJZPjxic9WclmXKHeaKoxAR3b22icttIBEc0qLs94ZGCtnfK', 'test@test.tt', 1);
INSERT INTO public.pouzivatelia (id_pouzivatela, meno, priezvisko, nick, heslo, email, typ_pouzivatela) VALUES (42, 'Test', 'Testovací', 'tester', '$2a$12$8K9YrpEW6i3LFYlpTTMYO.Ex6GckVNymKD81u5HVsQpinbcF9YpzO', 'tester@tester.sk', 1);


--
-- TOC entry 2891 (class 0 OID 32772)
-- Dependencies: 199
-- Data for Name: typ_pouzivatela; Type: TABLE DATA; Schema: public; Owner: docker
--

INSERT INTO public.typ_pouzivatela (id_typu, typ, popis) VALUES (1, 'REGULAR', NULL);
INSERT INTO public.typ_pouzivatela (id_typu, typ, popis) VALUES (2, 'ADMIN', NULL);
INSERT INTO public.typ_pouzivatela (id_typu, typ, popis) VALUES (3, 'SUPERADMIN', NULL);


--
-- TOC entry 2902 (class 0 OID 0)
-- Dependencies: 196
-- Name: pouzivatelia_id_pouzivatelia_seq; Type: SEQUENCE SET; Schema: public; Owner: docker
--

SELECT pg_catalog.setval('public.pouzivatelia_id_pouzivatelia_seq', 43, true);


--
-- TOC entry 2903 (class 0 OID 0)
-- Dependencies: 198
-- Name: typ_pouzivatela_id_typu_seq; Type: SEQUENCE SET; Schema: public; Owner: docker
--

SELECT pg_catalog.setval('public.typ_pouzivatela_id_typu_seq', 1, false);


--
-- TOC entry 2765 (class 2606 OID 40971)
-- Name: autorizacne_kluce autorizacne_kluce_pkey; Type: CONSTRAINT; Schema: public; Owner: docker
--

ALTER TABLE ONLY public.autorizacne_kluce
    ADD CONSTRAINT autorizacne_kluce_pkey PRIMARY KEY (kluc);


--
-- TOC entry 2759 (class 2606 OID 24585)
-- Name: pouzivatelia pouzivatelia_pkey; Type: CONSTRAINT; Schema: public; Owner: docker
--

ALTER TABLE ONLY public.pouzivatelia
    ADD CONSTRAINT pouzivatelia_pkey PRIMARY KEY (id_pouzivatela);


--
-- TOC entry 2763 (class 2606 OID 32780)
-- Name: typ_pouzivatela typ_pouzivatela_pkey; Type: CONSTRAINT; Schema: public; Owner: docker
--

ALTER TABLE ONLY public.typ_pouzivatela
    ADD CONSTRAINT typ_pouzivatela_pkey PRIMARY KEY (id_typu);


--
-- TOC entry 2761 (class 2606 OID 32794)
-- Name: pouzivatelia unique_nick; Type: CONSTRAINT; Schema: public; Owner: docker
--

ALTER TABLE ONLY public.pouzivatelia
    ADD CONSTRAINT unique_nick UNIQUE (nick);


--
-- TOC entry 2757 (class 1259 OID 32792)
-- Name: fki_fk_typ_pouzivatela; Type: INDEX; Schema: public; Owner: docker
--

CREATE INDEX fki_fk_typ_pouzivatela ON public.pouzivatelia USING btree (typ_pouzivatela);


--
-- TOC entry 2766 (class 2606 OID 32787)
-- Name: pouzivatelia fk_typ_pouzivatela; Type: FK CONSTRAINT; Schema: public; Owner: docker
--

ALTER TABLE ONLY public.pouzivatelia
    ADD CONSTRAINT fk_typ_pouzivatela FOREIGN KEY (typ_pouzivatela) REFERENCES public.typ_pouzivatela(id_typu) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 2899 (class 0 OID 0)
-- Dependencies: 2898
-- Name: DATABASE vpa_jarosd; Type: ACL; Schema: -; Owner: postgres
--

GRANT ALL ON DATABASE vpa_jarosd TO docker;


-- Completed on 2019-05-28 16:35:44

--
-- PostgreSQL database dump complete
--

