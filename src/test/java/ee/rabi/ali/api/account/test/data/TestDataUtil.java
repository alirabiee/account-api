package ee.rabi.ali.api.account.test.data;

import ee.rabi.ali.api.account.test.data.account.AccountTestData;
import ee.rabi.ali.api.account.test.data.balance_snapshot.BalanceSnapshotTestData;
import ee.rabi.ali.api.account.test.data.ledger.LedgerTestData;
import ee.rabi.ali.api.account.test.data.transfer.TransferTestData;
import lombok.RequiredArgsConstructor;

import javax.inject.Singleton;

@Singleton
@RequiredArgsConstructor
public class TestDataUtil {
    //CHECKSTYLE:OFF
    public final AccountTestData accounts;
    public final BalanceSnapshotTestData balanceSnapshots;
    public final LedgerTestData ledgers;
    public final TransferTestData transfers;
    //CHECKSTYLE:ON
}
