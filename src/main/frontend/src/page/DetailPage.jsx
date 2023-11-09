import React, { useState, useEffect } from "react";
import axios from "axios";
import { useNavigate, useParams } from "react-router-dom";
import LogoutHeader from "./LogoutHeader";
import Footer from "./Footer";

function DetailPage() {
  const { pId } = useParams(); // url/{pid} 처럼 변수 가져오기
  const [isDrop, setDrop] = useState(false);
  const [product, setProduct] = useState([]);
  const [firstPrice, setFirstPrice] = useState(100000);
  const [price, setPrice] = useState(100000);
  const [coupon, setCoupon] = useState(2);
  const [isCoupon, setIsCoupon] = useState(false);
  const [isEvent, setIsEvent] = useState(false);

  const couponDrop = () => {
    if (coupon) {
      // 쿠폰이 있을 때
      if (!isCoupon) {
        // 쿠폰을 사용하지 않았을 때
        setDrop((isDrop) => !isDrop); // 클릭마다 drop 여부
      }
    }
  };

  const couponCancelDrop = () => {
    setIsCoupon(!isCoupon); // 쿠폰 사용 취소
    setCoupon((coupon) => coupon + 1); // 쿠폰 1개 다시 되돌리기
    setPrice(firstPrice); // 가격 돌려놓기
  };

  const discount = () => {
    couponDrop();
    setPrice(Math.ceil(firstPrice * 0.85));
    setCoupon((coupon) => coupon - 1); // 쿠폰 1개 빼기
    setIsCoupon(!isCoupon);
  };

  const cart = async () => { // 장바구니에 넣고 끝
    const res = await axios.get(`/cart/save`);
  };

  useEffect(() => {
    const getProduct = async () => {
      const res = await axios.get(`/product/detail/${pId}`);
      const data = res.data;
      setProduct(data);
      setFirstPrice(data.price);
      setPrice(data.price);
    };

    getProduct();

    let now = new Date(); // 현재
    let day = now.getDay(); // 현재 요일 일요일부터 0~6
    if (day === 0 || day === 5 || day === 6) {
      // 금토일 이면
      setIsEvent(true);
      setFirstPrice(Math.ceil(firstPrice * 0.7));
      setPrice(Math.ceil(firstPrice * 0.7));
    } else {
      setIsEvent(false);
    }
  }, [pId]);

  return (
    <div className="page">
      <LogoutHeader />
      <main>
        <div className="detailBox">
          <div className="detailImgBox"></div>
          <div className="detailInfoBox">
            <div className="detailTitle">{product.title}</div>
            <div className="detailCoupon">
              <button
                className="detailCouponBtn"
                onClick={
                  isCoupon ? () => couponCancelDrop() : () => couponDrop()
                }
              >
                {isCoupon
                  ? "쿠폰 사용 취소하기"
                  : coupon
                  ? `15% 할인 쿠폰 남은 개수 : ${coupon}개`
                  : "남은 쿠폰이 없습니다."}
              </button>
              <button className="detailCartBtn" onClick={() => cart()}>
                장바구니 넣기
              </button>
              <div className="productCount">남은 수량: {product.stock}개</div>
              <div className={isDrop ? "dropCouponContent" : "hideContent"}>
                <button onClick={() => discount()}>15% 할인 쿠폰</button>
              </div>
            </div>
            <div className="detailEvent">
              {isEvent ? "금토일 이벤트 30% 할인 적용" : "이벤트 요일이 아닙니다."}
            </div>
            <div className="detailPrice">{isEvent ? Math.ceil(price * 0.7) : price}원</div>
          </div>
        </div>
        <button type="" className="payBtn">
          결제하기
        </button>
      </main>
      <Footer />
    </div>
  );
}

export default DetailPage;
