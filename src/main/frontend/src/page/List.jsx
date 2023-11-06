import React from "react";
import classNames from "classnames";

const List = ({ list, idx }) => {

  const payCancel = () => {

  };

  return (
    <button className={classNames("list", "list" + { idx })}>
      <button className="mainImgBox">
      </button>
      <div className="mainInfoBox">
        <div className="myTitle">
          상품명상품명상품명상품명상품명상품명상품명상품명상품명
        </div>
        <div className="myDate">
          2023.11.05
        </div>
        <div className="myPayBox">
          <div className="myPayPrice">
            100000원
          </div>
          <button className="myPayCancel" onClick={() => payCancel()}>결제취소</button>
        </div>
      </div>
    </button>
  );
};

export default List;
