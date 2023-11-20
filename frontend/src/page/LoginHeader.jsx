import React, { forwardRef, useImperativeHandle } from "react";
import axios from "axios";

const LoginHeader = forwardRef((token, ref) => {
  useImperativeHandle(ref, () => ({
    // 부모 컴포넌트에서 사용할 함수를 선언
    checkExpire,
  }));
  token = token.token;

  // 자식 컴포넌트의 함수를 사용한다고 선언

  if (token === null) {
    alert("현재 페이지는 회원만 접근할 수 있습니다.");
    window.location.replace("/");
  }

  const checkExpire = async () => {
    // refreshToken을 가지고 가서 DB의 refreshToken이랑 다르면 로그아웃 -> 중복로그인 방지
    axios.post(`/user/validate/${token.refreshToken}`)
    .then((res) => {
      // 1이면 로그인 유지
      // 0이면 로그아웃
      if(res.data === 0) {
        localStorage.removeItem("token");
        window.location.replace("/");
        alert("다른 기기에서 로그인으로 로그아웃됩니다.");
        return false;
      } 
    })
    .catch((error) => {
      alert(error);
    });

    let now = new Date().getTime();

    if (token !== null && token.tokenExpire < now) {
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
          const data = res.data;

          if (data.message) {
            alert(
              "토큰 에러로 인해 다시 로그인해주세요. 에러 메시지 : " +
                data.message
            );
            localStorage.removeItem("token");
            window.location.replace("/");
          } else {
            localStorage.setItem("token", JSON.stringify(res.data));
            token = data; // 새로 발급받은 토큰 저장
          }
        })
        .catch((error) => {
          // refresh token 도 만료 -> 재로그인
          alert("토큰 에러로 인해 다시 로그인해주세요. 에러 메시지 : "+error);
          localStorage.removeItem("token");
          window.location.replace("/");
        });
    }
  };

  const home = () => {
    window.location.replace("/");
  };

  const logout = async () => {
    // 로그아웃 시 DB에 있는 refreshToken을 삭제해서 중복 로그인 체크
    await axios.put(`/user/logout`,
    {
      headers: {
        Authorization: "Bearer " + token.accessToken,
      }
    })
    .then((res) => {
      if(res.data.message) {
        alert("로그아웃에 실패하였습니다. 다시 시도해주세요. 에러 메시지 : " + res.data.message);
      } else {
      localStorage.clear("token"); // 토큰 삭제
      window.location.replace("/");
      }
    })
    .catch((error) => {
      alert("로그아웃에 실패하였습니다. 다시 로그인해주세요.");
      localStorage.clear("token"); // 토큰 삭제
      window.location.replace("/");
    });
  };

  const mycart = () => {
    window.location.replace("/cart");
  };

  const myPage = () => {
    window.location.replace("/mypage");
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
