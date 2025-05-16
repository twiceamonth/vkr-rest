--
-- PostgreSQL database dump
--

-- Dumped from database version 17.2 (Debian 17.2-1.pgdg120+1)
-- Dumped by pg_dump version 17.2 (Debian 17.2-1.pgdg120+1)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: vkr; Type: SCHEMA; Schema: -; Owner: postgres
--

CREATE SCHEMA vkr;


ALTER SCHEMA vkr OWNER TO postgres;

--
-- Name: uuid-ossp; Type: EXTENSION; Schema: -; Owner: -
--

CREATE EXTENSION IF NOT EXISTS "uuid-ossp" WITH SCHEMA public;


--
-- Name: EXTENSION "uuid-ossp"; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION "uuid-ossp" IS 'generate universally unique identifiers (UUIDs)';


SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: achievements_dictionary; Type: TABLE; Schema: vkr; Owner: postgres
--

CREATE TABLE vkr.achievements_dictionary (
    achievement_id uuid DEFAULT public.uuid_generate_v4() NOT NULL,
    title character varying(25) NOT NULL,
    description character varying(150) NOT NULL,
    reset_on_event boolean DEFAULT false NOT NULL,
    reset_event_type character varying(15),
    criteria_type character varying(15) NOT NULL,
    criteria_value integer NOT NULL
);


ALTER TABLE vkr.achievements_dictionary OWNER TO postgres;

--
-- Name: achievements_progress; Type: TABLE; Schema: vkr; Owner: postgres
--

CREATE TABLE vkr.achievements_progress (
    progress_id uuid DEFAULT public.uuid_generate_v4() NOT NULL,
    user_login character varying(25) NOT NULL,
    progress_value integer DEFAULT 0 NOT NULL,
    is_completed boolean DEFAULT false NOT NULL,
    complete_date date,
    achievement_id uuid NOT NULL
);


ALTER TABLE vkr.achievements_progress OWNER TO postgres;

--
-- Name: active_boss; Type: TABLE; Schema: vkr; Owner: postgres
--

CREATE TABLE vkr.active_boss (
    active_boss_id uuid DEFAULT public.uuid_generate_v4() NOT NULL,
    boss_id uuid NOT NULL,
    user_login character varying(25) NOT NULL,
    is_completed boolean DEFAULT false NOT NULL,
    current_hp smallint NOT NULL
);


ALTER TABLE vkr.active_boss OWNER TO postgres;

--
-- Name: active_effect_event; Type: TABLE; Schema: vkr; Owner: postgres
--

CREATE TABLE vkr.active_effect_event (
    active_effect_id uuid DEFAULT public.uuid_generate_v4() NOT NULL,
    effect_event_id uuid NOT NULL,
    start_date date NOT NULL,
    end_date date NOT NULL,
    is_completed boolean DEFAULT false NOT NULL
);


ALTER TABLE vkr.active_effect_event OWNER TO postgres;

--
-- Name: active_event; Type: TABLE; Schema: vkr; Owner: postgres
--

CREATE TABLE vkr.active_event (
    active_event_id uuid DEFAULT public.uuid_generate_v4() NOT NULL,
    event_id uuid NOT NULL,
    start_date date NOT NULL,
    end_date date NOT NULL,
    is_completed boolean DEFAULT false NOT NULL
);


ALTER TABLE vkr.active_event OWNER TO postgres;

--
-- Name: bonuses; Type: TABLE; Schema: vkr; Owner: postgres
--

CREATE TABLE vkr.bonuses (
    bonus_id uuid DEFAULT public.uuid_generate_v4() NOT NULL,
    bonus_type character varying(15) NOT NULL,
    description character varying(200) NOT NULL,
    effect character varying(15) NOT NULL,
    multiplier integer NOT NULL
);


ALTER TABLE vkr.bonuses OWNER TO postgres;

--
-- Name: bosses_dictionary; Type: TABLE; Schema: vkr; Owner: postgres
--

CREATE TABLE vkr.bosses_dictionary (
    boss_id uuid DEFAULT public.uuid_generate_v4() NOT NULL,
    boss_name character varying(20) NOT NULL,
    criteria_type character varying(15) NOT NULL,
    criteria_value smallint NOT NULL,
    max_hp smallint NOT NULL,
    resist_type character varying(15) NOT NULL,
    resist_value smallint NOT NULL,
    bonus_type character varying(15) NOT NULL,
    bonus_value smallint NOT NULL,
    base_damage smallint DEFAULT 1 NOT NULL,
    image_path text NOT NULL
);


ALTER TABLE vkr.bosses_dictionary OWNER TO postgres;

--
-- Name: character; Type: TABLE; Schema: vkr; Owner: postgres
--

