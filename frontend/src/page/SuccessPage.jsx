import React from "react";
import { useNavigate, useLocation } from "react-router-dom";

function SuccessPage() {
  const navigate = useNavigate();
  const location = useLocation(); // 데이터 json 형태로 가져오기
  const { method, price, productTitle, paymentDate} = location.state;

  const myPage = () => {
    navigate("/mypage");
  };

  return (
    <div className="page">
      <div className="miniPage">
        <div className="miniHeader">
          <div className="payHeaderFont">결제 성공</div>
        </div>
        <div className="payBox">
          <div className="payProductBox">
            <div>구매 제품명:</div>
            <div className="payProduct">
              {productTitle}
            </div>
          </div>
          <div className="payPriceBox">
            <div>결제 금액:</div> <div className="payPrice">{price}원</div>
          </div>
          <div className="payMethodBox">
            <div>결제 수단:</div> <div className="payMethod">{method}</div>
          </div>
          <div className="payDateBox">
            <div>결제 날짜:</div> <div className="payDate">{new Date(paymentDate).toLocaleString()}</div>
          </div>
          <button className="miniButton" onClick={() => myPage()}>
            마이페이지
          </button>
        </div>
      </div>
    </div>
  );
}

export default SuccessPage;
