import React from "react";

function LogoutHeader() {

  const home = () => {
    window.location.replace("/");
  };

  const login = () => {
    window.location.replace("/login");
  };

  const register = () => {
    window.location.replace("/register");
  };

  return (
    <div className="header">
      <button className="HoosMall" onClick={() => home()}>HoosMall</button>
      <div className="headerBtnBox">
        <button className="login" onClick={() => login()}>로그인</button>
        <button className="register" onClick={() => register()}>회원가입</button>
      </div>
    </div>
  );
}

export default LogoutHeader;
