$("#order_form").on("input", function() {
    let cakePrice = $("select[name=cake]").find(":selected").data()["price"] || 0;
    let toppingPrice = $("select[name=topping]").find(":selected").data()["price"] || 0;
    let quantity = $("input[name=quantity]").val() || 1;

    let totalPrice = formattedPrice((cakePrice + toppingPrice) * quantity);

    $(".total-price").find("span").text(totalPrice);
});

/*

DataTables

 */

$("table").not(".no-sort").DataTable({
    "paging": false,
    "searching": true,
    "info": true,
    order: [],
    "language": {
        "search": "Søg:",
        "info":           "Viser _START_ til _END_ ud af _TOTAL_ resultater",
        "infoEmpty":      "Viser 0 til 0 ud af 0 resultater",
        "infoFiltered":   "(sorteret fra _MAX_ resultater)",
    }
});

/*

Admin

 */

$(".admin--balance-editor").on("keydown focusout", function (event) {
    let rawValue = $(this).attr("data-rawvalue");
    let newBalance = $(this).text();
    let action = "update-balance";
    let userId = $(this).closest("tr").find(".user-id").text();

    if(event.keyCode === 13 || event.which === 13) {
        event.preventDefault();
        $(this).attr("contenteditable", false);
        $.ajax({
            url: contextPath + "/admin/users",
            method: "post",
            data: {"action": action, "new-balance": newBalance, "user-id": userId},
        }).done((resp) => {
            resp = JSON.parse(resp);
            $(this).attr("data-rawvalue", resp.balance);
        }).fail(() => {
            //Maybe give error?

        }).always(() => {
            $(this).attr("contenteditable", true);
            $(this).text(formattedPrice($(this).attr("data-rawvalue")));
        });
    }
    else if((event.keyCode === 27 || event.which === 27 || event.type === "focusout") && $(this).attr("contenteditable") === "true") {
        if($(this).is(":focus")) $(this).blur();
        $(this).text(formattedPrice(rawValue));
    }

});

/*

Functions

 */

function formattedPrice(priceInCents) {
    return new Intl.NumberFormat("da-DK", {minimumFractionDigits: 2}).format(priceInCents / 100);
}

/*

Defaults

 */

$(function () {
    $('[data-toggle="tooltip"]').tooltip()
})