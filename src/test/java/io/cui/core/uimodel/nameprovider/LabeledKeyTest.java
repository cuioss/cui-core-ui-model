package io.cui.core.uimodel.nameprovider;

import io.cui.test.valueobjects.ValueObjectTest;
import io.cui.test.valueobjects.api.contracts.VerifyConstructor;

@VerifyConstructor(of = "content", required = "content")
class LabeledKeyTest extends ValueObjectTest<LabeledKey> {

}
