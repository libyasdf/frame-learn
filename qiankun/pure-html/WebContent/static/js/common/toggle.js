//点击折叠
function showTable(table,img){
    $("#"+table).toggle();
    if($("#"+table).is(":hidden")){
        $("#"+img).attr({"src":"/static/images/icon7_down.gif","title":"点击展开"});
    }else{
        $("#"+img).attr({"src":"/static/images/icon7.gif","title":"点击折叠"});
    }
}