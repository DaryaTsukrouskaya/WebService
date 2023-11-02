DROP TABLE IF EXISTS shop.roles;
CREATE TABLE IF NOT EXISTS shop.roles(
    id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(10) NOT NULL,
    PRIMARY KEY(id));

DROP TABLE IF EXISTS shop.users_roles;
CREATE TABLE IF NOT EXISTS shop.users_roles(
    userId INT NOT NULL,
    roleId INT NOT NULL,
    PRIMARY KEY (userId,roleId),
    CONSTRAINT FK_users_roles_userId_users_id
    FOREIGN KEY (userId)
    REFERENCES shop.users(id),
    CONSTRAINT FK_users_roles_roleId_roles_id
    FOREIGN KEY (roleId)
    REFERENCES shop.roles(id)
    ON DELETE CASCADE
    ON UPDATE CASCADE);

INSERT INTO shop.roles(name) VALUES ("ADMIN");
INSERT INTO shop.roles(name) VALUES ("USER");

