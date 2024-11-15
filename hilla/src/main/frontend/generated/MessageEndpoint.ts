import { EndpointRequestInit as EndpointRequestInit_1, Subscription as Subscription_1 } from "@vaadin/hilla-frontend";
import type Message_1 from "./com/example/application/model/Message.js";
import client_1 from "./connect-client.default.js";
function join_1(): Subscription_1<Message_1 | undefined> { return client_1.subscribe("MessageEndpoint", "join", {}); }
async function send_1(message: Message_1 | undefined, init?: EndpointRequestInit_1): Promise<void> { return client_1.call("MessageEndpoint", "send", { message }, init); }
export { join_1 as join, send_1 as send };
