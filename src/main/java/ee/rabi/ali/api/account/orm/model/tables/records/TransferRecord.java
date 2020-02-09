/*
 * This file is generated by jOOQ.
 */
package ee.rabi.ali.api.account.orm.model.tables.records;


import ee.rabi.ali.api.account.orm.model.tables.Transfer;
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
public class TransferRecord extends UpdatableRecordImpl<TransferRecord> implements Record5<String, String, String, Long, Timestamp> {

    private static final long serialVersionUID = 1863145272;

    /**
     * Setter for <code>PUBLIC.TRANSFER.ID</code>.
     */
    public void setId(String value) {
        set(0, value);
    }

    /**
     * Getter for <code>PUBLIC.TRANSFER.ID</code>.
     */
    public String getId() {
        return (String) get(0);
    }

    /**
     * Setter for <code>PUBLIC.TRANSFER.FROM_ACCOUNT_ID</code>.
     */
    public void setFromAccountId(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>PUBLIC.TRANSFER.FROM_ACCOUNT_ID</code>.
     */
    public String getFromAccountId() {
        return (String) get(1);
    }

    /**
     * Setter for <code>PUBLIC.TRANSFER.TO_ACCOUNT_ID</code>.
     */
    public void setToAccountId(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>PUBLIC.TRANSFER.TO_ACCOUNT_ID</code>.
     */
    public String getToAccountId() {
        return (String) get(2);
    }

    /**
     * Setter for <code>PUBLIC.TRANSFER.AMOUNT</code>.
     */
    public void setAmount(Long value) {
        set(3, value);
    }

    /**
     * Getter for <code>PUBLIC.TRANSFER.AMOUNT</code>.
     */
    public Long getAmount() {
        return (Long) get(3);
    }

    /**
     * Setter for <code>PUBLIC.TRANSFER.CREATED_AT</code>.
     */
    public void setCreatedAt(Timestamp value) {
        set(4, value);
    }

    /**
     * Getter for <code>PUBLIC.TRANSFER.CREATED_AT</code>.
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
        return Transfer.TRANSFER.ID;
    }

    @Override
    public Field<String> field2() {
        return Transfer.TRANSFER.FROM_ACCOUNT_ID;
    }

    @Override
    public Field<String> field3() {
        return Transfer.TRANSFER.TO_ACCOUNT_ID;
    }

    @Override
    public Field<Long> field4() {
        return Transfer.TRANSFER.AMOUNT;
    }

    @Override
    public Field<Timestamp> field5() {
        return Transfer.TRANSFER.CREATED_AT;
    }

    @Override
    public String component1() {
        return getId();
    }

    @Override
    public String component2() {
        return getFromAccountId();
    }

    @Override
    public String component3() {
        return getToAccountId();
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
        return getFromAccountId();
    }

    @Override
    public String value3() {
        return getToAccountId();
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
    public TransferRecord value1(String value) {
        setId(value);
        return this;
    }

    @Override
    public TransferRecord value2(String value) {
        setFromAccountId(value);
        return this;
    }

    @Override
    public TransferRecord value3(String value) {
        setToAccountId(value);
        return this;
    }

    @Override
    public TransferRecord value4(Long value) {
        setAmount(value);
        return this;
    }

    @Override
    public TransferRecord value5(Timestamp value) {
        setCreatedAt(value);
        return this;
    }

    @Override
    public TransferRecord values(String value1, String value2, String value3, Long value4, Timestamp value5) {
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
     * Create a detached TransferRecord
     */
    public TransferRecord() {
        super(Transfer.TRANSFER);
    }

    /**
     * Create a detached, initialised TransferRecord
     */
    public TransferRecord(String id, String fromAccountId, String toAccountId, Long amount, Timestamp createdAt) {
        super(Transfer.TRANSFER);

        set(0, id);
        set(1, fromAccountId);
        set(2, toAccountId);
        set(3, amount);
        set(4, createdAt);
    }
}
