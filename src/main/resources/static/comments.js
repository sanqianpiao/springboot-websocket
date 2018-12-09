var stompClient = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    } else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

function connect() {
    var socket = new SockJS('/gs-guide-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/comments', function (greeting) {
            showGreeting(greeting.body);
        });
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function simulateComments() {
    $.get("/comment");
}

function stopSimulatingComments() {
    $.get("/comment/stop");
}

function showGreeting(message) {
    $("#greetings").prepend("<tr><td>" + message + "</td></tr>");
}

function postComment() {
    var comment = $("#comment").val();
    $.post("/comment", {comment: comment}).done(function(data) {
        console.log(data)
    });
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $("#connect").click(function () {
        connect();
    });
    $("#disconnect").click(function () {
        disconnect();
    });
    $("#start").click(function () {
        simulateComments();
    });
    $("#stop").click(function () {
        stopSimulatingComments();
    });
    $("#postComment").click(function() {
        postComment();
    });
});