CREATE TABLE vkr."character" (
    user_login character varying(25) NOT NULL,
    character_id uuid DEFAULT public.uuid_generate_v4() NOT NULL,
    character_name character varying(25) NOT NULL,
    gender boolean DEFAULT false NOT NULL,
    hair_id uuid NOT NULL,
    chestplate_id uuid NOT NULL,
    foots_id uuid NOT NULL,
    legs_id uuid NOT NULL,
    background_id uuid NOT NULL,
    character_type character varying(25) NOT NULL,
    level integer DEFAULT 0 NOT NULL,
    max_hp integer DEFAULT 50 NOT NULL,
    current_hp integer DEFAULT 50 NOT NULL,
    exp integer DEFAULT 0 NOT NULL,
    coins integer DEFAULT 0 NOT NULL,
    stress_coef smallint DEFAULT 0 NOT NULL,
    is_dead boolean DEFAULT false NOT NULL,
    base_damage smallint DEFAULT 1 NOT NULL,
    created_at time with time zone DEFAULT now() NOT NULL,
    dead_at time with time zone,
    mood_level smallint DEFAULT 100 NOT NULL
);


ALTER TABLE vkr."character" OWNER TO postgres;

--
-- Name: character_type; Type: TABLE; Schema: vkr; Owner: postgres
--

CREATE TABLE vkr.character_type (
    character_type character varying(25) NOT NULL,
    image_path text NOT NULL,
    bonus_id uuid NOT NULL
);


ALTER TABLE vkr.character_type OWNER TO postgres;

--
-- Name: details; Type: TABLE; Schema: vkr; Owner: postgres
--

CREATE TABLE vkr.details (
    details_id uuid DEFAULT public.uuid_generate_v4() NOT NULL,
    link_url text NOT NULL,
    link_name character varying(25) NOT NULL,
    task_id uuid NOT NULL
);


ALTER TABLE vkr.details OWNER TO postgres;

--
-- Name: dialogs; Type: TABLE; Schema: vkr; Owner: postgres
--

CREATE TABLE vkr.dialogs (
    dialog_id uuid DEFAULT public.uuid_generate_v4() NOT NULL,
    dialog_text text NOT NULL,
    is_for_mood smallint NOT NULL
);


ALTER TABLE vkr.dialogs OWNER TO postgres;

--
-- Name: difficulty; Type: TABLE; Schema: vkr; Owner: postgres
--

CREATE TABLE vkr.difficulty (
    difficulty_name character varying(15) NOT NULL,
    multiplier integer NOT NULL
);


ALTER TABLE vkr.difficulty OWNER TO postgres;

--
-- Name: effect_event_dictionary; Type: TABLE; Schema: vkr; Owner: postgres
--

CREATE TABLE vkr.effect_event_dictionary (
    effect_event_id uuid DEFAULT public.uuid_generate_v4() NOT NULL,
    effect_name character varying(25) NOT NULL,
    description character varying(100) NOT NULL,
    effect_icon text NOT NULL,
    chance smallint NOT NULL,
    criteria_type character varying(15) NOT NULL,
    criteria_value smallint NOT NULL
);


ALTER TABLE vkr.effect_event_dictionary OWNER TO postgres;

--
-- Name: event_dictionary; Type: TABLE; Schema: vkr; Owner: postgres
--

CREATE TABLE vkr.event_dictionary (
    event_id uuid DEFAULT public.uuid_generate_v4() NOT NULL,
    event_name character varying(25) NOT NULL,
    description character varying(100) NOT NULL,
    event_icon text NOT NULL,
    money smallint NOT NULL,
    exp smallint NOT NULL,
    criteria_type character varying(15) NOT NULL,
    criteria_value smallint NOT NULL
);


ALTER TABLE vkr.event_dictionary OWNER TO postgres;

--
-- Name: frequency; Type: TABLE; Schema: vkr; Owner: postgres
--

CREATE TABLE vkr.frequency (
    frequency_name character varying(15) NOT NULL
);


ALTER TABLE vkr.frequency OWNER TO postgres;

--
-- Name: habit; Type: TABLE; Schema: vkr; Owner: postgres
--

CREATE TABLE vkr.habit (
    user_login character varying(25) NOT NULL,
    habit_id uuid DEFAULT public.uuid_generate_v4() NOT NULL,
    title character varying(50) NOT NULL,
    difficulty character varying(15) NOT NULL,
    frequency character varying(15) NOT NULL,
    timer_interval time without time zone,
    description character varying(500),
    created_at timestamp with time zone DEFAULT now() NOT NULL,
    streak_count integer DEFAULT 0 NOT NULL,
    last_performed_at timestamp with time zone,
    base_exp integer DEFAULT 5 NOT NULL
);


ALTER TABLE vkr.habit OWNER TO postgres;

--
-- Name: item; Type: TABLE; Schema: vkr; Owner: postgres
--

CREATE TABLE vkr.item (
    item_id uuid DEFAULT public.uuid_generate_v4() NOT NULL,
    store_id uuid NOT NULL,
    user_login character varying(25) NOT NULL
);


ALTER TABLE vkr.item OWNER TO postgres;

--
-- Name: level; Type: TABLE; Schema: vkr; Owner: postgres
--

CREATE TABLE vkr.level (
    level integer NOT NULL,
    exp integer NOT NULL,
    max_hp integer NOT NULL
);


