import React, { useState, useEffect } from "react";
import axios from "axios";
import { useNavigate, useLocation } from "react-router-dom";

function FailPage() {
  const navigate = useNavigate();
  const location = useLocation(); // 데이터 json 형태로 가져오기
  const {failMassage, pId} = location.state;
  console.log(location.state);

  const detailPage = () => {
    navigate("/detail/" + pId);
  };

  const MyCart = () => {
    navigate("/cart");
  };

  const homePage = () => {
    navigate("/");
  };

  return (
    <div className="page">
      <div className="miniPage">
        <div className="miniHeader">
          <div className="payHeaderFailFont">결제 실패</div>
        </div>
        <div className="payBox">
          <div className="payProductBox">
            <div>실패 이유:</div>
            <div className="payProduct">
           {failMassage}
            </div>
          </div>
          <button className="miniButton" onClick={pId === 0 ? () => MyCart() : () => detailPage()}>
            {pId === 0 ? "장바구니 페이지" : "제품 상세페이지"}
          </button>
          <button className="paySecondBtn" onClick={() => homePage()}>
            홈페이지
          </button>
        </div>
      </div>
    </div>
  );
}

export default FailPage;
