function formattedPrice(priceInCents) {
    return new Intl.NumberFormat("da-DK", {minimumFractionDigits: 2}).format(priceInCents / 100);
}

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