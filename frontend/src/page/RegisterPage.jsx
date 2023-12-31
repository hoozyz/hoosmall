import React, { useState, useEffect } from "react";
import axios from "axios";

function RegisterPage() {

  const [email, setEmail] = useState("");
  const [pwd, setPwd] = useState("");
  const [pwdConfirm, setPwdConfirm] = useState("");
  const [wrongEmail, setWrongEmail] = useState("");
  const [wrongPwd, setWrongPwd] = useState("");
  const [wrongPwdConfirm, setWrongPwdConfirm] = useState("");

  const [isEmail, setIsEmail] = useState(false);
  const [isPwd, setIsPwd] = useState(false);
  const [isPwdConfirm, setIsPwdConfirm] = useState(false);
  const isValid = isEmail && isPwd && isPwdConfirm;

  // "이미 존재하는 이메일입니다.";
  // "영어 대소문자, 숫자를 조합하여 8~16자리를 입력해주세요.";
  // "비밀번호가 일치하지 않습니다.";

  const emailRegEx = /^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,}$/i;
  const passwordRegEx = /^(?=.*[a-zA-Z])(?=.*[0-9]).{8,16}$/; // 영어 대소문자, 숫자를 혼합하여 8~16글자

  const emailCheck = async (email) => {
    if (emailRegEx.test(email)) {
      const res = await axios.get("/api/user/register/duplicate/" + email);
      const check = res.data;

      if (check === 0) {
        // 중복이 아닐 때
        setEmail(email);
        setIsEmail(true);
        setWrongEmail("");
      } else {
        setEmail("");
        setIsEmail(false);
        setWrongEmail("이미 존재하는 이메일입니다.");
      }
    } else {
      setEmail("");
      setIsEmail(false);
      setWrongEmail("이메일 형식에 맞지 않습니다.");
    }
  };

  const passwordCheck = (pwd) => {
    if (passwordRegEx.test(pwd)) {
      // 비밀번호 형식이 맞을 때
      setPwd(pwd);
      setIsPwd(true);
      setWrongPwd("");
    } else {
      setPwd("");
      setIsPwd(false);
      setWrongPwd("영어 대소문자, 숫자를 조합하여 8~16자리를 입력해주세요.");
    }
  };

  const passwordConfirmCheck = (pwdConfirm) => {
    if (pwdConfirm === pwd) {
      // 일치할 때
      setPwdConfirm(pwdConfirm);
      setIsPwdConfirm(true);
      setWrongPwdConfirm("");
    } else {
      setPwdConfirm("");
      setIsPwdConfirm(false);
      setWrongPwdConfirm("비밀번호가 일치하지 않습니다.");
    }
  };

  const submit = async () => {
    if (isValid) {
      // 유효성 검사를 다 통과했을 때
      const res = await axios
        .post(`/api/user/register`, {
          email: email, // 서버로 보낼 데이터
          pwd: pwd,
        })
        .then((res) => {
          alert("회원가입에 성공하였습니다.");

          // 홈페이지로 이동
          window.location.replace("/");
        })
        .catch((error) => {
          alert("회원가입에 실패하였습니다. 에러 메시지: " + error);
        });
    } else {
      return false;
    }
  };

  useEffect = (() => {}, [isEmail, isPwd, isPwdConfirm]);

  const home = () => {
    window.location.replace("/");
  };

  return (
    <div className="page">
      <div className="miniPage">
        <div className="miniHeader">
          <button className="miniHeaderVector" onClick={() => home()}></button>
          <div className="miniHeaderFont">가입하기</div>
        </div>
        <div>
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

            <input
              className="miniInputBox"
              placeholder="비밀번호 확인"
              type="password"
              onChange={(e) => passwordConfirmCheck(e.target.value)}
            />
            <div className="miniBoxLine"></div>
            <div className="wrongPwdConfirm">{wrongPwdConfirm}</div>

            <button
              className={isValid ? "miniButton" : "miniFailButton"}
              onClick={isValid ? () => submit() : undefined}
            >
              회원가입
            </button>
          </div>
        </div>
      </div>
    </div>
  );
}

export default RegisterPage;
