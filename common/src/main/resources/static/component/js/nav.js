$(function () {
    $("li>a").on("click",function (e) {
        e.preventDefault();
        $("#iframeMain").attr("src", $(this).attr("data-href"));

        $(this).parent().siblings('li').removeClass('active');
        $(this).parent().addClass('active');
    });
});

