import React from 'react';
import classNames from "classnames";

const Product = ({ product, idx }) => {

  return (
    <button className={classNames('list', 'list'+{idx})}>
      <button className="mainImgBox">
      </button>
      <div className="mainInfoBox">
        <div className="mainCategory">카테고리</div>
        <div className="mainTitle">상품명상품명상품명상품명상품명상품명상품명상품명상품명</div>
        <div className="mainPrice">100000원</div>
      </div>
    </button>
  );
};

export default Product;