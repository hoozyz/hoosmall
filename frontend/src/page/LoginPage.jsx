import React, { useState } from "react";
import axios from "axios";

function LoginPage() {
  const [email, setEmail] = useState("");
  const [pwd, setPwd] = useState("");
  const [wrongEmail, setWrongEmail] = useState("");
  const [wrongPwd, setWrongPwd] = useState("");
  const [isEmail, setIsEmail] = useState(false);
  const [isPwd, setIsPwd] = useState(false);

  const emailRegEx = /^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,}$/i;
  const passwordRegEx = /^(?=.*[a-zA-Z])(?=.*[0-9]).{8,16}$/;

  const isValid = isEmail && isPwd;

  const emailCheck = (email) => {
    if (emailRegEx.test(email)) {
      setEmail(email);
      setIsEmail(true);
      setWrongEmail("");
    } else {
      setEmail("");
      setIsEmail(false);
      setWrongEmail("이메일 형식에 맞지 않습니다.");
    }
  };

  const passwordCheck = (pwd) => {
    if (passwordRegEx.test(pwd)) {
      setPwd(pwd);
      setIsPwd(true);
      setWrongPwd("");
    } else {
      setPwd("");
      setIsPwd(false);
      setWrongPwd("비밀번호는 영문,숫자를 포함한 8 ~ 16 자리입니다.");
    }
  };

  const submit = async () => {
    if (isValid) {
      await axios
        .post(`/api/user/login/duplicate`, {
          email: email,
          pwd: pwd,
        })
        .then((res) => {
          const data = res.data;

          if (data === 1) {
            // 중복 로그인이면
            // 중복 로그인 시도 선택
            if (
              window.confirm(
                "다른 기기에 로그인 되어있습니다. 현재 기기에서 로그인 하시겠습니까?"
              )
            ) {
              axios
                .post(`/api/user/login`, {
                  email: email,
                  pwd: pwd,
                })
                .then((res) => {
                  alert("로그인에 성공하였습니다.");
                  localStorage.setItem("token", JSON.stringify(res.data));
                  window.location.replace("/");
                })
                .catch((error) => {
                  alert("회원이 아니거나 정보가 틀렸습니다.");
                });
            } else {
              alert("로그인을 취소하였습니다. 다시 로그인해주세요.");
              window.location.reload();
            }
          } else {
            // 중복 로그인이 아니면 바로 로그인
            axios
              .post(`/api/user/login`, {
                email: email,
                pwd: pwd,
              })
              .then((res) => {
                alert("로그인에 성공하였습니다.");
                localStorage.setItem("token", JSON.stringify(res.data));
                window.location.replace("/");
              })
              .catch((error) => {
                alert("회원이 아니거나 정보가 틀렸습니다.");
              });
          }
        })
        .catch(() => {
          alert("회원이 아니거나 정보가 틀렸습니다.");
        });
    } else {
      return false;
    }
  };

  const test = async () => {
    await axios
      .post(`/api/user/login/duplicate`, {
        // 중복 로그인 검증
        email: "test@test.com",
        pwd: "qwer1234",
      })
      .then((res) => {
        const data = res.data;

        if (data === 1) {
          // 중복 로그인이면
          // 중복 로그인 시도 선택
          if (
            window.confirm(
              "다른 기기에 로그인 되어있습니다. 현재 기기에서 로그인 하시겠습니까?"
            )
          ) {
            axios
              .post(`/api/user/login`, {
                email: "test@test.com",
                pwd: "qwer1234",
              })
              .then((res) => {
                alert("로그인에 성공하였습니다.");
                localStorage.setItem("token", JSON.stringify(res.data));
                window.location.replace("/");
              })
              .catch((error) => {
                alert("회원이 아니거나 정보가 틀렸습니다.");
              });
          } else {
            alert("로그인을 취소하였습니다. 다시 로그인해주세요.");
            window.location.reload();
          }
        } else {
          // 중복 로그인이 아니면 바로 로그인
          axios
            .post(`/api/user/login`, {
              email: "test@test.com",
              pwd: "qwer1234",
            })
            .then((res) => {
              alert("로그인에 성공하였습니다.");
              localStorage.setItem("token", JSON.stringify(res.data));
              window.location.replace("/");
            })
            .catch((error) => {
              alert("회원이 아니거나 정보가 틀렸습니다.");
            });
        }
      })
      .catch(() => {
        alert("회원이 아니거나 정보가 틀렸습니다.");
      });
  };

  const home = () => {
    window.location.replace("/");
  };

  return (
    <div className="page">
      <div className="miniPage">
        <div className="miniHeader">
          <button className="miniHeaderVector" onClick={() => home()}></button>
          <div className="miniHeaderFont">로그인</div>
        </div>
        <div className="miniBox">
          <input
            className="miniInputBox"
            placeholder="email@email.com"
            type="email"
            onChange={(e) => emailCheck(e.target.value)}
          />
          <div className="miniBoxLine"></div>
          <div className="wrongEmail">{wrongEmail}</div>

          <input
            className="miniInputBox"
            placeholder="비밀번호"
            type="password"
            onChange={(e) => passwordCheck(e.target.value)}
          />
          <div className="miniBoxLine"></div>
          <div className="wrongPwd">{wrongPwd}</div>

          <button
            className={isValid ? "miniButton" : "miniFailButton"}
            onClick={isValid ? () => submit() : undefined}
          >
            로그인
          </button>
          <button className={"miniButton"} onClick={() => test()}>
            테스트 계정 로그인
          </button>
        </div>
      </div>
    </div>
  );
}

export default LoginPage;
