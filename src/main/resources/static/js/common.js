
var list;

$.get("/getCardType",{},function (data) {
    if(data.status==200){
        list = data.data;
    }
},'json');

//将safecode转换成名字
function changeIdToName(id) {
    for (var i=0;i<list.length;i++) {
        var id1 = list[i].id;
        if (id==id1) {
            return list[i].name;
        }
    }
}