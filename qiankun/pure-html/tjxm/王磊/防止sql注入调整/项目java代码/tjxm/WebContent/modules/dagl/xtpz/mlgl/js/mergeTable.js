function mergeTableRow(field) {
    $table = $("#tb_customer1");
    var obj = getObjFromTableRow($table, field);
    for (var item in obj) {
        $("#tb_customer1").bootstrapTable('mergeCells', {
            index: obj[item].index,
            field: field,
            colspan: 1,
            rowspan: obj[item].row
        })
        if("ljdwName" == field){
            //合并操作列
            $("#tb_customer1").bootstrapTable('mergeCells', {
                index: obj[item].index,
                field: "operate",
                colspan: 1,
                rowspan: obj[item].row
            })
            //合并复选框
            $("#tb_customer1").bootstrapTable('mergeCells', {
                index: obj[item].index,
                field: "ljdwAdminName",
                colspan: 1,
                rowspan: obj[item].row
            })
        }
    }
}

function getObjFromTableRow($table, field) {
    var obj = [];
    var maxV = $table.find("th").length;
    var columnIndex = 0;
    var fieldVar;
    for (columnIndex = 0; columnIndex < maxV; columnIndex++) {
        fieldVar = $table.find("th").eq(columnIndex).attr("data-field");
        if (fieldVar == field) {
            break;
        }
    }
    var $trs = $table.find("tbody > tr");
    var $tr;
    var index = 0;
    var content = "";
    var row = 1;
    for (var i = 0; i < $trs.length; i++) {
        $tr = $trs.eq(i);
        var contentItem = $tr.find("td").eq(columnIndex).html();
        if (contentItem.length > 0 && content == contentItem) {
            row++;
        } else {
            if (row > 1) {
                obj.push({"index": index, "row": row});
            }
            index = i;
            content = contentItem;
            row = 1;
        }
    }
    if (row > 1) {
        obj.push({"index": index, "row": row});
    }
    return obj;
}

function mergeTableCol() {
    $table = $("#tb_customer1");
    var obj = getObjFromTableCol($table);
    for (var item in obj) {
        $("#tb_customer1").bootstrapTable('mergeCells', {
            index: obj[item].index,
            field: obj[item].field,
            colspan: obj[item].col,
            rowspan: 1
        })
    }
}

function getObjFromTableCol($table) {
    var obj = [];
    var $trs = $table.find("tbody > tr");
    var $tr;
    var $tds;
    var $td;
    var index = 0;
    var content = "";
    var col = 1;
    var fieldVar;
    for (var i = 0; i < $trs.length; i++) {
        $tr = $trs.eq(i);
        $tds = $tr.find("td");
        for (var j = 0; j < $tds.length; j++) {
            $td = $tds.eq(j);
            var contentItem = $td.html();
            if (contentItem.length > 0 && content == contentItem) {
                col++;
            } else {
                if (col > 1) {
                    obj.push({"index": index, "field": fieldVar, "col": col});
                }
                fieldVar = $table.find("th").eq(j).attr("data-field");
                index = i;
                content = contentItem;
                col = 1;
            }
        }
        if (col > 1) {
            obj.push({"index": index, "field": fieldVar, "col": col});
        }
    }
    return obj;
}