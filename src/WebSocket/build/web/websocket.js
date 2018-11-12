var websocket = new WebSocket("ws://localhost:8080/WebSocket/ws123");
websocket.onopen = function () {
    console.log("connected to server");
};
websocket.onmessage = function (message) {
    console.log(message);
};
websocket.onclose = function () {

};
websocket.onerror = function (message) {

};