var userName = document.getElementById('userName').value;

function updateQuantity(productId) {

        var button = event.target;
        var row = button.closest('tr');
        var inputElement = row.querySelector('.quantity-input');
        var quantity = parseInt(inputElement.value);

        if (quantity < 1 || quantity > 10) {
            alert('數量必須在1到10之間！');
            location.reload();
            return;
        }

        var addToCartRequestDTO = {
        userName: userName,
        productId: productId,
        productQuantity: quantity
        };


          fetch('/cart/replace/' + userName, {
            method: 'PUT',
            headers: {
              'Content-Type': 'application/json'
            },
            body: JSON.stringify(addToCartRequestDTO)
          }).then(response => {
            if (response.ok) {
              alert("購物車更新成功！");
              location.reload();
            } else {
              alert("購物車更新失敗！");
            }
          }).catch(error => {
            alert("購物車更新發生錯誤！");
          });
        }


function deleteProduct(productId) {

      fetch('/cart/delete/' + userName +'/' + productId, {
        method: 'DELETE'
      }).then(response => {
        if (response.ok) {
          alert("購物車刪除成功！");
          location.reload();
        } else {
          alert("購物車刪除失敗！");
        }
      }).catch(error => {
        alert("購物車刪除發生錯誤！");
      });
    }

function beginCheckout() {
    window.location.href = '/checkout/' + userName;
}