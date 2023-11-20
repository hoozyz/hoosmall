import React, { useState, useEffect } from "react";
import axios from "axios";
import { useNavigate, useLocation } from "react-router-dom";

function CancelPage() {
  const navigate = useNavigate();
  const location = useLocation();
  const { productTitle, price, method, paymentDate} = location.state;

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
            <div>취소 제품명:</div>{" "}
            <div className="payProduct">
              {productTitle}
            </div>
          </div>
          <div className="payPriceBox">
            <div>취소 금액:</div> <div className="payPrice">{price}원</div>
          </div>
          <div className="payMethodBox">
            <div>결제 수단:</div> <div className="payMethod">{method}</div>
          </div>
          <div className="payDateBox">
            <div>취소 날짜:</div> <div className="payDate">{new Date(paymentDate).toLocaleString()}</div>
          </div>
          <button className="miniButton" onClick={() => myPage()}>
            마이페이지
          </button>
        </div>
      </div>
    </div>
  );
}

export default CancelPage;
