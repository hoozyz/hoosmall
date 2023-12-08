import React, { useState, useEffect, useRef } from "react";
import axios from "axios";
import "./HomePage.css";
import { useNavigate } from "react-router-dom";
import uuid from "react-uuid";
import Cart from "./Cart";
import LoginHeader from "./LoginHeader";
import Footer from "./Footer";

function MyCart() {
  const navigate = useNavigate();
  const [products, setProducts] = useState([]);
  const [coupon, setCoupon] = useState(0); // 자식인 cart에서 가져올 현재 로그인 유저가 가지고 있는 쿠폰 수
  const [totalPrice, setTotalPrice] = useState(0); // 자식마다 결제 금액을 가져와서 합치기
  const [priceArr, setPriceArr] = useState([0, 0, 0, 0, 0, 0]);
  const [title, setTitle] = useState("");
  const [idCoupon, setIdCoupon] = useState([]);
  const [isEvent, setIsEvent] = useState(false);

  const childRef = useRef();
  let token = JSON.parse(localStorage.getItem("token"));

  const changeCoupon = (coupon) => {
    setCoupon(coupon);
  };

  const setTotal = (idx, price) => {
    // idx(상품순서)에 따른 총금액 정하기
    priceArr[idx] = price; // 순서에 맞게 가격 가져오기
    setPriceArr(priceArr);

    let total = 0;
    for (let i = 0; i < priceArr.length; i++) {
      total += priceArr[i];
    }

    setTotalPrice(total);
  };

  const setInfo = (idx, info) => {
    // 결제할 때 필요한 자식 컴포넌트 각각의 정보(pId, count, coupon)
    idCoupon[idx] = {
      pId: idCoupon[idx].pId,
      count: info.count,
      coupon: info.coupon,
    };
    setIdCoupon(idCoupon);
  };

  // 쿠폰 정보 보낼 때 가격, 쿠폰 사용 횟수 같이 리스트로 보내기
  const payment = async () => {
    // 결제
    if (totalPrice) {
      // 총금액이 0원이 아닐 때
      var IMP = window.IMP;
      IMP.init("imp33422434");

      let now = new Date().getTime() + uuid();

      childRef.current.checkExpire();

      let email = "";
      await axios
      .get(`/api/user/me`, {
        headers: {
          Authorization: "Bearer " + token.accessToken,
        },
      })
      .then((res) => {
        email = res.data;
      })
      .catch((error) => {
        alert(error);
      }); // 회원의 이메일 가져오기

      await axios
        .post(
          `/api/pay/preorder`,
          {
            merchantUid: now,
            amount: isEvent ? Math.ceil(totalPrice * 0.7) : totalPrice,
          },
          {
            headers: {
              Authorization: "Bearer " + token.accessToken,
            },
          }
        )
        .then((res) => {
          IMP.request_pay(
            {
              pg: "tosspayments", // 토스페이먼츠
              merchant_uid: now,
              name: title,
              pay_method: "card",
              amount: isEvent ? Math.ceil(totalPrice * 0.7) : totalPrice,
              buyer_name: "홍길동",
              buyer_email: email, // 회원 이메일
            },
            async (res) => {
              // PG 사에서 응답
              if (!res.error_msg) {
                childRef.current.checkExpire();

                await axios
                  .post(
                    `/api/pay/validate`,
                    {
                      impUid: res.imp_uid, // 결제 요청한 결제 고유 번호
                      merchantUid: res.merchant_uid, // 결제 요청한 상품 고유 번호
                      amount: isEvent
                        ? Math.ceil(totalPrice * 0.7)
                        : totalPrice, // 결제 요청한 금액
                      idCoupon: idCoupon, // 각 상품당 상품id, 구매하는 개수, 쿠폰 사용 개수
                    },
                    {
                      headers: {
                        Authorization: "Bearer " + token.accessToken,
                      },
                    }
                  )
                  .then((res) => {
                    const resultData = res.data;
                    if (resultData.message) {
                      alert(
                        "에러가 생겼습니다. 다시 로그인해주세요." +
                          resultData.message
                      );
                      localStorage.removeItem("token");
                      window.location.replace("/");
                      return false;
                    }
                    if (resultData.failMassage) {
                      navigate("/pay/fail/", {
                        state: {
                          failMassage: resultData.failMassage,
                          pId: 0,
                        },
                      });
                    } else {
                      navigate("/pay/success/", {
                        state: resultData,
                      });
                    }
                  })
                  .catch((error) => {
                    alert(error.response);
                  });
              } else {
                alert("결제에 실패하였습니다. 에러 메시지 : " + res.error_msg);
              }
            }
          );
        })
        .catch((error) => {
          alert(error);
        });
    }
  };

  const getCart = async () => {
    const res = await axios.get(`/api/cart/list`, {
      headers: {
        Authorization: "Bearer " + token.accessToken,
      },
    });
    const data = res.data;

    setTotalPrice(data.totalPrice);
    if (data.list.length != 0) {
      // 장바구니에 물건이 있을 때
      setProducts(data.list);
      setCoupon(data.list[0].coupon);
      setTitle(
        data.list[0].product.title + " 외 " + (data.list.length - 1) + "개"
      ); // 처음에 제목 설정 -> 외 *개
      // 초기 가격 설정
      let idCoupon = [];
      data.list.map((li, idx) => {
        priceArr[idx] = li.product.price;
        idCoupon[idx] = {
          pId: li.product.id,
          count: li.count,
          coupon: li.useCoupon,
        };
      });
      setIdCoupon(idCoupon);
    }
  };

  useEffect(() => {
    if (products.length === 0) {
      getCart();
    }

    let now = new Date(); // 현재
    let day = now.getDay(); // 현재 요일 일요일부터 0~6
    if (day === 0 || day === 5 || day === 6) {
      // 금토일 이면
      setIsEvent(true);
    } else {
      setIsEvent(false);
    }

    const iamport = document.createElement("script");
    iamport.src = "https://cdn.iamport.kr/v1/iamport.js";
    document.head.appendChild(iamport);
    return () => {
      document.head.removeChild(iamport);
    };
  }, [products]);

  return (
    <div className="page">
      <LoginHeader token={token} ref={childRef} />
      <main>
        <div className="mypageTitle">
          {products.length != 0
            ? `나의 장바구니(남은 쿠폰 수 : ${coupon}개)`
            : "장바구니가 비어있습니다."}
        </div>
        <div className="mainContainer">
          {products &&
            products.map((product, idx) => (
              <Cart
                key={idx}
                cart={product}
                idx={idx}
                coupon={coupon}
                setTotal={setTotal}
                changeCoupon={changeCoupon}
                setInfo={setInfo}
                token={token}
              /> // getCoupon, setTotal 메소드 자식도 사용 가능
            ))}
        </div>
        <div className="totalPriceBox">
          <div className="totlePrice">
            총금액 : {isEvent ? Math.ceil(totalPrice * 0.7) : totalPrice}원
          </div>
          <button className="payBtn" onClick={() => payment()}>
            결제하기
          </button>
        </div>
      </main>
      <Footer />
    </div>
  );
}
export default MyCart;
