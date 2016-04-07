var readyf = function initCategories() {
    var categories = document.getElementsByClassName('category_item');
    if (categories == null || categories.length == 0)  return;

    var nowItem = window.location.href.substr(window.location.href.lastIndexOf('/') + 1, window.location.href.length);

    if (nowItem == null || nowItem.length == 0) {
        categories[0].className += ' active_category';
    } else {
        categories.getElementsByName(nowItem).className += ' active_category';
    }
};

document.ready = function (callback) {
    ///兼容FF,Google
    if (document.addEventListener) {
        document.addEventListener('DOMContentLoaded', function () {
            document.removeEventListener('DOMContentLoaded', arguments.callee, false);
            callback();
        }, false)
    }
    //兼容IE
    else if (document.attachEvent) {
        document.attachEvent('onreadytstatechange', function () {
            if (document.readyState == "complete") {
                document.detachEvent("onreadystatechange", arguments.callee);
                callback();
            }
        })
    }
    else if (document.lastChild == document.body) {
        callback();
    }
}
document.ready = readyf;