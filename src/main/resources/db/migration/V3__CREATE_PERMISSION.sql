CREATE TABLE permission
(
    id          BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    description VARCHAR(255)                            NOT NULL,
    CONSTRAINT pk_permission PRIMARY KEY (id)
);