ALTER TABLE vkr.level OWNER TO postgres;

--
-- Name: priority; Type: TABLE; Schema: vkr; Owner: postgres
--

CREATE TABLE vkr.priority (
    priority_name character varying(15) NOT NULL,
    multiplier integer NOT NULL
);


ALTER TABLE vkr.priority OWNER TO postgres;

--
-- Name: refresh_tokens; Type: TABLE; Schema: vkr; Owner: postgres
--

CREATE TABLE vkr.refresh_tokens (
    token_id uuid DEFAULT public.uuid_generate_v4() NOT NULL,
    user_id character varying(25),
    token_hash character varying(128) NOT NULL,
    expires_at timestamp without time zone NOT NULL
);


ALTER TABLE vkr.refresh_tokens OWNER TO postgres;

--
-- Name: store; Type: TABLE; Schema: vkr; Owner: postgres
--

CREATE TABLE vkr.store (
    store_id uuid DEFAULT public.uuid_generate_v4() NOT NULL,
    cost smallint NOT NULL,
    lvl_to_buy integer NOT NULL,
    title character varying(25) NOT NULL,
    type character varying(10) NOT NULL,
    image_path text NOT NULL,
    description character varying(200) NOT NULL
);


ALTER TABLE vkr.store OWNER TO postgres;

--
-- Name: subtask; Type: TABLE; Schema: vkr; Owner: postgres
--

CREATE TABLE vkr.subtask (
    subtask_id uuid DEFAULT public.uuid_generate_v4() NOT NULL,
    title character varying(50) NOT NULL,
    task_id uuid NOT NULL,
    status boolean DEFAULT false NOT NULL
);


ALTER TABLE vkr.subtask OWNER TO postgres;

--
-- Name: task; Type: TABLE; Schema: vkr; Owner: postgres
--

CREATE TABLE vkr.task (
    task_id uuid DEFAULT public.uuid_generate_v4() NOT NULL,
    user_login character varying(25) NOT NULL,
    title character varying(50) NOT NULL,
    end_time timestamp with time zone,
    difficulty character varying(15),
    priority character varying(15),
    frequency character varying(15),
    status boolean DEFAULT false NOT NULL,
    timer_interval time without time zone,
    description character varying(500),
    created_at timestamp with time zone DEFAULT now() NOT NULL,
    finished_at timestamp with time zone,
    base_exp integer DEFAULT 5 NOT NULL
);


ALTER TABLE vkr.task OWNER TO postgres;

--
-- Name: user; Type: TABLE; Schema: vkr; Owner: postgres
--

CREATE TABLE vkr."user" (
    login character varying(25) NOT NULL,
    password character varying(128) NOT NULL,
    registered_at date DEFAULT now() NOT NULL
);


ALTER TABLE vkr."user" OWNER TO postgres;

--
-- Name: user_event_progress; Type: TABLE; Schema: vkr; Owner: postgres
--

CREATE TABLE vkr.user_event_progress (
    progress_id uuid DEFAULT public.uuid_generate_v4() NOT NULL,
    active_event_id uuid NOT NULL,
    user_login character varying(25) NOT NULL,
    progress smallint DEFAULT 0 NOT NULL,
    is_completed boolean DEFAULT false NOT NULL
);


ALTER TABLE vkr.user_event_progress OWNER TO postgres;

--
-- Data for Name: achievements_dictionary; Type: TABLE DATA; Schema: vkr; Owner: postgres
--

COPY vkr.achievements_dictionary (achievement_id, title, description, reset_on_event, reset_event_type, criteria_type, criteria_value) FROM stdin;
eeb12c81-7583-4731-bfba-14988e26a44c	ccv	qw3f	f	efe	wv	24
2b1b7398-0d7e-4ac4-9b5c-f5943a47a774	qwe	qwe	f	qwe	qwe	2
\.


--
-- Data for Name: achievements_progress; Type: TABLE DATA; Schema: vkr; Owner: postgres
--

COPY vkr.achievements_progress (progress_id, user_login, progress_value, is_completed, complete_date, achievement_id) FROM stdin;
02cc8f22-39e6-4d47-b63b-49ad1d1c3289	test	0	f	\N	2b1b7398-0d7e-4ac4-9b5c-f5943a47a774
\.


--
-- Data for Name: active_boss; Type: TABLE DATA; Schema: vkr; Owner: postgres
--

COPY vkr.active_boss (active_boss_id, boss_id, user_login, is_completed, current_hp) FROM stdin;
5356e300-8b58-40de-bb37-7caf6f5aef08	96b0e063-fc0e-4795-a439-4c7d03debc57	test	t	-2
\.


--
-- Data for Name: active_effect_event; Type: TABLE DATA; Schema: vkr; Owner: postgres
--

COPY vkr.active_effect_event (active_effect_id, effect_event_id, start_date, end_date, is_completed) FROM stdin;
374050a5-cebc-4bc5-8fd3-93598302e4a7	f498ec4a-14c9-4527-9d21-7149bc7e1d81	2025-03-25	2025-04-01	f
\.


