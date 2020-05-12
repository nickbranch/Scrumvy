"use strict"


let token = $("meta[name='_csrf']").attr("content");
let header = $("meta[name='_csrf_header']").attr("content");
let csrf = {};
csrf[header] = token;


function makeTaskEditable(id) {
    let btn = $(event.target);
    if ($(event.target).html() === "edit") {
        $(event.target).parent().siblings(".editable").attr("contenteditable", true);
        $(event.target).parent().siblings(".editable").css('border', '2px solid rgb(75,0,130)');
        $(event.target).html("Save");
    } else {
        let data = {};
//        data[header] = token;
        data['taskId'] = id;
        data['description'] = $("#taskDescription_" + id).html();
        $.ajax({
            url: "/saveTask",
            type: "POST",
//            ajaxOptions: {
//                beforeSend: function (xhr)
//                {
//                    xhr.setRequestHeader(header, token);
//                }
//            },
            data: data,
            success: function (data) {
//                console.log(data);
                btn.parent().siblings(".editable").attr("contenteditable", false);
                btn.parent().siblings(".editable").removeAttr("style");
                btn.html("edit");
            }
        })
    }
}



$(document).ready(function () {
    let currentSprintId = $("#tasksTable").attr("data-sprintId");
    $.ajax({
        url: "/getSprintTasks",
        type: "POST",
        data: {
            sprintId: currentSprintId
        },
        success: function (data) {
               data.forEach(function (task){
                   categorizeTasksToTable(task) 
               })
        }
    })
});



function categorizeTasksToTable(task) {
    switch (task["statusId"]) {
        case 1:
            $("#bodyOfTaskTable").append(
         `<tr>
          <td id="toDoTask" data-sprintId="${task.taskId}"> ${task.description} </td>
          <td> </td>
          <td> </td> 
          </tr>`)
            break;
        case 2:
            $("#bodyOfTaskTable").append(
         `<tr>
          <td> </td>
          <td id="inProgressTask" data-sprintId="${task.taskId}"> ${task.description} </td>
          <td> </td> 
          </tr>`)
            break;
        case 3:
            $("#bodyOfTaskTable").append(
         `<tr>
          <td> </td>
          <td> </td>
          <td id="completeTask" data-sprintId="${task.taskId}"> ${task.description} </td> 
          </tr>`)
            break;
    }



}
