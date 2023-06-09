package domain.jooq.link_info.tables.records;

import jakarta.validation.constraints.Size;

import java.beans.ConstructorProperties;
import java.time.LocalDateTime;

import javax.annotation.processing.Generated;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record6;
import org.jooq.Row6;
import org.jooq.impl.UpdatableRecordImpl;

import domain.jooq.link_info.tables.Link;


/**
 * This class is generated by jOOQ.
 */
@Generated(
        value = {
                "https://www.jooq.org",
                "jOOQ version:3.18.3"
        },
        comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class LinkRecord extends UpdatableRecordImpl<LinkRecord> implements Record6<Long, String, String, LocalDateTime, LocalDateTime, Long> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>LINK_INFO.LINK.ID</code>.
     */
    public void setId(@Nullable Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>LINK_INFO.LINK.ID</code>.
     */
    @Nullable
    public Long getId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>LINK_INFO.LINK.URL</code>.
     */
    public void setUrl(@NotNull String value) {
        set(1, value);
    }

    /**
     * Getter for <code>LINK_INFO.LINK.URL</code>.
     */
    @jakarta.validation.constraints.NotNull
    @Size(max = 512)
    @NotNull
    public String getUrl() {
        return (String) get(1);
    }

    /**
     * Setter for <code>LINK_INFO.LINK.TYPE</code>.
     */
    public void setType(@NotNull String value) {
        set(2, value);
    }

    /**
     * Getter for <code>LINK_INFO.LINK.TYPE</code>.
     */
    @jakarta.validation.constraints.NotNull
    @Size(max = 10)
    @NotNull
    public String getType() {
        return (String) get(2);
    }

    /**
     * Setter for <code>LINK_INFO.LINK.LAST_UPDATE</code>.
     */
    public void setLastUpdate(@Nullable LocalDateTime value) {
        set(3, value);
    }

    /**
     * Getter for <code>LINK_INFO.LINK.LAST_UPDATE</code>.
     */
    @Nullable
    public LocalDateTime getLastUpdate() {
        return (LocalDateTime) get(3);
    }

    /**
     * Setter for <code>LINK_INFO.LINK.LAST_CHECK</code>.
     */
    public void setLastCheck(@Nullable LocalDateTime value) {
        set(4, value);
    }

    /**
     * Getter for <code>LINK_INFO.LINK.LAST_CHECK</code>.
     */
    @Nullable
    public LocalDateTime getLastCheck() {
        return (LocalDateTime) get(4);
    }

    /**
     * Setter for <code>LINK_INFO.LINK.CHAT_ID</code>.
     */
    public void setChatId(@NotNull Long value) {
        set(5, value);
    }

    /**
     * Getter for <code>LINK_INFO.LINK.CHAT_ID</code>.
     */
    @jakarta.validation.constraints.NotNull
    @NotNull
    public Long getChatId() {
        return (Long) get(5);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    @NotNull
    public Record1<Long> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record6 type implementation
    // -------------------------------------------------------------------------

    @Override
    @NotNull
    public Row6<Long, String, String, LocalDateTime, LocalDateTime, Long> fieldsRow() {
        return (Row6) super.fieldsRow();
    }

    @Override
    @NotNull
    public Row6<Long, String, String, LocalDateTime, LocalDateTime, Long> valuesRow() {
        return (Row6) super.valuesRow();
    }

    @Override
    @NotNull
    public Field<Long> field1() {
        return Link.LINK.ID;
    }

    @Override
    @NotNull
    public Field<String> field2() {
        return Link.LINK.URL;
    }

    @Override
    @NotNull
    public Field<String> field3() {
        return Link.LINK.TYPE;
    }

    @Override
    @NotNull
    public Field<LocalDateTime> field4() {
        return Link.LINK.LAST_UPDATE;
    }

    @Override
    @NotNull
    public Field<LocalDateTime> field5() {
        return Link.LINK.LAST_CHECK;
    }

    @Override
    @NotNull
    public Field<Long> field6() {
        return Link.LINK.CHAT_ID;
    }

    @Override
    @Nullable
    public Long component1() {
        return getId();
    }

    @Override
    @NotNull
    public String component2() {
        return getUrl();
    }

    @Override
    @NotNull
    public String component3() {
        return getType();
    }

    @Override
    @Nullable
    public LocalDateTime component4() {
        return getLastUpdate();
    }

    @Override
    @Nullable
    public LocalDateTime component5() {
        return getLastCheck();
    }

    @Override
    @NotNull
    public Long component6() {
        return getChatId();
    }

    @Override
    @Nullable
    public Long value1() {
        return getId();
    }

    @Override
    @NotNull
    public String value2() {
        return getUrl();
    }

    @Override
    @NotNull
    public String value3() {
        return getType();
    }

    @Override
    @Nullable
    public LocalDateTime value4() {
        return getLastUpdate();
    }

    @Override
    @Nullable
    public LocalDateTime value5() {
        return getLastCheck();
    }

    @Override
    @NotNull
    public Long value6() {
        return getChatId();
    }

    @Override
    @NotNull
    public LinkRecord value1(@Nullable Long value) {
        setId(value);
        return this;
    }

    @Override
    @NotNull
    public LinkRecord value2(@NotNull String value) {
        setUrl(value);
        return this;
    }

    @Override
    @NotNull
    public LinkRecord value3(@NotNull String value) {
        setType(value);
        return this;
    }

    @Override
    @NotNull
    public LinkRecord value4(@Nullable LocalDateTime value) {
        setLastUpdate(value);
        return this;
    }

    @Override
    @NotNull
    public LinkRecord value5(@Nullable LocalDateTime value) {
        setLastCheck(value);
        return this;
    }

    @Override
    @NotNull
    public LinkRecord value6(@NotNull Long value) {
        setChatId(value);
        return this;
    }

    @Override
    @NotNull
    public LinkRecord values(@Nullable Long value1, @NotNull String value2, @NotNull String value3, @Nullable LocalDateTime value4, @Nullable LocalDateTime value5, @NotNull Long value6) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached LinkRecord
     */
    public LinkRecord() {
        super(Link.LINK);
    }

    /**
     * Create a detached, initialised LinkRecord
     */
    @ConstructorProperties({ "id", "url", "type", "lastUpdate", "lastCheck", "chatId" })
    public LinkRecord(@Nullable Long id, @NotNull String url, @NotNull String type, @Nullable LocalDateTime lastUpdate, @Nullable LocalDateTime lastCheck, @NotNull Long chatId) {
        super(Link.LINK);

        setId(id);
        setUrl(url);
        setType(type);
        setLastUpdate(lastUpdate);
        setLastCheck(lastCheck);
        setChatId(chatId);
        resetChangedOnNotNull();
    }

    /**
     * Create a detached, initialised LinkRecord
     */
    public LinkRecord(domain.jooq.link_info.tables.pojos.Link value) {
        super(Link.LINK);

        if (value != null) {
            setId(value.getId());
            setUrl(value.getUrl());
            setType(value.getType());
            setLastUpdate(value.getLastUpdate());
            setLastCheck(value.getLastCheck());
            setChatId(value.getChatId());
            resetChangedOnNotNull();
        }
    }
}