create table user
(
id INTEGER	PRIMARY KEY AUTOINCREMENT,
username TEXT NOT NULL,
password TEXT NOT NULL
);

create table survey
(
id INTEGER	PRIMARY KEY AUTOINCREMENT,
name TEXT NOT NULL,
description TEXT NOT NULL,
last_date_to_fill  DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
is_response_editable BOOLEAN NOT NULL,
created_by_id INTEGER,
FOREIGN KEY(created_by_id) REFERENCES user(id)
);

create table question
(
id INTEGER PRIMARY KEY AUTOINCREMENT,
que TEXT NOT NULL,
survey_id INTEGER,
FOREIGN KEY(survey_id) REFERENCES survey(id)
);

create table survey_response
(
id INTEGER	PRIMARY KEY AUTOINCREMENT,
survey_id INTEGER,
question_id INTEGER,
user_id INTEGER,
ans INTEGER,
FOREIGN KEY(survey_id) REFERENCES survey(id)
FOREIGN KEY(user_id) REFERENCES user(id)
FOREIGN KEY(question_id) REFERENCES question(id)
);

create table feedback
(
id INTEGER PRIMARY KEY AUTOINCREMENT,
description TEXT
);

