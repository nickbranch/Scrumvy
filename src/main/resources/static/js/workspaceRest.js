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

$(".sprintSelector").on("click", function () {
    let sprintIdFromTable = $(event.currentTarget).attr("data-selectSprintId");
    console.log(sprintIdFromTable);

    $("#bodyOfTaskTable").empty();
    $.ajax({
        url: "/getSprintTasks",
        type: "POST",
        data: {
            sprintId: sprintIdFromTable
        },
        success: function (data) {
            console.log(data);
            let taskForSprintDates = data[0];
            fillSprintDates(taskForSprintDates);
            data.forEach(function (task) {
            categorizeTasksToTable(task);
            dragDropFunctionality();
            })
        }
    })
})



$(document).ready(function () {
    let currentSprintId = $("#tasksTable").attr("data-sprintId");
    $.ajax({
        url: "/getSprintTasks",
        type: "POST",
        data: {
            sprintId: currentSprintId
        },
        success: function (data) {
                data.forEach(function (task) {
                categorizeTasksToTable(task);
                dragDropFunctionality();
            })
        }

    })


});

function categorizeTasksToTable(task) {
    switch (task["statusId"]) {
        case 1:
            $("#bodyOfTaskTable").append(
                    ` 
                    <tr >
          <td  class="drop-target" id="${task.taskId}" data-sprintId="${task.taskId}" data-statusId="1" draggable="true" > ${task.description} </td>
          <td class="drop-target" data-sprintId="${task.taskId}" data-statusId="2"> </td>
          <td class="drop-target" data-sprintId="${task.taskId}" data-statusId="3"> </td> 
          </tr>
          `)
            break;
        case 2:
            $("#bodyOfTaskTable").append(
                    `
                    <tr >
          <td class="drop-target" data-sprintId="${task.taskId}" data-statusId="1"> </td>
          <td class="drop-target" id="${task.taskId}" data-sprintId="${task.taskId}" data-statusId="2" draggable="true"> ${task.description} </td>
          <td class="drop-target" data-sprintId="${task.taskId}" data-statusId="3"> </td> 
          </tr>`)
            break;
        case 3:
            $("#bodyOfTaskTable").append(
                    ` 
                    <tr  >
          <td class="drop-target" data-sprintId="${task.taskId}" data-statusId="1"> </td>
          <td class="drop-target" data-sprintId="${task.taskId}" data-statusId="2"> </td>
          <td class="drop-target" id="${task.taskId}" data-sprintId="${task.taskId}" data-statusId="3" draggable="true"> ${task.description} </td> 
          </tr>
                   `)
            break;
    }
}

function fillSprintDates(task) {

    $("#sprintDatedFromTask").empty();
    $("#sprintDatedFromTask").append(`
            <h4 class = "font-italic">
                        <span>${task.sprintStartDate}</span> -
                        <span>${task.sprintEndDate}</span>

            </h4>`)

}

function saveTaskStatusUpdate(taskId, statusId ) {
    $.ajax({
        url: "/updateTaskStatus",
        type: "POST",
        data: {
            taskId: taskId,
            statusId: statusId,
        },
        success: function (data) {
            console.log(data)
            }
        })   
      }

function handleDragStart(event) {
    event.dataTransfer.setData("text/plain", event.currentTarget.id);

}

function preventDefault(event) {
    event.preventDefault();
}

function handleDrop(event) {
    let draggedElementId = event.dataTransfer.getData("text/plain");
    let draggedElement = document.getElementById(draggedElementId);
    if (draggedElementId === $(event.currentTarget).attr("data-sprintId")) {
        console.log(draggedElementId);
        $(event.currentTarget).attr("id", draggedElementId);
        $(event.currentTarget).attr("draggable", true);
        $(event.currentTarget).html(draggedElement.textContent);
        draggedElement.innerHTML = "";
        draggedElement.removeAttribute("id");
        draggedElement.classList.remove("draggedElement");
        let columnStatus = $(event.currentTarget).attr("data-statusId")
        saveTaskStatusUpdate(draggedElementId, columnStatus);
        
    } else {
        console.log("den perase")
    }
}

function addDragStartEventListener(el) {
    el.addEventListener("dragstart", handleDragStart);
}

function initDrop(el) {
    el.addEventListener("dragover", preventDefault);
    el.addEventListener("drop", handleDrop);
}

function dragDropFunctionality() {
    let listItems = document.querySelectorAll("#bodyOfTaskTable td");
    let dropTargets = document.querySelectorAll("#bodyOfTaskTable .drop-target");

    listItems.forEach(addDragStartEventListener);
    dropTargets.forEach(initDrop);
}
            