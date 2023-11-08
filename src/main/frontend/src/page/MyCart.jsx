import React, { useState, useEffect } from "react";
import axios from "axios";
import "./HomePage.css";
import { useNavigate } from "react-router-dom";
import Cart from "./Cart";
import LoginHeader from "./LoginHeader";
import Footer from "./Footer";

function MyCart() {
  //   const [products, setProducts] = useState([]);
  const [coupon, setCoupon] = useState(2); // 자식인 cart에서 가져올 현재 로그인 유저가 가지고 있는 쿠폰 수
  const [totalPrice, setTotalPrice] = useState(100000); // 자식마다 결제 금액을 가져와서 합치기
  const [priceArr, setPriceArr] = useState([0,0,0,0,0,0]);


  // 쿠폰 정보 보낼 때 가격, 쿠폰 사용 횟수 같이 리스트로 보내기

  const changeCoupon = (coupon) => {
    console.log(coupon);
    setCoupon(coupon);
  };

  const setTotal = (idx, price) => { // idx(상품순서)에 따른 총금액 정하기
    priceArr[idx] = price // 순서에 맞게 가격 가져오기
    setPriceArr(priceArr);

    let total = 0;
    for(let i = 0; i < priceArr.length; i++) {
      total += priceArr[i];
    }
    
    setTotalPrice(total);
  };

  const products = [
    {
      product: "",
      id: 1,
    },
    {
      product: "",
      id: 2,
    },
    {
      product: "",
      id: 3,
    },
    {
      product: "",
      id: 4,
    },
    {
      product: "",
      id: 5,
    },
    {
      product: "",
      id: 6,
    },
  ];

  const getCart = async () => {
    const res = await axios.get("/product/cart");
    // setProducts(res.data); // 응답 중에서 상품들 리스트
  };

  useEffect(() => {
    if(products == null) {
      getCart();
    }
  }, [products]);

  return (
    <div className="page">
      <LoginHeader />
      <main>
        <div className="mypageTitle">나의 장바구니(남은 쿠폰 수 : {coupon}개)</div>
        <div className="mainContainer">
          {products.map((product, idx) => (
            <Cart
              key={product.id}
              cart={product}
              idx={idx}
              coupon={coupon}
              setTotal={setTotal}
              changeCoupon={changeCoupon}
            /> // getCoupon, setTotal 메소드 자식도 사용 가능
          ))}
        </div>
        <div className="totalPriceBox">
            <div className="totlePrice">총금액 : {totalPrice}원</div>
            <button type="" className="payBtn">
              결제하기
            </button>
        </div>
      </main>
      <Footer />
    </div>
  );
}
export default MyCart;
