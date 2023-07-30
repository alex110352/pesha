var userName = document.getElementById('userName').value;

function addProduct() {
var productName = window.prompt("請輸入商品名稱:");
var productPrice = window.prompt("請輸入商品價格:");

if (productName && productPrice) {

  var productData = {
    productName: productName,
    productPrice: productPrice
  };

  fetch('/product/add', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(productData)
  }).then(response => {
    if (response.ok) {
      alert("商品新增成功！");
      location.reload();
    } else {
      alert("商品新增失敗！");
    }
  }).catch(error => {
    alert("商品新增發生錯誤！");
  });
}
}



function editProduct(productId) {
var productName = window.prompt("請輸入修改後的商品名稱:");
var productPrice = window.prompt("請輸入修改後的商品價格:");

if (productName && productPrice) {

  var productData = {
    productName: productName,
    productPrice: productPrice
  };


  fetch('/product/replace/' + productId, {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(productData)
  }).then(response => {
    if (response.ok) {
      alert("商品修改成功！");
      location.reload();
    } else {
      alert("商品修改失敗！");
    }
  }).catch(error => {
    alert("商品修改發生錯誤！");
  });
}
}

function addToCart(productId) {

  var addToCartRequestDTO = {
    userName: userName,
    productId: productId,
    productQuantity : 1
  };

  fetch('/cart/add/', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(addToCartRequestDTO)
  }).then(response => {
    if (response.ok) {
      alert("購物車新增成功！");
      location.reload();
    } else {
      alert("購物車新增失敗！");
    }
  }).catch(error => {
    alert("購物車新增發生錯誤！");
  });
}

function viewCart() {
    if (userName) {
      window.location.href = '/cart/' + userName;
    } else {
      alert("請先登入以查看購物車！");
    }
  }
