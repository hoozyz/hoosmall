import React from "react";
import classNames from "classnames";
import { useNavigate } from "react-router-dom";

const Product = ({ product, idx }) => {
  const navigate = useNavigate();
  const { id, category, link, title, price } = product;

  const goDetail = () => {
    navigate("/detail/"+id);
  };

  return (
    <button className={classNames("list", "list" + { idx })}>
      <div
        className="mainImgBox"
        style={{
          background: `url(${link}) no-repeat center`,
          backgroundSize: "cover",
        }}
      ></div>
      <div className="mainInfoBox">
        <div className="mainCategory">{category}</div>
        <button className="mainTitle" onClick={() => goDetail()}>
          {title}
        </button>
        <div className="mainPrice">{price}원</div>
      </div>
    </button>
  );
};

export default Product;
