import React from "react";
import classNames from "classnames";
import axios from "axios";
import { useNavigate } from "react-router-dom";

const List = ({ list, idx }) => {
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
    if(!cancel) { // 취소하지 않았을 때
      if(window.confirm("정말 삭제하시겠습니까?")) {
        await axios
        .put("/pay/cancel", {
          id: id,
          impUid: impUid,
          merchatnUid: merchatnUid,
          amount: price,
        })
        .then((res) => {
          const data = res.data;
          navigate("/pay/cancel", {
            state: data,
          });
        })
        .catch((error) => {
          alert(error);
        })
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
            { cancel ? "취소 완료" : "결제 취소" }
          </button>
        </div>
      </div>
    </button>
  );
};

export default List;
