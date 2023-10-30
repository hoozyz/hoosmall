import React from "react";

function LogoutHeader() {
  return (
    <div className="header">
      <button className="HoosMall">HoosMall</button>
      <div className="headerBtnBox">
        <button className="login">로그인</button>
        <button className="register">회원가입</button>
      </div>
    </div>
  );
}

export default LogoutHeader;
