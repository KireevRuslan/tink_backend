--liquibase formatted sql

--changeset admin:1
INSERT INTO link_info.chat(chat_id) VALUES (333);
INSERT INTO link_info.chat(chat_id) VALUES (6633);
INSERT INTO link_info.chat(chat_id) VALUES (99999);
INSERT INTO link_info.link(url, chat_id) VALUES ('https://github.com/KireevRuslan/TinkKontest', 333);
INSERT INTO link_info.link(url, chat_id) VALUES ('https://stackoverflow.com/questions/76040410/redis-master-slave-connection', 333);
INSERT INTO link_info.link(url, chat_id) VALUES ('https://github.com/KireevRuslan/tink_backend', 6633);
INSERT INTO link_info.link(url, chat_id) VALUES ('https://stackoverflow.com/questions/76040402/how-to-export-gcps-automl-model-and-run-it-on-the-local-machine', 6633);