package ee.rabi.ali.api.account.app.common.service.model;

import org.jooq.TableRecord;

public interface ServiceDto<R extends TableRecord<R>> {

    R toRecord();
}
