import React, { useState, useEffect } from "react";
import axios from "axios";
import { useNavigate, useParams } from "react-router-dom";

function FailPage() {
  const { pId } = useParams();
  const navigate = useNavigate();

  const detailPage = () => {
    navigate("/detail" + pId);
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
            잔액 부족
            </div>
          </div>
          <button className="miniButton" onClick={() => detailPage()}>
            제품 상세페이지
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
