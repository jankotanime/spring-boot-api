--
-- PostgreSQL database dump
--

-- Dumped from database version 14.18 (Debian 14.18-1.pgdg120+1)
-- Dumped by pg_dump version 14.18 (Debian 14.18-1.pgdg120+1)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: refresh_tokens; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.refresh_tokens (
    id uuid NOT NULL,
    created_at timestamp(6) with time zone NOT NULL,
    expires_at timestamp(6) with time zone NOT NULL,
    token character varying(255) NOT NULL,
    user_id uuid NOT NULL
);


ALTER TABLE public.refresh_tokens OWNER TO postgres;

--
-- Name: users; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.users (
    id uuid NOT NULL,
    created_at timestamp(6) with time zone,
    email character varying(255) NOT NULL,
    password character varying(255) NOT NULL,
    username character varying(255) NOT NULL
);


ALTER TABLE public.users OWNER TO postgres;

--
-- Data for Name: refresh_tokens; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.refresh_tokens (id, created_at, expires_at, token, user_id) FROM stdin;
4c5922c0-682b-463f-ae64-9777fbebed21	2025-07-20 12:06:26.567514+00	2025-07-20 13:06:26.567514+00	$2a$10$xQ0Ze4vvBjKa4uANr1GuU.SKNKv9gAuQkIfHeFNdAZ7NomiIoFMi2	11bcbf2c-7a91-4d5f-8058-4b90ac90b03e
45ec43d7-cae1-42e5-8d3c-e4c6b77a239a	2025-07-20 12:06:27.619009+00	2025-07-20 13:06:27.619009+00	$2a$10$NGc4svTFD6cCNFz8vLS0J.EwD9EUKZWFzA86tW5XJsl5SkASaLEkq	11bcbf2c-7a91-4d5f-8058-4b90ac90b03e
0cb96be1-ff5c-40ae-98fd-ef9eaa473292	2025-07-20 12:06:54.559557+00	2025-07-20 13:06:54.559557+00	$2a$10$2uKBf8Vtczf112oasWDDt.erto89tV.nulj500Q2f/.MNSPI0/mEe	11bcbf2c-7a91-4d5f-8058-4b90ac90b03e
f499be82-47e7-4f03-88af-236fd85b2523	2025-07-20 12:06:56.144372+00	2025-07-20 13:06:56.144372+00	$2a$10$9Ul1u8MFcx8EWTYm3x2SluYzddU5.sb26uk.JjZnBkw8MMPVBxaP6	11bcbf2c-7a91-4d5f-8058-4b90ac90b03e
\.


--
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.users (id, created_at, email, password, username) FROM stdin;
11bcbf2c-7a91-4d5f-8058-4b90ac90b03e	\N	misio@m.m	$2a$10$fHzXI/toJsDaww4N2zO.PuotniH5tHz5Zk1mwWSi5iA8Jlv6O3GRC	misio
\.


--
-- Name: refresh_tokens refresh_tokens_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.refresh_tokens
    ADD CONSTRAINT refresh_tokens_pkey PRIMARY KEY (id);


--
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- Name: refresh_tokens fk1lih5y2npsf8u5o3vhdb9y0os; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.refresh_tokens
    ADD CONSTRAINT fk1lih5y2npsf8u5o3vhdb9y0os FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- PostgreSQL database dump complete
--

