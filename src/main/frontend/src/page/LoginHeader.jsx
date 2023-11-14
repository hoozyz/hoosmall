import React from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";

function LoginHeader({ token }) {
  const navigate = new useNavigate();

  let nowTime = new Date().getTime();

  const home = () => {
    navigate("/");
  };

  const logout = async () => {
    localStorage.clear("token"); // 토큰 삭제
    window.location.replace("/");
  };

  const mycart = () => {
    navigate("/mycart");
  };

  const myPage = () => {
    navigate("/mypage");
  };

  return (
    <div className="header">
      <button className="HoosMall" onClick={() => home()}>
        HoosMall
      </button>
      <div className="headerBtnBox">
        <button className="logout" onClick={() => logout()}>
          로그아웃
        </button>
        <button className="logout" onClick={() => mycart()}>
          장바구니
        </button>
        <button className="mypage" onClick={() => myPage()}>
          마이페이지
        </button>
      </div>
    </div>
  );
}

export default LoginHeader;
