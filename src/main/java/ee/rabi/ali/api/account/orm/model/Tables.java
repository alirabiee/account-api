/*
 * This file is generated by jOOQ.
 */
package ee.rabi.ali.api.account.orm.model;


import ee.rabi.ali.api.account.orm.model.tables.Account;
import ee.rabi.ali.api.account.orm.model.tables.Ledger;
import ee.rabi.ali.api.account.orm.model.tables.Transfer;

import javax.annotation.processing.Generated;


/**
 * Convenience access to all tables in PUBLIC
 */
@Generated(
        value = {
                "http://www.jooq.org",
                "jOOQ version:3.12.4"
        },
        comments = "This class is generated by jOOQ"
)
@SuppressWarnings({"all", "unchecked", "rawtypes"})
public class Tables {

    /**
     * The table <code>PUBLIC.ACCOUNT</code>.
     */
    public static final Account ACCOUNT = Account.ACCOUNT;

    /**
     * The table <code>PUBLIC.LEDGER</code>.
     */
    public static final Ledger LEDGER = Ledger.LEDGER;

    /**
     * The table <code>PUBLIC.TRANSFER</code>.
     */
    public static final Transfer TRANSFER = Transfer.TRANSFER;
}
