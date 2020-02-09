CREATE TABLE account
(
    id varchar(36) NOT NULL
);

CREATE TABLE ledger
(
    id             varchar(36) NOT NULL,
    account_id     varchar(36) NOT NULL,
    transaction_id varchar(36) NOT NULL,
    amount         bigint      NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT ledger_account_id_fk FOREIGN KEY (account_id) REFERENCES account (id)
);
