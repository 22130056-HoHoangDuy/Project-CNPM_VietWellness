CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    avatar_url VARCHAR(512),
    UNIQUE KEY uq_users_email (email)
);

CREATE TABLE groups (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(1000),
    privacy VARCHAR(50) NOT NULL
);

CREATE TABLE posts (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    content VARCHAR(2000),
    media_url VARCHAR(512),
    status VARCHAR(50),
    author_id BIGINT,
    group_id BIGINT,
    created_at DATETIME,
    CONSTRAINT fk_posts_author FOREIGN KEY (author_id) REFERENCES users(id),
    CONSTRAINT fk_posts_group FOREIGN KEY (group_id) REFERENCES groups(id)
);

CREATE TABLE comments (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    content VARCHAR(1000),
    commenter_id BIGINT,
    post_id BIGINT,
    commented_at DATETIME,
    CONSTRAINT fk_comments_commenter FOREIGN KEY (commenter_id) REFERENCES users(id),
    CONSTRAINT fk_comments_post FOREIGN KEY (post_id) REFERENCES posts(id)
);

CREATE TABLE group_memberships (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    member_id BIGINT,
    group_id BIGINT,
    approved BOOLEAN,
    CONSTRAINT fk_groupmembership_member FOREIGN KEY (member_id) REFERENCES users(id),
    CONSTRAINT fk_groupmembership_group FOREIGN KEY (group_id) REFERENCES groups(id)
);

CREATE TABLE reports (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    reason VARCHAR(1000),
    reported_post_id BIGINT,
    reported_by_id BIGINT,
    reported_at DATETIME,
    CONSTRAINT fk_reports_post FOREIGN KEY (reported_post_id) REFERENCES posts(id),
    CONSTRAINT fk_reports_user FOREIGN KEY (reported_by_id) REFERENCES users(id)
);