--
-- Data for Name: active_event; Type: TABLE DATA; Schema: vkr; Owner: postgres
--

COPY vkr.active_event (active_event_id, event_id, start_date, end_date, is_completed) FROM stdin;
de17e3ac-16d9-4ad3-bede-242880347043	47b4c8d5-5d70-4b82-8a49-810dc60c4b32	2025-03-02	2025-03-09	f
\.


--
-- Data for Name: bonuses; Type: TABLE DATA; Schema: vkr; Owner: postgres
--

COPY vkr.bonuses (bonus_id, bonus_type, description, effect, multiplier) FROM stdin;
876ec630-7666-4b05-8bf3-a77fa522d9cc	123	123	123	123
ced75317-da34-45f6-b880-d67bd065f5f5	qwe	qwe	qwe	123
\.


--
-- Data for Name: bosses_dictionary; Type: TABLE DATA; Schema: vkr; Owner: postgres
--

COPY vkr.bosses_dictionary (boss_id, boss_name, criteria_type, criteria_value, max_hp, resist_type, resist_value, bonus_type, bonus_value, base_damage, image_path) FROM stdin;
96b0e063-fc0e-4795-a439-4c7d03debc57	sdfdsfsd	asdf	123	123	qwe	123	qwe	123	1	sdfwsdfsdf
\.


--
-- Data for Name: character; Type: TABLE DATA; Schema: vkr; Owner: postgres
--

COPY vkr."character" (user_login, character_id, character_name, gender, hair_id, chestplate_id, foots_id, legs_id, background_id, character_type, level, max_hp, current_hp, exp, coins, stress_coef, is_dead, base_damage, created_at, dead_at, mood_level) FROM stdin;
test	d49eb738-345e-4c59-9718-087ae9447d30	Мой новый герой	f	5baca5ad-3a41-4eea-94c2-8e8bb9f228db	e68167bd-d198-4998-b02b-bdd2f0fdc34d	88684e07-31ef-4dd4-b4cd-c8055905fed1	86728153-2335-4ec9-972c-b1cc0643ddf6	089bfa42-3064-480b-be2d-6173fb0fac0b	qwe	0	50	20	0	1024	0	f	1	19:41:41.916175+07	\N	100
\.


--
-- Data for Name: character_type; Type: TABLE DATA; Schema: vkr; Owner: postgres
--

COPY vkr.character_type (character_type, image_path, bonus_id) FROM stdin;
qwe	adasd	876ec630-7666-4b05-8bf3-a77fa522d9cc
asdasdasd	asdasd	ced75317-da34-45f6-b880-d67bd065f5f5
\.


--
-- Data for Name: details; Type: TABLE DATA; Schema: vkr; Owner: postgres
--

COPY vkr.details (details_id, link_url, link_name, task_id) FROM stdin;
c74af1e0-fc6b-41ba-8284-a86e38b8b056	sdafsdfsdfsd	name	9b6a1c10-c5f8-4f3d-9e04-5aabeeac65f3
\.


--
-- Data for Name: dialogs; Type: TABLE DATA; Schema: vkr; Owner: postgres
--

COPY vkr.dialogs (dialog_id, dialog_text, is_for_mood) FROM stdin;
\.


--
-- Data for Name: difficulty; Type: TABLE DATA; Schema: vkr; Owner: postgres
--

COPY vkr.difficulty (difficulty_name, multiplier) FROM stdin;
easy	1
medium	2
hard	3
\.


--
-- Data for Name: effect_event_dictionary; Type: TABLE DATA; Schema: vkr; Owner: postgres
--

