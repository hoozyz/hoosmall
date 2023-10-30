import React from "react";

function LoginHeader() {
  return (
    <div className="header">
      <button className="HoosMall">HoosMall</button>
      <div className="headerBtnBox">
        <button className="logout">로그아웃</button>
        <button className="mypage">마이페이지</button>
      </div>
    </div>
  );
}

export default LoginHeader;
