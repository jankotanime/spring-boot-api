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
-- Name: achievement; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.achievement (
    id uuid NOT NULL,
    name text NOT NULL,
    prize integer
);


ALTER TABLE public.achievement OWNER TO postgres;

--
-- Name: plants; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.plants (
    id uuid NOT NULL,
    name character varying(255) NOT NULL,
    cost integer NOT NULL,
    max_level integer NOT NULL,
    minutes_to_level_up integer NOT NULL
);


ALTER TABLE public.plants OWNER TO postgres;

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
-- Name: reset_tokens; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.reset_tokens (
    id uuid NOT NULL,
    expires_at timestamp(6) with time zone NOT NULL,
    token character varying(255) NOT NULL,
    user_id uuid NOT NULL,
    created_at timestamp(6) with time zone NOT NULL
);


ALTER TABLE public.reset_tokens OWNER TO postgres;

--
-- Name: study_sessions; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.study_sessions (
    id uuid NOT NULL,
    user_id uuid NOT NULL,
    time_min integer NOT NULL,
    session_end_at timestamp(6) with time zone NOT NULL
);


ALTER TABLE public.study_sessions OWNER TO postgres;

--
-- Name: users; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.users (
    id uuid NOT NULL,
    created_at timestamp(6) with time zone DEFAULT now(),
    email character varying(255) NOT NULL,
    password character varying(255),
    username character varying(255) NOT NULL,
    google_id character varying(255),
    delete_at timestamp(6) with time zone
);


ALTER TABLE public.users OWNER TO postgres;

--
-- Data for Name: achievement; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.achievement (id, name, prize) FROM stdin;
\.


--
-- Data for Name: plants; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.plants (id, name, cost, max_level, minutes_to_level_up) FROM stdin;
f10bb90e-f30a-4693-89e4-6c7bcf5e0e1c	Orchid	15	8	80
767402b0-a096-421d-9d5f-b72ee5054077	Parsies	5	5	30
\.


--
-- Data for Name: refresh_tokens; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.refresh_tokens (id, created_at, expires_at, token, user_id) FROM stdin;
\.


--
-- Data for Name: reset_tokens; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.reset_tokens (id, expires_at, token, user_id, created_at) FROM stdin;
\.


--
-- Data for Name: study_sessions; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.study_sessions (id, user_id, time_min, session_end_at) FROM stdin;
\.


--
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.users (id, created_at, email, password, username, google_id, delete_at) FROM stdin;
0c0d9319-b559-4922-a58a-e075606ae3af	2025-08-12 18:11:56.85664+00	superjanek333@gmail.com	$2a$10$j.xbNJRSAnEUTI0NKm4uce1dnuyiV07EiWQmS28UTDt.oPqzFBAPS	jasiuuu	\N	\N
\.


--
-- Name: achievement achievement_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.achievement
    ADD CONSTRAINT achievement_pkey PRIMARY KEY (id);


--
-- Name: plants plants_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.plants
    ADD CONSTRAINT plants_pkey PRIMARY KEY (id);


--
-- Name: refresh_tokens refresh_tokens_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.refresh_tokens
    ADD CONSTRAINT refresh_tokens_pkey PRIMARY KEY (id);


--
-- Name: reset_tokens reset_tokens_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.reset_tokens
    ADD CONSTRAINT reset_tokens_pkey PRIMARY KEY (id);


--
-- Name: study_sessions study_sessions_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.study_sessions
    ADD CONSTRAINT study_sessions_pkey PRIMARY KEY (id);


--
-- Name: users users_google_id_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_google_id_key UNIQUE (google_id);


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
-- Name: study_sessions fk1pw09mt08ohr4r1cuu33qwyw0; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.study_sessions
    ADD CONSTRAINT fk1pw09mt08ohr4r1cuu33qwyw0 FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- Name: reset_tokens fkk9rk4pnm1ya8wcmy78r9gmuxv; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.reset_tokens
    ADD CONSTRAINT fkk9rk4pnm1ya8wcmy78r9gmuxv FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- PostgreSQL database dump complete
--

