import React from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";

function LoginHeader() {
  const navigate = new useNavigate();

  const home = () => {
    navigate("/");
  };

  const logout = async () => {
    // const res = await axios.get("/logout");

    navigate("/");
  };

  const mycart = () => {
    navigate("/mycart");
  };

  const myPage = () => {
    navigate("/mypage");
  };

  return (
    <div className="header">
      <button className="HoosMall" onClick={() => home()}>HoosMall</button>
      <div className="headerBtnBox">
        <button className="logout" onClick={() => logout()}>로그아웃</button>
        <button className="logout" onClick={() => mycart()}>장바구니</button>
        <button className="mypage" onClick={() => myPage()}>마이페이지</button>
      </div>
    </div>
  );
}

export default LoginHeader;
