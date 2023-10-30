import React from 'react';
import classNames from "classnames";

const Product = ({ product, idx }) => {

  return (
    <button className={classNames('list', 'list'+{idx})}>
      <div className="mainImgBox">
        <img alt=""/>
      </div>
      <div className="mainInfoBox">
        <div className="mainCategory"><h2>카테고리</h2></div>
        <div className="mainTitle"><h3>ㅁㄴㅇㅁㄴㅇㅁㄴㅇㅁㄴ</h3></div>
        <div className="mainPrice"><h2>100000원</h2></div>
      </div>
    </button>
  );
};

export default Product;