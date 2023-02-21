package io.cui.core.uimodel.nameprovider.data;

import io.cui.test.valueobjects.ValueObjectTest;
import io.cui.test.valueobjects.api.contracts.VerifyConstructor;

@VerifyConstructor(of = "key", required = "key")
@VerifyConstructor(of = { "key", "value" }, required = "key")
class EntryDataTest extends ValueObjectTest<EntryData<?, ?>> {

}
