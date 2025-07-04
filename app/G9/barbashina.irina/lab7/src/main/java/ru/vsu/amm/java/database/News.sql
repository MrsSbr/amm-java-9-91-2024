CREATE TABLE Author
(
	id_author BIGSERIAL,
	surname TEXT NOT NULL,
	name_author TEXT NOT NULL,
	patronymic TEXT,
	registration_date date
);

ALTER TABLE Author
ADD CONSTRAINT pk_author PRIMARY KEY(id_author);

CREATE TABLE Category
(
	id_category BIGSERIAL,
	name_category TEXT NOT NULL
);

ALTER TABLE Category
ADD CONSTRAINT pk_category PRIMARY KEY(id_category);

ALTER TABLE Category
ADD CONSTRAINT unique_name UNIQUE(name_category);

CREATE TABLE Article
(
	id_article BIGSERIAL,
	title TEXT NOT NULL,
	article_content TEXT NOT NULL,
	date_publication date NOT NULL
);

ALTER TABLE Article
ADD CONSTRAINT pk_article PRIMARY KEY(id_article);

ALTER TABLE Article ADD ref_category BIGINT;
ALTER TABLE Article
ADD CONSTRAINT fk_id_category FOREIGN KEY(ref_category) REFERENCES Category(id_category);

ALTER TABLE Article ADD ref_author BIGINT;
ALTER TABLE Article
ADD CONSTRAINT fk_id_author FOREIGN KEY(ref_author) REFERENCES Author(id_author);

CREATE INDEX idx_article_ref_category ON Article (ref_category);
CREATE INDEX idx_article_ref_author ON Article (ref_author);
