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