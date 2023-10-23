CREATE TABLE IF NOT EXISTS eshop.refreshTokens(
    id INT PRIMARY KEY AUTO_INCREMENT,
    token VARCHAR(300) NOT NULL,
    username VARCHAR(20) NOT NULL,
    CONSTRAINT FK_refreshTokens_username_users_email
    FOREIGN KEY (username)
    REFERENCES eshop.users (email)
    ON DELETE CASCADE
    ON UPDATE CASCADE);