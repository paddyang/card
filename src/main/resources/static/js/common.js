
var CardTypeList;

$.get("/getAllCardType",{},function (data) {
    if(data.status==200){
        CardTypeList = data.data;
    }
},'json');

function getCardTypeList() {
    return CardTypeList;
}

//将safecode转换成名字
function changeIdToName(id) {
    for (var i=0;i<CardTypeList.length;i++) {
        var id1 = CardTypeList[i].id;
        if (id==id1) {
            return CardTypeList[i].name;
        }
    }
}