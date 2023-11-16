import React, { useState, useEffect } from "react";
import axios from "axios";
import "./HomePage.css";
import Product from "./Product";
import LogoutHeader from "./LogoutHeader";
import LoginHeader from "./LoginHeader";
import Footer from "./Footer";

function HomePage() {
  const [currentPage, setCurrentPage] = useState(1);
  const [pageInfo, setPageInfo] = useState([]);
  const [products, setProducts] = useState([]);
  const [pages, setPages] = useState([]);
  let token = JSON.parse(localStorage.getItem("token"));

  const goToPage = (page) => {
    setCurrentPage(page);
  };

  useEffect(() => {
    const getProduct = async () => {
      const res = await axios.get(`/product/list/${currentPage}`);
      const data = res.data;
      setProducts(data.list); // 응답 중에서 상품들 리스트
      setPageInfo(data.pageInfo); // 응답 중 페이지에 대한 정보

      const pageArr = [];
      // 현재 페이지의 시작페이지부터 마지막 페이지 까지 페이지 번호 넣기
      for (let i = pageInfo.startPage; i <= pageInfo.endPage; i++) {
        pageArr.push(i);
      }
      setPages(pageArr);
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

        <div className="pagination">
          {currentPage !== 1 && (
            <>
              <button
                className="firstPage"
                onClick={() => goToPage(1)}
              ></button>
              <button
                className="prevPage"
                onClick={() => goToPage(currentPage - 1)}
              ></button>
            </>
          )}

          {pages.map((page) => (
            <button
              key={page}
              onClick={() => goToPage(page)}
              className={currentPage === page ? "pageActive" : "pageBtn"}
            >
              {page}
            </button>
          ))}

          {currentPage !== pageInfo.maxPage && (
            <>
              <button
                className="nextPage"
                onClick={() => goToPage(currentPage + 1)}
              ></button>
              <button
                className="lastPage"
                onClick={() => goToPage(pageInfo.maxPage)}
              ></button>
            </>
          )}
        </div>
      </main>
      <Footer />
    </div>
  );
}
export default HomePage;
