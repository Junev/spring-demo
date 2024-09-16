use practice;

CREATE TABLE IF NOT EXISTS `country`(
    `id` INT not null AUTO_INCREMENT,
    `country_name` VARCHAR(255) NULL,
    `country_code` VARCHAR(255) NULL,
    PRIMARY KEY ( `id` )
);

INSERT country (`country_name`, `country_code`)
VALUES ('中国', 'CN'), ('美国', 'US'), ('俄罗斯', 'RU'),
        ('英国', 'GB'), ('法国', 'FR');