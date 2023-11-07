import React, { useState, useEffect } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";

function LoginPage() {
  const navigate = useNavigate();

  const [email, setEmail] = useState("");
  const [pwd, setPwd] = useState("");
  const [wrongEmail, setWrongEmail] = useState("");
  const [wrongPwd, setWrongPwd] = useState("");
  const [isEmail, setIsEmail] = useState(false);
  const [isPwd, setIsPwd] = useState(false);

  const emailRegEx = /^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,}$/i
  const passwordRegEx = /^(?=.*[a-zA-Z])(?=.*[0-9]).{8,16}$/

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
      setWrongPwd("비밀번호는 8글자 이상, 16글자 이하 입니다.");
    }
  };

  const submit = async () => {
    if (isValid) {
      const res = await axios
        .post("/login", {
          email: email,
          pwd: pwd,
        })
        .then((res) => {
          window.alert("로그인에 성공하였습니다.");
          navigate("/");
        })
        .error((error) => {
          window.alert("로그인에 실패하였습니다.");
        });
    } else {
      return false;
    }
  };

  return (
    <div className="page">
      <div className="miniPage">
        <div className="miniHeader">
          <button type="" className="miniHeaderVector"></button>
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
        </div>
      </div>
    </div>
  );
}

export default LoginPage;
