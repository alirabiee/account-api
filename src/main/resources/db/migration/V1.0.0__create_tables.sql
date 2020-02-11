CREATE TABLE account
(
    id       varchar(36) NOT NULL,
    currency varchar(3)  NOT NULL
);

CREATE TABLE ledger
(
    id             varchar(36) NOT NULL,
    account_id     varchar(36) NOT NULL,
    transaction_id varchar(36) NOT NULL,
    amount         decimal     NOT NULL,
    created_at     timestamp   NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT ledger_account_id_fk FOREIGN KEY (account_id) REFERENCES account (id)
);

CREATE TABLE transfer
(
    id              varchar(36) NOT NULL,
    from_account_id varchar(36) NOT NULL,
    to_account_id   varchar(36) NOT NULL,
    amount          decimal     NOT NULL,
    created_at      timestamp   NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT transfer_from_account_id_fk FOREIGN KEY (from_account_id) REFERENCES account (id),
    CONSTRAINT transfer_to_account_id_fk FOREIGN KEY (to_account_id) REFERENCES account (id)
);

CREATE TABLE balance_snapshot
(
    account_id varchar(36) NOT NULL,
    balance    decimal     NOT NULL,
    updated_at timestamp   NOT NULL,
    version    int         NOT NULL,
    PRIMARY KEY (account_id),
    CONSTRAINT balance_snapshot_account_id_fk FOREIGN KEY (account_id) REFERENCES account (id)
);
