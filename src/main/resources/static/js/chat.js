const url = 'http://localhost:8082';
let stompClient;
let selectedUser;
let newMessages = new Map();

function connectToChat() {
    let socket = new SockJS(url + '/chat');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
     //   stompClient.subscribe("/topic/messages/" + userName, function (response) {
        stompClient.subscribe("/topic/messages/" + $("#projectId").val(), function (response) {
            let data = JSON.parse(response.body);
            console.log("elava minima: " + data.message + " from " + data.fromLogin);
            if (data.fromLogin === $("#loggedInUser").val()) return;
            render(data.message, data.fromLogin);
            /*
            if (selectedUser === data.fromLogin) {
                render(data.message, data.fromLogin);
            } else {
                newMessages.set(data.fromLogin, data.message);
                $('#userNameAppender_' + data.fromLogin).append('<span id="newMessage_' + data.fromLogin + '" style="color: red">+1</span>');
            }*/
        });
    });
}

function sendMsg(from, text) {
//    stompClient.send("/demo/chat/" + selectedUser, {}, JSON.stringify({
    const chatURL = "/topic/messages/" + $("#projectId").val();
      stompClient.send(chatURL, {}, JSON.stringify({
        fromLogin: from,
        message: text
    }));
}


function selectUser(userName) {
    console.log("selecting users: " + userName);
    selectedUser = userName;
    let isNew = document.getElementById("newMessage_" + userName) !== null;
    if (isNew) {
        let element = document.getElementById("newMessage_" + userName);
        element.parentNode.removeChild(element);
        render(newMessages.get(userName), userName);
    }
    $('#selectedUserId').html('');
    $('#selectedUserId').append('Chat with ' + userName);
}


connectToChat();