create TABLE IF NOT EXISTS application_user (
    id INT AUTO_INCREMENT,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(500) NOT NULL,
    role VARCHAR(100) NOT NULL,
    PRIMARY KEY(id)
);