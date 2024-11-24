const stompClient = new StompJs.Client({
    brokerURL: 'ws://localhost:5000/server'
});

stompClient.onConnect = (frame) => {
    setConnected(true);
    console.log('Connected: ' + frame);
    stompClient.subscribe('/topic/element-answer', (greeting) => {
        showGreeting(JSON.parse(greeting.body).text);
    });
};

stompClient.onWebSocketError = (error) => {
    console.error('Error with websocket', error);
};

stompClient.onStompError = (frame) => {
    console.error('Broker reported error:' + frame.headers['message']);
    console.error('Additional details: ' + frame.body);
};

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

function connect() {
    stompClient.activate();
}

function disconnect() {
    stompClient.deactivate();
    setConnected(false);
    console.log("Disconnected");
}

function sendName() {
    stompClient.publish({
        destination: "/app/element",
        body: JSON.stringify({
            'userID': $("#userID").val(),
            'listID': $("#listID").val(),
            'elementID': $("#elementID").val(),
            'action': $("#action").val(),
            'element': {
                'name': $("#name").val(),
                'priority': $("#priority").val(),
                'status': $("#status").val(),
                'dueDate': $("#dueDate").val(),
                'tags': $("#tags").val().split(";")
            }
        })
    });
}

function showGreeting(message) {
    $("#greetings").append("<tr><td>" + message + "</td></tr>");
}

$(function () {
    $("form").on('submit', (e) => e.preventDefault());
    $("#connect").click(() => connect());
    $("#disconnect").click(() => disconnect());
    $("#send").click(() => sendName());
});