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
            //edo grafomai ston broker kai kai ekei vlepo kai akouo minimata
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

//function fetchAll() {
//    $.get(url + "/demo/fetchAllUsers", function (response) {
//        let users = response;
//        console.log("0");
//        let usersTemplateHTML = "";
//        for (let i = 0; i < users.length; i++) {
//            
//            usersTemplateHTML += " <li>"+users[i]+ "</li>";
//            
//            usersTemplateHTML = usersTemplateHTML + '<a href="#" onclick="selectUser(\'' + users[i] + '\')"><li class="clearfix">\n' +
//                '                <img src="https://rtfm.co.ua/wp-content/plugins/all-in-one-seo-pack/images/default-user-image.png" width="55px" height="55px" alt="avatar" />\n' +
//                '                <div class="about">\n' +
//                '                    <div id="userNameAppender_' + users[i] + '" class="name">' + users[i] + '</div>\n' +
//                '                    <div class="status">\n' +
//                '                        <i class="fa fa-circle offline"></i>\n' +
//                '                    </div>\n' +
//                '                </div>\n' +
//                '            </li></a>';
//        console.log("edo giati den ektipono? ?"); //kai auto edo de m to ektiponei kati paei lathos
//        }
//        $('#usersList').html(usersTemplateHTML);
//        console.log("1");
//    });


//function fetchAll() {
//    $.get(url + "/demo/fetchAllUsers", function (response) {
//        let users = response;        
//        let usersTemplateHTML = "";
//        for (let i = 0; i < users.length; i++) {
//			usersTemplateHTML += "<li>" + users[i] + "</li>";
//        }
//        $('#usersList').html(usersTemplateHTML);
//        console.log("asdasdasd");
//    });
//}

connectToChat();