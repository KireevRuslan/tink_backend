--liquibase formatted sql

--changeset admin:31
INSERT INTO link_info.chat(chat_id)
VALUES (333);
INSERT INTO link_info.chat(chat_id)
VALUES (6633);
INSERT INTO link_info.chat(chat_id)
VALUES (99999);
INSERT INTO link_info.link(url, type, chat_id)
VALUES ('https://github.com/KireevRuslan/TinkKontest', 'github', 333);
INSERT INTO link_info.link(url, type, chat_id)
VALUES ('https://stackoverflow.com/questions/76040410/redis-master-slave-connection','stack', 333);
INSERT INTO link_info.link(url, type, chat_id)
VALUES ('https://github.com/KireevRuslan/tink_backend', 'github', 6633);
INSERT INTO link_info.link(url, type, chat_id)
VALUES ('https://stackoverflow.com/questions/76040402/how-to-export-gcps-automl-model-and-run-it-on-the-local-machine', 'stack', 6633);
INSERT INTO link_info.link(url, type, chat_id)
VALUES ('https://github.com/cnapagoda/carbon-governance/tree/master/components/governance', 'github', 99999);
INSERT INTO link_info.github_updates(id)
VALUES (1);
INSERT INTO link_info.github_updates(id)
VALUES (3);
INSERT INTO link_info.github_updates(id)
VALUES (5);
INSERT INTO link_info.stackoverflow_updates(id)
VALUES (2);
INSERT INTO link_info.stackoverflow_updates(id)
VALUES (4);
