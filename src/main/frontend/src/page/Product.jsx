import React from "react";
import classNames from "classnames";
import { Navigate } from "react-router-dom";

const Product = ({ product, idx }) => {
  const pId = product.id;
  const goDetail = () => {
    Navigate("/detail/"+{pId});
  };

  return (
    <button className={classNames("list", "list" + { idx })}>
      <div className="mainImgBox"></div>
      <div className="mainInfoBox">
        <div className="mainCategory">카테고리</div>
        <button className="mainTitle" onClick={() => goDetail()}>
          상품명상품명상품명상품명상품명상품명상품명상품명상품명
        </button>
        <div className="mainPrice">100000원</div>
      </div>
    </button>
  );
};

export default Product;
