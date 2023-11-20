import React, { useState, useEffect, Fragment, useRef } from "react";
import uuid from "react-uuid";
import axios from "axios";
import { useNavigate, useParams } from "react-router-dom";
import LogoutHeader from "./LogoutHeader";
import LoginHeader from "./LoginHeader";
import Footer from "./Footer";

function DetailPage() {
  const { pId } = useParams(); // url/{pid} 처럼 변수 가져오기
  const navigate = useNavigate();
  const [isDrop, setDrop] = useState(false);
  const [product, setProduct] = useState([]);
  const [firstPrice, setFirstPrice] = useState(0);
  const [price, setPrice] = useState(0);
  const [coupon, setCoupon] = useState(0);
  const [isCoupon, setIsCoupon] = useState(false);
  const [isEvent, setIsEvent] = useState(false);

  const childRef = useRef();
  let token = null;
  if(localStorage.getItem("token")) {
    token = JSON.parse(localStorage.getItem("token"));
  }
  const couponDrop = () => {
    if (coupon) {
      // 쿠폰이 있을 때
      if (!isCoupon) {
        // 쿠폰을 사용하지 않았을 때
        setDrop((isDrop) => !isDrop); // 클릭마다 drop 여부
      }
    }
  };

  const couponCancelDrop = () => {
    setIsCoupon(!isCoupon); // 쿠폰 사용 취소
    setCoupon((coupon) => coupon + 1); // 쿠폰 1개 다시 되돌리기
    setPrice(firstPrice); // 가격 돌려놓기
  };

  const discount = () => {
    couponDrop();
    setPrice(Math.ceil(firstPrice * 0.85));
    setCoupon((coupon) => coupon - 1); // 쿠폰 1개 빼기
    setIsCoupon(!isCoupon);
  };

  const cart = async () => {
    // 장바구니에 넣고 끝 -> 6개면 못넣게 백엔드에서 보기
    if (token === null) {
      alert("장바구니는 회원만 이용할 수 있습니다.");
      return false;
    }

    childRef.current.checkExpire(); // 로그인 헤더의 토큰 만료 체크 함수

    await axios
      .post(
        `/cart/save/${pId}`,
        {}, // 데이터 없음 -> post는 데이터 같이 보내야함(없어도)
        {
          headers: {
            Authorization: "Bearer " + token.accessToken,
          },
        }
      )
      .then((res) => {
        const data = res.data;
        if (data.message) alert(data.message);
        else {
          alert("현재 상품을 장바구니에 넣었습니다.");
          window.location.replace("/");
        }
      })
      .catch((error) => {
        alert(error);
      });
  };

  // 결제
  const payment = async () => {
    if (token === null) {
      alert("결제는 회원만 가능합니다.");
      return false;
    }

    childRef.current.checkExpire();

    let email = "";
    await axios
      .get(`/user/me`, {
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

    var IMP = window.IMP;
    IMP.init("imp33422434");

    let now = new Date().getTime() + uuid();

    childRef.current.checkExpire();

    // 사전 검증요청 -> 바로 아래에 requestpay에서 실제로 요청할 때 사전요청과 맞나 확인하기 위해
    await axios
      .post(
        "/pay/preorder",
        {
          merchantUid: now,
          amount: isEvent ? Math.ceil(price * 0.7) : price,
        },
        {
          headers: {
            Authorization: "Bearer " + token.accessToken,
          },
        }
      )
      .then(async (res) => {
        const data = res.data;

        if (data.message != null) {
          // 에러가 생겼을 때
          alert(
            "에러가 생겼습니다. 다시 결제해주세요. 에러메시지 : " + data.message
          );
          window.location.reload();
          return false;
        }
        IMP.request_pay(
          {
            pg: "tosspayments", // 토스페이먼츠
            merchant_uid: now,
            name: product.title,
            pay_method: "card",
            amount: isEvent ? Math.ceil(price * 0.7) : price,
            buyer_name: "홍길동", // 이름 고정
            buyer_email: email,
          },
          async (res) => {
            // PG 사에서 응답
            if (!res.error_msg) {
              // 에러메시지가 없을 때 결제 완료

              childRef.current.checkExpire();

              await axios
                .post(
                  "/pay/validate",
                  {
                    impUid: res.imp_uid, // 결제 요청한 결제 고유 번호
                    merchantUid: res.merchant_uid, // 결제 요청한 상품 고유 번호
                    amount: isEvent ? Math.ceil(price * 0.7) : price, // 결제 요청한 금액
                    idCoupon: [
                      {
                        // 제품번호와 쿠폰 세트 -> 제품번호에 해당하는 원가에 쿠폰 개수만큼 빼면 결제가격
                        pId: pId,
                        count: 1, // 상세에서는 한개만 구매가능 -> 구매 개수
                        coupon: isCoupon ? 1 : 0, // 쿠폰을 썼으면 1개 안썼으면 0개
                      },
                    ],
                  },
                  {
                    headers: {
                      Authorization: "Bearer " + token.accessToken,
                    },
                  }
                )
                .then((res) => {
                  const resultData = res.data;
                  if (resultData.failMassage) {
                    navigate("/pay/fail/", {
                      state: {
                        failMassage: resultData.failMassage,
                        pId: pId,
                      },
                    });
                  } else {
                    navigate("/pay/success/", {
                      state: resultData,
                    });
                  }
                })
                .catch((error) => {
                  alert(error);
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
  };

  useEffect(() => {
    const getProduct = async () => {
      const res = await axios.get(`/product/detail/${pId}`);
      const data = res.data;
      setProduct(data);
      setFirstPrice(data.price);
      setPrice(data.price);

      if(token) {
        await axios
        .get("/user/coupon", {
          headers: {
            Authorization: "Bearer " + token.accessToken,
          },
        })
        .then((res) => {
          setCoupon(res.data);
        })
        .catch((error) => {
          alert(error);
        });
      }
    };

    getProduct();

    let now = new Date(); // 현재
    let day = now.getDay(); // 현재 요일 일요일부터 0~6
    if (day === 0 || day === 5 || day === 6) {
      // 금토일 이면
      setIsEvent(true);
      setPrice(Math.ceil(firstPrice * 0.7));
      setFirstPrice(Math.ceil(firstPrice * 0.7));
    } else {
      setIsEvent(false);
    }

    // jquery, iamport 추가
    const iamport = document.createElement("script");
    iamport.src = "https://cdn.iamport.kr/v1/iamport.js";
    document.head.appendChild(iamport);
    return () => {
      document.head.removeChild(iamport);
    };
  }, [pId]);

  return (
    <Fragment>
      <div className="page">
        {token === null ? (
          <LogoutHeader />
        ) : (
          <LoginHeader token={token} ref={childRef} />
        )}
        <main>
          <div className="detailBox">
            <div
              className="detailImgBox"
              style={{
                background: `url(${product.link}) no-repeat center`,
              }}
            ></div>
            <div className="detailInfoBox">
              <div className="detailTitle">{product.title}</div>
              <div className="detailCoupon">
                <button
                  className="detailCouponBtn"
                  onClick={
                    isCoupon ? () => couponCancelDrop() : () => couponDrop()
                  }
                >
                  {token === null
                    ? "쿠폰은 회원만 사용가능합니다."
                    : isCoupon
                    ? "쿠폰 사용 취소하기"
                    : coupon
                    ? `15% 할인 쿠폰 남은 개수 : ${coupon}개`
                    : "남은 쿠폰이 없습니다."}
                </button>
                <button className="detailCartBtn" onClick={() => cart()}>
                  장바구니 넣기
                </button>
                <div className="productCount">남은 수량: {product.stock}개</div>
                <div className={isDrop ? "dropCouponContent" : "hideContent"}>
                  <button onClick={() => discount()}>15% 할인 쿠폰</button>
                </div>
              </div>
              <div className="detailEvent">
                {isEvent
                  ? "금토일 이벤트 30% 할인 적용"
                  : "이벤트 요일이 아닙니다."}
              </div>
              <div className="detailPrice">
                {isEvent ? Math.ceil(price * 0.7) : price}원
              </div>
            </div>
          </div>
          <button type="" className="payBtn" onClick={() => payment()}>
            결제하기
          </button>
        </main>
        <Footer />
      </div>
    </Fragment>
  );
}

export default DetailPage;
