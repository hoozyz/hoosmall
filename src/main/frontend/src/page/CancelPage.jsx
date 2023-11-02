import React, { useState, useEffect } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";

function CancelPage() {
  const navigate = useNavigate();

  const myPage = () => {
    navigate("/mypage");
  };

  return (
    <div className="page">
      <div className="miniPage">
        <div className="miniHeader">
          <div className="payHeaderFont">결제 취소 성공</div>
        </div>
        <div className="payBox">
          <div className="payProductBox">
            <div>취소 제품명:</div> <div className="payProduct">살아숨쉬는 사과asㅁㄴㅇㅁㄴㅇdasdasdasdadasdasdasd</div>
          </div>
          <div className="payPriceBox">
            <div>취소 금액:</div> <div className="payPrice">100000원</div>
          </div>
          <div className="payMethodBox">
            <div>결제 수단:</div> <div className="payMethod">카드</div>
          </div>
          <div className="payDateBox">
            <div>취소 날짜:</div> <div className="payDate">2022.11.02</div>
          </div>
          <button className="miniButton" onClick={myPage()}>
            마이페이지
          </button>
        </div>
      </div>
    </div>
  );
}

export default CancelPage;