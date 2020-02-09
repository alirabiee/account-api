/*
 * This file is generated by jOOQ.
 */
package ee.rabi.ali.api.account.orm.model.tables.records;


import ee.rabi.ali.api.account.orm.model.tables.Ledger;
import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record5;
import org.jooq.Row5;
import org.jooq.impl.UpdatableRecordImpl;

import javax.annotation.processing.Generated;
import java.sql.Timestamp;


/**
 * This class is generated by jOOQ.
 */
@Generated(
        value = {
                "http://www.jooq.org",
                "jOOQ version:3.12.4"
        },
        comments = "This class is generated by jOOQ"
)
@SuppressWarnings({"all", "unchecked", "rawtypes"})
public class LedgerRecord extends UpdatableRecordImpl<LedgerRecord> implements Record5<String, String, String, Long, Timestamp> {

    private static final long serialVersionUID = 984003562;

    /**
     * Setter for <code>PUBLIC.LEDGER.ID</code>.
     */
    public void setId(String value) {
        set(0, value);
    }

    /**
     * Getter for <code>PUBLIC.LEDGER.ID</code>.
     */
    public String getId() {
        return (String) get(0);
    }

    /**
     * Setter for <code>PUBLIC.LEDGER.ACCOUNT_ID</code>.
     */
    public void setAccountId(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>PUBLIC.LEDGER.ACCOUNT_ID</code>.
     */
    public String getAccountId() {
        return (String) get(1);
    }

    /**
     * Setter for <code>PUBLIC.LEDGER.TRANSACTION_ID</code>.
     */
    public void setTransactionId(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>PUBLIC.LEDGER.TRANSACTION_ID</code>.
     */
    public String getTransactionId() {
        return (String) get(2);
    }

    /**
     * Setter for <code>PUBLIC.LEDGER.AMOUNT</code>.
     */
    public void setAmount(Long value) {
        set(3, value);
    }

    /**
     * Getter for <code>PUBLIC.LEDGER.AMOUNT</code>.
     */
    public Long getAmount() {
        return (Long) get(3);
    }

    /**
     * Setter for <code>PUBLIC.LEDGER.CREATED_AT</code>.
     */
    public void setCreatedAt(Timestamp value) {
        set(4, value);
    }

    /**
     * Getter for <code>PUBLIC.LEDGER.CREATED_AT</code>.
     */
    public Timestamp getCreatedAt() {
        return (Timestamp) get(4);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<String> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record5 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row5<String, String, String, Long, Timestamp> fieldsRow() {
        return (Row5) super.fieldsRow();
    }

    @Override
    public Row5<String, String, String, Long, Timestamp> valuesRow() {
        return (Row5) super.valuesRow();
    }

    @Override
    public Field<String> field1() {
        return Ledger.LEDGER.ID;
    }

    @Override
    public Field<String> field2() {
        return Ledger.LEDGER.ACCOUNT_ID;
    }

    @Override
    public Field<String> field3() {
        return Ledger.LEDGER.TRANSACTION_ID;
    }

    @Override
    public Field<Long> field4() {
        return Ledger.LEDGER.AMOUNT;
    }

    @Override
    public Field<Timestamp> field5() {
        return Ledger.LEDGER.CREATED_AT;
    }

    @Override
    public String component1() {
        return getId();
    }

    @Override
    public String component2() {
        return getAccountId();
    }

    @Override
    public String component3() {
        return getTransactionId();
    }

    @Override
    public Long component4() {
        return getAmount();
    }

    @Override
    public Timestamp component5() {
        return getCreatedAt();
    }

    @Override
    public String value1() {
        return getId();
    }

    @Override
    public String value2() {
        return getAccountId();
    }

    @Override
    public String value3() {
        return getTransactionId();
    }

    @Override
    public Long value4() {
        return getAmount();
    }

    @Override
    public Timestamp value5() {
        return getCreatedAt();
    }

    @Override
    public LedgerRecord value1(String value) {
        setId(value);
        return this;
    }

    @Override
    public LedgerRecord value2(String value) {
        setAccountId(value);
        return this;
    }

    @Override
    public LedgerRecord value3(String value) {
        setTransactionId(value);
        return this;
    }

    @Override
    public LedgerRecord value4(Long value) {
        setAmount(value);
        return this;
    }

    @Override
    public LedgerRecord value5(Timestamp value) {
        setCreatedAt(value);
        return this;
    }

    @Override
    public LedgerRecord values(String value1, String value2, String value3, Long value4, Timestamp value5) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached LedgerRecord
     */
    public LedgerRecord() {
        super(Ledger.LEDGER);
    }

    /**
     * Create a detached, initialised LedgerRecord
     */
    public LedgerRecord(String id, String accountId, String transactionId, Long amount, Timestamp createdAt) {
        super(Ledger.LEDGER);

        set(0, id);
        set(1, accountId);
        set(2, transactionId);
        set(3, amount);
        set(4, createdAt);
    }
}
