import React from "react";
import classNames from "classnames";
import axios from "axios";
import { useNavigate } from "react-router-dom";

const List = ({ list, idx, token, checkExpire }) => {
  const navigate = useNavigate();
  const {
    id,
    pId,
    productTitle,
    price,
    paymentDate,
    impUid,
    merchatnUid,
    link,
    cancel,
  } = list;

  const payCancel = async () => {
    if (!cancel) {
      // 취소하지 않았을 때
      if (window.confirm("정말 취소하시겠습니까?")) {
        checkExpire(); // 만료여부 확인

        await axios
          .put(
            "/pay/cancel",
            {
              id: id,
              impUid: impUid,
              merchatnUid: merchatnUid,
              amount: price,
            },
            {
              headers: {
                Authorization: "Bearer " + token.accessToken,
              },
            }
          )
          .then((res) => {
            const data = res.data;

            if (data.message) {
              alert(
                "토큰에 에러가 생겼습니다. 다시 로그인해주세요. 에러 메시지 : " +
                  data.message
              );
              localStorage.removeItem("token");
              window.location.replace("/");
              return false;
            }
            navigate("/pay/cancel", {
              state: data,
            });
          })
          .catch((error) => {
            alert(error);
          });
      }
    }
  };

  const detail = () => {
    navigate("/detail/" + pId);
  };

  return (
    <button className={classNames("list", "list" + { idx })}>
      <button
        className="mainImgBox"
        style={{
          background: `url(${link}) no-repeat center`,
          backgroundSize: "cover",
        }}
      ></button>
      <div className="mainInfoBox">
        <button className="myTitle" onClick={() => detail()}>
          {productTitle}
        </button>
        <div className="myDate">{new Date(paymentDate).toLocaleString()}</div>
        <div className="myPayBox">
          <div className="myPayPrice">{price}원</div>
          <button className="myPayCancel" onClick={() => payCancel()}>
            {cancel ? "취소 완료" : "결제 취소"}
          </button>
        </div>
      </div>
    </button>
  );
};

export default List;
