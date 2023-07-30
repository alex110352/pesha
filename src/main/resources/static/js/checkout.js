var userName = document.getElementById('userName').value;
var cart = document.getElementById('cart');
var totalPrice = cart.getAttribute('data-total-price');
totalPrice = parseFloat(totalPrice);
var discountAmount = 0;
var finalPrice = totalPrice - discountAmount;

function submitOrder() {
    var shippingAddress = document.getElementById('shippingAddress').value;
    var paymentMethod = document.getElementById('paymentMethod').value;

    var orderRequestDTO = {
        userName: userName,
        shippingAddress: shippingAddress,
        paymentMethod: paymentMethod,
        discount: discountAmount,
        totalPrice: totalPrice
    };

    if (!shippingAddress) {
        alert('請輸入運送地址！');
        return;
    }

    fetch('/checkout', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(orderRequestDTO)
    }).then(response => {
        if (response.ok) {
            alert('感謝您的訂單，我們將盡快處理！');
            window.location.href = '/order';
        } else {
            alert('感謝您的訂單，我們將盡快處理！');
            window.location.href = '/order/' + userName;
        }
    }).catch(error => {
        alert('訂單提交發生錯誤！');
    });
}

function roundToWholeNumber(value) {
    return Math.round(value);
}

function updateDiscount() {
    var discountCodeElement = document.getElementById('discountCode');
    if (discountCodeElement){
        var discountCode = discountCodeElement.value;
    }

    if (discountCode === 'DISCOUNT10') {
        discountAmount = totalPrice * 0.1;
    } else {
        discountAmount = 0;
    }

    // 更新右側顯示的折扣金額
    var discountElement = document.getElementById('discountAmount');
    discountElement.innerText = '$' + roundToWholeNumber(discountAmount);
    discountAmount = roundToWholeNumber(discountAmount);
    finalPrice = totalPrice - discountAmount;

    // 更新顯示的最終價格
    var finalPriceElement = document.getElementById('finalPrice');
    finalPriceElement.innerText = '$' + finalPrice;
}

// 載入時顯示初始總計
document.addEventListener('DOMContentLoaded', function () {
    var discountElement = document.getElementById('discountAmount');
    discountElement.innerText = '$' + roundToWholeNumber(discountAmount);

    var finalPriceElement = document.getElementById('finalPrice');
    finalPriceElement.innerText = '$' + finalPrice;
});