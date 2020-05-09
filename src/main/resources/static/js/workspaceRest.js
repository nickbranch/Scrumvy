function getContextPath() {
    return window.location.pathname;
}


function makeEditable(){
     $(event.target).parent().siblings(".editable").attr("contenteditable", true);
 
}




//function getTrainer(id) {
//    $.ajax({
//        url: getContextPath() + "/getTrainer",
//        type: "POST",
//        data: {
//            trainerId: id
//        },
//        success: function (response) {