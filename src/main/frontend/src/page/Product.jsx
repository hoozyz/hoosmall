import React from "react";
import classNames from "classnames";

const Product = ({ product, idx, goDetail }) => {
  const { id, category, link, title, price } = product;

  const detail = () => {
    goDetail(id);
  };

  return (
    <button className={classNames("list", "list" + { idx })}>
      <div
        className="mainImgBox"
        style={{ background: `url(${link})`, backgroundSize: "300px 300px" }}
      ></div>
      <div className="mainInfoBox">
        <div className="mainCategory">{category}</div>
        <button className="mainTitle" onClick={() => detail()}>
          {title}
        </button>
        <div className="mainPrice">{price}원</div>
      </div>
    </button>
  );
};

export default Product;
