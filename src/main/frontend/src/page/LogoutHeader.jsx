import React from "react";
import { useNavigate } from "react-router-dom";

function LogoutHeader() {
  const navigate = new useNavigate();

  const login = () => {
    navigate("/login");
  };

  const register = () => {
    navigate("/register");
  };

  return (
    <div className="header">
      <button className="HoosMall">HoosMall</button>
      <div className="headerBtnBox">
        <button className="login" onClick={() => login()}>로그인</button>
        <button className="register" onClick={() => register()}>회원가입</button>
      </div>
    </div>
  );
}

export default LogoutHeader;
