import React, { useState, useEffect } from "react";
import axios from "axios";
import "./HomePage.css";
import Product from "./Product";
import LogoutHeader from "./LogoutHeader";
import LoginHeader from "./LoginHeader";
import Footer from "./Footer";
import Pagination from "./Pagination";

function HomePage() {
  const [currentPage, setCurrentPage] = useState(1);
  const [pageInfo, setPageInfo] = useState([]);
  const [products, setProducts] = useState([]);
  let token = JSON.parse(localStorage.getItem("token"));

  const goToPage = (page) => {
    setCurrentPage(page);
  };

  useEffect(() => {
    const getProduct = async () => {
      const res = await axios.get(`/api/product/list/${currentPage}`);
      const data = res.data;
      setProducts(data.list); // 응답 중에서 상품들 리스트
      setPageInfo(data.pageInfo); // 응답 중 페이지에 대한 정보
    };

    getProduct();
  }, [currentPage]);

  return (
    <div className="page">
      {token === null ? <LogoutHeader /> : <LoginHeader token={token} />}
      <main>
        <div className="mainContainer">
          {products.map((product, idx) => (
            <Product key={product.id} product={product} idx={idx} />
          ))}
        </div>

        <Pagination
          currentPage={currentPage}
          start={pageInfo.startPage}
          end={pageInfo.endPage}
          max={pageInfo.maxPage}
          goToPage={goToPage}
        />
      </main>
      <Footer />
    </div>
  );
}
export default HomePage;
