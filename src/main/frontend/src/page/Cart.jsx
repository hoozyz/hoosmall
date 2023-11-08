import React, { useState, useEffect } from "react";
import axios from "axios";
import classNames from "classnames";

const Cart = ({ cart, idx, setTotal, coupon, changeCoupon }) => {
  const [isDrop, setDrop] = useState(false); // 쿠폰 drop 여부
  const [isCountDrop, setCountDrop] = useState(false); // 수량 drop 여부
  const [buyCount, setBuyCount] = useState(0); // 구매 개수
  const [firstPrice, setFirstPrice] = useState(100000); // 상품 원가격
  const [price, setPrice] = useState(100000); // 상품 최종 구매가격
  const [count, setCount] = useState(30); // 현재 구매하게 되면 남는 재고
  const [firstCount, setFirstCount] = useState(30); // 상품 최초 재고
  // const [coupon, setCoupon] = useState(2); // 보유 쿠폰 개수
  const [isCouponCount, setIsCouponCount] = useState(0); // 쿠폰 사용 개수

  const [useCoupon, setUseCoupon] = useState(0); // 쿠폰을 사용할 수 있는 수 최대 3개

  const url = "http://shopping.phinf.naver.net/main_1031546/10315467179.jpg";

  let isEvent = false;
  let now = new Date(); // 현재
  let day = now.getDay(); // 현재 요일 일요일부터 0~6
  if (day === 0 || day === 5 || day === 6) {
    // 금토일 이면
    isEvent = true;
    setFirstPrice((firstPrice) => Math.ceil(firstPrice * 0.7)); // 이벤트면 시작부터 30퍼 세일
  } else {
    isEvent = false;
  }

  const couponDrop = () => {
    if (buyCount) {
      if (coupon) {
        // 쿠폰이 있을 때
        if (!isCouponCount) {
          // 쿠폰을 사용하지 않았을 때
          setDrop((isDrop) => !isDrop); // 클릭마다 drop 여부
        }
      }
    }
  };

  const setCouponCount = (count) => {
    let sale = (1 - 0.15 * count.i);
    changeCoupon((coupon) => coupon - count.i); // 고른 개수만큼 빼기
    setIsCouponCount(count); // 사용한 쿠폰 개수
    changeCoupon(coupon - count.i); // 부모 컴포넌트인 나의 장바구니에 이 회원의 남은 쿠폰 개수 보내기
    setPrice((price) => Math.ceil(price * sale)); // 현재 상품 총 금액 변경
    setTotal(idx, Math.ceil(price * sale)); // 회원 총 금액 변경
    setDrop((isDrop) => !isDrop); // 클릭마다 drop 여부
  };

  const changeBuyCount = (buy) => { // 구매 개수 변경
    setBuyCount(buy);
    setPrice(firstPrice * buy);
    setTotal(idx, firstPrice * buy); // 회원 총 금액 변경
    setCountDrop(!isCountDrop);
    setCount(firstCount - buy); // 제품 개수 변경
  };

  useEffect(() => {
    // 구매수량, 쿠폰수 가 바뀔 때 마다 쿠폰 사용할 수 있는 개수 적용하기
    // 사용할 수 있는 쿠폰 개수는 buycount 보다 적고, coupon보다 적어야함
    if (count >= coupon) {
      // 선택한 수량이 사용가능한 쿠폰 개수를 초과할 때 -> 쿠폰 개수만큼 사용가능
      if (coupon <= 3) {
        // 쿠폰 개수가 3개보다 크면 3개로 고정, 적으면 쿠폰 개수로 고정
        setUseCoupon(coupon);
      } else {
        setUseCoupon(3);
      }
    } else {
      // 선택한 수량이 사용가능한 쿠폰보다 적을 때 -> 수량만큼만 사용가능
      setUseCoupon(coupon);
    }
  }, [buyCount, coupon]);

  const arr = [1,2,3];

  const countDrop = () => {
    if(!isCouponCount) { // 쿠폰을 사용했으면 드롭 안됨
      setCountDrop((isCountDrop) => !isCountDrop); // 클릭마다 drop 여부
    }
  };

  const cancelCoupon = () => {
    // 쿠폰 취소
    changeCoupon((coupon) => coupon + isCouponCount.i); // 취소한 쿠폰 개수만큼 되돌리기
    setIsCouponCount(0); // 사용한 쿠폰 개수 0개로 변경
    changeCoupon(coupon + isCouponCount.i); // 부모 컴포넌트인 나의 장바구니에 이 회원의 남은 쿠폰 개수 보내기
    setPrice(firstPrice * buyCount); // 총 금액에서 할인가격 복구
    setTotal(idx, firstPrice * buyCount); // 회원 총 금액에서 할인가격 복구
  };

  return (
    <div className={classNames("list", "list" + { idx })}>
      <button
        className="mainImgBox"
        style={{ background: `url(${url})`, backgroundSize: "cover" }}
      ></button>
      <div className="mainInfoBox">
        <button className="cartTitle">
          상품명상품명상품명상품명상품명상품명상품명상품명상품명
        </button>
        <div className="cartCount">남은 수량 : {count}개</div>
        <div className="cartCoupon">
          <button
            className="cartCouponBtn"
            onClick={isCouponCount ? () => cancelCoupon() : () => couponDrop()}
          >
            {buyCount
              ? isCouponCount
                ? "쿠폰 사용 취소하기"
                : coupon
                ? `15% 할인 쿠폰 남은 개수 : ${coupon}개`
                : "남은 쿠폰이 없습니다."
              : "구매 수량부터 선택해주세요."}
          </button>
          <div
            className={isDrop && buyCount ? "dropCouponContent" : "hideContent"}
          >
            {
              arr.map((i, idx) => {
                if(i <= useCoupon) { // 현재 사용할 수 있는 쿠폰 수 만큼만 쿠폰 나타내기
                  return (<button key={i} className="dropBtn" onClick={() => setCouponCount({i})}>{i}개</button>);
                }
              })
            }

          </div>
        </div>
        <div className="cartEvent">
          {isEvent ? "금토일 이벤트 30% 할인" : "이벤트 요일이 아닙니다."}
        </div>
        <div className="cartPayBox">
          <div className="cartPayPrice">{price}원</div>
          <button className="cartCountBtn" onClick={() => countDrop()}>
            {buyCount ? <div>{buyCount}개</div> : <div>수량 선택</div>}
          </button>
          <div className={isCountDrop ? "dropCountContent" : "hideContent"}>
            <button className="dropBtn" onClick={() => changeBuyCount(1)}>
              1개
            </button>
            <button className="dropBtn" onClick={() => changeBuyCount(2)}>
              2개
            </button>
            <button className="dropBtn" onClick={() => changeBuyCount(3)}>
              3개
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Cart;
