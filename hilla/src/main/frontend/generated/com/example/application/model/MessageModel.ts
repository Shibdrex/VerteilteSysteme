import { _getPropertyModel as _getPropertyModel_1, makeObjectEmptyValueCreator as makeObjectEmptyValueCreator_1, ObjectModel as ObjectModel_1, StringModel as StringModel_1 } from "@vaadin/hilla-lit-form";
import type Message_1 from "./Message.js";
class MessageModel<T extends Message_1 = Message_1> extends ObjectModel_1<T> {
    static override createEmptyValue = makeObjectEmptyValueCreator_1(MessageModel);
    get text(): StringModel_1 {
        return this[_getPropertyModel_1]("text", (parent, key) => new StringModel_1(parent, key, false, { meta: { javaType: "java.lang.String" } }));
    }
    get time(): StringModel_1 {
        return this[_getPropertyModel_1]("time", (parent, key) => new StringModel_1(parent, key, true, { meta: { javaType: "java.time.Instant" } }));
    }
    get userName(): StringModel_1 {
        return this[_getPropertyModel_1]("userName", (parent, key) => new StringModel_1(parent, key, false, { meta: { javaType: "java.lang.String" } }));
    }
}
export default MessageModel;
