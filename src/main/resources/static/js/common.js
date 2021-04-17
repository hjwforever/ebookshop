//form序列化为json
$.fn.serializeObject = function () {
    var o = {};
    var a = this.serializeArray();
    $.each(a, function () {
        if (o[this.name] !== undefined) {
            if (!o[this.name].push) {
                o[this.name] = [o[this.name]];
            }
            o[this.name].push(this.value || '');
        } else {
            o[this.name] = this.value || '';
        }
    });
    return o;
};

function bookRefresh(newPageNum) {
    $('#bookList').load("/books/refresh?newPageNum=" + newPageNum);
}

function contentRefresh(PageNum, bookId) {
    alert(PageNum);
    $('#content').load("/books/content/refresh?PageNum=" + PageNum + "&bookId=" + bookId);
}

function bookSearch() {
    alert($("#bookSearchText").val()+ "12")
    this.location.href = "/books?bookName=" + $("#bookSearchText").val();
    // if (searchBookName === undefind || searchBookName === null) {
    //     searchBookName = ""
    // }
    // $('#bookList').load("/books/refresh?newPageNum=2&bookName=" + $("#bookSearchText").val());
}

function bookDownload(bookId) {
    // var bookId = $('#downloadBookName').val();
    window.location.href = "/books/download?bookId=" + bookId;
    // $.get("/books/download?bookname="+bookname).then(res => {
    //   return res;
    // });
}

//获取url后的参数值
function getUrlParam(key) {
    var href = window.location.href;
    var url = href.split("?");
    if (url.length <= 1) {
        return "";
    }
    var params = url[1].split("&");

    for (var i = 0; i < params.length; i++) {
        var param = params[i].split("=");
        if (key == param[0]) {
            return param[1];
        }
    }
}