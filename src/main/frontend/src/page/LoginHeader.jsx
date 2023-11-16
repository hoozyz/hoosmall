import React, {forwardRef, useImperativeHandle} from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";

const LoginHeader = forwardRef(( token, ref ) => { // 자식 컴포넌트의 함수를 사용한다고 선언
  const navigate = new useNavigate();
  useImperativeHandle(ref, () => ({
    // 부모 컴포넌트에서 사용할 함수를 선언
    checkExpire
  }));

  if(token === null) {
    alert("현재 페이지는 회원만 접근할 수 있습니다.");
    window.location.replace("/");
  }
  
  const checkExpire = async () => {
    let now = new Date().getTime();
    if (token !== null && token.tokenExpire < now) {
      console.log(token);
      // 토큰 만료 -> 토큰 새로 발급받기
      await axios
        .post(
          "/user/refresh",
          {
            accessToken: token.accessToken,
            refreshToken: token.refreshToken,
          },
          {
            headers: {
              Authorization: "Bearer " + token.refreshToken,
            },
          }
        )
        .then((res) => {
          const data = JSON.parse(JSON.stringify(res.data));
          console.log(data);
          if (data.message) {
            // refresh token 도 만료 -> 재로그인
            alert("토큰 에러로 인해 다시 로그인해주세요. 에러 메시지 : "+data.message);
            localStorage.removeItem("token");
            window.location.replace("/");
          } else {
            localStorage.setItem("token", JSON.stringify(res.data));
            token = data; // 새로 발급받은 토큰 저장
          }
        })
        .catch((error) => {
          alert(error);
        });
    }
  };

  checkExpire();

  const home = () => {
    navigate("/");
  };

  const logout = async () => {
    localStorage.clear("token"); // 토큰 삭제
    window.location.replace("/");
  };

  const mycart = () => {
    navigate("/cart");
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
});

export default LoginHeader;
