-- Insert test authors
INSERT INTO Author (surname, name_author, patronymic, registration_date)
VALUES ('Doe', 'John', 'Smith', '2020-01-01'),
       ('Smith', 'Jane', null, '2020-02-01');

-- Insert test categories
INSERT INTO Category (name_category)
VALUES ('Technology'),
       ('Science');

-- Insert test articles
INSERT INTO Article (title, article_content, date_publication, ref_category, ref_author)
VALUES ('Test Article', 'Test Content', '2023-01-01', 1, 1),
       ('Another Article', 'More Content', '2023-02-01', 2, 2);