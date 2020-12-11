$(function () {
    $('#searchbox').keypress(function (e) {
        var key = e.which; //e.which是按键的值
        if (key == 13) {
            var keyword = $(this).val();
            if (keyword && keyword != '') {
                window.location.href = '/search/' + keyword + '/1';
            }
        }
    });
});

function searchForIndex() {
    var keyword = $('#searchbox').val();
    if (keyword && keyword != '') {
        window.location.href = '/search/' + keyword + '/1';
    }
}