COPY vkr.effect_event_dictionary (effect_event_id, effect_name, description, effect_icon, chance, criteria_type, criteria_value) FROM stdin;
f498ec4a-14c9-4527-9d21-7149bc7e1d81	qweqwe	qweqwe	qweqwe	12	qwewq	1232
3f2bef1e-4f8f-42c5-a896-07fceb7168ff	`1sff	qyfw	wqr314	2	qr	34
\.


--
-- Data for Name: event_dictionary; Type: TABLE DATA; Schema: vkr; Owner: postgres
--

COPY vkr.event_dictionary (event_id, event_name, description, event_icon, money, exp, criteria_type, criteria_value) FROM stdin;
47b4c8d5-5d70-4b82-8a49-810dc60c4b32	qwvewr2	qwecv	qwwefwrf	123	123	qwe	5
\.


--
-- Data for Name: frequency; Type: TABLE DATA; Schema: vkr; Owner: postgres
--

COPY vkr.frequency (frequency_name) FROM stdin;
aday
aweek
ayear
no
\.


--
-- Data for Name: habit; Type: TABLE DATA; Schema: vkr; Owner: postgres
--

COPY vkr.habit (user_login, habit_id, title, difficulty, frequency, timer_interval, description, created_at, streak_count, last_performed_at, base_exp) FROM stdin;
\.


--
-- Data for Name: item; Type: TABLE DATA; Schema: vkr; Owner: postgres
--

COPY vkr.item (item_id, store_id, user_login) FROM stdin;
edf76b4a-fa08-4ea9-b168-d16d5ba75d9a	d27d4678-5a0e-41b8-b791-102c6eb28f75	test
88684e07-31ef-4dd4-b4cd-c8055905fed1	d27d4678-5a0e-41b8-b791-102c6eb28f75	test
86728153-2335-4ec9-972c-b1cc0643ddf6	d27d4678-5a0e-41b8-b791-102c6eb28f75	test
089bfa42-3064-480b-be2d-6173fb0fac0b	d27d4678-5a0e-41b8-b791-102c6eb28f75	test
5baca5ad-3a41-4eea-94c2-8e8bb9f228db	a77be246-584c-4dae-a4f5-aedf6ae2e012	test
e68167bd-d198-4998-b02b-bdd2f0fdc34d	a77be246-584c-4dae-a4f5-aedf6ae2e012	test
\.


--
-- Data for Name: level; Type: TABLE DATA; Schema: vkr; Owner: postgres
--

COPY vkr.level (level, exp, max_hp) FROM stdin;
0	0	50
\.


--
-- Data for Name: priority; Type: TABLE DATA; Schema: vkr; Owner: postgres
--

COPY vkr.priority (priority_name, multiplier) FROM stdin;
low	1
medium	2
hight	3
\.


--
-- Data for Name: refresh_tokens; Type: TABLE DATA; Schema: vkr; Owner: postgres
--

COPY vkr.refresh_tokens (token_id, user_id, token_hash, expires_at) FROM stdin;
e590773f-194f-44f8-900f-d26022e0978b	login	cc2354f52ce17a9ad907001289a1f29349120e1088cb5c412c89adfd6a53cdc4	2025-05-23 23:08:06.730854
5661bc99-cac5-4135-b5f6-8ca2994c15e1	login	a92ab9c0511eb9f2b3ae3269b083ed6f9d02f660f8ce5f3379ec50c6a83699f6	2025-05-23 23:18:23.17665
\.


--
-- Data for Name: store; Type: TABLE DATA; Schema: vkr; Owner: postgres
--

COPY vkr.store (store_id, cost, lvl_to_buy, title, type, image_path, description) FROM stdin;
d27d4678-5a0e-41b8-b791-102c6eb28f75	123	1	qwe	bcg	qwe	qwe
a77be246-584c-4dae-a4f5-aedf6ae2e012	12345	34	eqrd	hat	123123445	qwe
\.


--
-- Data for Name: subtask; Type: TABLE DATA; Schema: vkr; Owner: postgres
--

COPY vkr.subtask (subtask_id, title, task_id, status) FROM stdin;
6a91fb01-2da3-4c22-9757-f0ea689567ae	sub 111	9b6a1c10-c5f8-4f3d-9e04-5aabeeac65f3	t
cddc99b6-13a8-4408-9da6-c2e572ca4812	sdafsdfsdfsd	9b6a1c10-c5f8-4f3d-9e04-5aabeeac65f3	f
\.


--
-- Data for Name: task; Type: TABLE DATA; Schema: vkr; Owner: postgres
--

COPY vkr.task (task_id, user_login, title, end_time, difficulty, priority, frequency, status, timer_interval, description, created_at, finished_at, base_exp) FROM stdin;
9b6a1c10-c5f8-4f3d-9e04-5aabeeac65f3	test	new test task	\N	easy	low	no	f	\N	my test description of my tests task	2025-02-20 15:50:58.504557+00	\N	1
\.


--
-- Data for Name: user; Type: TABLE DATA; Schema: vkr; Owner: postgres
--

COPY vkr."user" (login, password, registered_at) FROM stdin;
test	test	2025-02-20
login	$2a$12$L5GLItNVQVRMq6Xw/.dBD.ww0gBJps/lg9xzZs5MRJhtzI.9k3Cyi	2025-04-23
\.


--
-- Data for Name: user_event_progress; Type: TABLE DATA; Schema: vkr; Owner: postgres
--

COPY vkr.user_event_progress (progress_id, active_event_id, user_login, progress, is_completed) FROM stdin;
16fbd159-9008-40ae-9709-f5465ba5f6a1	de17e3ac-16d9-4ad3-bede-242880347043	test	4	f
\.


--
-- Name: achievements_dictionary achievements_dictionary_pk; Type: CONSTRAINT; Schema: vkr; Owner: postgres
--

ALTER TABLE ONLY vkr.achievements_dictionary
    ADD CONSTRAINT achievements_dictionary_pk PRIMARY KEY (achievement_id);


--
-- Name: achievements_progress achievements_pk; Type: CONSTRAINT; Schema: vkr; Owner: postgres
--

ALTER TABLE ONLY vkr.achievements_progress
    ADD CONSTRAINT achievements_pk PRIMARY KEY (progress_id);


--
-- Name: active_effect_event acrive_effect_event_pk; Type: CONSTRAINT; Schema: vkr; Owner: postgres
--

ALTER TABLE ONLY vkr.active_effect_event
    ADD CONSTRAINT acrive_effect_event_pk PRIMARY KEY (active_effect_id);


--
-- Name: active_boss active_boss_pk; Type: CONSTRAINT; Schema: vkr; Owner: postgres
--

ALTER TABLE ONLY vkr.active_boss
    ADD CONSTRAINT active_boss_pk PRIMARY KEY (active_boss_id);


--
-- Name: active_event active_event_pk; Type: CONSTRAINT; Schema: vkr; Owner: postgres
--

ALTER TABLE ONLY vkr.active_event
    ADD CONSTRAINT active_event_pk PRIMARY KEY (active_event_id);


--
-- Name: bonuses bonuses_pk; Type: CONSTRAINT; Schema: vkr; Owner: postgres
--

ALTER TABLE ONLY vkr.bonuses
    ADD CONSTRAINT bonuses_pk PRIMARY KEY (bonus_id);


--
-- Name: bosses_dictionary bosses_dictionary_pk; Type: CONSTRAINT; Schema: vkr; Owner: postgres
--

ALTER TABLE ONLY vkr.bosses_dictionary
    ADD CONSTRAINT bosses_dictionary_pk PRIMARY KEY (boss_id);


--
-- Name: character character_pk; Type: CONSTRAINT; Schema: vkr; Owner: postgres
--

ALTER TABLE ONLY vkr."character"
    ADD CONSTRAINT character_pk PRIMARY KEY (character_id);


--
-- Name: character_type character_type_pk; Type: CONSTRAINT; Schema: vkr; Owner: postgres
--

ALTER TABLE ONLY vkr.character_type
    ADD CONSTRAINT character_type_pk PRIMARY KEY (character_type);


--
-- Name: details details_pk; Type: CONSTRAINT; Schema: vkr; Owner: postgres
--

ALTER TABLE ONLY vkr.details
    ADD CONSTRAINT details_pk PRIMARY KEY (details_id);


--
-- Name: dialogs dialogs_pk; Type: CONSTRAINT; Schema: vkr; Owner: postgres
--

ALTER TABLE ONLY vkr.dialogs
    ADD CONSTRAINT dialogs_pk PRIMARY KEY (dialog_id);


--
-- Name: difficulty difficulty_pk; Type: CONSTRAINT; Schema: vkr; Owner: postgres
--

ALTER TABLE ONLY vkr.difficulty
    ADD CONSTRAINT difficulty_pk PRIMARY KEY (difficulty_name);


--
-- Name: effect_event_dictionary effect_event_dictionary_pk; Type: CONSTRAINT; Schema: vkr; Owner: postgres
--

ALTER TABLE ONLY vkr.effect_event_dictionary
    ADD CONSTRAINT effect_event_dictionary_pk PRIMARY KEY (effect_event_id);


--
-- Name: event_dictionary event_dictionary_pk; Type: CONSTRAINT; Schema: vkr; Owner: postgres
--

ALTER TABLE ONLY vkr.event_dictionary
    ADD CONSTRAINT event_dictionary_pk PRIMARY KEY (event_id);


--
-- Name: frequency frequency_pk; Type: CONSTRAINT; Schema: vkr; Owner: postgres
--

ALTER TABLE ONLY vkr.frequency
    ADD CONSTRAINT frequency_pk PRIMARY KEY (frequency_name);


--
-- Name: habit habbit_pk; Type: CONSTRAINT; Schema: vkr; Owner: postgres
--

ALTER TABLE ONLY vkr.habit
    ADD CONSTRAINT habbit_pk PRIMARY KEY (habit_id);


--
-- Name: item item_pk; Type: CONSTRAINT; Schema: vkr; Owner: postgres
--

ALTER TABLE ONLY vkr.item
    ADD CONSTRAINT item_pk PRIMARY KEY (item_id);


--
-- Name: level level_pk; Type: CONSTRAINT; Schema: vkr; Owner: postgres
--

ALTER TABLE ONLY vkr.level
    ADD CONSTRAINT level_pk PRIMARY KEY (level);


--
-- Name: priority priority_pk; Type: CONSTRAINT; Schema: vkr; Owner: postgres
--

ALTER TABLE ONLY vkr.priority
    ADD CONSTRAINT priority_pk PRIMARY KEY (priority_name);


--
-- Name: refresh_tokens refresh_tokens_pkey; Type: CONSTRAINT; Schema: vkr; Owner: postgres
--

ALTER TABLE ONLY vkr.refresh_tokens
    ADD CONSTRAINT refresh_tokens_pkey PRIMARY KEY (token_id);


--
-- Name: store store_pk; Type: CONSTRAINT; Schema: vkr; Owner: postgres
--

ALTER TABLE ONLY vkr.store
    ADD CONSTRAINT store_pk PRIMARY KEY (store_id);


--
-- Name: subtask subtask_pk; Type: CONSTRAINT; Schema: vkr; Owner: postgres
--

ALTER TABLE ONLY vkr.subtask
    ADD CONSTRAINT subtask_pk PRIMARY KEY (subtask_id);


--
-- Name: task task_pk; Type: CONSTRAINT; Schema: vkr; Owner: postgres
--

ALTER TABLE ONLY vkr.task
    ADD CONSTRAINT task_pk PRIMARY KEY (task_id);


--
-- Name: user_event_progress user_event_progress_pk; Type: CONSTRAINT; Schema: vkr; Owner: postgres
--

ALTER TABLE ONLY vkr.user_event_progress
    ADD CONSTRAINT user_event_progress_pk PRIMARY KEY (progress_id);


--
-- Name: user user_pk; Type: CONSTRAINT; Schema: vkr; Owner: postgres
--

ALTER TABLE ONLY vkr."user"
    ADD CONSTRAINT user_pk PRIMARY KEY (login);


--
-- Name: achievements_progress achievements_progress_achievements_dictionary_fk; Type: FK CONSTRAINT; Schema: vkr; Owner: postgres
--

ALTER TABLE ONLY vkr.achievements_progress
    ADD CONSTRAINT achievements_progress_achievements_dictionary_fk FOREIGN KEY (achievement_id) REFERENCES vkr.achievements_dictionary(achievement_id);


--
-- Name: achievements_progress achievements_user_fk; Type: FK CONSTRAINT; Schema: vkr; Owner: postgres
--

ALTER TABLE ONLY vkr.achievements_progress
    ADD CONSTRAINT achievements_user_fk FOREIGN KEY (user_login) REFERENCES vkr."user"(login);


--
-- Name: active_effect_event acrive_effect_event_effect_event_dictionary_fk; Type: FK CONSTRAINT; Schema: vkr; Owner: postgres
--

ALTER TABLE ONLY vkr.active_effect_event
    ADD CONSTRAINT acrive_effect_event_effect_event_dictionary_fk FOREIGN KEY (effect_event_id) REFERENCES vkr.effect_event_dictionary(effect_event_id);


--
-- Name: active_boss active_boss_bosses_dictionary_fk; Type: FK CONSTRAINT; Schema: vkr; Owner: postgres
--

ALTER TABLE ONLY vkr.active_boss
    ADD CONSTRAINT active_boss_bosses_dictionary_fk FOREIGN KEY (boss_id) REFERENCES vkr.bosses_dictionary(boss_id);


--
-- Name: active_boss active_boss_user_fk; Type: FK CONSTRAINT; Schema: vkr; Owner: postgres
--

ALTER TABLE ONLY vkr.active_boss
    ADD CONSTRAINT active_boss_user_fk FOREIGN KEY (user_login) REFERENCES vkr."user"(login);


--
-- Name: active_event active_event_event_dictionary_fk; Type: FK CONSTRAINT; Schema: vkr; Owner: postgres
--

ALTER TABLE ONLY vkr.active_event
    ADD CONSTRAINT active_event_event_dictionary_fk FOREIGN KEY (event_id) REFERENCES vkr.event_dictionary(event_id);


--
-- Name: character character_character_type_fk; Type: FK CONSTRAINT; Schema: vkr; Owner: postgres
--

ALTER TABLE ONLY vkr."character"
    ADD CONSTRAINT character_character_type_fk FOREIGN KEY (character_type) REFERENCES vkr.character_type(character_type);


--
-- Name: character character_item_fk; Type: FK CONSTRAINT; Schema: vkr; Owner: postgres
--

ALTER TABLE ONLY vkr."character"
    ADD CONSTRAINT character_item_fk FOREIGN KEY (hair_id) REFERENCES vkr.item(item_id);


--
-- Name: character character_item_fk_1; Type: FK CONSTRAINT; Schema: vkr; Owner: postgres
--

ALTER TABLE ONLY vkr."character"
    ADD CONSTRAINT character_item_fk_1 FOREIGN KEY (chestplate_id) REFERENCES vkr.item(item_id);


--
-- Name: character character_item_fk_2; Type: FK CONSTRAINT; Schema: vkr; Owner: postgres
--

ALTER TABLE ONLY vkr."character"
    ADD CONSTRAINT character_item_fk_2 FOREIGN KEY (foots_id) REFERENCES vkr.item(item_id);


--
-- Name: character character_item_fk_3; Type: FK CONSTRAINT; Schema: vkr; Owner: postgres
--

ALTER TABLE ONLY vkr."character"
    ADD CONSTRAINT character_item_fk_3 FOREIGN KEY (legs_id) REFERENCES vkr.item(item_id);


--
-- Name: character character_item_fk_4; Type: FK CONSTRAINT; Schema: vkr; Owner: postgres
--

ALTER TABLE ONLY vkr."character"
    ADD CONSTRAINT character_item_fk_4 FOREIGN KEY (background_id) REFERENCES vkr.item(item_id);


--
-- Name: character character_level_fk; Type: FK CONSTRAINT; Schema: vkr; Owner: postgres
--

ALTER TABLE ONLY vkr."character"
    ADD CONSTRAINT character_level_fk FOREIGN KEY (level) REFERENCES vkr.level(level);


--
-- Name: character_type character_type_bonuses_fk; Type: FK CONSTRAINT; Schema: vkr; Owner: postgres
--

ALTER TABLE ONLY vkr.character_type
    ADD CONSTRAINT character_type_bonuses_fk FOREIGN KEY (bonus_id) REFERENCES vkr.bonuses(bonus_id);


--
-- Name: character character_user_fk; Type: FK CONSTRAINT; Schema: vkr; Owner: postgres
--

ALTER TABLE ONLY vkr."character"
    ADD CONSTRAINT character_user_fk FOREIGN KEY (user_login) REFERENCES vkr."user"(login);


--
-- Name: details details_task_fk; Type: FK CONSTRAINT; Schema: vkr; Owner: postgres
--

ALTER TABLE ONLY vkr.details
    ADD CONSTRAINT details_task_fk FOREIGN KEY (task_id) REFERENCES vkr.task(task_id);


--
-- Name: habit habbit_difficulty_fk; Type: FK CONSTRAINT; Schema: vkr; Owner: postgres
--

ALTER TABLE ONLY vkr.habit
    ADD CONSTRAINT habbit_difficulty_fk FOREIGN KEY (difficulty) REFERENCES vkr.difficulty(difficulty_name);


--
-- Name: habit habbit_frequency_fk; Type: FK CONSTRAINT; Schema: vkr; Owner: postgres
--

ALTER TABLE ONLY vkr.habit
    ADD CONSTRAINT habbit_frequency_fk FOREIGN KEY (frequency) REFERENCES vkr.frequency(frequency_name);


--
-- Name: habit habbit_user_fk; Type: FK CONSTRAINT; Schema: vkr; Owner: postgres
--

ALTER TABLE ONLY vkr.habit
    ADD CONSTRAINT habbit_user_fk FOREIGN KEY (user_login) REFERENCES vkr."user"(login);


--
-- Name: item item_store_fk; Type: FK CONSTRAINT; Schema: vkr; Owner: postgres
--

ALTER TABLE ONLY vkr.item
    ADD CONSTRAINT item_store_fk FOREIGN KEY (store_id) REFERENCES vkr.store(store_id);


--
-- Name: item item_user_fk; Type: FK CONSTRAINT; Schema: vkr; Owner: postgres
--

ALTER TABLE ONLY vkr.item
    ADD CONSTRAINT item_user_fk FOREIGN KEY (user_login) REFERENCES vkr."user"(login);


--
-- Name: refresh_tokens refresh_tokens_user_id_fkey; Type: FK CONSTRAINT; Schema: vkr; Owner: postgres
--

ALTER TABLE ONLY vkr.refresh_tokens
    ADD CONSTRAINT refresh_tokens_user_id_fkey FOREIGN KEY (user_id) REFERENCES vkr."user"(login);


--
-- Name: subtask subtask_task_fk; Type: FK CONSTRAINT; Schema: vkr; Owner: postgres
--

ALTER TABLE ONLY vkr.subtask
    ADD CONSTRAINT subtask_task_fk FOREIGN KEY (task_id) REFERENCES vkr.task(task_id);


--
-- Name: task task_difficulty_fk; Type: FK CONSTRAINT; Schema: vkr; Owner: postgres
--

ALTER TABLE ONLY vkr.task
    ADD CONSTRAINT task_difficulty_fk FOREIGN KEY (difficulty) REFERENCES vkr.difficulty(difficulty_name);


--
-- Name: task task_frequency_fk; Type: FK CONSTRAINT; Schema: vkr; Owner: postgres
--

ALTER TABLE ONLY vkr.task
    ADD CONSTRAINT task_frequency_fk FOREIGN KEY (frequency) REFERENCES vkr.frequency(frequency_name);


--
-- Name: task task_priority_fk; Type: FK CONSTRAINT; Schema: vkr; Owner: postgres
--

ALTER TABLE ONLY vkr.task
    ADD CONSTRAINT task_priority_fk FOREIGN KEY (priority) REFERENCES vkr.priority(priority_name);


--
-- Name: task task_user_fk; Type: FK CONSTRAINT; Schema: vkr; Owner: postgres
--

ALTER TABLE ONLY vkr.task
    ADD CONSTRAINT task_user_fk FOREIGN KEY (user_login) REFERENCES vkr."user"(login);


--
-- Name: user_event_progress user_event_progress_active_event_fk; Type: FK CONSTRAINT; Schema: vkr; Owner: postgres
--

ALTER TABLE ONLY vkr.user_event_progress
    ADD CONSTRAINT user_event_progress_active_event_fk FOREIGN KEY (active_event_id) REFERENCES vkr.active_event(active_event_id);


--
-- Name: user_event_progress user_event_progress_user_fk; Type: FK CONSTRAINT; Schema: vkr; Owner: postgres
--

ALTER TABLE ONLY vkr.user_event_progress
    ADD CONSTRAINT user_event_progress_user_fk FOREIGN KEY (user_login) REFERENCES vkr."user"(login);


--
-- PostgreSQL database dump complete
--

