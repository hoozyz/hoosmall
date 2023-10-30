import React, { useState, useEffect } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import LogoutHeader from "./LogoutHeader";
import Footer from "./Footer";

function DetailPage() {
  const [isDrop, setDrop] = useState(false);
  const [isEvent, setEvent] = useState(false);
  const [firstPrice, setFirstPrice] = useState(100000);
  const [price, setPrice] = useState(100000);

  const couponDrop = () => {
    setDrop((isDrop) => !isDrop); // 클릭마다 drop 여부
  };

  const discount = (per) => {
    couponDrop();
    switch (
      per // 세일마다 가격 다르게
    ) {
      case "one":
        setPrice(Math.ceil(firstPrice * 0.9));
        break;
      case "two":
        setPrice(Math.ceil(firstPrice * 0.8));
        break;
      case "three":
        setPrice(Math.ceil(firstPrice * 0.7));
        break;
    }
  };

  useEffect =
    (() => {
      // event일 떄마다 30퍼 세일
      discount("three");
    },
    [isEvent]);

  return (
    <div className="page">
      <LogoutHeader />
      <main>
        <div className="detailBox">
          <div className="detailImgBox"></div>
          <div className="detailInfoBox">
            <div className="detailTitle">
              <h2>어남어마너아머낭ㅁㄴㅇㅁ</h2>
            </div>
            <div className="detailCoupon">
              <button className="detailCouponBtn" onClick={() => couponDrop()}>
                쿠폰을 선택하세요.
              </button>
              <div className={isDrop ? "dropContent" : "hideContent"}>
                <button onClick={() => discount("one")}>10% 할인 쿠폰</button>
                <button onClick={() => discount("two")}>20% 할인 쿠폰</button>
                <button onClick={() => discount("three")}>30% 할인 쿠폰</button>
              </div>
            </div>
            <div className="detailEvent">
              <h2>
                {isEvent
                  ? "금토일 이벤트 30% 할인"
                  : "할인중이 아닙니다. 금토일을 기다려주세요."}
              </h2>
            </div>
            <div className="detailPrice">
              <h2>{price}원</h2>
            </div>
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
