package ee.rabi.ali.api.account.app.common.service.model;

import io.micronaut.core.annotation.Introspected;
import org.jooq.TableRecord;

@Introspected
public interface ServiceDto<R extends TableRecord<R>> {
    R toRecord();
}
