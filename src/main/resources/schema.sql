CREATE DATABASE discodeit;
CREATE ROLE discodeit_user WITH LOGIN PASSWORD 'discodeit1234';
GRANT ALL PRIVILEGES ON DATABASE discodeit TO discodeit_user;

-- 1. binary_contents
CREATE TABLE binary_contents(
                                id UUID PRIMARY KEY,
                                created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                file_name VARCHAR(255) NOT NULL,
                                size BIGINT NOT NULL,
                                content_type VARCHAR(100) NOT NULL,
                                bytes BYTEA NOT NULL
);

-- 2. channels
CREATE TABLE channels(
                         id UUID PRIMARY KEY,
                         created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
                         name VARCHAR(100),
                         description VARCHAR(500),
                         type VARCHAR(10) NOT NULL
);

-- 3. users
CREATE TABLE users(
                      id UUID PRIMARY KEY,
                      created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
                      updated_at TIMESTAMPTZ,
                      username VARCHAR(50) UNIQUE NOT NULL,
                      email VARCHAR(100) UNIQUE NOT NULL,
                      password VARCHAR(60) NOT NULL,
                      profile_id UUID UNIQUE,
                      CONSTRAINT fk_user_profile FOREIGN KEY (profile_id) REFERENCES binary_contents(id) ON DELETE SET NULL
);

-- 4. user_status
CREATE TABLE user_status(
                            id UUID PRIMARY KEY,
                            created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
                            updated_at TIMESTAMPTZ,
                            user_id UUID UNIQUE,
                            last_active_at TIMESTAMPTZ NOT NULL,
                            CONSTRAINT fk_user_status_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- 5. messages
CREATE TABLE messages(
                         id UUID PRIMARY KEY,
                         created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
                         updated_at TIMESTAMPTZ,
                         content TEXT,
                         channel_id UUID,
                         author_id UUID,
                         CONSTRAINT fk_message_channel FOREIGN KEY (channel_id) REFERENCES channels(id) ON DELETE CASCADE,
                         CONSTRAINT fk_message_author FOREIGN KEY (author_id) REFERENCES users(id) ON DELETE SET NULL
);

-- 6. read_statuses
CREATE TABLE read_statuses (
                               id UUID PRIMARY KEY,
                               created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
                               updated_at TIMESTAMPTZ,
                               user_id UUID NOT NULL,
                               channel_id UUID NOT NULL,
                               last_read_at TIMESTAMPTZ NOT NULL,
                               CONSTRAINT fk_read_status_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
                               CONSTRAINT fk_read_status_channel FOREIGN KEY (channel_id) REFERENCES channels(id) ON DELETE CASCADE,
                               UNIQUE (user_id, channel_id)
);

-- 7. message_attachments
CREATE TABLE message_attachments (
                                     message_id UUID NOT NULL,
                                     attachment_id UUID NOT NULL,
                                     PRIMARY KEY (message_id, attachment_id),
                                     CONSTRAINT fk_attachment_message FOREIGN KEY (message_id) REFERENCES messages(id) ON DELETE CASCADE,
                                     CONSTRAINT fk_attachment_binary FOREIGN KEY (attachment_id) REFERENCES binary_contents(id) ON DELETE CASCADE
);
